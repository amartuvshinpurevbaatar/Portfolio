import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Connexion {
    boolean connecter = true;
    ListeCompte Comptes;
    ListeDemande Demandes;
    ListeContrat Contrats;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    public Connexion(ListeCompte C, ListeDemande D, ListeContrat CC) {
        this.Comptes = C;
        this.Demandes = D;
        this.Contrats = CC;
    }

    public boolean run() {

        String UN;
        String PWD;

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("[ SE CONNECTER ]");
            System.out.print("[ USERNAME ] : ");
            UN = sc.nextLine();
            System.out.print("[ MOT DE PASSE ] : ");
            PWD = sc.nextLine();

            if (this.Comptes.se_connecter(UN, PWD)) {
                Compte seConnecter = this.Comptes.renvoieCompte(UN, PWD);
                while (true && seConnecter.ifRoleExists1(seConnecter.getRoles(), "ADMIN") == 1) {
                    System.out.println(GREEN + "[ VOUS ÊTES CONNECTÉE EN TANT QUE : ADMIN ]" + ANSI_RESET);
                    System.out.println("\t[ -1 Se dconnecter ]"
                            + "\n\t[ 0 Supprimer ce compte ]"
                            + "\n\t[ 1 Afficher tout les comptes ]"
                            + "\n\t[ 2 Ajouter un compte ]");

                    int choix = sc.nextInt();

                    if (choix == -1) {
                        break;
                    } else if (choix == 0) {
                        if (this.Comptes.supprimer_Compte(UN) == 1) {
                            System.out.println("\u001B[32m" + "Vous avez supprimé ce compte !" + ANSI_RESET);
                        }
                        try {
                            FileOutputStream fos = new FileOutputStream("SaveCompte");
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            oos.writeObject(Comptes);
                            oos.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    } else if (choix == 1) {
                        Comptes.afficher_tout();
                    } else if (choix == 2) {
                        if (Comptes.ajouter_compte1() == 1) {
                            System.out.print("\u001B[32m" + "[ VÔTRE COMPTE A ÉTÉ BIEN AJOUTÉE ]" + ANSI_RESET);
                        }
                    }

                }

                while (true && seConnecter.ifRoleExists1(seConnecter.getRoles(), "SERVICE") == 1) {
                    System.out.println(GREEN + "[ VOUS ÊTES CONNECTÉE EN TANT QUE : SERVICE ]" + ANSI_RESET);
                    System.out.println("\t[ -1 Se dconnecter ]"
                            + "\n\t[ 0 Supprimer ce compte ]"
                            + "\n\t[ 1 Afficher les demandes ]"
                            + "\n\t[ 2 Traiter une demande ]"
                            + "\n\t[ 3 Afficher les contrats ]");

                    int choix = sc.nextInt();

                    if (choix == -1) {
                        break;
                    } else if (choix == 0) {
                        if (this.Comptes.supprimer_Compte(UN) == 1) {
                            System.out.println("\u001B[32m" + "Vous avez supprimé ce compte !" + ANSI_RESET);
                        }
                        break;
                    } else if (choix == 1) {
                        this.Demandes.afficher_tout_service();
                    } else if (choix == 2) {
                        System.out.println("[ METTEZ L'ID DE DEMANDE : ]");
                        int x = sc.nextInt();
                        Demande D = Demandes.chercher_demande(x);

                        if (D == null) {
                            System.out.println("ID que vous avez saissie est incorrecte!");
                            break;
                        }
                        D.afficher();
                        if (D.getEtat().equals("en_cours")) {
                            System.out.println(
                                    "[ MODIFIER SURFACE ET ENVOYER LE CONTRAT : 1 | ENVOYER LE CONTRAT : 2 ]");
                            int modif = sc.nextInt();
                            if (modif == 1) {
                                System.out.print("[ SURFACE ]: ");
                                D.setSurface(sc.nextInt());
                                Contrat c = new Contrat();
                                c = c.CreerContrat(D.getEtablissement(), D);
                                if (Contrats.ajouter_contrat(c) == 1) {
                                    System.out.print("\n" + YELLOW + "CHARGEMENT EN COURS");
                                    for (int i = 0; i < 3; i++) {
                                        System.out.print(" . ");
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                    System.out.println(GREEN + "Le contrat a été bien envoyé! " + ANSI_RESET);
                                    D.setCommentaire("Le contrat est envoyé!");
                                }
                            } else if (modif == 2) {
                                Contrat c = new Contrat(D.getEtablissement(), D,
                                        "Votre demande est accepté sans changement!");
                                if (Contrats.ajouter_contrat(c) == 1) {
                                    System.out.print("\n" + YELLOW + "CHARGEMENT EN COURS");
                                    for (int i = 0; i < 3; i++) {
                                        System.out.print(" . ");
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                    System.out.println(GREEN + "Le contrat a été bien envoyé! " + ANSI_RESET);
                                    D.setCommentaire("Le contrat est envoyé!");
                                }
                            }
                        } else if (D.getEtat().equals("prête")) {
                            System.out.println("[ RECEVABLE : 1  | NON-RECEVABLE : 2 ]");
                            int etat = sc.nextInt();
                            if (etat == 1) {
                                D.setEtat(1);
                                D.setCommentaire("En attente de surveillant !");
                            } else if (etat == 2) {
                                D.setEtat(-1);
                                D.setCommentaire("La demande non-recevable !");
                            }
                        } else {
                            D.afficher();
                        }

                    } else if (choix == 3) {
                        Contrats.afficher_tout();
                    }
                }

                while (true && seConnecter.ifRoleExists1(seConnecter.getRoles(), "EXPLOITANT") == 1) {
                    System.out.println(GREEN + "[ VOUS ÊTES CONNECTÉE EN TANT QUE : EXPLOITANT ]" + ANSI_RESET);
                    System.out.println("\t[ -1 Se dconnecter ]"
                            + "\n\t[ 0 Supprimer ce compte ]"
                            + "\n\t[ 1 Afficher les établissements ]"
                            + "\n\t[ 2 Ajouter un établissement ]"
                            + "\n\t[ 3 Supprimer un établissement ]"
                            + "\n\t[ 4 Afficher les terrase d'un établissement ]"
                            + "\n\t[ 5 Ajouter une terrase dans un établissement ]"
                            + "\n\t[ 6 Faire une demande ]"
                            + "\n\t[ 7 Consulter les demandes ]"
                            + "\n\t[ 8 Consulter les contrats ]");

                    int choix = sc.nextInt();

                    if (choix == -1) {
                        break;
                    } else if (choix == 0) {
                        if (this.Comptes.supprimer_Compte(UN) == 1) {
                            System.out.println("\u001B[32m" + "Vous avez supprimé ce compte !" + ANSI_RESET);
                        }
                        break;
                    } else if (choix == 1) {
                        seConnecter.getpersonne().get_listeEtablissement().afficher_tout();
                    } else if (choix == 2) {
                        if (seConnecter.getpersonne().get_listeEtablissement().ajouter_Etablissement_V2() == 1) {
                            System.out.println("\u001B[32m" + "L'Établissement a été bien ajoutée !" + ANSI_RESET);
                        }
                    } else if (choix == 3) {
                        System.out.println("[ METTEZ L'ID D'ÉTABLISSEMENT : ]");
                        int Et = sc.nextInt();
                        if (seConnecter.getpersonne().get_listeEtablissement()
                                .supprimer_Etablissement(Et) == 1) {
                            System.out.println("\u001B[32m" + "L'Établissement a été bien supprimée !" + ANSI_RESET);
                        }
                    } else if (choix == 4) {
                        System.out.println("[ METTEZ L'ID D'ÉTABLISSEMENT : ]");
                        int Et = sc.nextInt();
                        Etablissement ET = seConnecter.getpersonne().get_listeEtablissement()
                                .trouverEtablissement(Et);
                        if (ET != null) {
                            ET.getlisteTerrase().afficher_tout();
                        }
                    } else if (choix == 5) {
                        System.out.println("[ METTEZ L'ID D'ÉTABLISSEMENT : ]");
                        int Et = sc.nextInt();
                        Etablissement ET = seConnecter.getpersonne().get_listeEtablissement()
                                .trouverEtablissement(Et);
                        if (ET != null) {
                            if (ET.getlisteTerrase().ajouter_terasse() == 1) {
                                System.out
                                        .println("\u001B[32m" + "La terasse a été bien rajoutée !" + ANSI_RESET);
                            }
                        }
                    } else if (choix == 6) {
                        System.out.println("[ METTEZ L'ID D'ÉTABLISSEMENT : ]");
                        int Et = sc.nextInt();
                        Etablissement ET = seConnecter.getpersonne().get_listeEtablissement()
                                .trouverEtablissement(Et);
                        if (ET != null) {
                            if (this.Demandes.ajouter_demande(ET) == 1) {
                                System.out
                                        .println("\u001B[32m" + "La demande a été bien rajoutée !" + ANSI_RESET);
                            }
                        }
                    } else if (choix == 7) {
                        System.out.println("[ METTEZ L'ID D'ÉTABLISSEMENT : ]");
                        int ID_Etab = sc.nextInt();
                        Etablissement ET = seConnecter.getpersonne().get_listeEtablissement()
                                .trouverEtablissement(ID_Etab);
                        if (ET != null) {
                            Demandes.afficher_tout_parEtab(ET.getAppelation());
                            System.out.println("[ MODIFIER LA DEMANDE ID ( N ): ] [ SINON: -1 ]");
                            int n = sc.nextInt();
                            while (n != -1) {
                                if (Demandes.chercher_demande(n) == null) {
                                    System.out.println(RED + "La demande " + n + "n'existe pas !" + ANSI_RESET);
                                } else {
                                    System.out.println("[ SUPPRIMER LA DEMANDE : 1 ][ MODIFIER LA SURFACE : 2 ]");
                                    int ss = sc.nextInt();
                                    if (ss == 1) {
                                        if (Demandes.supprimer_demande(Demandes.chercher_demande(n)) == 1) {
                                            System.out
                                                    .println(GREEN + "La demande a été bien supprimé" + ANSI_RESET);
                                        }
                                    } else if (ss == 2) {
                                        System.out.println(
                                                "[ ANCIEN SURFACE ]:" + Demandes.chercher_demande(n).getSurface());
                                        System.out.print("[ NOUVELLE SURFACE ]:");
                                        int nouveau = sc.nextInt();
                                        Demandes.chercher_demande(n).setSurface(nouveau);
                                        System.out
                                                .println(GREEN + "La surface a été bien modifié" + ANSI_RESET);
                                        break;
                                    }
                                }
                            }

                        }

                    } else if (choix == 8) {
                        System.out.println("[ METTEZ L'ID D'ÉTABLISSEMENT : ]");
                        int ID_Etab = sc.nextInt();
                        Etablissement ET = seConnecter.getpersonne().get_listeEtablissement()
                                .trouverEtablissement(ID_Etab);
                        if (ET != null) {
                            Demandes.afficher_tout_parEtab(ET.getAppelation());
                            System.out.println("[ CHOISIR ID ( N ) DE DEMANDE ]");
                            int n = sc.nextInt();
                            if (Demandes.chercher_demande(n) == null) {
                                System.out.println(RED + "La demande " + n + "n'existe pas !" + ANSI_RESET);
                            } else {
                                Contrat contrat_show = Contrats
                                        .chercher_par_ID_Demand(Demandes.chercher_demande(n).getID());
                                if (contrat_show == null) {
                                    System.out.println(
                                            YELLOW + "La demande " + n + " est toujours en cours !" + ANSI_RESET);
                                } else {
                                    contrat_show.afficher();
                                    System.out.println("[ ACCEPTER : 1 ][ REFUSER : 2 ]");
                                    int answer = sc.nextInt();
                                    if (answer == 1) {
                                        contrat_show.setAccepter(1);
                                        contrat_show.setAcceptation();
                                        System.out.println("[ SIGNER : 1 ][ ULTERIEUREMENT : 2 ]");
                                        int sign = sc.nextInt();
                                        if (sign == 1) {
                                            System.out.println("[ SIGNATURE ]:");
                                            sc.next();
                                            Demandes.chercher_demande(n).setEtat(5);
                                            contrat_show.setSignature(1);
                                            System.out.println(GREEN + "[ LE CONTRAT EST FINALISER ! ]" + ANSI_RESET);
                                        } else {
                                            System.out.println(GREEN + "[ LE CONTRAT EST ACCEPTÉ ! ]" + ANSI_RESET);
                                        }

                                    } else if (answer == 2) {
                                        contrat_show.setAccepter(-1);
                                        Demandes.chercher_demande(n).setEtat(4);
                                        System.out.println(GREEN + "[ LE CONTRAT NON ABOUTI ! ]" + ANSI_RESET);
                                    }
                                }
                            }

                        }

                    }
                }

                while (true && seConnecter.ifRoleExists1(seConnecter.getRoles(), "SURVEILLANT") == 1) {
                    System.out.println(GREEN + "[ VOUS ÊTES CONNECTÉE EN TANT QUE : SURVEILLANT ]" + ANSI_RESET);
                    System.out.println("\t[ -1 Se dconnecter ]"
                            + "\n\t[ 1 Mettre le rapport ]");

                    int choix = sc.nextInt();
                    System.out.print("\n" + YELLOW + "CHARGEMENT EN COURS");
                    for (int i = 0; i < 3; i++) {
                        System.out.print(" . ");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    System.out.println(ANSI_RESET);

                    if (choix == -1) {
                        break;
                    } else if (choix == 1) {
                        int x = Demandes.afficher_tout_parEtat("mise en attente");
                        if (x == 0) {
                            System.out.println(
                                    RED + "Pour l'instant il n'y a pas de demande à mettre un rapport!" + ANSI_RESET);
                        } else {
                            System.out.println("\n[ METTEZ L'ID DE DEMANDE : ]");
                            int ID_Demande = sc.nextInt();
                            sc.nextLine();
                            System.out.println("[ METTEZ LE RAPPORT : ]");
                            String Rapport = sc.nextLine();
                            Demandes.chercher_demande(ID_Demande).setRapport(Rapport);
                            Demandes.chercher_demande(ID_Demande).setEtat(3);
                            Demandes.chercher_demande(ID_Demande).setCommentaire("");
                        }
                    }
                }

                break;
            }
        }
        return connecter = false;
    }

    public ListeCompte getListCompte() {
        return Comptes;
    }

    public ListeDemande getListDemande() {
        return Demandes;
    }

    public ListeContrat getListContrat() {
        return Contrats;
    }
}
