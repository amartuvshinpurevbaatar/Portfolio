import java.io.Serializable;
import java.util.LinkedList;

public class ListePersonne implements Serializable {
    private LinkedList<Personne> listpersonne;

    public ListePersonne() {
        listpersonne = new LinkedList<Personne>();
    }

    public int ajouter_Personne(Personne P) {
        listpersonne.add(P);
        return 1;
    }

    public int supprimer_Personne(Object P) {
        for (Personne personne : listpersonne) {
            if (P instanceof PersonneMorale) {
                PersonneMorale p1 = (PersonneMorale) P;
                PersonneMorale p2 = (PersonneMorale) personne;
                if (p1.getRS().equals(p2.getRS())) {
                    listpersonne.remove(personne);
                    return 1;
                }
            }

            if (P instanceof PersonnePhysique) {
                PersonnePhysique p1 = (PersonnePhysique) P;
                PersonnePhysique p2 = (PersonnePhysique) personne;
                if (p1.getNom().equals(p2.getNom()) && p1.getAge() == p2.getAge()) {
                    listpersonne.remove(personne);
                    return 1;
                }
            }
        }
        return 0;
    }

    public int ajouter_Personne1(int i) {
        if (i == 1) {
            PersonnePhysique p = new PersonnePhysique();
            p.creerPersonnePhysique();
            return 1;
        }

        if (i == 2) {
            PersonneMorale p = new PersonneMorale();
            p.creerPersonneMorale();
            return 1;
        }

        return 0;
    }

    public void afficher_tout() {
        for (Personne personne : listpersonne) {
            if (personne instanceof PersonnePhysique) {
                PersonnePhysique p = (PersonnePhysique) personne;
                System.out
                        .println("[ NOM : " + p.getNom() + " ] [ PRENOM : " + p.getPrenom() + " ] [ AGE : " + p.getAge()
                                + " ]");
            }

            if (personne instanceof PersonneMorale) {
                PersonneMorale p = (PersonneMorale) personne;
                System.out
                        .println("[ RAISON SOCIALE : " + p.getRS() + " ] [ FORME JURIDIQUE : " + p.getFJ()
                                + " ] [ CODE SIRET : " + p.getCS() + " ]");
            }
        }
    }
}
