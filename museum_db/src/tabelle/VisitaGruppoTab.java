package tabelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import connection.DBConnection;
import entità.SupportoGuida;
import entità.VisitaGruppo;

public class VisitaGruppoTab {

    private DBConnection conn;
    private String tabName;

    public VisitaGruppoTab() {
        this.conn = new DBConnection();
        this.tabName = "visitaGruppo";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "numeroGruppo INT NOT NULL, "
                    + "codMuseo INT NOT NULL, "
                    + "codFiscale CHAR(16) NOT NULL, "
                    + "data DATE NOT NULL, "
                    + "oraInizio INT NOT NULL, "
                    + "CONSTRAINT PK_VISITAGRUPPO PRIMARY KEY(numeroGruppo,codMuseo))");
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
    
    public void insertInDB(VisitaGruppo visitaGruppo){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement ();
                int nextCod = selectLastCod(visitaGruppo.getCodMuseo()) + 1;
                new SupportoGuidaTab().insertInDB(new SupportoGuida(nextCod, visitaGruppo.getCodFiscale(), visitaGruppo.getData(),
                        visitaGruppo.getOraInizio(), visitaGruppo.getCodMuseo()));
                statement.executeUpdate (
                        "INSERT INTO "+ this.tabName +" (numeroGruppo,codFiscale,data,oraInizio,codMuseo)"
                        +"VALUES ('" + nextCod + "','" + visitaGruppo.getCodFiscale() + "','"
                                + visitaGruppo.getData() + "','" + visitaGruppo.getOraInizio() + "','" + visitaGruppo.getCodMuseo() + "')"
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
    
    public void deleteFromDB(int numeroGruppo, int codMuseo) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE numeroGruppo = ? AND codMuseo = ?";
        if(this.findByPrimaryKey(numeroGruppo, codMuseo).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, numeroGruppo);
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
    
    public VisitaGruppo findByPrimaryKey(int numeroGruppo, int codMuseo) {
        VisitaGruppo visitaGruppo = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + "WHERE numeroGruppo = ? AND codMuseo = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, numeroGruppo);
            statement.setInt(2, codMuseo);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                visitaGruppo = new VisitaGruppo(result.getString("codFiscale"), result.getDate("data"), result.getInt("oraInizio"),
                        result.getInt("numeroGruppo"), result.getInt("codMuseo"));
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
        return visitaGruppo;
    }
    
    public List<VisitaGruppo> getAllTable()  {
        List<VisitaGruppo> visiteGruppi = null;
        VisitaGruppo visitaGruppo = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                visiteGruppi = new LinkedList<VisitaGruppo>();
                visitaGruppo = new VisitaGruppo(result.getString("codFiscale"), result.getDate("data"), result.getInt("oraInizio"),
                        result.getInt("numeroGruppo"), result.getInt("codMuseo"));
                visiteGruppi.add(visitaGruppo);
            }
            while(result.next()) {
                visitaGruppo = new VisitaGruppo(result.getString("codFiscale"), result.getDate("data"), result.getInt("oraInizio"),
                        result.getInt("numeroGruppo"), result.getInt("codMuseo"));
                visiteGruppi.add(visitaGruppo);}
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
        return visiteGruppi;
    }
    
    public int selectLastCod(int codMuseo) {
        int lastCod = 600;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT TOP 1 numeroGruppo FROM " + this.tabName + " WHERE codMuseo = ? ORDER BY numeroGruppo DESC";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                lastCod = result.getInt("numeroGruppo");
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
    
    public List<VisitaGruppo> getAllTable(int codMuseo)  {
        List<VisitaGruppo> visiteGruppi = null;
        VisitaGruppo visitaGruppo = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * from "+ this.tabName + " where codMuseo = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                visiteGruppi = new LinkedList<VisitaGruppo>();
                visitaGruppo = new VisitaGruppo(result.getString("codFiscale"), result.getDate("data"), result.getInt("oraInizio"),
                        result.getInt("numeroGruppo"), result.getInt("codMuseo"));
                visiteGruppi.add(visitaGruppo);
            }
            while(result.next()) {
                visitaGruppo = new VisitaGruppo(result.getString("codFiscale"), result.getDate("data"), result.getInt("oraInizio"),
                        result.getInt("numeroGruppo"), result.getInt("codMuseo"));
                visiteGruppi.add(visitaGruppo);}
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
        return visiteGruppi;
    }
}
