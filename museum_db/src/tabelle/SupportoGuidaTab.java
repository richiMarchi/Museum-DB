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
import entità.SupportoGuida;

public class SupportoGuidaTab {

    private DBConnection conn;
    private String tabName;

    public SupportoGuidaTab() {
        this.conn = new DBConnection();
        this.tabName = "supportoGuida";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FKSUPPORTOGUIDA_GUIDA");
//                statement.executeUpdate("ALTER TABLE " + "visitaGruppo" + " DROP CONSTRAINT FKVISITAGRUPPO_SUPPORTOGUIDA");
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "codGruppo INT NOT NULL, "
                    + "codMuseo INT NOT NULL, "
                    + "codFiscale CHAR(16) NOT NULL, "
                    + "data DATE NOT NULL, "
                    + "oraInizio INT NOT NULL, "
                    + "CONSTRAINT ID_GRUPPO UNIQUE (codGruppo,codMuseo), "
                    + "CONSTRAINT PK_SUPPORTOGUIDA PRIMARY KEY (codFiscale, data, oraInizio))");
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
    
    public void insertInDB(SupportoGuida supportoGuida){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement ();
                statement.executeUpdate (
                    "INSERT INTO "+ this.tabName +" (codGruppo,codMuseo,codFiscale,data,oraInizio)" + "VALUES ('" + supportoGuida.getNumeroGruppo()
                    + "','" + supportoGuida.getCodMuseo() + "','" + supportoGuida.getCodFiscale() + "','" + supportoGuida.getData()
                    + "','" + supportoGuida.getOraInizio() + "')"
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
    
    public void deleteFromDB(String codFiscale, Date data, int oraInizio) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE codFiscale = ? AND data = ? AND oraInizio = ?";
        if(this.findByPrimaryKey(codFiscale, data, oraInizio).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, codFiscale);
            statement.setDate(2, data);
            statement.setInt(3, oraInizio);
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
    
    public SupportoGuida findByPrimaryKey(String codFiscale, Date data, int oraInizio) {
        SupportoGuida supportoGuida = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codFiscale = ? AND data = ? AND oraInizio = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, codFiscale);
            statement.setDate(2, data);
            statement.setInt(3, oraInizio);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                supportoGuida = new SupportoGuida(result.getInt("codGruppo"), result.getString("codFiscale"), result.getDate("data"),
                        result.getInt("oraInizio"), result.getInt("codMuseo"));
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
        return supportoGuida;
    }
    
    public List<SupportoGuida> getAllTable()  {
        List<SupportoGuida> supportiGuide = null;
        SupportoGuida supportoGuida = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                supportiGuide = new LinkedList<SupportoGuida>();
                supportoGuida = new SupportoGuida(result.getInt("codGruppo"), result.getString("codFiscale"), result.getDate("data"),
                        result.getInt("oraInizio"), result.getInt("codMuseo"));
                supportiGuide.add(supportoGuida);
            }
            while(result.next()) {
                supportoGuida = new SupportoGuida(result.getInt("codGruppo"), result.getString("codFiscale"), result.getDate("data"),
                        result.getInt("oraInizio"), result.getInt("codMuseo"));
                supportiGuide.add(supportoGuida);}
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
        return supportiGuide;
    }

    public List<SupportoGuida> getTabPerGruppi(int codMuseo, int numeroBadge) {
        List<SupportoGuida> supportiGuide = null;
        SupportoGuida supportoGuida = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select s.codGruppo,g.numeroBadge,s.codMuseo,s.oraInizio,s.data from supportoGuida s, guida g "
                + "where s.codFiscale = g.codFiscale and g.codMuseo = ? and g.numeroBadge = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setInt(2, numeroBadge);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                supportiGuide = new LinkedList<SupportoGuida>();
                supportoGuida = new SupportoGuida(result.getInt("codGruppo"), "" + result.getInt("numeroBadge"), result.getDate("data"),
                        result.getInt("oraInizio"), result.getInt("codMuseo"));
                supportiGuide.add(supportoGuida);
            }
            while(result.next()) {
                supportoGuida = new SupportoGuida(result.getInt("codGruppo"), "" + result.getInt("numeroBadge"), result.getDate("data"),
                        result.getInt("oraInizio"), result.getInt("codMuseo"));
                supportiGuide.add(supportoGuida);
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
        return supportiGuide;
    }
    
    public List<SupportoGuida> getTabPerGruppi(Date data, Date data2) {
        List<SupportoGuida> supportiGuide = null;
        SupportoGuida supportoGuida = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select s.codGruppo,g.numeroBadge,s.codMuseo,s.oraInizio,s.data from supportoGuida s, guida g "
                + "where s.codFiscale = g.codFiscale and data > ? and data < ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setDate(1, data);
            statement.setDate(2, data2);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                supportiGuide = new LinkedList<SupportoGuida>();
                supportoGuida = new SupportoGuida(result.getInt("codGruppo"), "" + result.getInt("numeroBadge"), result.getDate("data"),
                        result.getInt("oraInizio"), result.getInt("codMuseo"));
                supportiGuide.add(supportoGuida);
            }
            while(result.next()) {
                supportoGuida = new SupportoGuida(result.getInt("codGruppo"), "" + result.getInt("numeroBadge"), result.getDate("data"),
                        result.getInt("oraInizio"), result.getInt("codMuseo"));
                supportiGuide.add(supportoGuida);
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
        return supportiGuide;
    }
    
    public List<SupportoGuida> getTabPerGruppi(int codMuseo, int numeroBadge, Date data, Date data2) {
        List<SupportoGuida> supportiGuide = null;
        SupportoGuida supportoGuida = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select s.codGruppo,g.numeroBadge,s.codMuseo,s.oraInizio,s.data from supportoGuida s, guida g "
                + "where s.codFiscale = g.codFiscale and s.codMuseo = ? and g.numeroBadge = ? and s.data > ? and s.data < ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setInt(2, numeroBadge);
            statement.setDate(3, data);
            statement.setDate(4, data2);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                supportiGuide = new LinkedList<SupportoGuida>();
                supportoGuida = new SupportoGuida(result.getInt("codGruppo"), "" + result.getInt("numeroBadge"), result.getDate("data"),
                        result.getInt("oraInizio"), result.getInt("codMuseo"));
                supportiGuide.add(supportoGuida);
            }
            while(result.next()) {
                supportoGuida = new SupportoGuida(result.getInt("codGruppo"), "" + result.getInt("numeroBadge"), result.getDate("data"),
                        result.getInt("oraInizio"), result.getInt("codMuseo"));
                supportiGuide.add(supportoGuida);
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
        return supportiGuide;
    }
    
    public List<SupportoGuida> getTabPerGruppi() {
        List<SupportoGuida> supportiGuide = null;
        SupportoGuida supportoGuida = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select s.codGruppo,g.numeroBadge,s.codMuseo,s.oraInizio,s.data from supportoGuida s, guida g "
                + "where s.codFiscale = g.codFiscale";
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                supportiGuide = new LinkedList<SupportoGuida>();
                supportoGuida = new SupportoGuida(result.getInt("codGruppo"), "" + result.getInt("numeroBadge"), result.getDate("data"),
                        result.getInt("oraInizio"), result.getInt("codMuseo"));
                supportiGuide.add(supportoGuida);
            }
            while(result.next()) {
                supportoGuida = new SupportoGuida(result.getInt("codGruppo"), "" + result.getInt("numeroBadge"), result.getDate("data"),
                        result.getInt("oraInizio"), result.getInt("codMuseo"));
                supportiGuide.add(supportoGuida);
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
        return supportiGuide;
    }
}
