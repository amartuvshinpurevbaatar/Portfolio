public class Inscription {
    boolean connecter = true;
    ListeCompte Comptes;

    public Inscription(ListeCompte C) {
        this.Comptes = C;
    }

    public boolean run() {

        Compte Compte = new Compte();
        Compte = Compte.creerCompte();
        if (Comptes.ajouter_compte(Compte) == 1) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[ BIENVENUE : " + Compte.getusername() + " ] " + "VÔTRE VOMPTE A ETE BIEN CREE!");

            return connecter = false;
        }

        return connecter;
    }

    public ListeCompte getListCompte() {
        return Comptes;
    }
}
