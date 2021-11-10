public class DatabaseNode {
    public enum OpState {STOPPED, STARTING, RUNNING, STOPPING};
    public enum AdminState {ACTIVE, DRAIN};
    private String connectionName;
    private String serverName;
    private OpState opState;
    private AdminState adminState;

    public DatabaseNode(String connectionName, String serverName, String opState, String adminState) {
        this.connectionName = connectionName;
        this.serverName = serverName;
        this.opState = mapToOpState(opState);
        this.adminState = mapToAdminState(adminState);
    }
    private OpState mapToOpState(String s){
        switch(s){
            case "0":
                return OpState.STOPPED;
            case "1":
                return OpState.STARTING;
            case "2":
                return OpState.RUNNING;
            case "3":
                return OpState.STOPPING;
            default:
                break;
        }
        return OpState.STOPPED;
    }

    public OpState getOpState() {
        return opState;
    }

    public void setOpState(OpState opState) {
        this.opState = opState;
    }

    public AdminState getAdminState() {
        return adminState;
    }

    public void setAdminState(AdminState adminState) {
        this.adminState = adminState;
    }

    private AdminState mapToAdminState(String s){
        switch(s){
            case "0":
                return AdminState.ACTIVE;
            case "8":
                return AdminState.DRAIN;
            default:
                break;
        }
        return AdminState.ACTIVE;
    }

    public String getConnectionName() { return connectionName; }

    public void setConnectionName(String connectionName) { this.connectionName = connectionName; }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String name) {
        this.serverName = serverName;
    }


}
