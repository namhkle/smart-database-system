import org.postgresql.ds.PGSimpleDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class Jdbc {


    public Connection getReadConnection() throws SQLException{
        Connection connection = null;
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://192.168.0.11:5434/dvdrental",
                    "cloudteam", "password");
            connection.setAutoCommit(false);
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return connection;
    }

    public Connection getWriteConnection() throws SQLException{
        Connection connection = null;
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://192.168.0.11:5433/dvdrental",
                    "cloudteam", "password");
            connection.setAutoCommit(false);
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }
        return connection;
    }

}
