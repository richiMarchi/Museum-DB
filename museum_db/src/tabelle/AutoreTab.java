package tabelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import connection.DBConnection;
import entità.Autore;

public class AutoreTab {

    private DBConnection conn;
    private String tabName;

    public AutoreTab() {
        this.conn = new DBConnection();
        this.tabName = "autore";
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
                    + "codAutore INT IDENTITY(201,1) PRIMARY KEY, "
                    + "nome_cognome VARCHAR(40) NOT NULL, "
                    + "pseudonimo VARCHAR(20))");
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

    public void insertInDB(Autore autore){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement ();
                statement.executeUpdate (
                    "INSERT INTO "+ this.tabName +" (nome_cognome)"
                    +"VALUES ('" + autore.getNome_cognome() + "')"
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
    
    public void deleteFromDB(int codAutore) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE codAutore = ?";
        if(this.findByPrimaryKey(codAutore).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codAutore);
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

    public Autore findByPrimaryKey(int codAutore) {
        Autore autore = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codAutore = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codAutore);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                autore = new Autore(result.getInt("codAutore"), result.getString("nome_cognome"), result.getString("pseudonimo"));
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
        return autore;
    }
    
    public List<Autore> getAllTable()  {
        List<Autore> autori = null;
        Autore autore = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                autori = new LinkedList<Autore>();
                autore = new Autore(result.getInt("codAutore"), result.getString("nome_cognome"), result.getString("pseudonimo"));
                autori.add(autore);
            }
            while(result.next()) {
                autore = new Autore(result.getInt("codAutore"), result.getString("nome_cognome"), result.getString("pseudonimo"));
                autori.add(autore);}
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
        return autori;
    }
}
