import java.io.*;
import java.net.*;

public class ServerA {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1235); // Puerto 1235 para escuchar conexiones
        System.out.println("ServerA is running and listening on port 1235.");

        while (true) {
            Socket clientSocket = serverSocket.accept(); // Esperar a que llegue una conexión de cliente
            System.out.println("Accepted connection from client " + clientSocket.getInetAddress());

            // Crear un nuevo hilo para manejar la conexión del cliente
            Thread thread = new Thread(new ClientHandler(clientSocket));
            thread.start();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            // Obtener los flujos de entrada y salida para comunicarse con el cliente
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // Leer los tres números que el cliente envía (NUMERO, numeroInicial, numeroFinal)
            long NUMERO = Long.parseLong(in.readLine());
            long numeroInicial = Long.parseLong(in.readLine());
            long numeroFinal = Long.parseLong(in.readLine());

            // Dividir NUMERO entre cada n número desde numeroInicial hasta numeroFinal
            boolean divide = false;
            for (long n = numeroInicial; n <= numeroFinal; n++) {
                if (NUMERO % n == 0) {
                    divide = true;
                    break;
                }
            }

            // Enviar la respuesta al cliente
            if (divide) {
                System.out.println("Divide");
                out.println("Divide");
            } else {
                System.out.println("No Divide");
                out.println("No Divide");
            }

            // Cerrar la conexión con el cliente
            clientSocket.close();
            System.out.println("Closed connection with client " + clientSocket.getInetAddress());
        } catch (IOException e) {
            System.out.println("Error handling client connection: " + e.getMessage());
        }
    }
}
