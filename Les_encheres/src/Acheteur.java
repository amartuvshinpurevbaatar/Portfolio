import java.io.Serializable;

public class Acheteur extends Personne implements Observer, Serializable {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public Acheteur(String nom, CompteBancaire cb) {
        super(nom, cb);
    }

    public String participer(Encheres ench, int bidproposer) {

        if (this.getCb().getSolde() > bidproposer) {
            if (ench.getPrices().getLast() < bidproposer) {
                ench.attach(this);
                ench.getPrices().addLast(bidproposer);
                return ANSI_GREEN + "SERVER" + ANSI_RESET + " : LE PRIX QUE VOUS AVEZ PROPOSÉ EST ACCEPTÉE !";
            } else {
                return ANSI_GREEN + "SERVER" + ANSI_RESET + " : LE PRIX QUE VOUS AVEZ PROPOSÉ EST RÉFUSÉE !";
            }
        } else {
            return ANSI_GREEN + "SERVER" + ANSI_RESET + " : VOUS N'AVEZ PAS ASSEZ DE SOLDE DANS VÔTRE COMPTE !"
                    + "\nIL VOUS RESTE : [ " + this.getCb().getSolde() + " ]";
        }
    }

    public String toOthers(Encheres ench, int bidproposer) {
        if (ench.getPrices().getLast() == bidproposer) {
            return ANSI_GREEN + "SERVER" + ANSI_RESET
                    + " : [ LE MEILLEUR PRIX AU MOMENT EST : " + ench.getPrices().getLast() + " EUROS ]";
        } else {
            return null;
        }
    }

    // public String top(Encheres ench, int bidproposer) {
    //
    // if (ench.getPrices().getLast() == bidproposer) {
    // blockchain.addBlock(blockchain.newBlock(ench.getPrices().getLast() + "
    // EUROS"));
    // return blockchain.toString();
    // } else {
    // return null;
    // }
    // }

    // public String getSolde() {
    // return solde;
    // }

    @Override
    public void update(Object o) {
        if (o instanceof Encheres) {
            Encheres ench = (Encheres) o;
            Acheteur buyer = (Acheteur) ench.getBuyers().getLast();
            System.out.println("[ LE MEILLEUR PRIX EST : " + ench.getPrices().getLast() + "$ ]");
            System.out.println("[ PROPOSÉ PAR : " + ench.getPrices().getLast() + " ]");
        }
        // TODO Auto-generated method stub
    }

}
