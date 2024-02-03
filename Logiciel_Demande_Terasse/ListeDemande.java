import java.io.Serializable;
import java.util.LinkedList;
import java.util.Scanner;

public class ListeDemande implements Serializable {
    private LinkedList<Demande> listeDemande;
    private int ID = 0;

    public ListeDemande() {
        this.listeDemande = new LinkedList<Demande>();
    }

    public int getID() {
        return ID;
    }

    public void setID() {
        this.ID = this.ID + 1;
    }

    public int ajouter_demande(Etablissement E) {
        Demande D = new Demande();
        D = D.creerDemande(E);
        this.setID();
        D.setID(this.ID);

        this.listeDemande.add(D);
        return 1;
    }

    public int ajouter_demande(Demande D) {
        this.listeDemande.add(D);
        return 1;
    }

    public int supprimer_demande() {
        Scanner sc = new Scanner(System.in);
        System.out.println("[CHOISIR LA DEMANDE]:");
        this.afficher_tout();
        int demande = sc.nextInt();
        for (Demande elm : listeDemande) {
            if (elm.getID() == demande) {
                this.listeDemande.remove(elm);
                return 1;
            }
        }

        return 0;
    }

    public int supprimer_demande(Demande D) {
        for (Demande elm : listeDemande) {
            if (elm.getID() == D.getID()) {
                this.listeDemande.remove(elm);
                return 1;
            }
        }

        return 0;
    }

    public void afficher_tout() {
        for (Demande elm : this.listeDemande) {
            System.out.println(
                    "[ ID : " + elm.getID() + " ] " + "[ " + elm.getEtat() + " ] " + "[ " + elm.getCommentaire()
                            + " ] " + "[ " + elm.getEtablissement().getAppelation() + " ] ");
        }
    }

    public void afficher_tout_service() {
        for (Demande elm : this.listeDemande) {
            System.out.println(
                    "[ ID : " + elm.getID() + " ] " + "[ " + elm.getEtat() + " ] " + "[ " + elm.getCommentaire()
                            + " ] " + "[ " + elm.getEtablissement().getAppelation() + " ] " + "[ "
                            + elm.getRapport() + " ] ");
        }
    }

    public void afficher_tout_parEtab(String Etablissement) {
        for (Demande elm : listeDemande) {
            if (elm.getEtablissement().getAppelation().equals(Etablissement)) {
                System.out.println(
                        "[ ID : " + elm.getID() + " ] " + "[ " + elm.getEtat() + " ] " + "[ " + elm.getCommentaire()
                                + " ] " + "[ " + elm.getEtablissement().getAppelation() + " ] ");
            }
        }
    }

    public int afficher_tout_parEtat(String attente) {
        int i = 0;
        for (Demande elm : listeDemande) {
            if (elm.getEtat().equals(attente)) {
                System.out.println(
                        "[ ID : " + elm.getID() + " ] " + "[ " + elm.getEtat() + " ] " + "[ " + elm.getCommentaire()
                                + " ] " + "[ " + elm.getZone() + " ] " + "[ " + elm.getSurface() + " ] ");
                i++;
            }
        }
        return i;
    }

    public Demande chercher_demande(int ID) {
        for (Demande elm : listeDemande) {
            if (elm.getID() == ID)
                return elm;
        }

        return null;
    }
}
