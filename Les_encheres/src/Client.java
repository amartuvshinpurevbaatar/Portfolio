import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String username;
    private String typeClient;

    private String item;
    private String prixInit;
    private String duree;

    public Client(Socket socket, String username, String typeClient, String item, String prixInit, String duree) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            this.typeClient = typeClient;
            this.item = item;
            this.prixInit = prixInit;
            this.duree = duree;
        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(this.username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            bufferedWriter.write(this.typeClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            if (this.typeClient == "Vendeur") {

                bufferedWriter.write(this.item);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                bufferedWriter.write(this.prixInit);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                bufferedWriter.write(this.duree);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

            Scanner sc = new Scanner(System.in);
            while (this.socket.isConnected()) {
                String messageToSend = sc.nextLine();
                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String messageFromOthers;

                while (socket.isConnected()) {
                    try {
                        messageFromOthers = bufferedReader.readLine();
                        System.out.println(messageFromOthers);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedWriter, bufferedReader);
                    }
                }
            }

        }).start();
    }

    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
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

    /**
     * @param args
     * @throws UnknownHostException
     * @throws IOException
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print(ANSI_GREEN + "[ VEUILLEZ ENTRER VÔTRE USERNAME ] : " + ANSI_RESET);
        String username = sc.nextLine();

        System.out.println(
                ANSI_GREEN + "[ VOUS ÊTES CONNECTÉE EN TANTQUE ] : [ 1 : VENDEUR ][ 2 : ACHETEUR ]"
                        + ANSI_RESET);
        int type = sc.nextInt();
        String clientType = "";

        if (type == 1) {
            clientType = "Vendeur";
        } else if (type == 2) {
            clientType = "Acheteur";
        }

        String item = "";
        String prixInit = "";
        String duree = "";

        if (type == 1) {
            System.out.println(ANSI_GREEN + "[ VOUS ALLEZ CRÉER UNE ENCHERE ]\n" +
                    ANSI_RESET);
            System.out.print(ANSI_GREEN + " ITEM" + ANSI_RESET + " : ");
            item = sc.next();
            System.out.print(ANSI_GREEN + " PRIX DE DEBUT" + ANSI_RESET + " : ");
            prixInit = sc.next();
            System.out.print(ANSI_GREEN + " DUREE DE L'ENCHERE" + ANSI_RESET + " : ");
            duree = sc.next();
        }

        Socket socket = new Socket("localhost", 7770);
        Client client = new Client(socket, username, clientType, item, prixInit, duree);
        client.listenForMessage();
        client.sendMessage();
    }
}
