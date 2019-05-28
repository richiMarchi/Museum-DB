package utilities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import connection.DBConnection;
import entità.Personale;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import tabelle.AutoreTab;
import tabelle.BigliettoTab;
import tabelle.GuidaTab;
import tabelle.MuseoTab;
import tabelle.OperaTab;
import tabelle.PermanenzaTab;
import tabelle.RestauratoreTab;
import tabelle.RestauroTab;
import tabelle.SezioneTab;
import tabelle.SupportoGuidaTab;
import tabelle.SupportoMultimedialeTab;
import tabelle.TipologiaTab;
import tabelle.UtilizzoStrumentoTab;
import tabelle.VisitaGruppoTab;
import tabelle.VisitaSingolaTab;
import tabelle.VisitatoreTab;

public class GenericUtilities {

    public static void createTables() {
        new UtilizzoStrumentoTab().dropAndCreateTable();
        new PermanenzaTab().dropAndCreateTable();
        new VisitaSingolaTab().dropAndCreateTable();
        new BigliettoTab().dropAndCreateTable();
        new RestauroTab().dropAndCreateTable();
        new RestauratoreTab().dropAndCreateTable();
        new OperaTab().dropAndCreateTable();
        new SezioneTab().dropAndCreateTable();
        new SupportoGuidaTab().dropAndCreateTable();
        new VisitaGruppoTab().dropAndCreateTable();
        new GuidaTab().dropAndCreateTable();
        new SupportoMultimedialeTab().dropAndCreateTable();
        new AutoreTab().dropAndCreateTable();
        new MuseoTab().dropAndCreateTable();
        new VisitatoreTab().dropAndCreateTable();
        new TipologiaTab().dropAndCreateTable();
        new ConstraintManager().addConstraint();
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    public static List<Personale> getAllStaffs(int status, int codMuseo) {
        List<Personale> staff = null;
        DBConnection conn = new DBConnection();
        Connection connection = conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = null;
        if (codMuseo!=0) {
            query = "SELECT codMuseo,numeroBadge,nome,cognome,inizioLavoro,fineLavoro,mail,numeroTelefono FROM guida WHERE fineLavoro IS NULL "
                    + "UNION "
                    + "SELECT codMuseo,numeroBadge,nome,cognome,inizioLavoro,fineLavoro,mail,numeroTelefono FROM restauratore "
                    + "WHERE fineLavoro IS NULL and codMuseo = ?";
        } else {
            if (status == 0) {
                query = "SELECT codMuseo,numeroBadge,nome,cognome,inizioLavoro,fineLavoro,mail,numeroTelefono FROM guida UNION "
                        + "SELECT codMuseo,numeroBadge,nome,cognome,inizioLavoro,fineLavoro,mail,numeroTelefono FROM restauratore";
            } else if (status == 1) {
                query = "SELECT codMuseo,numeroBadge,nome,cognome,inizioLavoro,fineLavoro,mail,numeroTelefono FROM guida WHERE fineLavoro IS NULL "
                        + "UNION "
                        + "SELECT codMuseo,numeroBadge,nome,cognome,inizioLavoro,fineLavoro,mail,numeroTelefono FROM restauratore "
                        + "WHERE fineLavoro IS NULL";
            } else if (status == 2) {
                query = "SELECT codMuseo,numeroBadge,nome,cognome,inizioLavoro,fineLavoro,mail,numeroTelefono FROM guida WHERE fineLavoro IS NOT NULL "
                        + "UNION "
                        + "SELECT codMuseo,numeroBadge,nome,cognome,inizioLavoro,fineLavoro,mail,numeroTelefono FROM restauratore "
                        + "WHERE fineLavoro IS NOT NULL";
            } else if (status > 100) {
                query = "SELECT codMuseo,numeroBadge,codFiscale,nome,cognome,inizioLavoro,dataNascita,fineLavoro,mail,numeroTelefono "
                        + "FROM guida WHERE codMuseo = ? "
                        + "UNION "
                        + "SELECT codMuseo,numeroBadge,codFiscale,nome,cognome,inizioLavoro,dataNascita,fineLavoro,mail,numeroTelefono "
                        + "FROM restauratore WHERE codMuseo = ?";
            }
        }
        try {
            if (codMuseo!=0) {
                statement = connection.prepareStatement(query);
                statement.setInt(1, codMuseo);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    staff = new LinkedList<>();
                    staff.add(new Personale(result.getInt("codMuseo"), "", result.getString("nome"), result.getString("cognome"),
                            Date.valueOf(LocalDate.now()), result.getString("mail"), result.getInt("numeroBadge"), result.getString("numerotelefono"),
                            result.getDate("inizioLavoro"), result.getDate("fineLavoro")));
                }
                while (result.next()) {
                    staff.add(new Personale(result.getInt("codMuseo"), "", result.getString("nome"), result.getString("cognome"),
                            Date.valueOf(LocalDate.now()), result.getString("mail"), result.getInt("numeroBadge"), result.getString("numerotelefono"),
                            result.getDate("inizioLavoro"), result.getDate("fineLavoro")));
                }
            } else {
                if (status > 100) {
                    statement = connection.prepareStatement(query);
                    statement.setInt(1, status);
                    statement.setInt(2, status);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        staff = new LinkedList<>();
                        staff.add(new Personale(result.getInt("codMuseo"), result.getString("codFiscale"), result.getString("nome"),
                                result.getString("cognome"), result.getDate("dataNascita"), result.getString("mail"), result.getInt("numeroBadge"),
                                result.getString("numerotelefono"), result.getDate("inizioLavoro"), result.getDate("fineLavoro")));
                    }
                    while (result.next()) {
                        staff.add(new Personale(result.getInt("codMuseo"), result.getString("codFiscale"), result.getString("nome"),
                                result.getString("cognome"), result.getDate("dataNascita"), result.getString("mail"), result.getInt("numeroBadge"),
                                result.getString("numerotelefono"), result.getDate("inizioLavoro"), result.getDate("fineLavoro")));
                    }
                } else {
                    statement = connection.prepareStatement(query);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        staff = new LinkedList<>();
                        staff.add(new Personale(result.getInt("codMuseo"), "", result.getString("nome"), result.getString("cognome"),
                                Date.valueOf(LocalDate.now()), result.getString("mail"), result.getInt("numeroBadge"), result.getString("numerotelefono"),
                                result.getDate("inizioLavoro"), result.getDate("fineLavoro")));
                    }
                    while (result.next()) {
                        staff.add(new Personale(result.getInt("codMuseo"), "", result.getString("nome"), result.getString("cognome"),
                                Date.valueOf(LocalDate.now()), result.getString("mail"), result.getInt("numeroBadge"), result.getString("numerotelefono"),
                                result.getDate("inizioLavoro"), result.getDate("fineLavoro")));
                    }
                }
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
        return staff;
    }
    
    public static boolean hasVisited(int codMuseo, int codBiglietto) {
        boolean visite = false;
        DBConnection conn = new DBConnection();
        Connection connection = conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query = "select * from visitaSingola where codMuseo = ? and codBiglietto = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codMuseo);
            statement.setInt(2, codBiglietto);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                visite = true;
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
    
    public static void setDataLic(int codMuseo, int numeroBadge) {
        DBConnection conn = new DBConnection();
        Connection connection = conn.getMsSQLConnection();
        PreparedStatement statement = null;
        String query1 = "UPDATE guida SET fineLavoro = ? WHERE codMuseo = ? AND numeroBadge = ?";
        String query2 = "UPDATE restauratore SET fineLavoro = ? WHERE codMuseo = ? AND numeroBadge = ?";
        try {
            statement = connection.prepareStatement(query1);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setInt(2, codMuseo);
            statement.setInt(3, numeroBadge);
            statement.executeQuery();
        } catch (SQLException e) {
            new Exception(e.getMessage());
            System.out.println("Errore"+ e.getMessage());
        }
        try {
            statement = connection.prepareStatement(query2);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setInt(2, codMuseo);
            statement.setInt(3, numeroBadge);
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
