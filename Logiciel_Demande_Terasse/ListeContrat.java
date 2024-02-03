import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class ListeContrat implements Serializable {
    LinkedList<Contrat> listeContrat;
    int ID = 0;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    public ListeContrat() {
        this.listeContrat = new LinkedList<Contrat>();
    }

    public Contrat chercher_par_ID_Demand(int ID) {
        for (Contrat elm : listeContrat) {
            if (elm.getDemande().getID() == ID) {
                return elm;
            }
        }

        return null;
    }

    public int ajouter_contrat(Contrat C) {
        this.ID++;
        C.setID(this.ID);
        this.listeContrat.add(C);
        return 1;
    }

    public void afficher_tout() {
        for (Contrat elm : listeContrat) {
            elm.afficher();
            if (elm.getAccepter() == 1 && elm.getSignature() == 1) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String date = df.format(elm.getAcceptation());
                System.out
                        .println(GREEN + "[ ACCEPTÉ ET SIGNÉ ] " + ANSI_RESET + "[ DATE ACCEPTATION : " + date + " ]");
            } else if (elm.getAccepter() == -1) {
                System.out.println(RED + "[ REFUSÉ ] ");
            } else if (elm.getAccepter() == 1 && elm.getSignature() == 0) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String date = df.format(elm.getAcceptation());
                System.out
                        .println(YELLOW + "[ ACCEPTÉ ] " + ANSI_RESET + "[ DATE ACCEPTATION : " + date + " ]");
            }
        }
    }
}
