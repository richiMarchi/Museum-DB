package tabelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import connection.DBConnection;
import entità.Museo;

public class MuseoTab {

    private DBConnection conn;
    private String tabName;

    public MuseoTab() {
        this.conn = new DBConnection();
        this.tabName = "museo";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                //statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FK_AUTORI");
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "codMuseo INT IDENTITY(101,1) PRIMARY KEY, "
                    + "nomeMuseo VARCHAR(20) NOT NULL, "
                    + "città VARCHAR(20) NOT NULL, "
                    + "via_piazza VARCHAR(20) NOT NULL)");
            statement.close();
        } catch (SQLException e) {
            System.out.println("Some problems executing statement.");
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Something went wrong with statement or connection closure.");
            }
        }
    }

    public void insertInDB(Museo museo){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement ();
                statement.executeUpdate (
                        "INSERT INTO "+ this.tabName +" (nomeMuseo,città,via_piazza)"
                        +"VALUES ('" + museo.getNomeMuseo() + "','" + museo.getCittà() + "','" + museo.getVia_piazza() + "')"
                    );
                statement.close ();
            }
            catch (SQLException e) {
                 new Exception(e.getMessage());
                 System.out.println("Errore"+ e.getMessage());
                 new IllegalArgumentException();
            }
            finally {
                try {
                    if (statement != null) 
                        statement.close();
                    if (connection!= null)
                        connection.close();
                }
                catch (SQLException e) {
                         new Exception(e.getMessage());
                     System.out.println("Errore"+ e.getMessage());
                }
            }
   
    }
    
    public void deleteFromDB(int codMuseo) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE codMuseo = ?";
        if(this.findByPrimaryKey(codMuseo).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) 
                    statement.close();
                if (connection!= null)
                    connection.close();
            } catch (SQLException e) {
                new Exception(e.getMessage());
                     System.out.println("Errore"+ e.getMessage());
            }
        }
    }
    
    public Museo findByPrimaryKey(int codMuseo) {
        Museo museo = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codMuseo = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                museo = new Museo(result.getInt("codMuseo"), result.getString("nomeMuseo"), result.getString("città"), result.getString("via_piazza"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) 
                    statement.close();
                if (connection!= null)
                    connection.close();
            } catch (SQLException e) {
                new Exception(e.getMessage());
                     System.out.println("Errore"+ e.getMessage());
            }
        }
        return museo;
    }
    
    public List<Museo> getAllTable()  {
        List<Museo> musei = null;
        Museo museo = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                musei = new LinkedList<Museo>();
                museo = new Museo(result.getInt("codMuseo"), result.getString("nomeMuseo"), result.getString("città"), result.getString("via_piazza"));
                musei.add(museo);
            }
            while(result.next()) {
                museo = new Museo(result.getInt("codMuseo"), result.getString("nomeMuseo"), result.getString("città"), result.getString("via_piazza"));
                musei.add(museo);}
        } catch (SQLException e) {
            new Exception(e.getMessage());
            System.out.println("Errore"+ e.getMessage());
        }
        finally {
            try {
                if (statement != null) 
                    statement.close();
                if (connection!= null)
                    connection.close();
            } catch (SQLException e) {
                new Exception(e.getMessage());
                System.out.println("Errore"+ e.getMessage());
            }
        }
        return musei;
    }
}
