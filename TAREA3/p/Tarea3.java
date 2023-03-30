package tarea3;
import java.io.*;
import java.net.*;

public class Tarea3 {
    private static final String GROUP_IP = "239.0.0.0";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: java Tarea3 <nombre de usuario>");
            System.exit(1);
        }
        
        String username = args[0];
        System.out.println("Bienvenido " + username);

        // Crear thread para recibir mensajes multicast
        Thread receiver = new Thread(() -> {
            try {
                MulticastSocket socket = new MulticastSocket(PORT);
                InetAddress group = InetAddress.getByName(GROUP_IP);
                socket.joinGroup(group);

                while (true) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    String message = new String(packet.getData(), packet.getOffset(), packet.getLength());
                    System.out.println(username + "--->" + message);
                }
            } catch (IOException e) {
                System.err.println("Error al recibir mensajes multicast: " + e.getMessage());
            }
        });
        receiver.start();

        // Enviar mensajes multicast desde el hilo principal
        try {
            InetAddress group = InetAddress.getByName(GROUP_IP);
            DatagramSocket socket = new DatagramSocket();

            while (true) {
                System.out.print("Escribe tu mensaje: ");
                String input = System.console().readLine();
                String message = username + " ---> " + input;

                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                socket.send(packet);
                System.out.println("Mensaje enviado");
            }
        } catch (IOException e) {
            System.err.println("Error al enviar mensaje multicast: " + e.getMessage());
        }
    }
}
