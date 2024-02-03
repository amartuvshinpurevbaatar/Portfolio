import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Terasse {
    private int code;
    private String type;
    private int surface;
    private Date DInstallation;

    public Terasse(int c, String t, int s, Date di) {
        this.code = c;
        this.type = t;
        this.surface = s;
        this.DInstallation = di;
    }

    public Terasse() {

    }

    public int getCode() {
        return this.code;
    }

    public String getType() {
        return this.type;
    }

    public int getSurface() {
        return this.surface;
    }

    public Date getDInstallation() {
        return this.DInstallation;
    }

    public void setCode(int c) {
        this.code = c;
    }

    public void setType(String t) {
        this.type = t;
    }

    public void setSurface(int s) {
        this.surface = s;
    }

    public void setDInstallation(Date d) {
        this.DInstallation = d;
    }

    public Terasse creerTerasse() {
        Scanner sc = new Scanner(System.in);
        System.out.println("[ TYPE ] : [1:PERMANENTE] [2:SEMI-PERM] [3:D'ÉTÉ]");
        int type_choix = sc.nextInt();
        String type = "";
        if (type_choix == 1) {
            type = "Permanente";
        } else if (type_choix == 2) {
            type = "Semi-Permanente";
        } else if (type_choix == 3) {
            type = "D'Été";
        }
        System.out.println("[ SURFACE M² ] :");
        int surface = sc.nextInt();
        System.out.println("[ DATE INSTALLATION ] :");
        String date = sc.next();
        Date debut = new Date();

        try {
            debut = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Terasse(-1, type, surface, debut);
    }
}
