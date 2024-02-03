import java.util.LinkedList;

public class Init_tout {
    private ListeCompte LC = new ListeCompte();
    private ListeDemande LD = new ListeDemande();

    public Init_tout() {
        final Personne admin1 = new PersonnePhysique("Purevbaatar", "Amartuvshin", 22, null, "Mr");
        LinkedList<Role> roleadmin1 = new LinkedList<Role>();
        roleadmin1.add(new Role("ADMIN"));
        Compte compteAdmin1 = new Compte("admin1", "admin", admin1, roleadmin1);
        LC.ajouter_compte_admin(compteAdmin1);

        Etablissement ET2 = new Etablissement("CAFE", "2 RUE d'Amiens", 2);
        Etablissement ET1 = new Etablissement("CARAMEL", "1 RUE d'Amiens", 1);
        Etablissement ET3 = new Etablissement("BAR", "3 RUE d'Amiens", 3);
        Etablissement ET4 = new Etablissement("RESTAU", "4 RUE d'Amiens", 4);
        Etablissement ET5 = new Etablissement("DISCO", "5 RUE d'Amiens", 5);

        listeEtablissement LE1 = new listeEtablissement();
        LE1.ajouter_Etablissement(ET1);
        LE1.ajouter_Etablissement(ET2);
        LE1.ajouter_Etablissement(ET3);
        LE1.ajouter_Etablissement(ET4);
        LE1.ajouter_Etablissement(ET5);

        PersonnePhysique PP1 = new PersonnePhysique("Purevbaatar", "Amartuvshin", 22, null, "Mr");
        PersonnePhysique PP2 = new PersonnePhysique("Sbai Kriat", "Mohamed", 22, LE1, "Mr");
        PersonneMorale PM1 = new PersonneMorale("Chicken", "Online", 2, "RUE de Paris", new listeEtablissement());
        PersonnePhysique PP3 = new PersonnePhysique("Surveillant", "", 30, null, "");

        LinkedList<Role> LR1 = new LinkedList<Role>();
        LR1.add(new Role("SERVICE"));
        LinkedList<Role> LR2 = new LinkedList<Role>();
        LR2.add(new Role("EXPLOITANT"));
        LinkedList<Role> LR3 = new LinkedList<Role>();
        LR3.add(new Role("SURVEILLANT"));
        LinkedList<Role> LR4 = new LinkedList<Role>();
        LR4.add(new Role("EXPLOITANT"));
        LR4.add(new Role("SURVEILLANT"));

        Compte C1 = new Compte("Amartuvshin", "amka", PP1, LR1);
        Compte C2 = new Compte("Mohamed", "momo", PP2, LR2);
        Compte C3 = new Compte("Surveillant", "surv", PP3, LR3);
        Compte C4 = new Compte("Moral", "moral", PM1, LR4);

        LC.ajouter_compte(C1);
        LC.ajouter_compte(C2);
        LC.ajouter_compte(C3);
        LC.ajouter_compte(C4);

        Demande D1 = new Demande(ET1, "Permanente", "A", 23, "23 / 12 / 2022");
        LD.ajouter_demande(D1);
    }

    public ListeCompte getListeCompteInit() {
        return LC;
    }

    public ListeDemande getListeDemadeInit() {
        return LD;
    }
}
