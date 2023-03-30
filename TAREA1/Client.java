import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java Client <numero>");
            System.exit(1);
        }

        long numero = Long.parseLong(args[0]);

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket("localhost", 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: localhost");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not connect to localhost");
            System.exit(1);
        }

        out.println(numero);

        String response = in.readLine();
        System.out.println("ServerB response: " + response);

        out.close();
        in.close();
        socket.close();
    }
}
