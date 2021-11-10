import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class DatabaseNodeService {
    private static final String WRITE_NODE = "haproxy_writes";
    private static final String READ_NODE = "haproxy_reads";
    private Stack<DatabaseNode> activeWriteNodes;
    private Stack<DatabaseNode> inactiveWriteNodes;
    private Stack<DatabaseNode> activeReadNodes;
    private Stack<DatabaseNode> inactiveReadNodes;

    public DatabaseNodeService() {
        activeWriteNodes = new Stack<>();
        inactiveWriteNodes = new Stack<>();
        activeReadNodes = new Stack<>();
        inactiveReadNodes = new Stack<>();
    }

    public void showServerState(){
        Runtime run = Runtime.getRuntime();
        Process p = null;

        String cmd = "echo \"show servers state\" | sudo socat stdio /var/run/haproxy.socket";
        String[] cmds = {"/bin/bash", "-c", cmd};

        try{
            p = run.exec(cmds);
            p.getErrorStream();
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        }
        catch(IOException | InterruptedException e){
            e.printStackTrace();
            System.out.println("ERROR.RUNNING.SHOW SERVER STATE");
        }
        finally{
            p.destroy();
        }
    }
    //Show server state provides us information about the nodes connected to the load balancer for reading and writing
    //The following are the column indices for data we are using
    //[1] connection name, [3] server name, [5] db node operational state (0 = stopped, 1 = starting, 2 = running, 3 = stopping)
    // [6] db nodes administrative state (0 = active, 8 = drain)
    public void setNodes() {
        Runtime run = Runtime.getRuntime();
        Process p = null;
        String cmd = "echo \"show servers state\" | sudo socat stdio /var/run/haproxy.socket";
        String[] cmds = {"/bin/bash", "-c", cmd};

        try{
            p = run.exec(cmds);
            p.getErrorStream();
            p.waitFor();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while((line = buffer.readLine()) != null ) {
                if (line.length() != 0){
                    if (line.charAt(0) != '#' && line.charAt(0) != '1') {
                        String[] vals = line.split(" ");
                        if(vals[1].equals(WRITE_NODE)){
                            DatabaseNode node = new DatabaseNode(vals[1], vals[3], vals[5], vals[6]);
                            if (node.getAdminState() == DatabaseNode.AdminState.DRAIN || node.getOpState() == DatabaseNode.OpState.STOPPED || node.getOpState() ==
                            DatabaseNode.OpState.STARTING || node.getOpState() == DatabaseNode.OpState.STOPPING){
                                inactiveWriteNodes.push(node);
                            }
                            else{
                                activeWriteNodes.push(node);
                            }
                        }
                        else if (vals[1].equals(READ_NODE)){
                            DatabaseNode node = new DatabaseNode(vals[1], vals[3], vals[5], vals[6]);
                            if (node.getAdminState() == DatabaseNode.AdminState.DRAIN || node.getOpState() == DatabaseNode.OpState.STOPPED || node.getOpState() ==
                                    DatabaseNode.OpState.STARTING || node.getOpState() == DatabaseNode.OpState.STOPPING){
                                inactiveReadNodes.push(node);
                            }
                            else{
                                activeReadNodes.push(node);
                            }
                        }
                    }
                }
            }
        }
        catch(IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error setting db nodes");
        }
        finally {
            p.destroy();
        }
    }

    public void getNodeStates(){
        Iterator activeWrites = activeWriteNodes.iterator();
        Iterator inactiveWrites = inactiveWriteNodes.iterator();
        Iterator activeReads = activeReadNodes.iterator();
        Iterator inactiveReads = inactiveReadNodes.iterator();
        System.out.println("States for all active write nodes");
        while(activeWrites.hasNext()){
            DatabaseNode node = (DatabaseNode) activeWrites.next();
            System.out.println("Connection name: " + node.getConnectionName() + "\tServer name: " +
                    node.getServerName() + "\tOperational State: " + node.getOpState() + "\tAdministrative State: "
            + node.getAdminState());
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("States for all inactive write nodes");
        while(inactiveWrites.hasNext()){
            DatabaseNode node = (DatabaseNode) inactiveWrites.next();
            System.out.println("Connection name: " + node.getConnectionName() + "\tServer name: " +
                    node.getServerName() + "\tOperational State: " + node.getOpState() + "\tAdministrative State: "
                    + node.getAdminState());
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("States for all active read nodes");
        while(activeReads.hasNext()){
            DatabaseNode node = (DatabaseNode) activeReads.next();
            System.out.println("Connection name: " + node.getConnectionName() + "\tServer name: " +
                    node.getServerName() + "\tOperational State: " + node.getOpState() + "\tAdministrative State: "
                    + node.getAdminState());
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("States for all inactive read nodes");
        while(inactiveReads.hasNext()){
            DatabaseNode node = (DatabaseNode) inactiveReads.next();
            System.out.println("Connection name: " + node.getConnectionName() + "\tServer name: " +
                    node.getServerName() + "\tOperational State: " + node.getOpState() + "\tAdministrative State: "
                    + node.getAdminState());
        }
    }


    public void getMemoryUsage(){
        //command:  sar -r 1 5
    }


    public void addInactiveReadNode(DatabaseNode node){
        inactiveReadNodes.push(node);
    }

    public DatabaseNode getInactiveReadNode(){
        if (inactiveReadNodes.empty()){
            System.out.println("No more database nodes to activate");
            return null;
        }
        Stack<DatabaseNode> tmpNodes = (Stack<DatabaseNode>)inactiveReadNodes.clone();
        while(tmpNodes.empty() == false && tmpNodes.peek().getOpState() != DatabaseNode.OpState.RUNNING){
            tmpNodes.pop();
        }
        if(tmpNodes.empty()){
            System.out.println("No operational database nodes to activate");
            return null;
        }
        inactiveReadNodes.remove(tmpNodes.size()-1);
        return tmpNodes.pop();
    }

    public void addActiveReadNode(DatabaseNode node){
        activeReadNodes.push(node);
    }

    public DatabaseNode getActiveReadNode(){
        if (activeReadNodes.empty()){
            System.out.println("There are no active database nodes");
            return null;
        }
        else if (activeReadNodes.size() == 1){
            System.out.println("Must have at least one active node");
            return null;
        }
        return activeReadNodes.pop();
    }

    public void turnOffNode(){
        Runtime run = Runtime.getRuntime();
        Process p = null;
        DatabaseNode node = getActiveReadNode();
        if (node == null){
            System.out.println("Cannot deactivate node, must have at least one active node for database queries");
        }
        else {
            String cmd = "echo 'set server " + node.getConnectionName() + "/" + node.getServerName() + " state drain'"
                    + " | sudo socat stdio /var/run/haproxy.socket";
            String[] cmds = {"/bin/bash", "-c", cmd};
            try {
                p = run.exec(cmds);
                p.getErrorStream();
                p.waitFor();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                String line = "";
//                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                }
                node.setAdminState(DatabaseNode.AdminState.DRAIN);
                addInactiveReadNode(node);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                System.out.println("ERROR.RUNNING.SET SERVER DRAIN");
            } finally {
                p.destroy();
            }
        }
    }

    public void turnOnNode(){
        Runtime run = Runtime.getRuntime();
        Process p = null;

        DatabaseNode node = getInactiveReadNode();
        if(node == null){
            System.out.println("No database node to activate");
        }
        else {
            String cmd = "echo 'set server " + node.getConnectionName().trim() + "/" + node.getServerName().trim() + " state ready'" +
                    " | sudo socat stdio /var/run/haproxy.socket";
            String[] cmds = {"/bin/bash", "-c", cmd};

            try {
                p = run.exec(cmds);
                p.getErrorStream();
                p.waitFor();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                String line = "";
//                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                }
                node.setAdminState(DatabaseNode.AdminState.ACTIVE);
                addActiveReadNode(node);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                System.out.println("ERROR.RUNNING.SET SERVER ACTIVE");
            } finally {
                p.destroy();
            }
        }
    }
}
