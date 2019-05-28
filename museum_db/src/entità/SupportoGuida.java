package entità;

import java.sql.Date;

public class SupportoGuida implements Supporto {

    private int numeroGruppo;
    private int codMuseo;
    private String codFiscale;
    private Date data;
    private int oraInizio;

    public SupportoGuida(int numeroGruppo, String codFiscale, Date data, int oraInizio, int codMuseo) {
        this.numeroGruppo = numeroGruppo;
        this.codFiscale = codFiscale;
        this.data = data;
        this.oraInizio = oraInizio;
        this.codMuseo = codMuseo;
    }

    public int getNumeroGruppo() {
        return numeroGruppo;
    }

    public void setNumeroGruppo(int numeroGruppo) {
        this.numeroGruppo = numeroGruppo;
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

    public int getCodMuseo() {
        return codMuseo;
    }

    public void setCodMuseo(int codMuseo) {
        this.codMuseo = codMuseo;
    }
    
}
