package entità;

public class Museo {

    private int codMuseo;
    private String nomeMuseo;
    private String città;
    private String via_piazza;

    public Museo(int codMuseo, String nomeMuseo, String città, String via_piazza) {
        this.codMuseo = codMuseo;
        this.nomeMuseo = nomeMuseo;
        this.città = città;
        this.via_piazza = via_piazza;
    }

    public int getCodMuseo() {
        return codMuseo;
    }

    public void setCodMuseo(int codMuseo) {
        this.codMuseo = codMuseo;
    }

    public String getNomeMuseo() {
        return nomeMuseo;
    }

    public void setNomeMuseo(String nomeMuseo) {
        this.nomeMuseo = nomeMuseo;
    }

    public String getCittà() {
        return città;
    }

    public void setCittà(String città) {
        this.città = città;
    }

    public String getVia_piazza() {
        return via_piazza;
    }

    public void setVia_piazza(String via_piazza) {
        this.via_piazza = via_piazza;
    }
    
}
