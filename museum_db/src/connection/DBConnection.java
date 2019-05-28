package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

private static final String DB_NAME = "museum";
    
    
    public Connection getMsSQLConnection()  {
         
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbUri = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME;
        String userName = "sa";
        String password = "ciccio";
         
        Connection connection = null;
         try {
             Class.forName(driver);
             connection = DriverManager.getConnection(dbUri, userName, password);
         }
         catch (ClassNotFoundException e) {
             new Exception(e.getMessage());
             System.out.println("ErroreCNF"+ e.getMessage());
         }
         catch(SQLException e) {
             new Exception(e.getMessage());
             System.out.println("ErroreSQL"+ e.getMessage());
         }
         return connection;
     }
}
