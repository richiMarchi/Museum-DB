package entità;

public class SupportoMultimediale implements Supporto {

    private int codMuseo;
    private int codStrumento;

    public SupportoMultimediale(int codStrumento, int codMuseo) {
        this.codStrumento = codStrumento;
        this.codMuseo = codMuseo;
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
    
}
