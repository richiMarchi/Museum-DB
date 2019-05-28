package tabelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import connection.DBConnection;
import entità.Tipologia;

public class TipologiaTab {

    private DBConnection conn;
    private String tabName;

    public TipologiaTab() {
        this.conn = new DBConnection();
        this.tabName = "tipologia";
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
                    + "nomeTipologia VARCHAR(20) PRIMARY KEY)");
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
    
    public void insertInDB(Tipologia tipologia){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement ();
                statement.executeUpdate (
                        "INSERT INTO "+ this.tabName +" (nomeTipologia)"
                        +"VALUES ('" + tipologia.getNomeTipologia() + "')"
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
    
    public void deleteFromDB(String nomeTipologia) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE nomeTipologia = ?";
        if(this.findByPrimaryKey(nomeTipologia).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, nomeTipologia);
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
    
    public Tipologia findByPrimaryKey(String nomeTipologia) {
        Tipologia tipologia = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE nomeTipologia = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, nomeTipologia);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                tipologia = new Tipologia(result.getString("nomeTipologia"));
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
        return tipologia;
    }
    
    public List<Tipologia> getAllTable()  {
        List<Tipologia> tipologie = null;
        Tipologia tipologia = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                tipologie = new LinkedList<Tipologia>();
                tipologia = new Tipologia(result.getString("nomeTipologia"));
                tipologie.add(tipologia);
            }
            while(result.next()) {
                tipologia = new Tipologia(result.getString("nomeTipologia"));
                tipologie.add(tipologia);}
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
        return tipologie;
    }
}
