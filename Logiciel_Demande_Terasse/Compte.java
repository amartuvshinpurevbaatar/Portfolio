import java.io.Serializable;
import java.util.LinkedList;
import java.util.Scanner;

public class Compte implements Serializable {
    private String username;
    private String password;
    private Personne personne;
    private LinkedList<Role> roles = new LinkedList<Role>();

    public Compte(String UN, String PWD, Personne P, LinkedList<Role> R) {

        this.username = UN;
        this.password = PWD;
        this.personne = P;
        this.roles = R;
    }

    public Compte() {
    }

    public String getusername() {
        return this.username;
    }

    public String getpassword() {
        return this.password;
    }

    public Personne getpersonne() {
        return this.personne;
    }

    public LinkedList<Role> getRoles() {
        return this.roles;
    }

    public void setusername(String UN) {
        this.username = UN;
    }

    public void setpassword(String PWD) {
        this.password = PWD;
    }

    public void setpersonne(Personne P) {
        this.personne = P;
    }

    public LinkedList<Role> ajouter_role(Role R) {
        for (Role role : this.roles) {
            if (role.getRole().equals(R.getRole())) {
                return null;
            }
        }

        roles.add(R);
        return roles;
    }

    public String afficher_tout_roles() {
        StringBuffer R = new StringBuffer();
        for (Role role : this.roles) {
            R.append(" [ " + role.getRole() + " ] ");
        }

        return R.toString();
    }

    public Compte creerCompte() {
        Scanner sc = new Scanner(System.in);
        System.out.print("[ USERNAME ] : ");
        String UN = sc.nextLine();
        System.out.print("[ PASSWORD ] : ");
        String PWD = sc.nextLine();

        System.out.println("[ SERVICE : 1 ] [ EXPLOITANT : 2 ] [ SURVEILLANT : 3 ]");
        int x = sc.nextInt();
        LinkedList<Role> ROLE = new LinkedList<Role>();
        if (x == 1) {
            ROLE.add(new Role("SERVICE"));
        } else if (x == 3) {
            ROLE.add(new Role("SURVEILLANT"));
        } else if (x == 2) {
            ROLE.add(new Role("EXPLOITANT"));
        }

        boolean stop = true;
        while (stop) {
            System.out
                    .println(
                            "[ SERVICE : 1 ] [ EXPLOITANT : 2 ] [ SURVEILLANT : 3 ] [ SORTIR : 4 ]");
            x = sc.nextInt();
            if (x == 1 && ifRoleExists(ROLE, "SERVICE") == 0) {
                ROLE.add(new Role("SERVICE"));
            } else if (x == 3 && ifRoleExists(ROLE, "SURVEILLANT") == 0) {
                ROLE.add(new Role("SURVEILLANT"));
            } else if (x == 2 && ifRoleExists(ROLE, "EXPLOITANT") == 0) {
                ROLE.add(new Role("EXPLOITANT"));
            } else if (x == 4) {
                stop = false;
            }
        }

        this.roles = ROLE;
        System.out.println(ROLE.size());

        System.out.println("[ PERSONNE PHYSIQUE : 1 ] [ PERSONNE MORALE : 2 ]");
        int i = sc.nextInt();
        if (i == 1) {
            PersonnePhysique P = new PersonnePhysique();
            return new Compte(UN, PWD, P.creerPersonnePhysique(), ROLE);
        } else {
            PersonneMorale P = new PersonneMorale();
            return new Compte(UN, PWD, P.creerPersonneMorale(), ROLE);
        }
    }

    public int ifRoleExists(LinkedList<Role> L, String R) {
        for (Role role : L) {
            if (role.getRole().equals(R)) {
                System.out.println("VOUS AVEZ DEJA CE RÔLE ! [ " + R + " ]");
                return 1;
            }
        }
        return 0;
    }

    public int ifRoleExists1(LinkedList<Role> L, String R) {
        for (Role role : L) {
            if (role.getRole().equals(R)) {
                return 1;
            }
        }
        return 0;
    }

    public int countRole() {
        return this.roles.size();
    }

}
