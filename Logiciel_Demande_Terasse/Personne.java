import java.io.Serializable;

public abstract class Personne implements Serializable {
    private listeEtablissement listeEtablissement;

    public Personne(listeEtablissement listeE) {
        this.listeEtablissement = listeE;
    }

    public Personne() {

    }

    public listeEtablissement get_listeEtablissement() {
        return this.listeEtablissement;
    }

    public void setEtablissement(listeEtablissement E) {
        this.listeEtablissement = E;
    }
}
