import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by hossein on 11/21/15.
 */
public class Client {
    private String id;
    private String type;
    private String serverIP;
    private int serverPort;
    private String outLog;
    private ArrayList<Transaction> transactions;

    private BufferedReader in;
    private PrintWriter out;

    public Client() {

    }

    @Override
    public String toString(){
        return "\nterminal id: " + id + "\nterminal type: " + type + "\nserver ip: " + serverIP +
                "\nserver port: " + serverPort + "\noutLog: " + outLog + transactions.toString();
    }

    public void connectToServer() throws IOException {
        System.out.println("Connecting to server...");
        Socket socket = new Socket(serverIP, serverPort);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);


        // Consume the initial welcoming messages from the server
        for (int i = 0; i < 3; i++) {
            //messageArea.append(in.readLine() + "\n");
            System.out.println(in.readLine() + "\n");
        }
    }


    public static void main(String[] args){
        Client client = new Client();
        File inputFile = new File("Terminal.xml");
        xmlParser xParser = new xmlParser(inputFile);
        client.id = xParser.getTerminalId();
        client.type = xParser.getTerminalType();
        client.serverIP = xParser.getServerIP();
        client.serverPort = xParser.getServerPort();
        client.outLog = xParser.getOutLogPath();
        client.transactions = xParser.getTransactions();
        System.out.println(client.toString());
        try {
            client.connectToServer();
            System.out.println("Connection to server: " + client.serverIP + ", port: " + client.serverPort + " established.");
        } catch (IOException e) {
            System.out.println("problem connecting to server");
            e.printStackTrace();
        }
    }
}
