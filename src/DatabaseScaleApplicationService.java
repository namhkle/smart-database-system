import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseScaleApplicationService {
    private QueryService queryService;
    private DatabaseNodeService dbNodeService;
    private ModelPredictor predictor;

    public DatabaseScaleApplicationService() {
        queryService = new QueryService();
        dbNodeService = new DatabaseNodeService();
        predictor = new ModelPredictor();
        dbNodeService.setNodes();
    }


    public void displayCommands() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int input;
        boolean showCommands = true;
        predictor.getPredictionStats();
        predictor.makePredictions();
        try {
            while (showCommands) {
                System.out.println("Choose an option: \n1. Show server state \n2. Turn off node " +
                        "\n3. Turn on node \n4. Execute Read Queries \n5. Execute Write Queries \n" +
                        "6. Execute Mix of Queries \n7. Exit");
                input = sc.nextInt();
                if (input == 1) {
//                    dbNodeService.showServerState();
                    dbNodeService.getNodeStates();
                } else if (input == 2) {
                    dbNodeService.turnOffNode();
                } else if (input == 3) {
                    dbNodeService.turnOnNode();
                } else if (input == 4) {
                    System.out.println("How many execute queries should be made?");
                    input = sc.nextInt();
                    queryService.executeReadQueries(input);
                } else if (input == 5) {
                    System.out.println("How many execute queries should be made?");
                    input = sc.nextInt();
                    queryService.executeWriteQueries(input);
                } else if (input == 6) {
                    System.out.println("How many execute queries should be made?");
                    input = sc.nextInt();
                    queryService.executeMixQueries(input);
                } else if (input == 7) {
                    showCommands = false;
                    System.out.println("Program closed");
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
    }
}
