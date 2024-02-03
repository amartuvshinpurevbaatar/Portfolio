import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class listeTerasse {
    private LinkedList<Terasse> listeTerasse;
    int ID;

    public listeTerasse() {
        this.listeTerasse = new LinkedList<Terasse>();
        this.ID = 0;
    }

    public LinkedList<Terasse> getListeTerasse() {
        return this.listeTerasse;
    }

    public void setListeTerasse(LinkedList<Terasse> LT) {
        this.listeTerasse = LT;
    }

    public void afficher_tout() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        for (Terasse elm : listeTerasse) {
            System.out
                    .println("[ ID: " + elm.getCode() + " ]" + "[ TYPE: " + elm.getType() + " ]" + "[ SURFACE: "
                            + elm.getSurface() + "M² ]"
                            + "[ D/I: " + df.format(elm.getDInstallation()) + " ]");
        }
    }

    public int ajouter_terasse() {
        Terasse t = new Terasse();
        t = t.creerTerasse();
        t.setCode(ID + 1);

        this.listeTerasse.add(t);
        return 1;
    }

    public int ajouter_terasse1(Terasse T) {
        T.setCode(ID + 1);
        this.listeTerasse.add(T);
        return 1;
    }

    public int supprimer_terass(int ID) {
        for (Terasse T : listeTerasse) {
            if (T.getCode() == ID) {
                this.listeTerasse.remove(T);
                return 1;
            }
        }

        return 0;
    }
}
