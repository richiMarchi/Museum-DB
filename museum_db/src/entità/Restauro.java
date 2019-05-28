package entità;

import java.sql.Date;

public class Restauro {

    private String codFiscale;
    private int codAutore;
    private String nomeOpera;
    private Date dataInizio;
    private Date dataFine;
    private int numeroBadge;

    public Restauro(String codFiscale, int codAutore, String nomeOpera, Date dataInizio, Date dataFine) {
        this.codFiscale = codFiscale;
        this.codAutore = codAutore;
        this.nomeOpera = nomeOpera;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public int getCodAutore() {
        return codAutore;
    }

    public void setCodAutore(int codAutore) {
        this.codAutore = codAutore;
    }

    public String getNomeOpera() {
        return nomeOpera;
    }

    public void setNomeOpera(String nomeOpera) {
        this.nomeOpera = nomeOpera;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public int getNumeroBadge() {
        return numeroBadge;
    }

    public void setNumeroBadge(int numeroBadge) {
        this.numeroBadge = numeroBadge;
    }
    
}
