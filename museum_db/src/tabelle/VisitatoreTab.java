package tabelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import connection.DBConnection;
import entità.Visitatore;

public class VisitatoreTab {

    private DBConnection conn;
    private String tabName;

    public VisitatoreTab() {
        this.conn = new DBConnection();
        this.tabName = "visitatore";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                //statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FK_VISITATORI");
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//                //e.printStackTrace();
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "codFiscale CHAR(16) PRIMARY KEY, "
                    + "dataNascita DATE NOT NULL, "
                    + "nome VARCHAR(20) NOT NULL, "
                    + "cognome VARCHAR(20) NOT NULL, "
                    + "mail VARCHAR(40) NOT NULL UNIQUE)");
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
    
    public void insertInDB(Visitatore visitatore){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement ();
                statement.executeUpdate (
                        "INSERT INTO "+ this.tabName +" (codFiscale,nome,cognome,dataNascita,mail)"
                        +"VALUES ('" + visitatore.getCodFiscale() + "','" + visitatore.getNome() + "','" + visitatore.getCognome() + "','"
                                + visitatore.getDataNascita() + "','" + visitatore.getMail() + "')"
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
    
    public void deleteFromDB(String codFiscale) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE codFiscale = ?"
                + "AND oraInizio = ?";
        if(this.findByPrimaryKey(codFiscale).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, codFiscale);
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
    
    public Visitatore findByPrimaryKey(String codFiscale) {
        Visitatore visitatore = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + "WHERE codFiscale = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, codFiscale);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                visitatore = new Visitatore(result.getString("codFiscale"), result.getString("nome"), result.getString("cognome"),
                        result.getDate("dataNascita"), result.getString("mail"));
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
        return visitatore;
    }
    
    public List<Visitatore> getAllTable()  {
        List<Visitatore> visiteSingole = null;
        Visitatore visiteGruppo = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                visiteSingole = new LinkedList<Visitatore>();
                visiteGruppo = new Visitatore(result.getString("codFiscale"), result.getString("nome"), result.getString("cognome"),
                        result.getDate("dataNascita"), result.getString("mail"));
                visiteSingole.add(visiteGruppo);
            }
            while(result.next()) {
                visiteGruppo = new Visitatore(result.getString("codFiscale"), result.getString("nome"), result.getString("cognome"),
                        result.getDate("dataNascita"), result.getString("mail"));
                visiteSingole.add(visiteGruppo);}
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
        return visiteSingole;
    }
    
    public List<Visitatore> getAllTable(int codMuseo)  {
        List<Visitatore> visiteSingole = null;
        Visitatore visiteGruppo = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select distinct v.* from visitatore v, biglietto b where b.codMuseo = ? and b.codFiscale = v.codFiscale";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                visiteSingole = new LinkedList<Visitatore>();
                visiteGruppo = new Visitatore(result.getString("codFiscale"), result.getString("nome"), result.getString("cognome"),
                        result.getDate("dataNascita"), result.getString("mail"));
                visiteSingole.add(visiteGruppo);
            }
            while(result.next()) {
                visiteGruppo = new Visitatore(result.getString("codFiscale"), result.getString("nome"), result.getString("cognome"),
                        result.getDate("dataNascita"), result.getString("mail"));
                visiteSingole.add(visiteGruppo);}
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
        return visiteSingole;
    }

    public int getSpesaTotale(String codFiscale) {
        int spesaTotale = 0;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT SUM(prezzo) AS spesa FROM biglietto WHERE codFiscale = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, codFiscale);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                spesaTotale = result.getInt("spesa");
            }
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
        return spesaTotale;
    }
}
