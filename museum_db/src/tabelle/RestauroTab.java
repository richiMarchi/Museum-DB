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
import entità.Restauro;

public class RestauroTab {

    private DBConnection conn;
    private String tabName;

    public RestauroTab() {
        this.conn = new DBConnection();
        this.tabName = "restauro";
    }

    public void dropAndCreateTable() {
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
//            try {
//                statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FKRESTAURO_RESTAURATORE");
//                statement.executeUpdate("ALTER TABLE " + this.tabName + " DROP CONSTRAINT FKRESTAURO_OPERA");
//                statement.executeUpdate("DROP TABLE " + this.tabName);
//            } catch (SQLException e) {
//                System.out.println("Table " + this.tabName + " does not exist.");
//            }
            statement.executeUpdate("CREATE TABLE " + this.tabName + " ("
                    + "codFiscale CHAR(16) NOT NULL, "
                    + "codAutore INT NOT NULL, "
                    + "dataInizio DATE NOT NULL, "
                    + "dataFine DATE DEFAULT NULL, "
                    + "nomeOpera VARCHAR(20) NOT NULL, "
                    + "CONSTRAINT PK_RESTAURO PRIMARY KEY (codFiscale, codAutore, nomeOpera, dataInizio))");
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

    public void insertInDB(Restauro restauro){
        Connection connection = this.conn.getMsSQLConnection();
            Statement statement = null;
            try {
                statement = connection.createStatement();
                statement.executeUpdate (
                    "INSERT INTO "+ this.tabName +" (codFiscale,codAutore,nomeOpera,dataInizio)"
                    +"VALUES ('" + restauro.getCodFiscale() + "','" + restauro.getCodAutore()
                    + "','" + restauro.getNomeOpera() + "','" + restauro.getDataInizio() + "')"
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
    
    public void deleteFromDB(String codFiscale, int codAutore, String nomeOpera, Date dataInizio) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM " + this.tabName + " WHERE codFiscale = ? AND codAutore = ? AND nomeOpera = ? AND dataInizio = ?";
        if(this.findByPrimaryKey(codFiscale, codAutore, nomeOpera, dataInizio).equals(null)){
            throw new IllegalArgumentException();
        }
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, codFiscale);
            statement.setInt(2, codAutore);
            statement.setString(3, nomeOpera);
            statement.setDate(4, dataInizio);
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
    
    public Restauro findByPrimaryKey(String codFiscale, int codAutore, String nomeOpera, Date dataInizio) {
        Restauro restauro = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codFiscale = ? AND codAutore = ? AND nomeOpera = ? AND dataInizio = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, codFiscale);
            statement.setInt(2, codAutore);
            statement.setString(3, nomeOpera);
            statement.setDate(4, dataInizio);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                restauro = new Restauro(result.getString("codFiscale"), result.getInt("codAutore"), result.getString("nomeOpera"),
                        result.getDate("dataInizio"), result.getDate("dataFine"));
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
        return restauro;
    }
    
    public List<Restauro> getAllTable()  {
        List<Restauro> restauri = null;
        Restauro restauro = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from "+ this.tabName ;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                restauri = new LinkedList<Restauro>();
                restauro = new Restauro(result.getString("codFiscale"), result.getInt("codAutore"), result.getString("nomeOpera"),
                        result.getDate("dataInizio"), result.getDate("dataFine"));
                restauri.add(restauro);
            }
            while(result.next()) {
                restauro = new Restauro(result.getString("codFiscale"), result.getInt("codAutore"), result.getString("nomeOpera"),
                        result.getDate("dataInizio"), result.getDate("dataFine"));
                restauri.add(restauro);}
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
        return restauri;
    }

    public List<Restauro> getAllTable(int i) {
        List<Restauro> restauri = null;
        Restauro restauro = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = null;
        if (i == 0) {
            query = "select c.codMuseo,c.numeroBadge,r.codAutore,r.nomeOpera,r.dataInizio,r.dataFine from restauro r, restauratore c "
                    + "where r.codFiscale = c.codFiscale";
        } else if (i == 1) {
            query = "select c.codMuseo,c.numeroBadge,r.codAutore,r.nomeOpera,r.dataInizio,r.dataFine from restauro r, restauratore c "
                    + "where r.codFiscale = c.codFiscale and dataFine is null";
        } else if (i == 2) {
            query = "select c.codMuseo,c.numeroBadge,r.codAutore,r.nomeOpera,r.dataInizio,r.dataFine from restauro r, restauratore c "
                    + "where r.codFiscale = c.codFiscale and dataFine is not null";
        } else {
            query = "select c.codMuseo,c.numeroBadge,r.codAutore,r.nomeOpera,r.dataInizio,r.dataFine from restauro r, restauratore c "
                    + "where r.codFiscale = c.codFiscale AND codMuseo = ?";
        }
        try {
            statement = connection.prepareStatement(query);
            if (i > 100) {
                statement.setInt(1, i);
            }
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                restauri = new LinkedList<Restauro>();
                restauro = new Restauro("" + result.getInt("codMuseo"), result.getInt("codAutore"), result.getString("nomeOpera"),
                        result.getDate("dataInizio"), result.getDate("dataFine"));
                restauro.setNumeroBadge(result.getInt("numeroBadge"));
                restauri.add(restauro);
            }
            while(result.next()) {
                restauro = new Restauro("" + result.getInt("codMuseo"), result.getInt("codAutore"), result.getString("nomeOpera"),
                        result.getDate("dataInizio"), result.getDate("dataFine"));
                restauro.setNumeroBadge(result.getInt("numeroBadge"));
                restauri.add(restauro);
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
        return restauri;
    }

    

    public Restauro findAttivo(int parseInt, String string) {
        Restauro restauro = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "SELECT * FROM " + this.tabName + " WHERE codAutore = ? AND nomeOpera = ? AND dataFine IS NULL";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, parseInt);
            statement.setString(2, string);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                restauro = new Restauro(result.getString("codFiscale"), result.getInt("codAutore"), result.getString("nomeOpera"),
                        result.getDate("dataInizio"), result.getDate("dataFine"));
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
        return restauro;
    }

    public List<Restauro> getRestauriAttivi(int codMuseo) {
        List<Restauro> restauri = null;
        Restauro restauro = null;
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select r.* from "+ this.tabName + " r, permanenza p where r.nomeOpera = p.nomeOpera AND r.codAutore = p.codAutore "
                + "and p.codMuseo = ? and r.dataFine IS NULL";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                restauri = new LinkedList<Restauro>();
                restauro = new Restauro(result.getString("codFiscale"), result.getInt("codAutore"), result.getString("nomeOpera"),
                        result.getDate("dataInizio"), result.getDate("dataFine"));
                restauri.add(restauro);
            }
            while(result.next()) {
                restauro = new Restauro(result.getString("codFiscale"), result.getInt("codAutore"), result.getString("nomeOpera"),
                        result.getDate("dataInizio"), result.getDate("dataFine"));
                restauri.add(restauro);}
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
        return restauri;
    }

    public void terminaRest(int parseInt, String string) {
        Connection connection = this.conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "update " + this.tabName + " set dataFine = ? where codAutore = ? and nomeOpera = ? and dataFine IS NULL";
        try {
            statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setInt(2, parseInt);
            statement.setString(3, string);
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
}
