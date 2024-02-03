import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class listeEtablissement implements Serializable {
    private LinkedList<Etablissement> listeEtablissement;
    private LinkedList<DateOccuopation> listeDateOccuopations;

    public listeEtablissement() {
        listeEtablissement = new LinkedList<Etablissement>();
        listeDateOccuopations = new LinkedList<DateOccuopation>();
    }

    public int ajouter_Etablissement(Etablissement E) {
        for (Etablissement e : listeEtablissement) {
            if (e.getID() == E.getID()) {
                return 0;
            }
        }

        listeEtablissement.add(E);
        LocalDate now = java.time.LocalDate.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        DateOccuopation DATE = new DateOccuopation(formattedDate, E);
        listeDateOccuopations.add(DATE);

        return 1;
    }

    public int ajouter_Etablissement_V2() {

        Etablissement E = new Etablissement();

        for (Etablissement e : listeEtablissement) {
            if (e.getID() == E.getID()) {
                return 0;
            }
        }

        listeEtablissement.add(E.creerEtablissement());
        LocalDate now = java.time.LocalDate.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        DateOccuopation DATE = new DateOccuopation(formattedDate, E);
        listeDateOccuopations.add(DATE);

        return 1;
    }

    public int supprimer_Etablissement(int ID) {
        for (Etablissement e : listeEtablissement) {
            if (e.getID() == ID) {
                for (DateOccuopation elm : listeDateOccuopations) {
                    if (elm.geEtablissement().getID() == ID && elm.getDFin() == null) {

                        LocalDate now = java.time.LocalDate.now();
                        String formattedDate = now.format(DateTimeFormatter.ofPattern("dd/MM/yy"));

                        elm.setDFin(formattedDate);
                    }
                    listeEtablissement.remove(e);
                }
                return 1;
            }
        }

        return 0;
    }

    public void afficher_tout() {
        if (listeDateOccuopations.size() == 0) {
            System.out.println("Vous exploitez aucune établissement!");
        }
        for (Etablissement e : listeEtablissement) {
            System.out.println(
                    "[ ID ] : " + e.getID() + " [ NOM ] : " + e.getAppelation() + " [ ADRESSE ] : " + e.getAdresse());
        }

    }

    public Etablissement trouverEtablissement(int ID) {
        for (Etablissement E : listeEtablissement) {
            if (E.getID() == ID) {
                return E;
            }
        }

        return null;
    }

}
