package entità;

import java.sql.Date;

public class Permanenza {

    private int codMuseo;
    private String nomeSezione;
    private int codAutore;
    private String nomeOpera;
    private Date dataInserimento;
    private Date dataRimozione;
    private int anno;
    private String tipologia;

    public Permanenza(int codMuseo, String nomeSezione, int codAutore, String nomeOpera, Date dataInserimento, Date dataRimozione) {
        this.codMuseo = codMuseo;
        this.nomeSezione = nomeSezione;
        this.codAutore = codAutore;
        this.nomeOpera = nomeOpera;
        this.dataInserimento = dataInserimento;
        this.dataRimozione = dataRimozione;
    }
    
    public int getCodMuseo() {
        return codMuseo;
    }

    public void setCodMuseo(int codMuseo) {
        this.codMuseo = codMuseo;
    }

    public String getNomeSezione() {
        return nomeSezione;
    }

    public void setNomeSezione(String nomeSezione) {
        this.nomeSezione = nomeSezione;
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

    public Date getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public Date getDataRimozione() {
        return dataRimozione;
    }

    public void setDataRimozione(Date dataRimozione) {
        this.dataRimozione = dataRimozione;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }
    
}
