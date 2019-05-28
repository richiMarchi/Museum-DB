package entità;

import java.sql.Date;

public class VisitaGruppo {

    private String codFiscale;
    private Date data;
    private int oraInizio;
    private int codGruppo;
    private int codMuseo;

    public VisitaGruppo(String codFiscale, Date data, int oraInizio, int codGruppo, int codMuseo) {
        this.codFiscale = codFiscale;
        this.data = data;
        this.oraInizio = oraInizio;
        this.codGruppo = codGruppo;
        this.codMuseo = codMuseo;
    }
    
    public String getCodFiscale() {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(int oraInizio) {
        this.oraInizio = oraInizio;
    }

    public int getCodGruppo() {
        return this.codGruppo;
    }

    public void setCodGruppo(int codGruppo) {
        this.codGruppo = codGruppo;
    }

    public int getCodMuseo() {
        return codMuseo;
    }

    public void setCodMuseo(int codMuseo) {
        this.codMuseo = codMuseo;
    }
    
}
