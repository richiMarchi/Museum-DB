package entità;

public class Opera {

    private int codAutore;
    private String nomeTipologia;
    private String nomeOpera;
    private int anno;

    public Opera(int codAutore, String nomeTipologia, String nomeOpera, int anno) {
        this.codAutore = codAutore;
        this.nomeTipologia = nomeTipologia;
        this.nomeOpera = nomeOpera;
        this.anno = anno;
    }

    public int getCodAutore() {
        return codAutore;
    }

    public void setCodAutore(int codAutore) {
        this.codAutore = codAutore;
    }

    public String getNomeTipologia() {
        return nomeTipologia;
    }

    public void setNomeTipologia(String nomeTipologia) {
        this.nomeTipologia = nomeTipologia;
    }

    public String getNomeOpera() {
        return nomeOpera;
    }

    public void setNomeOpera(String nomeOpera) {
        this.nomeOpera = nomeOpera;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }
    
}
