import java.io.Serializable;
import java.util.Scanner;

public class Etablissement implements Serializable {
    private String appelation;
    private String adresse;
    private int ID;
    private listeTerasse listeTerasse;

    public Etablissement(String NOM, String ADR, int ID) {
        this.appelation = NOM;
        this.adresse = ADR;
        this.ID = ID;
        this.listeTerasse = new listeTerasse();
    }

    public Etablissement() {
    }

    public String getAppelation() {
        return this.appelation;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public int getID() {
        return this.ID;
    }

    public listeTerasse getlisteTerrase() {
        return this.listeTerasse;
    }

    public void setAppelation(String App) {
        this.appelation = App;
    }

    public void setAdresse(String Add) {
        this.adresse = Add;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public Etablissement creerEtablissement() {
        Scanner sc = new Scanner(System.in);
        System.out.print("[ APPELATION ] : ");
        String nom = sc.nextLine();
        System.out.print("[ ADRESSE ] : ");
        String adr = sc.nextLine();
        System.out.print("[ ID ] : ");
        int id = sc.nextInt();

        return new Etablissement(nom, adr, id);
    }
}
