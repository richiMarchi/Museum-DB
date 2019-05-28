package tabelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import connection.DBConnection;
import entità.Opera;

public class OperaTab {

    private DBConnection conn;
    private String tabName;

    public OperaTab() {
        this.conn = new DBConnection();
        this.tabName = "opera";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FKOPERA_AUTORE");
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "codAutore INT NOT NULL, "
                    + "anno INT NOT NULL, "
                    + "nomeOpera VARCHAR(20) NOT NULL, "
                    + "nomeTipologia VARCHAR(20) NOT NULL, "
                    + "CONSTRAINT PK_OPERA PRIMARY KEY (codAutore, nomeOpera))");
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

    public void insertInDB(Opera opera){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement ();
                statement.executeUpdate (
                        "INSERT INTO "+ this.tabName +" (codAutore,nomeTipologia,nomeOpera,anno)"
                        +"VALUES ('" + opera.getCodAutore() + "','" + opera.getNomeTipologia() + "','" + opera.getNomeOpera()
                        + "','" + opera.getAnno() + "')"
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
    
    public void deleteFromDB(int codAutore, String nomeOpera) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE codAutore = ? AND nomeOpera = ?";
        if(this.findByPrimaryKey(codAutore, nomeOpera).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codAutore);
            statement.setString(2, nomeOpera);
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
    
    public Opera findByPrimaryKey(int codAutore, String nomeOpera) {
        Opera opera = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codAutore = ? AND nomeOpera = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codAutore);
            statement.setString(2, nomeOpera);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                opera = new Opera(result.getInt("codAutore"), result.getString("nomeTipologia"), result.getString("nomeOpera"), result.getInt("anno"));
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
        return opera;
    }
    
    public List<Opera> getAllTable()  {
        List<Opera> opere = null;
        Opera opera = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                opere = new LinkedList<Opera>();
                opera = new Opera(result.getInt("codAutore"), result.getString("nomeTipologia"), result.getString("nomeOpera"), result.getInt("anno"));
                opere.add(opera);
            }
            while(result.next()) {
                opera = new Opera(result.getInt("codAutore"), result.getString("nomeTipologia"), result.getString("nomeOpera"), result.getInt("anno"));
                opere.add(opera);}
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
        return opere;
    }
    
    public List<Opera> getAllTable(int codMuseo)  {
        List<Opera> opere = null;
        Opera opera = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from opera o, permanenza p where codMuseo = ? and p.nomeOpera = o.nomeOpera and p.codAutore = o.codAutore "
                + "and p.dataRimozione IS NULL";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                opere = new LinkedList<Opera>();
                opera = new Opera(result.getInt("codAutore"), result.getString("nomeTipologia"), result.getString("nomeOpera"), result.getInt("anno"));
                opere.add(opera);
            }
            while(result.next()) {
                opera = new Opera(result.getInt("codAutore"), result.getString("nomeTipologia"), result.getString("nomeOpera"), result.getInt("anno"));
                opere.add(opera);}
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
        return opere;
    }
}
