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
import entità.VisitaSingola;

public class VisitaSingolaTab {

    private DBConnection conn;
    private String tabName;

    public VisitaSingolaTab() {
        this.conn = new DBConnection();
        this.tabName = "visitaSingola";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FKVISITASINGOLA_BIGLIETTO");
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "dataUtilizzo DATE NOT NULL, "
                    + "numeroProgressivo INT NOT NULL, "
                    + "codMuseo INT NOT NULL, "
                    + "codBiglietto INT NOT NULL, "
                    + "CONSTRAINT PK_VISITASINGOLA PRIMARY KEY (codMuseo,dataUtilizzo, numeroProgressivo))");
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
    
    public int insertInDB(VisitaSingola visitaSingola){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            int nextCod = 0;
            try {
                statement = connection.createStatement ();
                nextCod = selectLastCod(visitaSingola.getCodMuseo(), visitaSingola.getDataUtilizzo()) + 1;
                statement.executeUpdate (
                        "INSERT INTO "+ this.tabName +" (dataUtilizzo,numeroProgressivo,codMuseo,codBiglietto)"
                        +"VALUES ('" + visitaSingola.getDataUtilizzo() + "','" + nextCod + "','"
                                + visitaSingola.getCodMuseo() + "','" + visitaSingola.getCodBiglietto() + "')"
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
            return nextCod;
    }
    
    public void deleteFromDB(int codMuseo, Date dataUtilizzo, int numeroProgressivo) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE codMuseo = ? AND dataUtilizzo = ? AND numeroProgressivo = ?"
                + "AND oraInizio = ?";
        if(this.findByPrimaryKey(codMuseo, dataUtilizzo, numeroProgressivo).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setDate(2, dataUtilizzo);
            statement.setInt(3, numeroProgressivo);
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
    
    public VisitaSingola findByPrimaryKey(int codMuseo, Date dataUtilizzo, int numeroProgressivo) {
        VisitaSingola visitaSingola = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codMuseo = ? AND dataUtilizzo = ? AND numeroProgressivo = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setDate(2, dataUtilizzo);
            statement.setInt(3, numeroProgressivo);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                visitaSingola = new VisitaSingola(result.getInt("codMuseo"), result.getInt("codBiglietto"), result.getDate("dataUtilizzo"),
                        result.getInt("numeroProgressivo"));
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
        return visitaSingola;
    }
    
    public List<VisitaSingola> getAllTable()  {
        List<VisitaSingola> visiteSingole = null;
        VisitaSingola visiteGruppo = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                visiteSingole = new LinkedList<VisitaSingola>();
                visiteGruppo = new VisitaSingola(result.getInt("codMuseo"), result.getInt("codBiglietto"), result.getDate("dataUtilizzo"),
                        result.getInt("numeroProgressivo"));
                visiteSingole.add(visiteGruppo);
            }
            while(result.next()) {
                visiteGruppo = new VisitaSingola(result.getInt("codMuseo"), result.getInt("codBiglietto"), result.getDate("dataUtilizzo"),
                        result.getInt("numeroProgressivo"));
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
    
    
    
    public int selectLastCod(int codMuseo, Date data) {
        int lastCod = 0;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT TOP 1 numeroProgressivo FROM " + this.tabName + " WHERE dataUtilizzo = ? AND codMuseo = ? ORDER BY numeroProgressivo DESC";
        try {
            statement = connection.prepareStatement(query);
            statement.setDate(1, data);
            statement.setInt(2, codMuseo);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                lastCod = result.getInt("numeroProgressivo");
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
    
    public List<VisitaSingola> visiteSingolePerData(Date data, Date date) {
        List<VisitaSingola> visite = null;
        VisitaSingola visita = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE dataUtilizzo >= ? AND dataUtilizzo <= ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setDate(1, data);
            statement.setDate(2, date);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                visite = new LinkedList<VisitaSingola>();
                visita = new VisitaSingola(result.getInt("codMuseo"), result.getInt("codBiglietto"), result.getDate("dataUtilizzo"),
                        result.getInt("numeroProgressivo"));
                visite.add(visita);
            }
            while(result.next()) {
                visita = new VisitaSingola(result.getInt("codMuseo"), result.getInt("codBiglietto"), result.getDate("dataUtilizzo"),
                        result.getInt("numeroProgressivo"));
                visite.add(visita);
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
        return visite;
    }

    public List<VisitaSingola> getAllTable(int codMuseo) {
        List<VisitaSingola> visiteSingole = null;
        VisitaSingola visiteGruppo = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName + " where codMuseo = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                visiteSingole = new LinkedList<VisitaSingola>();
                visiteGruppo = new VisitaSingola(result.getInt("codMuseo"), result.getInt("codBiglietto"), result.getDate("dataUtilizzo"),
                        result.getInt("numeroProgressivo"));
                visiteSingole.add(visiteGruppo);
            }
            while(result.next()) {
                visiteGruppo = new VisitaSingola(result.getInt("codMuseo"), result.getInt("codBiglietto"), result.getDate("dataUtilizzo"),
                        result.getInt("numeroProgressivo"));
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
}
