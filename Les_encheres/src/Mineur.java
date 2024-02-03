import java.io.Serializable;

public class Mineur extends Personne implements Serializable {

    public Mineur(String nom, CompteBancaire cb) {
        super(nom, cb);
    }

    public String top(Encheres ench, int bidproposer) {
        Blockchain blockchain = new Blockchain(2);
        if (ench.getPrices().getLast() == bidproposer) {
            blockchain.addBlock(blockchain.newBlock(ench.getPrices().getLast() + " EUROS"));
            return blockchain.toString();
        } else {
            return null;
        }
    }

}