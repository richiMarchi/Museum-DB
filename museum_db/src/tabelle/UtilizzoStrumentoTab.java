package tabelle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import connection.DBConnection;
import entità.UtilizzoStrumento;
import entità.VisitaSingola;

public class UtilizzoStrumentoTab {

    private DBConnection conn;
    private String tabName;

    public UtilizzoStrumentoTab() {
        this.conn = new DBConnection();
        this.tabName = "utilizzoStrumento";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FKUTILIZZOSTRUMENTO_VISITASINGOLA");
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "dataUtilizzo DATE NOT NULL, "
                    + "numeroProgressivo INT NOT NULL, "
                    + "codStrumento INT NOT NULL, "
                    + "codMuseo INT NOT NULL, "
                    + "oraInizio INT NOT NULL, "
                    + "CONSTRAINT PK_UTILIZZOSTRUMENTO PRIMARY KEY (codStrumento,codMuseo,dataUtilizzo, numeroProgressivo, oraInizio))");
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
    
    public void insertInDB(UtilizzoStrumento utilizzoStrumento, int codBiglietto){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            int numProg = new VisitaSingolaTab().insertInDB(new VisitaSingola(utilizzoStrumento.getCodMuseo(),
                    codBiglietto,Date.valueOf(LocalDate.now()),0));
            try {
                statement = connection.createStatement ();
                statement.executeUpdate (
                    "INSERT INTO "+ this.tabName +" (dataUtilizzo,numeroProgressivo,codStrumento,codMuseo,oraInizio)"
                    + "VALUES ('" + utilizzoStrumento.getDataUtilizzo() + "','" + numProg + "','"
                    + utilizzoStrumento.getCodStrumento() + "','" + utilizzoStrumento.getCodMuseo() + "','" + utilizzoStrumento.getOraInizio() + "')"
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
    
    public void deleteFromDB(int codMuseo, int codStrumento, Date dataUtilizzo, int numeroProgressivo, int oraInizio) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE codMuseo = ? AND codStrumento = ? AND dataUtilizzo = ? AND numeroProgressivo = ?"
                + "AND oraInizio = ?";
        if(this.findByPrimaryKey(codMuseo, codStrumento, dataUtilizzo, numeroProgressivo, oraInizio).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setInt(2, codStrumento);
            statement.setDate(3, dataUtilizzo);
            statement.setInt(4, numeroProgressivo);
            statement.setInt(5, oraInizio);
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
    
    public UtilizzoStrumento findByPrimaryKey(int codMuseo, int codStrumento, Date dataUtilizzo, int numeroProgressivo, int oraInizio) {
        UtilizzoStrumento utilizzoStrumento = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codMuseo = ? AND codStrumento = ? AND dataUtilizzo = ? AND numeroProgressivo = ? "
                + "AND oraInizio = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setInt(2, codStrumento);
            statement.setDate(3, dataUtilizzo);
            statement.setInt(4, numeroProgressivo);
            statement.setInt(5, oraInizio);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                utilizzoStrumento = new UtilizzoStrumento(result.getDate("dataUtilizzo"), result.getInt("numeroProgressivo"), result.getInt("codStrumento"),
                        result.getInt("codMuseo"), result.getInt("oraInizio"));
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
        return utilizzoStrumento;
    }
    
    public List<UtilizzoStrumento> getAllTable()  {
        List<UtilizzoStrumento> utilizziStrumenti = null;
        UtilizzoStrumento utilizzoStrumento = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                utilizziStrumenti = new LinkedList<UtilizzoStrumento>();
                utilizzoStrumento = new UtilizzoStrumento(result.getDate("dataUtilizzo"), result.getInt("numeroProgressivo"), result.getInt("codStrumento"),
                        result.getInt("codMuseo"), result.getInt("oraInizio"));
                utilizziStrumenti.add(utilizzoStrumento);
            }
            while(result.next()) {
                utilizzoStrumento = new UtilizzoStrumento(result.getDate("dataUtilizzo"), result.getInt("numeroProgressivo"), result.getInt("codStrumento"),
                        result.getInt("codMuseo"), result.getInt("oraInizio"));
                utilizziStrumenti.add(utilizzoStrumento);}
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
        return utilizziStrumenti;
    }
    
    public List<UtilizzoStrumento> getAllTable(Date data, Date data2)  {
        List<UtilizzoStrumento> utilizziStrumenti = null;
        UtilizzoStrumento utilizzoStrumento = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName + " where dataUtilizzo >= ? and dataUtilizzo <= ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setDate(1, data);
            statement.setDate(2, data2);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                utilizziStrumenti = new LinkedList<UtilizzoStrumento>();
                utilizzoStrumento = new UtilizzoStrumento(result.getDate("dataUtilizzo"), result.getInt("numeroProgressivo"), result.getInt("codStrumento"),
                        result.getInt("codMuseo"), result.getInt("oraInizio"));
                utilizziStrumenti.add(utilizzoStrumento);
            }
            while(result.next()) {
                utilizzoStrumento = new UtilizzoStrumento(result.getDate("dataUtilizzo"), result.getInt("numeroProgressivo"), result.getInt("codStrumento"),
                        result.getInt("codMuseo"), result.getInt("oraInizio"));
                utilizziStrumenti.add(utilizzoStrumento);}
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
        return utilizziStrumenti;
    }

    public List<UtilizzoStrumento> findByPrimaryKey(int codMuseo, int value, Date valueOf, int value2) {
        List<UtilizzoStrumento> utilizziStrumenti = null;
        UtilizzoStrumento utilizzoStrumento = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName + " where codMuseo = ? and codStrumento = ? and dataUtilizzo = ? and oraInizio = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setInt(2, value);
            statement.setDate(3, valueOf);
            statement.setInt(4, value2);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                utilizziStrumenti = new LinkedList<UtilizzoStrumento>();
                utilizzoStrumento = new UtilizzoStrumento(result.getDate("dataUtilizzo"), result.getInt("numeroProgressivo"), result.getInt("codStrumento"),
                        result.getInt("codMuseo"), result.getInt("oraInizio"));
                utilizziStrumenti.add(utilizzoStrumento);
            }
            while(result.next()) {
                utilizzoStrumento = new UtilizzoStrumento(result.getDate("dataUtilizzo"), result.getInt("numeroProgressivo"), result.getInt("codStrumento"),
                        result.getInt("codMuseo"), result.getInt("oraInizio"));
                utilizziStrumenti.add(utilizzoStrumento);}
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
        return utilizziStrumenti;
    }

    public List<UtilizzoStrumento> getAllTable(int codMuseo) {
        List<UtilizzoStrumento> utilizziStrumenti = null;
        UtilizzoStrumento utilizzoStrumento = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName + " where codMuseo = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                utilizziStrumenti = new LinkedList<UtilizzoStrumento>();
                utilizzoStrumento = new UtilizzoStrumento(result.getDate("dataUtilizzo"), result.getInt("numeroProgressivo"), result.getInt("codStrumento"),
                        result.getInt("codMuseo"), result.getInt("oraInizio"));
                utilizziStrumenti.add(utilizzoStrumento);
            }
            while(result.next()) {
                utilizzoStrumento = new UtilizzoStrumento(result.getDate("dataUtilizzo"), result.getInt("numeroProgressivo"), result.getInt("codStrumento"),
                        result.getInt("codMuseo"), result.getInt("oraInizio"));
                utilizziStrumenti.add(utilizzoStrumento);}
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
        return utilizziStrumenti;
    }
}
