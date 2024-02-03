import java.io.Serializable;

public abstract class Personne implements Serializable {
    private String nom;
    private CompteBancaire cb;

    public Personne(String nom, CompteBancaire compte) {
        this.nom = nom;
        this.cb = compte;
    }

    public String getNom() {
        return nom;
    }

    public CompteBancaire getCb() {
        return cb;
    }

    public void setCb(CompteBancaire cb) {
        this.cb = cb;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
