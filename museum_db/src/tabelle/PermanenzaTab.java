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
import entità.Permanenza;

public class PermanenzaTab {

    private DBConnection conn;
    private String tabName;

    public PermanenzaTab() {
        this.conn = new DBConnection();
        this.tabName = "permanenza";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FKPERMANENZA_SEZIONE");
//                statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FKPERMANENZA_OPERA");
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "codMuseo INT NOT NULL, "
                    + "codAutore INT NOT NULL, "
                    + "dataInserimento DATE NOT NULL, "
                    + "dataRimozione DATE DEFAULT NULL, "
                    + "nomeOpera VARCHAR(20) NOT NULL, "
                    + "nomeSezione VARCHAR(20) NOT NULL, "
                    + "CONSTRAINT PK_PERMANENZA PRIMARY KEY (codMuseo, nomeSezione, codAutore, nomeOpera, dataInserimento))");
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

    public void insertInDB(Permanenza permanenza){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement ();
                statement.executeUpdate (
                    "INSERT INTO "+ this.tabName +" (codMuseo,nomeSezione,codAutore,nomeOpera,dataInserimento)"
                    +"VALUES ('" + permanenza.getCodMuseo() + "','" + permanenza.getNomeSezione()
                    + "','" + permanenza.getCodAutore() + "','" + permanenza.getNomeOpera() + "','"
                    + permanenza.getDataInserimento() + "')"
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
    
    public void deleteFromDB(int codMuseo, String nomeSezione, int codAutore, String nomeOpera, Date dataInserimento) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE codMuseo = ? AND nomeSezione = ? AND codAutore = ? AND nomeOpera = ? AND dataInserimento = ?";
        if(this.findByPrimaryKey(codMuseo, nomeSezione, codAutore, nomeOpera, dataInserimento).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setString(2, nomeSezione);
            statement.setInt(3, codAutore);
            statement.setString(4, nomeOpera);
            statement.setDate(5, dataInserimento);
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
    
    public Permanenza findByPrimaryKey(int codMuseo, String nomeSezione, int codAutore, String nomeOpera, Date dataInserimento) {
        Permanenza permanenza = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codMuseo = ? AND nomeSezione = ? AND codAutore = ? AND nomeOpera = ? "
                + "AND dataInserimento = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setString(2, nomeSezione);
            statement.setInt(3, codAutore);
            statement.setString(4, nomeOpera);
            statement.setDate(5, dataInserimento);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezion"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
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
        return permanenza;
    }
    
    public List<Permanenza> getAllTable()  {
        List<Permanenza> permanenze = null;
        Permanenza permanenza = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                permanenze = new LinkedList<Permanenza>();
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezion"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
                permanenze.add(permanenza);
            }
            while(result.next()) {
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezion"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
                permanenze.add(permanenza);}
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
        return permanenze;
    }

    public List<Permanenza> getAllTable(int parseInt) {
        List<Permanenza> permanenze = null;
        Permanenza permanenza = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = null;
        try {
            if (parseInt == 0) {
                query = "select p.codMuseo,p.nomeSezione,p.codAutore,p.nomeOpera,t.nomeTipologia,o.anno,p.dataInserimento,p.dataRimozione "
                    + "from permanenza p, opera o, tipologia t where o.nomeTipologia = t.nomeTipologia and p.codAutore = o.codAutore "
                    + "and p.nomeOpera = o.nomeOpera";
                statement = connection.prepareStatement(query);
            } else {
                query = "select p.codMuseo,p.nomeSezione,p.codAutore,p.nomeOpera,t.nomeTipologia,o.anno,p.dataInserimento,p.dataRimozione "
                        + "from permanenza p, opera o, tipologia t where o.nomeTipologia = t.nomeTipologia and p.codAutore = o.codAutore "
                        + "and p.nomeOpera = o.nomeOpera and o.codAutore = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, parseInt);
            }
            
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                permanenze = new LinkedList<Permanenza>();
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
                permanenza.setAnno(result.getInt("anno"));
                permanenza.setTipologia(result.getString("nomeTipologia"));
                permanenze.add(permanenza);
            }
            while(result.next()) {
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
                permanenza.setAnno(result.getInt("anno"));
                permanenza.setTipologia(result.getString("nomeTipologia"));
                permanenze.add(permanenza);}
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
        return permanenze;
    }

    public List<Permanenza> getAllTable(int parseInt, String string) {
        List<Permanenza> permanenze = null;
        Permanenza permanenza = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select p.codMuseo,p.nomeSezione,p.codAutore,p.nomeOpera,t.nomeTipologia,o.anno,p.dataInserimento,p.dataRimozione "
                + "from permanenza p, opera o, tipologia t where o.nomeTipologia = t.nomeTipologia and p.codAutore = o.codAutore "
                + "and p.nomeOpera = o.nomeOpera and codMuseo = ? and nomeSezione = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, parseInt);
            statement.setString(2, string);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                permanenze = new LinkedList<Permanenza>();
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
                permanenza.setAnno(result.getInt("anno"));
                permanenza.setTipologia(result.getString("nomeTipologia"));
                permanenze.add(permanenza);
            }
            while(result.next()) {
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
                permanenza.setAnno(result.getInt("anno"));
                permanenza.setTipologia(result.getString("nomeTipologia"));
                permanenze.add(permanenza);}
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
        return permanenze;
    }

    public List<Permanenza> getAllTableByMuseo(int codMuseo) {
        List<Permanenza> permanenze = null;
        Permanenza permanenza = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = null;
        try {
            query = "select p.codMuseo,p.nomeSezione,p.codAutore,p.nomeOpera,t.nomeTipologia,o.anno,p.dataInserimento,p.dataRimozione "
                    + "from permanenza p, opera o, tipologia t where o.nomeTipologia = t.nomeTipologia and p.codAutore = o.codAutore "
                    + "and p.nomeOpera = o.nomeOpera and codMuseo = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                permanenze = new LinkedList<Permanenza>();
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
                permanenza.setAnno(result.getInt("anno"));
                permanenza.setTipologia(result.getString("nomeTipologia"));
                permanenze.add(permanenza);
            }
            while(result.next()) {
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
                permanenza.setAnno(result.getInt("anno"));
                permanenza.setTipologia(result.getString("nomeTipologia"));
                permanenze.add(permanenza);}
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
        return permanenze;
    }

    public List<Permanenza> getRemovableOpere(int codMuseo) {
        List<Permanenza> permanenze = null;
        Permanenza permanenza = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = null;
        try {
            query = "select * from permanenza where codMuseo = ? AND dataRimozione IS NULL";
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                permanenze = new LinkedList<Permanenza>();
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
                permanenze.add(permanenza);
            }
            while(result.next()) {
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
                permanenze.add(permanenza);}
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
        return permanenze;
    }

    public void removeOpera(int codMuseo, int parseInt, String string) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = null;
        try {
            query = "update permanenza set dataRimozione = ? where codMuseo = ? AND codAutore = ? and nomeOpera = ?";
            statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setInt(2, codMuseo);
            statement.setInt(3, parseInt);
            statement.setString(4, string);
            statement.executeQuery();
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
    }

    public List<Permanenza> getAllTableBySez(String parseInt) {
        List<Permanenza> permanenze = null;
        Permanenza permanenza = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select p.codMuseo,p.nomeSezione,p.codAutore,p.nomeOpera,t.nomeTipologia,o.anno,p.dataInserimento,p.dataRimozione "
                + "from permanenza p, opera o, tipologia t where o.nomeTipologia = t.nomeTipologia and p.codAutore = o.codAutore "
                + "and p.nomeOpera = o.nomeOpera and nomeSezione = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, parseInt);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                permanenze = new LinkedList<Permanenza>();
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
                permanenza.setAnno(result.getInt("anno"));
                permanenza.setTipologia(result.getString("nomeTipologia"));
                permanenze.add(permanenza);
            }
            while(result.next()) {
                permanenza = new Permanenza(result.getInt("codMuseo"), result.getString("nomeSezione"), result.getInt("codAutore"),
                        result.getString("nomeOpera"), result.getDate("dataInserimento"), result.getDate("dataRimozione"));
                permanenza.setAnno(result.getInt("anno"));
                permanenza.setTipologia(result.getString("nomeTipologia"));
                permanenze.add(permanenza);}
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
        return permanenze;
    }
}
