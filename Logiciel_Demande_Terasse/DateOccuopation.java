import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateOccuopation implements Serializable {
    private Etablissement ET1;
    private Date Debut;
    private Date Fin;

    public DateOccuopation(String date, Etablissement e) {
        try {
            this.Debut = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        this.Fin = null;

        this.ET1 = e;
    }

    public Date getDDebut() {
        return this.Debut;
    }

    public Date getDFin() {
        return this.Fin;
    }

    public Etablissement geEtablissement() {
        return this.ET1;
    }

    public void setDDebut(String date) {
        try {
            this.Debut = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setDFin(String date) {
        try {
            this.Fin = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setEtablissement(Etablissement e) {
        this.ET1 = e;
    }

}
