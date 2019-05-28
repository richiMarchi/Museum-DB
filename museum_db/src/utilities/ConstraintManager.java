package utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import connection.DBConnection;

public class ConstraintManager {

    private DBConnection conn;

    public void addConstraint() {
        this.conn = new DBConnection();
        Connection connection = this.conn.getMsSQLConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("ALTER TABLE biglietto ADD CONSTRAINT FKBIGLIETTO_MUSEO FOREIGN KEY (codMuseo) REFERENCES museo");
            statement.executeUpdate("ALTER TABLE guida ADD CONSTRAINT FKGUIDA_MUSEO FOREIGN KEY (codMuseo) REFERENCES museo");
            statement.executeUpdate("ALTER TABLE opera ADD CONSTRAINT FKOPERA_AUTORE FOREIGN KEY (codAutore) REFERENCES autore");
            statement.executeUpdate("ALTER TABLE permanenza ADD CONSTRAINT FKPERMANENZA_SEZIONE FOREIGN KEY (codMuseo, nomeSezione) "
                    + "REFERENCES sezione");
            statement.executeUpdate("ALTER TABLE permanenza ADD CONSTRAINT FKPERMANENZA_OPERA FOREIGN KEY (codAutore, nomeOpera) REFERENCES opera");
            statement.executeUpdate("ALTER TABLE restauratore ADD CONSTRAINT FKRESTAURATORE_MUSEO FOREIGN KEY (codMuseo) REFERENCES museo");
            statement.executeUpdate("ALTER TABLE restauro ADD CONSTRAINT FKRESTAURO_RESTAURATORE FOREIGN KEY (codFiscale) REFERENCES restauratore");
            statement.executeUpdate("ALTER TABLE restauro ADD CONSTRAINT FKRESTAURO_OPERA FOREIGN KEY (codAutore, nomeOpera) REFERENCES opera");
            statement.executeUpdate("ALTER TABLE sezione ADD CONSTRAINT FKSEZIONE_MUSEO FOREIGN KEY (codMuseo) REFERENCES museo");
            statement.executeUpdate("ALTER TABLE supportoGuida ADD CONSTRAINT FKSUPPORTOGUIDA_GUIDA FOREIGN KEY (codFiscale) REFERENCES guida");
            statement.executeUpdate("ALTER TABLE supportoMultimediale ADD CONSTRAINT FKSUPPORTOMULTIMEDIALE_MUSEO FOREIGN KEY (codMuseo) "
                    + "REFERENCES museo");
            statement.executeUpdate("ALTER TABLE utilizzoStrumento ADD CONSTRAINT FKUTILIZZOSTRUMENTO_VISITASINGOLA "
                                    + "FOREIGN KEY (codMuseo, dataUtilizzo, numeroProgressivo) REFERENCES visitaSingola");
            statement.executeUpdate("ALTER TABLE visitaGruppo ADD CONSTRAINT FKVISITAGRUPPO_SUPPORTOGUIDA "
                                    + "FOREIGN KEY (codFiscale, data, oraInizio) REFERENCES supportoGuida");
            statement.executeUpdate("ALTER TABLE visitaSingola ADD CONSTRAINT FKVISITASINGOLA_BIGLIETTO "
                                    + "FOREIGN KEY (codMuseo, codBiglietto) REFERENCES biglietto");
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
}
