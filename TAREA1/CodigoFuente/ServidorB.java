import java.io.*;
import java.net.*;

public class ServerB {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            // Crear el servidor en el puerto 5678
            serverSocket = new ServerSocket(5678);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5678.");
            System.exit(1);
        }

        while (true) {
            // Esperar a que un cliente se conecte
            Socket clientSocket = null;
            try {
                System.out.println("Accept status");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            // Obtener los flujos de entrada y salida para comunicarse con el cliente
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Leer el número enviado por el cliente
            long numero = Long.parseLong(in.readLine());

            // Calcular los tres intervalos para dividir el intervalo [2, NUMERO - 1]
            long k = numero / 3;
            long i1 = 3, j1 = k;
            long i2 = k + 1, j2 = 2 * k;
            long i3 = 2 * k + 1, j3 = numero - 1;

            // Conectar con tres instancias del servidor A para dividir los intervalos
            Socket socket1 = new Socket("localhost", 1234);
            Socket socket2 = new Socket("localhost", 1234);
            Socket socket3 = new Socket("localhost", 1235);

            // Obtener los flujos de entrada y salida para comunicarse con las instancias del servidor A
            PrintWriter out1 = new PrintWriter(socket1.getOutputStream(), true);
            PrintWriter out2 = new PrintWriter(socket2.getOutputStream(), true);
            PrintWriter out3 = new PrintWriter(socket3.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            BufferedReader in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            BufferedReader in3 = new BufferedReader(new InputStreamReader(socket3.getInputStream()));

            // Enviar las peticiones a las instancias del servidor A
            out1.println(numero);
            out1.println(i1);
            out1.println(j1);

            out2.println(numero);
            out2.println(i2);
            out2.println(j2);

            out3.println(numero);
            out3.println(i3);
            out3.println(j3);

            // Leer las respuestas de las instancias del servidor A
            String respuesta1 = in1.readLine();
            String respuesta2 = in2.readLine();
            String respuesta3 = in3.readLine();
            
            // Determinar si el número es primo o no
            System.out.println("Las respuestas son "+respuesta1 + "   "+ respuesta2 + "   "+ respuesta3 + "\n\n");
            System.out.println("La respuesta del server B para el cliente esss:" +respuesta1.contentEquals("No Divide") + respuesta2.contentEquals("No Divide") + respuesta3.contentEquals("No Divide"));
            if (respuesta1.contentEquals("No Divide") && respuesta2.contentEquals("No Divide") && respuesta3.contentEquals("No Divide")) {
                out.println("ES PRIMO");
            } else {
                out.println("No es primo");
            }
            // Cerrar las conexiones con las instancias del servidor A
            socket1.close();
            socket2.close();
            socket3.close();



            // Cerrar la conexión con el cliente
            clientSocket.close();
        }
    }
}
