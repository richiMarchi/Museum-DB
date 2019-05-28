package entità;

import java.sql.Date;

public class VisitaSingola {

    private int codMuseo;
    private int codBiglietto;
    private Date dataUtilizzo;
    private int numeroProgressivo;

    public VisitaSingola(int codMuseo, int codBiglietto, Date dataUtilizzo, int numeroProgressivo) {
        this.codMuseo = codMuseo;
        this.codBiglietto = codBiglietto;
        this.dataUtilizzo = dataUtilizzo;
        this.numeroProgressivo = numeroProgressivo;
    }

    public int getCodMuseo() {
        return codMuseo;
    }

    public void setCodMuseo(int codMuseo) {
        this.codMuseo = codMuseo;
    }

    public int getCodBiglietto() {
        return codBiglietto;
    }

    public void setCodBiglietto(int codBiglietto) {
        this.codBiglietto = codBiglietto;
    }

    public Date getDataUtilizzo() {
        return dataUtilizzo;
    }

    public void setDataUtilizzo(Date dataUtilizzo) {
        this.dataUtilizzo = dataUtilizzo;
    }

    public int getNumeroProgressivo() {
        return numeroProgressivo;
    }

    public void setNumeroProgressivo(int numeroProgressivo) {
        this.numeroProgressivo = numeroProgressivo;
    }

}
