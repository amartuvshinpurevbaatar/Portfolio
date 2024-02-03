import java.io.Serializable;
import java.util.LinkedList;

public class ListeCompte implements Serializable {
    private LinkedList<Compte> ListeCompte;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    public ListeCompte() {
        ListeCompte = new LinkedList<Compte>();
    }

    public int ajouter_compte(Compte C) {
        if (!compteexists(C.getusername())) {
            ListeCompte.add(C);
            return 1;
        }

        System.out.println("LE COMPTE AVEC USERNAME [ " + C.getusername() + " ] EXISTE DEJA!");
        return 0;
    }

    public int ajouter_compte_admin(Compte C) {
        if (!compteexists(C.getusername())) {
            ListeCompte.add(C);
            return 1;
        }
        return 0;
    }

    public int ajouter_compte1() {
        Compte C = new Compte();
        C.creerCompte();
        this.ListeCompte.add(C);
        return 1;
    }

    public boolean compteexists(String UN) {
        for (Compte compte : this.ListeCompte) {
            if (compte.getusername().equals(UN)) {
                return true;
            }
        }
        return false;
    }

    public boolean se_connecter(String UN, String PWD) {
        System.out.print("\n" + YELLOW + "CHARGEMENT EN COURS");
        for (int i = 0; i < 3; i++) {
            System.out.print(" . ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.print(ANSI_RESET + "\n");
        for (Compte compte : this.ListeCompte) {
            if (compte.getusername().equals(UN) && compte.getpassword().equals(PWD)) {
                System.out.println("[ BIENVENUE " + compte.getusername() + " ]");
                return true;
            }
        }

        System.out.println(RED + "USERNAME ou MOT DE PASSE EST INCORRECTE !" + ANSI_RESET);
        return false;
    }

    public int supprimer_Compte(String UN) {
        for (Compte compte : this.ListeCompte) {
            if (compte.getusername().equals(UN)) {
                ListeCompte.remove(compte);
                return 1;
            }
        }

        System.out.println("Le Compte n'existe pas!");
        return 0;
    }

    public int afficher_tout() {
        for (Compte compte : ListeCompte) {
            System.out
                    .println("USERNAME : [ " + compte.getusername() + " ] " + "ROLES :" + compte.afficher_tout_roles());
        }
        return 1;
    }

    public Compte renvoieCompte(String UN, String PWD) {
        for (Compte compte : this.ListeCompte) {
            if (compte.getusername().equals(UN) && compte.getpassword().equals(PWD)) {
                return compte;
            }
        }
        return null;
    }
}
