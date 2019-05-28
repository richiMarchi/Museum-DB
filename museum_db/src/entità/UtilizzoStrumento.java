package entità;

import java.sql.Date;

public class UtilizzoStrumento {

    private Date dataUtilizzo;
    private int numeroProgressivo;
    private int codStrumento;
    private int codMuseo;
    private int oraInizio;

    public UtilizzoStrumento(Date dataUtilizzo, int numeroProgressivo, int codStrumento, int codMuseo, int oraInizio) {
        this.dataUtilizzo = dataUtilizzo;
        this.numeroProgressivo = numeroProgressivo;
        this.codStrumento = codStrumento;
        this.codMuseo = codMuseo;
        this.oraInizio = oraInizio;
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

    public int getCodStrumento() {
        return codStrumento;
    }

    public void setCodStrumento(int codStrumento) {
        this.codStrumento = codStrumento;
    }

    public int getCodMuseo() {
        return codMuseo;
    }

    public void setCodMuseo(int codMuseo) {
        this.codMuseo = codMuseo;
    }

    public int getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(int oraInizio) {
        this.oraInizio = oraInizio;
    }
}
