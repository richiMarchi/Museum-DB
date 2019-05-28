package entità;

public class Autore {

    private int codAutore;
    private String nome_cognome;
    private String pseudonimo;

    public Autore(int codAutore, String nome_cognome, String pseudonimo) {
        this.codAutore = codAutore;
        this.nome_cognome = nome_cognome;
        this.pseudonimo = pseudonimo;
    }

    public int getCodAutore() {
        return codAutore;
    }

    public void setCodAutore(int codAutore) {
        this.codAutore = codAutore;
    }

    public String getNome_cognome() {
        return nome_cognome;
    }

    public void setNome_cognome(String nome_cognome) {
        this.nome_cognome = nome_cognome;
    }

    public String getPseudonimo() {
        return pseudonimo;
    }

    public void setPseudonimo(String pseudonimo) {
        this.pseudonimo = pseudonimo;
    }
    
}
