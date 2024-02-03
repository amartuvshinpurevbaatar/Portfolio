import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Demande implements Serializable {
    private String etat;
    private int ID;

    private String initiale = "prête";
    private String non_recevable = "non recevable";
    private String attente = "mise en attente";
    private String en_cours = "en_cours";
    private String refus = "réfusée";
    private String satif = "satisfiate";
    private String sans_suite = "sans suite";

    private String commentaire;

    private Etablissement etablissement;
    private Contrat contrat;
    private String rapport;

    private String type;
    private String zone;
    private int surface;
    private String date;

    public Demande(Etablissement e, String t, String z, int s, String d) {
        this.ID = 0;
        this.etat = initiale;
        this.etablissement = e;
        this.commentaire = "";
        this.rapport = "";
        this.type = t;
        this.zone = z;
        this.surface = s;
        this.date = d;
    }

    public Demande() {
        this.ID = 0;
    }

    public String getEtat() {
        return this.etat;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public String getRapport() {
        return rapport;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public String getZone() {
        return zone;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public int getSurface() {
        return surface;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEtat(int x) {
        switch (x) {
            case -1:
                this.etat = non_recevable;
                break;

            case 1:
                this.etat = attente;
                break;

            case 2:
                this.etat = attente;
                break;

            case 3:
                this.etat = en_cours;
                break;

            case 4:
                this.etat = refus;
                break;

            case 5:
                this.etat = satif;
                break;

            case 6:
                this.etat = sans_suite;
                break;

            default:
                break;
        }
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public Demande creerDemande(Etablissement E) {
        Scanner sc = new Scanner(System.in);
        System.out.println("[ TYPE ] : [ 1 : PERMANENTE ] [ 2 : SEMI-PERM ] [ 3 : D'ÉTÉ ]");
        int type_choix = sc.nextInt();
        String type = "";
        if (type_choix == 1) {
            type = "Permanente";
        } else if (type_choix == 2) {
            type = "Semi-Permanente";
        } else if (type_choix == 3) {
            type = "D'Été";
        }
        System.out.println("[ ZONE ] : [ A ] [ B ] [ C ]");
        String zonee = sc.next();
        System.out.println("[ SURFACE M² ] :");
        int surface = sc.nextInt();
        System.out.println("[ DATE INSTALLATION ] :");
        String date = sc.next();
        return new Demande(E, type, zonee, surface, date);
    }

    public void afficher() {
        System.out
                .println("[ ID : " + this.getID() + " ] " + "\n[ ETAT : " + this.getEtat() + " ] " + "\n[COMMENTAIRE :"
                        + this.getCommentaire()
                        + " ] " + "\n[ ETABLISSEMENT : " + this.getEtablissement().getAppelation() + " ] "
                        + "\n[ ZONE : " + this.getZone() + " ] " + "\n[ TYPE : " + this.getZone() + " ] "
                        + "\n[ DATE : "
                        + this.getDate() + " ] " + "\n[ RAPPORT : " + this.getRapport() + " ] "
                        + "\n[ SURFACE : " + this.getSurface() + " ] ");
    }
}
