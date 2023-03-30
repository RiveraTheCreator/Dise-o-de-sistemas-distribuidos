import java.io.*;
import java.net.*;

public class main {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        int portNumber = 4444;

        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("ServerA is running...");
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(1);
        }

        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostName());
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            // Create a new thread for each client connection
            Thread thread = new Thread(new ClientHandler(clientSocket));
            thread.start();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                // Read input from client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                long NUMERO = Long.parseLong(in.readLine());
                long numeroInicial = Long.parseLong(in.readLine());
                long numeroFinal = Long.parseLong(in.readLine());

                // Check if NUMERO is divisible by any number from numeroInicial to numeroFinal
                boolean divide = false;
                for (long i = numeroInicial; i <= numeroFinal; i++) {
                    if (NUMERO % i == 0) {
                        divide = true;
                        break;
                    }
                }

                // Send response to client
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                if (divide) {
                    out.println("Divide");
                } else {
                    out.println("No Divide");
                }

                // Close streams and socket
                in.close();
                out.close();
                clientSocket.close();
                System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
