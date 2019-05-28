package entità;

import java.sql.Date;

public class Visitatore extends Persona {

    public Visitatore(String codFiscale, String nome, String cognome, Date dataNascita, String mail) {
        super(codFiscale, nome, cognome, dataNascita, mail);
    }

}
