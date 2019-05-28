package entità;

import java.sql.Date;

public class Guida extends Personale {

    public Guida(int codMuseo, String codFiscale, String nome, String cognome, Date dataNascita, String mail, int numeroBadge,
            String numeroTelefono, Date inizioLavoro, Date fineLavoro) {
        super(codMuseo, codFiscale, nome, cognome, dataNascita, mail, numeroBadge, numeroTelefono, inizioLavoro, fineLavoro);
    }
}
