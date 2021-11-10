import org.tensorflow.SavedModelBundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class ModelPredictor {
    private final int TIME_INTERVAL = 5000;
    private ArrayList<Double> cpuUsage;
    private ArrayList<Double> memoryUsage;
    private final int NUM_PREDICTIONS = 1;

    public ModelPredictor(){
        cpuUsage = new ArrayList<>();
        memoryUsage = new ArrayList<>();
    }

    private void getCpuUsage(){
//        command: sar -u 1 5
        Runnable runnable = () -> {
            Runtime run = Runtime.getRuntime();
            Process p = null;
            String cmd = "sudo sar -u 1 5 | awk 'NR==8 {print $9}'";
            String[] cmds = {"/bin/bash", "-c", cmd};
            try {
                while(true) {
                    p = run.exec(cmds);
                    p.waitFor();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = "";
                    Double avg = 0.0;
                    while ((line = reader.readLine()) != null) {
                        avg = Math.round((100 - Double.parseDouble(line)) * 100.0) / 100.0;
                    }
                    cpuUsage.add(avg);
                    Thread.sleep(TIME_INTERVAL);
                }
            }
            catch(IOException | InterruptedException e){
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println(e.getCause());
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void viewCpuUsageAll(){
        Iterator iterator = cpuUsage.iterator();
        Double avg = 0.0;
        while(iterator.hasNext()){
            Double cpu = (Double) iterator.next();
            System.out.println(cpu);
            avg += cpu;
        }
        avg = Math.round((avg/cpuUsage.size()) * 100.0) / 100.0;
        System.out.println("average: " + avg.toString());
    }

    private void getMemoryUsage(){
        Runnable runnable = () -> {
            Runtime run = Runtime.getRuntime();
            Process p = null;
            String cmd = "sudo sar -r 1 5 | awk 'NR==8 {print $6}'";
            String[] cmds = {"/bin/bash", "-c", cmd};
            while(true) {
                try {
                    p = run.exec(cmds);
                    p.waitFor();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = "";
                    Double avg = 0.0;
                    while ((line = reader.readLine()) != null) {
                        avg = Math.round((Double.parseDouble(line) * 100.0)) / 100.0;
                    }
                    memoryUsage.add(avg);
                    Thread.sleep(TIME_INTERVAL);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void getPredictionStats(){
        getCpuUsage();
        getMemoryUsage();
    }

    private void resetPredictionStats(){
        cpuUsage = new ArrayList<>();
        memoryUsage = new ArrayList<>();
    }

    private SavedModelBundle getModel(){
        return SavedModelBundle.load("model.joblib");
    }

    public void makePredictions(){
        SavedModelBundle bundle = getModel();

    }
}
