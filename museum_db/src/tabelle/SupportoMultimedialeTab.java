package tabelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import connection.DBConnection;
import entità.SupportoMultimediale;

public class SupportoMultimedialeTab {

    private DBConnection conn;
    private String tabName;

    public SupportoMultimedialeTab() {
        this.conn = new DBConnection();
        this.tabName = "supportoMultimediale";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FKSUPPORTOMULTIMEDIALE_MUSEO");
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "codStrumento INT NOT NULL, "
                    + "codMuseo INT NOT NULL, "
                    + "CONSTRAINT PK_SUPPORTOMULTIMEDIALE PRIMARY KEY (codStrumento, codMuseo))");
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
    
    public void insertInDB(SupportoMultimediale supportoMultimediale){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement ();
                int nextCod = selectLastCod(supportoMultimediale.getCodMuseo()) + 1;
                statement.executeUpdate (
                        "INSERT INTO "+ this.tabName +" (codStrumento,codMuseo)"
                        +"VALUES ('" + nextCod + "','" + supportoMultimediale.getCodMuseo() + "')"
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
    
    public void deleteFromDB(int codStrumento, int codMuseo) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE codStrumento = ? AND codMuseo = ?";
        if(this.findByPrimaryKey(codStrumento, codMuseo).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codStrumento);
            statement.setInt(2, codMuseo);
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
    
    public SupportoMultimediale findByPrimaryKey(int codStrumento, int codMuseo) {
        SupportoMultimediale supportoMultimediale = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codStrumento = ? AND codMuseo = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codStrumento);
            statement.setInt(2, codMuseo);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                supportoMultimediale = new SupportoMultimediale(result.getInt("codStrumento"), result.getInt("codMuseo"));
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
        return supportoMultimediale;
    }
    
    public List<SupportoMultimediale> getAllTable()  {
        List<SupportoMultimediale> supportiMultimediali = null;
        SupportoMultimediale supportoMultimediale = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                supportiMultimediali = new LinkedList<SupportoMultimediale>();
                supportoMultimediale = new SupportoMultimediale(result.getInt("codStrumento"), result.getInt("codMuseo"));
                supportiMultimediali.add(supportoMultimediale);
            }
            while(result.next()) {
                supportoMultimediale = new SupportoMultimediale(result.getInt("codStrumento"), result.getInt("codMuseo"));
                supportiMultimediali.add(supportoMultimediale);}
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
        return supportiMultimediali;
    }
    
    public int selectLastCod(int codMuseo) {
        int lastCod = 500;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT TOP 1 codStrumento FROM " + this.tabName + " WHERE codMuseo = ? ORDER BY codStrumento DESC";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                lastCod = result.getInt("codStrumento");
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
        return lastCod;
    }

    public List<SupportoMultimediale> getAllTable(int codMuseo) {
        List<SupportoMultimediale> supportiMultimediali = null;
        SupportoMultimediale supportoMultimediale = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName + " where codMuseo = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                supportiMultimediali = new LinkedList<SupportoMultimediale>();
                supportoMultimediale = new SupportoMultimediale(result.getInt("codStrumento"), result.getInt("codMuseo"));
                supportiMultimediali.add(supportoMultimediale);
            }
            while(result.next()) {
                supportoMultimediale = new SupportoMultimediale(result.getInt("codStrumento"), result.getInt("codMuseo"));
                supportiMultimediali.add(supportoMultimediale);}
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
        return supportiMultimediali;
    }
}
