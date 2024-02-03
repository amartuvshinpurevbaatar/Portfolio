import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Run {
    private ListeCompte Comptes;
    private ListeDemande Demandes;
    private ListeContrat Contrats;

    public Run() {
        this.Comptes = new ListeCompte();
        this.Demandes = new ListeDemande();
        this.Contrats = new ListeContrat();

        try {
            FileInputStream fis = new FileInputStream("Source/SaveCompte");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.Comptes = (ListeCompte) ois.readObject();
            ois.close();
            fis = new FileInputStream("Source/SaveDemande");
            ois = new ObjectInputStream(fis);
            this.Demandes = (ListeDemande) ois.readObject();
            ois.close();
            fis = new FileInputStream("Source/SaveContrat");
            ois = new ObjectInputStream(fis);
            this.Contrats = (ListeContrat) ois.readObject();
        } catch (Exception e) {
        }

        Init_tout initilisation = new Init_tout();
        this.Comptes = initilisation.getListeCompteInit();
        this.Demandes = initilisation.getListeDemadeInit();
        this.Contrats = new ListeContrat();

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("\t [ 1 ] Se connecter\n" +
                    "\t [ -1 ] Sortir\n");
            int n = sc.nextInt();

            if (n == -1) {
                break;
            } else if (n == 1) {
                Connexion Connecter = new Connexion(Comptes, Demandes, Contrats);
                while (Connecter.run()) {
                }
                Comptes = Connecter.getListCompte();
                Demandes = Connecter.getListDemande();
                Contrats = Connecter.getListContrat();
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream("Source/SaveCompte");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(Comptes);
            oos.close();

            fos = new FileOutputStream("Source/SaveDemande");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(Demandes);
            oos.close();

            fos = new FileOutputStream("Source/SaveContrat");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(Contrats);
            oos.close();

        } catch (Exception e) {
        }
    }
}
