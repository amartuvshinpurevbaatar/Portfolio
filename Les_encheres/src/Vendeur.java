import java.io.Serializable;
import java.util.Scanner;

public class Vendeur extends Personne implements Observer, Serializable {

    private String solde;

    public Vendeur(String nom, CompteBancaire cb) {
        super(nom, cb);
    }

    public Encheres ajouter_item() {
        System.out.println("Vous allez creer une enchère !");
        Scanner sc = new Scanner(System.in);
        String item = "";
        int prixinit, time;

        System.out.println("Inserez l'item : ");

        try {
            item = sc.next();
        } catch (Exception e) {
            System.out.println("Une erreur!");
        }

        System.out.println("Inserez le prix de début : ");
        while (true) {
            try {
                prixinit = sc.nextInt();
                break;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Vous devriez mettre une prix !");
                continue;
            }
        }

        System.out.println("Inserez le temps de durée : ");
        while (true) {
            try {
                time = sc.nextInt();
                break;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Vous devriez mettre le temps durée de l'enchère !");
                continue;
            }
        }

        Encheres first = new Encheres(item, prixinit, time);
        first.setVendeur(this);

        return first;
    }

    public Encheres ajoutEncheres(String item, int prixInit, int duree) {
        Encheres ench = new Encheres(item, prixInit, duree);
        ench.setVendeur(this);
        return ench;
    }

    @Override
    public void update(Object o) {
        if (o instanceof Encheres) {
            Encheres ench = (Encheres) o;

            System.out.println("[ LE MEILLEUR PRIX EST : " + ench.getBuyers().getLast() + "]");
            System.out.println("[ PROPOSÉ PAR : " + ench.getPrices().getLast() + "]");
        }
        // TODO Auto-generated method stub
    }
}
