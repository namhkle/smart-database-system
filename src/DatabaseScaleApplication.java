import java.sql.SQLException;

public class DatabaseScaleApplication {

    public static void main (String[] args) throws SQLException{
        DatabaseScaleApplicationService dbsaService = new DatabaseScaleApplicationService();
        dbsaService.displayCommands();
        System.out.println("GoodBye!");
    }
}
