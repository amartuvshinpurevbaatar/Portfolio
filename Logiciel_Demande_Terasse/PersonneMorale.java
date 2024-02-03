import java.io.Serializable;
import java.util.Scanner;

public class PersonneMorale extends Personne implements Serializable {
    private String raisonSociale, formeJuridique;
    private int codeSiret;

    public PersonneMorale(String RS, String FJ, int CS, String ADR, listeEtablissement LE) {
        super(LE);
        this.raisonSociale = RS;
        this.formeJuridique = FJ;
        this.codeSiret = CS;
    }

    public PersonneMorale() {
    }

    public String getRS() {
        return this.raisonSociale;
    }

    public String getFJ() {
        return this.formeJuridique;
    }

    public int getCS() {
        return this.codeSiret;
    }

    public void setRS(String RS) {
        this.raisonSociale = RS;
    }

    public void setFJ(String FJ) {
        this.formeJuridique = FJ;
    }

    public void setCS(int CS) {
        this.codeSiret = CS;
    }

    public PersonneMorale creerPersonneMorale() {
        Scanner sc = new Scanner(System.in);
        System.out.println("[ RAISON SOCIALE ] :");
        String RS = sc.nextLine();
        System.out.println("[ FORME JURIDIQUE ] :");
        String FJ = sc.nextLine();
        System.out.println("[ CODE SIRET ] :");
        int CS = sc.nextInt();
        System.out.print("[ ADRESSE ] : ");
        String ADR = sc.nextLine();
        listeEtablissement LE = new listeEtablissement();

        return new PersonneMorale(RS, FJ, CS, ADR, LE);
    }
}
