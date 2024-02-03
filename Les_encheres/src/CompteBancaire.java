import java.io.Serializable;
import java.util.Random;

public class CompteBancaire implements Serializable {
    private String nom;
    private String numCompte;
    private int solde;

    private final String[] numCompteRandom = { "FR76 6074 3450 4002 1232 8223 124", "FR76 6074 3450 4002 1237 8233 129",
            "US64 7621 0876 7642 6327 786", "AU32 7654 8087 2138 47381 6712 999" };

    private final int[] soldeRandom = { 5000, 10000, 78430, 2000000 };

    public CompteBancaire(String nom) {
        this.nom = nom;
        this.numCompte = getRandnumCompte();
        this.solde = getRandSolde();
    }

    public String getNom() {
        return nom;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public int getSolde() {
        return solde;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public void setRandomNumCompte() {

    }

    public String getRandnumCompte() {
        Random rand = new Random();
        int upperbound = 4;
        int int_random = rand.nextInt(upperbound);

        return this.numCompteRandom[int_random];
    }

    public int getRandSolde() {
        Random rand = new Random();
        int upperbound = 4;
        int int_random = rand.nextInt(upperbound);

        return this.soldeRandom[int_random];
    }
}
