import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Encheres implements Subject, Serializable {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private String item;
    private int debutPrix;
    private int timeSeconds;
    private Vendeur vendeur;
    private Acheteur acheteur;
    private LinkedList<Observer> buyers;
    private LinkedList<Integer> prices;
    private boolean isFinished;
    private boolean isStarted;

    public Encheres(String item, int debutPrix, int timeSeconds) {
        this.item = item;
        this.debutPrix = debutPrix;
        this.timeSeconds = timeSeconds;
        this.buyers = new LinkedList<Observer>();
        this.prices = new LinkedList<Integer>();
        this.prices.add(this.debutPrix);
        this.isFinished = false;
        this.isStarted = false;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setDebutPrix(int debutPrix) {
        this.debutPrix = debutPrix;
    }

    public void setTimeSeconds(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void setAcheteur() {
        this.acheteur = (Acheteur) this.buyers.getLast();
    }

    public void setVendeur(Vendeur vendeur) {
        this.vendeur = vendeur;
    }

    public String getItem() {
        return item;
    }

    public int getDebutPrix() {
        return debutPrix;
    }

    public int getTimeSeconds() {
        return timeSeconds;
    }

    public LinkedList<Observer> getBuyers() {
        return buyers;
    }

    public LinkedList<Integer> getPrices() {
        return prices;
    }

    public Vendeur getVendeur() {
        return vendeur;
    }

    public Acheteur getAcheteur() {
        return acheteur;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void start_encheres() {
        this.isStarted = true;
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        Runnable task1 = () -> this.isFinished = true;
        Runnable task2 = () -> System.out.println("AUCTION FINISHED -> " + this.item);
        ses.schedule(task1, this.timeSeconds, TimeUnit.SECONDS);
        ses.schedule(task2, this.timeSeconds, TimeUnit.SECONDS);

        ses.shutdown();
    }

    public boolean ajouter_propositions(int x, Acheteur a) {
        if (x > this.prices.getLast()) {
            this.attach(a);
            this.prices.addLast(x);
            Notify();

            return true;
        }

        return false;
    }

    public String toString() {
        String str = ANSI_YELLOW + "[ NOUVEL MISE ENCHERE ]" + ANSI_GREEN + "\nITEM" + ANSI_RESET + " : " + this.item
                + ANSI_GREEN + "\nPAR" + ANSI_RESET + " : " + this.vendeur.getNom()
                + ANSI_GREEN + "\nPRIX DE DÉBUT" + ANSI_RESET + " : " + this.debutPrix;
        return str;
    }

    public String toStringResult() {
        Acheteur acheteur = (Acheteur) this.buyers.getLast();
        String str = ANSI_RED + "[ LE TEMPS EST ÉCOULÉE ! RÉSULTAT ]" + ANSI_GREEN + "\nITEM" + ANSI_RESET + " : "
                + this.item
                + ANSI_GREEN + "\nVENDU PAR" + ANSI_RESET + " : " + this.vendeur.getNom()
                + ANSI_GREEN + "\nEST ACHATÉE PAR" + ANSI_RESET + " : " + acheteur.getNom()
                + ANSI_GREEN + "\nÀ" + ANSI_RESET + " : " + this.prices.getLast() + " EURO";
        return str;
    }

    public String toStringWinner() {
        String str = ANSI_YELLOW + "BRAVO VOUS AVEZ GAGNÉ" + ANSI_RESET + "\nIL VOUS RESTE : "
                + this.acheteur.getCb().getSolde() + " EURO";
        return str;
    }

    @Override
    public void attach(Observer o) {
        buyers.addLast(o);
    }

    @Override
    public void dettach(Observer o) {
        buyers.remove(o);
    }

    @Override
    public void Notify() {
        for (int i = 0; i < buyers.size(); i++) {
            buyers.get(i).update(this);
        }
    }
}
