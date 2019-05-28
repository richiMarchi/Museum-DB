package tabelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import connection.DBConnection;
import entità.Sezione;

public class SezioneTab {

    private DBConnection conn;
    private String tabName;

    public SezioneTab() {
        this.conn = new DBConnection();
        this.tabName = "sezione";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FKSEZIONE_MUSEO");
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "codMuseo INT NOT NULL, "
                    + "piano INT NOT NULL, "
                    + "nomeSezione VARCHAR(20) NOT NULL, "
                    + "CONSTRAINT PK_SEZIONE PRIMARY KEY (codMuseo, nomeSezione))");
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

    public void insertInDB(Sezione sezione){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement ();
                statement.executeUpdate (
                        "INSERT INTO "+ this.tabName +" (codMuseo,nomeSezione,piano)"
                        +"VALUES ('" + sezione.getCodMuseo() + "','" + sezione.getNomeSezione() + "','" + sezione.getPiano() + "')"
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
    
    public void deleteFromDB(int codMuseo, String nomeSezione) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE codMuseo = ? AND nomeSezione = ?";
        if(this.findByPrimaryKey(codMuseo, nomeSezione).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setString(2, nomeSezione);
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
    
    public Sezione findByPrimaryKey(int codMuseo, String nomeSezione) {
        Sezione sezione = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codMuseo = ? AND nomeSezione = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setString(2, nomeSezione);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                sezione = new Sezione(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("piano"));
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
        return sezione;
    }
    
    public List<Sezione> getAllTable()  {
        List<Sezione> sezioni = null;
        Sezione sezione = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                sezioni = new LinkedList<Sezione>();
                sezione = new Sezione(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("piano"));
                sezioni.add(sezione);
            }
            while(result.next()) {
                sezione = new Sezione(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("piano"));
                sezioni.add(sezione);}
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
        return sezioni;
    }

    public List<Sezione> getAllTable(int codMuseo) {
        List<Sezione> sezioni = null;
        Sezione sezione = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName + " where codMuseo = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                sezioni = new LinkedList<Sezione>();
                sezione = new Sezione(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("piano"));
                sezioni.add(sezione);
            }
            while(result.next()) {
                sezione = new Sezione(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("piano"));
                sezioni.add(sezione);}
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
        return sezioni;
    }
}
