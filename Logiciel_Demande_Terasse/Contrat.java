import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Contrat implements Serializable {
    private int ID;
    private Etablissement E;
    private Demande D;
    private String Detaille;
    private Date Acceptation;
    private int accepter;
    private int signature;

    public Contrat(Etablissement e, Demande d, String detail) {

        this.ID = -1;
        this.E = e;
        this.D = d;
        this.Detaille = detail;
        this.Acceptation = new Date();
        this.accepter = 0;
        this.signature = 0;
    }

    public Contrat() {

    }

    public Contrat CreerContrat(Etablissement E, Demande d) {
        Scanner sc = new Scanner(System.in);
        System.out.print("[ DETAILLE ] : ");
        String str = sc.nextLine();

        return new Contrat(E, d, str);
    }

    public Demande getDemande() {
        return D;
    }

    public Etablissement getEtablissement() {
        return E;
    }

    public String getDetaille() {
        return Detaille;
    }

    public int getID() {
        return ID;
    }

    public Date getAcceptation() {
        return Acceptation;
    }

    public int getAccepter() {
        return accepter;
    }

    public int getSignature() {
        return signature;
    }

    public void setSignature(int signature) {
        this.signature = signature;
    }

    public void setAccepter(int accepter) {
        this.accepter = accepter;
    }

    public void setAcceptation() {
        LocalDate now = java.time.LocalDate.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        try {
            this.Acceptation = new SimpleDateFormat("dd/MM/yyyy").parse(formattedDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setID(int iD) {
        ID = iD;
    }

    public void setDetaille(String detaille) {
        Detaille = detaille;
    }

    public void setEtablissement(Etablissement e) {
        E = e;
    }

    public void setDemande(Demande d) {
        D = d;
    }

    public void afficher() {
        System.out.println("[ ID CONTRAT ]: " + this.getID() + " [ DETAIL ]: " + this.getDetaille()
                + " [ ETABLISSEMENT ]: "
                + this.getEtablissement().getAppelation() + "\n[ SURFACE ]: " + getDemande().getSurface() + " [ D/I ]: "
                + getDemande().getDate());
    }
}
