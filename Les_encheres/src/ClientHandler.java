import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientHandler implements Runnable {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>(); // Pour garder tout les client qui se
    private static Encheres encheresGlobal = null;
    private static Blockchain lesOffres = new Blockchain(0);
    private static Blockchain transaction = new Blockchain(0);
    private static Blockchain lesTransactions = new Blockchain(5);
    private static int trouverOu = 0;
    // connecte au serveur

    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String clientUsername;
    private String typeClient;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine(); // Celui la va attendre que Client ecris son nom dans le
                                                             // function SendMessage();
            this.typeClient = bufferedReader.readLine();
            clientHandlers.add(this);
            broadCastMessage(
                    ANSI_GREEN + "SERVER : [ " + ANSI_BLUE + this.clientUsername + ANSI_GREEN
                            + " EST ENTRÉE DANS L'ENCHERES ]"
                            + ANSI_RESET);
        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    @Override
    public void run() {
        String recievedString;
        String recievedItem;
        int recievedPrixDebut;
        int recievedDuree;
        int proposed;

        while (this.socket.isConnected() && this.typeClient.equals("Vendeur")) {
            try {
                Vendeur vendeur = new Vendeur(this.clientUsername, null);
                recievedItem = bufferedReader.readLine();
                String prixInit = bufferedReader.readLine();
                String duree = bufferedReader.readLine();

                recievedPrixDebut = Integer.parseInt(prixInit);
                recievedDuree = Integer.parseInt(duree);

                encheresGlobal = vendeur.ajoutEncheres(recievedItem, recievedPrixDebut, recievedDuree);
                broadCastMessagetoAcheteur(this.encheresGlobal.toString());

                if (encheresGlobal.isStarted() == false) {
                    encheresGlobal.start_encheres();
                }

                ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

                Runnable task1 = () -> testFinished();
                ses.schedule(task1, encheresGlobal.getTimeSeconds(), TimeUnit.SECONDS);

                ses.shutdown();

            } catch (IOException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
                break;
            }
        }

        while (this.socket.isConnected() && this.typeClient.equals("Acheteur")) {
            try {
                Acheteur acheteur = new Acheteur(this.clientUsername, new CompteBancaire(this.clientUsername));

                while (encheresGlobal == null) {
                    Thread.sleep(1000);
                }
                while (encheresGlobal.isStarted() == true && encheresGlobal.isFinished() == false) {
                    broadCastMessagetoMOI("[ " + this.clientUsername + " ]");
                    recievedString = bufferedReader.readLine();
                    proposed = Integer.parseInt(recievedString);
                    broadCastMessagetoMOI(acheteur.participer(encheresGlobal, proposed));
                    if (acheteur.toOthers(encheresGlobal, proposed) != null) {
                        String data = this.clientUsername + proposed;
                        lesOffres.addBlock(lesOffres.newBlock(data));
                        minage(3);

                        if (lesTransactions.isBlockChainValid() == true) {
                            String str = this.clientUsername + ANSI_GREEN + " A PROPOSÉ " + ANSI_RESET + proposed
                                    + ANSI_GREEN + " ET VALIDÉ PAR BLOCKCHAIN " + ANSI_RESET;
                            broadCastMessageToServer(lesOffres.toString());
                            broadCastMessageToServer(str);
                            broadCastMessage(acheteur.toOthers(encheresGlobal, proposed));
                        }
                    }
                    // String i = acheteur.toOthers2(encheresGlobal, proposed);
                    // blockchain.addBlock(blockchain.newBlock(i));
                    // broadCastMessagetoMineur(blockchain.addBlock(blockchain.newBlock(i)));

                }
            } catch (IOException | InterruptedException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
                break;
            }
        }

    }

    public void broadCastMessageToServer(String messageToSend) {
        System.out.println(messageToSend);
    }

    public void broadCastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
            }
        }
    }

    public void broadCastMessagetoVendeur(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)
                        && clientHandler.typeClient.equals("Vendeur")) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
            }
        }
    }

    public void broadCastMessagetoAcheteur(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)
                        && clientHandler.typeClient.equals("Acheteur")) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
            }
        }
    }

    public void broadCastMessagetoMOI(String messageToSend) {
        try {
            this.bufferedWriter.write(messageToSend);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    public void broadCastMessagetoWinner(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (clientHandler.clientUsername.equals(encheresGlobal.getAcheteur().getNom())) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
            }
        }
    }

    public void testFinished() {
        if (encheresGlobal.isFinished() == true) {
            if (encheresGlobal.getBuyers().size() > 0) {
                encheresGlobal.setAcheteur();
                encheresGlobal.getAcheteur().getCb()
                        .setSolde(
                                encheresGlobal.getAcheteur().getCb().getSolde() - encheresGlobal.getPrices().getLast());
                ;

                broadCastMessagetoAcheteur(encheresGlobal.toStringResult());
                broadCastMessagetoMOI(encheresGlobal.toStringResult());

                System.out.println("ça marche");
                broadCastMessagetoWinner(encheresGlobal.toStringWinner());
            } else {
                broadCastMessage(
                        ANSI_RED + "[ LE TEMPS EST ÉCOULÉE ! RÉSULTAT ]" + ANSI_GREEN + "\nITEM" + ANSI_RESET + " : "
                                + this.encheresGlobal.getItem() + " N'EST PAS ÉTÉ VENDU !");
                broadCastMessagetoMOI(
                        ANSI_RED + "[ LE TEMPS EST ÉCOULÉE ! RÉSULTAT ]" + ANSI_GREEN + "\nITEM" + ANSI_RESET + " : "
                                + this.encheresGlobal.getItem() + " N'EST PAS ÉTÉ VENDU !");
            }
        }
    }

    public void removeCientHandler() {
        clientHandlers.remove(this);
        broadCastMessage(ANSI_RED + "SERVER : [ " + ANSI_BLUE + this.clientUsername + "EST SORTIE DE L'ENCHERE ] !"
                + ANSI_RESET);
    }

    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        removeCientHandler();
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void minage(int nbBlock) {
        if (transaction.getBlocks().size() == nbBlock) {
            lesTransactions.addBlock(lesTransactions.newBlock(transaction.toString()));
            broadCastMessageToServer(lesTransactions.toString());
            transaction = new Blockchain(0);
        }

        transaction.addBlock(lesOffres.latestBlock());
    }
}
