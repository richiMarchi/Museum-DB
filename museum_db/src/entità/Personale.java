package entità;

import java.sql.Date;

public class Personale extends Persona {

    private int codMuseo;
    private int numeroBadge;
    private String numeroTelefono;
    private Date inizioLavoro;
    private Date fineLavoro;

    public Personale(int codMuseo, String codFiscale, String nome, String cognome, Date dataNascita, String mail,
            int numeroBadge, String numeroTelefono, Date inizioLavoro, Date fineLavoro) {
        super(codFiscale, nome, cognome, dataNascita, mail);
        this.codMuseo = codMuseo;
        this.numeroBadge = numeroBadge;
        this.numeroTelefono = numeroTelefono;
        this.inizioLavoro = inizioLavoro;
        this.fineLavoro = fineLavoro;
    }

    public int getCodMuseo() {
        return codMuseo;
    }

    public void setCodMuseo(int codMuseo) {
        this.codMuseo = codMuseo;
    }

    public int getNumeroBadge() {
        return numeroBadge;
    }

    public void setNumeroBadge(int numeroBadge) {
        this.numeroBadge = numeroBadge;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public Date getInizioLavoro() {
        return inizioLavoro;
    }

    public void setInizioLavoro(Date inizioLavoro) {
        this.inizioLavoro = inizioLavoro;
    }

    public Date getFineLavoro() {
        return fineLavoro;
    }

    public void setFineLavoro(Date fineLavoro) {
        this.fineLavoro = fineLavoro;
    }
}
