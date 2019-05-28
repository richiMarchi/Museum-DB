package entità;

import java.sql.Date;

public class Biglietto {

    private int codMuseo;
    private String codFiscale;
    private int codBiglietto;
    private int codGruppo;
    private Date dataAcquisto;
    private int prezzo;

    public Biglietto(int codMuseo, String codFiscale, int codBiglietto, int codGruppo, Date dataAcquisto, int prezzo) {
        this.codMuseo = codMuseo;
        this.codFiscale = codFiscale;
        this.codBiglietto = codBiglietto;
        this.codGruppo = codGruppo;
        this.dataAcquisto = dataAcquisto;
        this.prezzo = prezzo;
    }

    public int getCodMuseo() {
        return codMuseo;
    }

    public void setMuseo(int codMuseo) {
        this.codMuseo = codMuseo;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public int getCodBiglietto() {
        return codBiglietto;
    }

    public void setCodBiglietto(int codBiglietto) {
        this.codBiglietto = codBiglietto;
    }

    public Date getData() {
        return dataAcquisto;
    }

    public void setData(Date data) {
        this.dataAcquisto = data;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }
    
    public int getCodGruppo() {
        return codGruppo;
    }
    
    public void setCodGruppo(int codGruppo) {
        this.codGruppo = codGruppo;
    }
}
