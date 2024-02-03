import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private ServerSocket serverSocket;

    public Server(ServerSocket server) {
        this.serverSocket = server;
    }

    public void StartServer() {
        Socket socket = null;
        System.out.println(ANSI_YELLOW + "[ EN ATTENTE DES CLIENTS ]" + ANSI_RESET);
        while (!this.serverSocket.isClosed()) {
            try {
                socket = this.serverSocket.accept();
                System.out.println(ANSI_GREEN + "[ UN CLIENT S'EST CONNECTÉE ]" + ANSI_RESET);

                ClientHandler clientHandler = new ClientHandler(socket);

                Thread threadClient = new Thread(clientHandler);
                threadClient.start();
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void CloseServerSocket(ServerSocket ss) {
        try {
            if (ss != null) {
                ss.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7770);
        Server server = new Server(serverSocket);
        server.StartServer();
        server.CloseServerSocket(serverSocket);
    }
}