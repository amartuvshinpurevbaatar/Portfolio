import java.io.Serializable;
import java.util.Scanner;

public class PersonnePhysique extends Personne implements Serializable {
    private String nom, prenom;
    private int age;
    private String civilite;

    public PersonnePhysique(String name, String prename, int n, listeEtablissement LE, String civ) {
        super(LE);
        this.nom = name;
        this.prenom = prename;
        this.age = n;
        this.civilite = civ;
    }

    public PersonnePhysique() {
        super();
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public int getAge() {
        return this.age;
    }

    public String getCivilite() {
        return this.civilite;
    }

    public void setNom(String name) {
        this.nom = name;
    }

    public void setPrenom(String prename) {
        this.prenom = prename;
    }

    public void setAge(int n) {
        this.age = n;
    }

    public void setCivilite(String civ) {
        this.civilite = civ;
    }

    public PersonnePhysique creerPersonnePhysique() {
        Scanner sc = new Scanner(System.in);

        System.out.print("[ NOM ] : ");
        String nom = sc.nextLine();

        System.out.print("[ PRENOM ] : ");
        String prenom = sc.nextLine();

        System.out.print("[ AGE ] : ");
        int age = sc.nextInt();

        System.out.print("[ CIVILITE ] : ");
        String civ = sc.nextLine();

        listeEtablissement LE = new listeEtablissement();
        return new PersonnePhysique(nom, prenom, age, LE, civ);
    }
}