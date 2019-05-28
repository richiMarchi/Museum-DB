package entità;

public class Sezione {

    private int codMuseo;
    private String nomeSezione;
    private int piano;

    public Sezione(int codMuseo, String nomeSezione, int piano) {
        this.codMuseo = codMuseo;
        this.nomeSezione = nomeSezione;
        this.piano = piano;
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

    public int getPiano() {
        return piano;
    }

    public void setPiano(int piano) {
        this.piano = piano;
    }

    
}
