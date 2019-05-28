package tabelle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import connection.DBConnection;
import entità.Biglietto;

public class BigliettoTab {

    private DBConnection conn;
    private String tabName;

    public BigliettoTab() {
        this.conn = new DBConnection();
        this.tabName = "biglietto";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FKBIGLIETTO_MUSEO");
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//                //e.printStackTrace();
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "codMuseo INT NOT NULL, "
                    + "codFiscale CHAR(16) NOT NULL, "
                    + "codBiglietto INT NOT NULL, "
                    + "codGruppo INT DEFAULT NULL, "
                    + "dataAcquisto DATE NOT NULL, "
                    + "prezzo INT NOT NULL, "
                    + "CONSTRAINT PK_BIGLIETTO PRIMARY KEY (codMuseo, codBiglietto))");
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

    public void insertInDB(Biglietto biglietto){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement ();
                int nextCod = selectLastCod(biglietto.getCodMuseo()) + 1;
                statement.executeUpdate (
                        "INSERT INTO "+ this.tabName +" (codMuseo,codFiscale,codBiglietto,dataAcquisto,prezzo)"
                        +"VALUES ('" + biglietto.getCodMuseo() + "','" + biglietto.getCodFiscale()
                        + "','" + nextCod + "','" + biglietto.getData() + "','" + biglietto.getPrezzo() + "')"
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
    
    public void deleteFromDB(int codMuseo, int codBiglietto) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE codMuseo = ? AND codBiglietto = ?";
        if(this.findByPrimaryKey(codMuseo, codBiglietto).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setInt(2, codBiglietto);
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
    
    public Biglietto findByPrimaryKey(int codMuseo, int codBiglietto) {
        Biglietto biglietto = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codMuseo = ? AND codBiglietto = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setInt(2, codBiglietto);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                biglietto = new Biglietto(result.getInt("codMuseo"), result.getString("codFiscale"), result.getInt("codBiglietto"),
                        result.getInt("codGruppo"), result.getDate("dataAcquisto"), result.getInt("prezzo"));
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
        return biglietto;
    }

    public List<Biglietto> getAllTable()  {
        List<Biglietto> biglietti = null;
        Biglietto biglietto = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                biglietti = new LinkedList<Biglietto>();
                biglietto = new Biglietto(result.getInt("codMuseo"), result.getString("codFiscale"), result.getInt("codBiglietto"),
                        result.getInt("codGruppo"), result.getDate("dataAcquisto"), result.getInt("prezzo"));
                biglietti.add(biglietto);
            }
            while(result.next()) {
                biglietto = new Biglietto(result.getInt("codMuseo"), result.getString("codFiscale"), result.getInt("codBiglietto"),
                        result.getInt("codGruppo"), result.getDate("dataAcquisto"), result.getInt("prezzo"));
                biglietti.add(biglietto);}
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
        return biglietti;
    }
    
    public List<Biglietto> getAllTable(int codMuseo)  {
        List<Biglietto> biglietti = null;
        Biglietto biglietto = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName + " where codMuseo = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                biglietti = new LinkedList<Biglietto>();
                biglietto = new Biglietto(result.getInt("codMuseo"), result.getString("codFiscale"), result.getInt("codBiglietto"),
                        result.getInt("codGruppo"), result.getDate("dataAcquisto"), result.getInt("prezzo"));
                biglietti.add(biglietto);
            }
            while(result.next()) {
                biglietto = new Biglietto(result.getInt("codMuseo"), result.getString("codFiscale"), result.getInt("codBiglietto"),
                        result.getInt("codGruppo"), result.getDate("dataAcquisto"), result.getInt("prezzo"));
                biglietti.add(biglietto);}
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
        return biglietti;
    }
    
    public int selectLastCod(int codMuseo) {
        int lastCod = 300;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT TOP 1 codBiglietto FROM " + this.tabName + " where codMuseo = ? ORDER BY codBiglietto DESC";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                lastCod = result.getInt("codBiglietto");
            }
        } catch (SQLException e) {
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
    
    public void setCodGruppo(int codBiglietto, int codGruppo, int codMuseo) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "UPDATE "+ this.tabName + " SET codGruppo = 1 WHERE codBiglietto = 0 AND ((SELECT COUNT(codGruppo) FROM " + this.tabName
                + " where codGruppo IS NOT NULL) <= 10)";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codGruppo);
            statement.setInt(2, codBiglietto);
            statement.setInt(3, codMuseo);
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

    public List<Biglietto> bigliettiPerData(Date data, Date date) {
        List<Biglietto> biglietti = null;
        Biglietto biglietto = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE dataAcquisto >= ? AND dataAcquisto <= ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setDate(1, data);
            statement.setDate(2, date);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                biglietti = new LinkedList<Biglietto>();
                biglietto = new Biglietto(result.getInt("codMuseo"), result.getString("codFiscale"), result.getInt("codBiglietto"),
                        result.getInt("codGruppo"), result.getDate("dataAcquisto"), result.getInt("prezzo"));
                biglietti.add(biglietto);
            }
            while(result.next()) {
                biglietto = new Biglietto(result.getInt("codMuseo"), result.getString("codFiscale"), result.getInt("codBiglietto"),
                        result.getInt("codGruppo"), result.getDate("dataAcquisto"), result.getInt("prezzo"));
                biglietti.add(biglietto);}
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
        return biglietti;
    }

    public List<Biglietto> bigliettiPerVisitatore(String codFiscale) {
        List<Biglietto> biglietti = null;
        Biglietto biglietto = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codFiscale = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, codFiscale);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                biglietti = new LinkedList<Biglietto>();
                biglietto = new Biglietto(result.getInt("codMuseo"), result.getString("codFiscale"), result.getInt("codBiglietto"),
                        result.getInt("codGruppo"), result.getDate("dataAcquisto"), result.getInt("prezzo"));
                biglietti.add(biglietto);
            }
            while(result.next()) {
                biglietto = new Biglietto(result.getInt("codMuseo"), result.getString("codFiscale"), result.getInt("codBiglietto"),
                        result.getInt("codGruppo"), result.getDate("dataAcquisto"), result.getInt("prezzo"));
                biglietti.add(biglietto);}
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
        return biglietti;
    }
}
