import java.io.*;
import java.net.*;

public class ClientB {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java ClientB <numero>");
            System.exit(1);
        }

        // Obtener el número que el usuario proporcionó como argumento
        long numero = Long.parseLong(args[0]);
        System.out.println("El numero introducido es  "+ numero +"\n");

        // Conectar con el servidor B en el puerto 5678
        Socket socket = new Socket("localhost", 5678);

        // Obtener los flujos de entrada y salida para comunicarse con el servidor
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Enviar el número al servidor
        out.println(numero);

        // Esperar la respuesta del servidor
        String respuesta = in.readLine();

        // Desplegar la respuesta en la consola
        System.out.println("ServerB responded: " + respuesta);

        // Cerrar la conexión con el servidor
        socket.close();
    }
}
