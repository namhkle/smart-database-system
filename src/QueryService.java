import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class QueryService {
    private Jdbc jdbc;
    private Queries queries;

    public QueryService() {
        jdbc = new Jdbc();
        queries = new Queries();
    }


    public ResultSet makeQuery(String query, Connection connection) throws SQLException {
        ResultSet rs = null;
        try{
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query);
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return rs;
    }

    public void makeUpdate(String query, Connection connection) throws SQLException {
        ResultSet rs = null;
        try{
            System.out.println(query);
            Statement statement = connection.createStatement();
            int res = statement.executeUpdate(query);
            System.out.println(res);
            connection.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
    }

    public void executeReadQueries(int queryCount) throws SQLException {
        try {
            for(int i = 0; i < queryCount; i++){
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
    }

    public void executeWriteQueries(int queryCount) throws SQLException {
        try {
            for(int i = 0; i < queryCount; i++){
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
    }

    public void executeMixQueries(int queryCount) throws SQLException {
        try {
            for(int i = 0; i < queryCount; i++){
                Random rand = new Random();

                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
                makeQuery(queries.getReadQuery(), jdbc.getReadConnection());
                makeUpdate(queries.getWriteQuery(), jdbc.getWriteConnection());
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
    }

}
