import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hossein on 11/21/15.
 */
public class ServerConnection {
    private static Server serverConfig;

    private static class MyServerSocket extends Thread {
        private Socket socket;
        private int clientNumber;

        MyServerSocket (Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            System.out.println("New connection with client # " + clientNumber + " at " + socket);
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Hello, you are client #" + clientNumber + ".");
                out.println("Enter a line with only a period to quit\n");

                // Get messages from the client, line by line
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
                    out.println(input.toUpperCase());
                }
            } catch (IOException e) {
                System.out.println("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Couldn't close a socket, what's going on?");
                }
                System.out.println("Connection with client# " + clientNumber + " closed");
            }
        }
    }

        public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    private static void jsonParser() throws IOException {
        String config = readFile("core.json");
        System.out.println();
        Gson g = new Gson();
        serverConfig = g.fromJson(config, Server.class);
        for (Deposit d: serverConfig.getDeposits())
            d.setDecimals();
        System.out.println("port: " + serverConfig.getPort());
        System.out.println("outLogFile: " + serverConfig.getOutLog());
        System.out.println("deposits: " + serverConfig.getDeposits().toString());
    }
    public static void main(String[] args) {
        try {
            jsonParser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("The server is running...");
        int clientNumber = 0;
        ServerSocket listener = null;
        try {
            listener = new ServerSocket(serverConfig.getPort());
        } catch (IOException e) {
            // listen problem
            e.printStackTrace();
        }
        try {
            while (true) {
                new MyServerSocket(listener.accept(), clientNumber++).start();
            }
        } catch (IOException e) {
            // accept problem
            e.printStackTrace();
        } finally {
            try {
                listener.close();
            } catch (IOException e) {
                // close problem
                e.printStackTrace();
            }
        }

    }
}
