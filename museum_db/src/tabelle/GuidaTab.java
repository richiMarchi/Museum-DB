package tabelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import connection.DBConnection;
import entità.Guida;

public class GuidaTab {

    private DBConnection conn;
    private String tabName;

    public GuidaTab() {
        this.conn = new DBConnection();
        this.tabName = "guida";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FKGUIDA_MUSEO");
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "codMuseo INT NOT NULL, "
                    + "codFiscale CHAR(16) PRIMARY KEY, "
                    + "dataNascita DATE NOT NULL, "
                    + "numeroBadge INT NOT NULL, "
                    + "inizioLavoro DATE NOT NULL, "
                    + "fineLavoro DATE DEFAULT NULL, "
                    + "cognome VARCHAR(20) NOT NULL, "
                    + "nome VARCHAR(20) NOT NULL, "
                    + "numeroTelefono VARCHAR(15) NOT NULL, "
                    + "mail VARCHAR(40) NOT NULL UNIQUE, "
                    + "CONSTRAINT ID_GUIDA UNIQUE (codMuseo, numeroBadge))");
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

    public void insertInDB(Guida guida){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement ();
                int nextCod = selectLastCod(guida.getCodMuseo()) + 1;
                    statement.executeUpdate (
                        "INSERT INTO "+ this.tabName +" (codMuseo,codFiscale,nome,cognome,dataNascita,mail,numeroBadge,numeroTelefono,"
                                + "inizioLavoro)"
                        +"VALUES ('" + guida.getCodMuseo() + "','" + guida.getCodFiscale().toUpperCase() + "','" + guida.getNome()
                        + "','" + guida.getCognome() + "','" + guida.getDataNascita() + "','" + guida.getMail() + "','" + nextCod
                        + "','" + guida.getNumeroTelefono() + "','" + guida.getInizioLavoro() + "')"
                    );
                statement.close ();
            }
            catch (SQLException e) {
                 new Exception(e.getMessage());
                 System.out.println("Errore"+ e.getMessage());
                 System.out.println("A");
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
        String query = "DELETE FROM " + this.tabName + " WHERE codFiscale = ?";
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
    
    public Guida findByPrimaryKey(String codFiscale) {
        Guida guida = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codFiscale = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, codFiscale.toUpperCase());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                guida = new Guida(result.getInt("codMuseo"), result.getString("codFiscale"), result.getString("nome"),result.getString("cognome"),
                        result.getDate("dataNascita"), result.getString("mail"), result.getInt("numeroBadge"), result.getString("numeroTelefono"),
                        result.getDate("inizioLavoro"), result.getDate("fineLavoro"));
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
        return guida;
    }
    
    public List<Guida> getAllTable(int status)  {
        List<Guida> guide = null;
        Guida guida = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = null;
        switch(status) {
        case 0:
            query = "select * from "+ this.tabName;
            break;
        case 1:
            query = "select * from "+ this.tabName + " where fineLavoro is null";
            break;
        case 2:
            query = "select * from "+ this.tabName + " where fineLavoro is not null";
            break;
        default:
            query = "select * from " + this.tabName + " where codMuseo = ? and fineLavoro is null";
        } 
        try {
            if (status == 0 || status == 1 || status == 2) {
                statement = connection.prepareStatement(query);
                ResultSet result = statement.executeQuery();
                if(result.next()) {
                    guide = new LinkedList<Guida>();
                    guida = new Guida(result.getInt("codMuseo"), result.getString("codFiscale"), result.getString("nome"),result.getString("cognome"),
                            result.getDate("dataNascita"), result.getString("mail"), result.getInt("numeroBadge"), result.getString("numeroTelefono"),
                            result.getDate("inizioLavoro"), result.getDate("fineLavoro"));
                    guide.add(guida);
                }
                while(result.next()) {
                    guida = new Guida(result.getInt("codMuseo"), result.getString("codFiscale"), result.getString("nome"),result.getString("cognome"),
                            result.getDate("dataNascita"), result.getString("mail"), result.getInt("numeroBadge"), result.getString("numeroTelefono"),
                            result.getDate("inizioLavoro"), result.getDate("fineLavoro"));
                    guide.add(guida);}
            } else {
                statement = connection.prepareStatement(query);
                statement.setInt(1, status);
                ResultSet result = statement.executeQuery();
                if(result.next()) {
                    guide = new LinkedList<Guida>();
                    guida = new Guida(result.getInt("codMuseo"), result.getString("codFiscale"), result.getString("nome"),result.getString("cognome"),
                            result.getDate("dataNascita"), result.getString("mail"), result.getInt("numeroBadge"), result.getString("numeroTelefono"),
                            result.getDate("inizioLavoro"), result.getDate("fineLavoro"));
                    guide.add(guida);
                }
                while(result.next()) {
                    guida = new Guida(result.getInt("codMuseo"), result.getString("codFiscale"), result.getString("nome"),result.getString("cognome"),
                            result.getDate("dataNascita"), result.getString("mail"), result.getInt("numeroBadge"), result.getString("numeroTelefono"),
                            result.getDate("inizioLavoro"), result.getDate("fineLavoro"));
                    guide.add(guida);}
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
        return guide;
    }
    
    public int selectLastCod(int codMuseo) {
        int lastCod = 400;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select max(numeroBadge) as numeroBadge from guida where codMuseo = ?";
        String query2 = "select max(numeroBadge) as numeroBadge from restauratore where codMuseo = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                if (result.getInt("numeroBadge") > lastCod)
                lastCod = result.getInt("numeroBadge");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement = connection.prepareStatement(query2);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                if (lastCod < result.getInt("numeroBadge")) {
                    lastCod = result.getInt("numeroBadge");
                }
            }
        } catch(Exception e) {
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
    
    public List<String> getGuideConSupporti() {
        List<String> guide = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT DISTINCT g.codMuseo,g.numeroBadge FROM guida g, supportoGuida s WHERE g.codFiscale = s.codFiscale";
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                guide = new LinkedList<>();
                guide.add("" + result.getInt("codMuseo") + " " + result.getInt("numeroBadge"));
            }
            while (result.next()) {
                guide.add("" + result.getInt("codMuseo") + " " + result.getInt("numeroBadge"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return guide;
    }
}
