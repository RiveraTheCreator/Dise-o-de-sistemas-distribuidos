import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Scanner;


public class ProgramaPrincipal {
	public static String USER;
    static class MyThread extends Thread {
        public void run() {
            while(true) {
                try {
                    MulticastSocket socket = new MulticastSocket(5000);
                    InetAddress group = InetAddress.getByName("239.0.0.0");
                    NetworkInterface ni = NetworkInterface.getByName("Ethernet"); // <--- el nombre de la interfaz de red
                    socket.joinGroup(new InetSocketAddress(group, 5000), ni);
                    byte[] buf = recibirMensaje(socket, 1024); /// <--- Aquí se queda esperando, los parametros son el socket y la longitud del mensaje
		    byte[] name = recibirNombre(socket,1024);
                    System.out.print(new String(name,"ISO-8859-1") +"\b\b ---> " + new String(buf, "ISO-8859-1"));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
    
    public static void enviarMensaje(byte[] buffer, String ip, int puerto) throws IOException {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(ip);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, puerto);
            socket.send(packet);
            socket.close();
        } catch (IOException e) {
            System.out.println("Error al enviar el mensaje: " + e.getMessage());
        }
    }
    
    public static byte[] recibirMensaje(MulticastSocket socket, int longitudMensaje) throws IOException {
        byte[] buffer = new byte[longitudMensaje];
        try {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            return packet.getData();
        } catch (IOException e) {
            System.out.println("Error al recibir el mensaje: " + e.getMessage());
        }
        return null;
        
    }
        //---------------------------------------------------------------------------------
    public static byte[] recibirNombre(MulticastSocket socket,int longitudMensaje) throws IOException {
        byte[] buffer = new byte[longitudMensaje];
        try {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            return packet.getData();
        } catch (IOException e) {
            System.out.println("Error al recibir el mensaje: " + e.getMessage());
        }
        return null;
        
    }
    
    public static void enviarNombre(byte[] buffer, String ip, int puerto) throws IOException {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(ip);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, puerto);
            socket.send(packet);
            socket.close();
        } catch (IOException e) {
            System.out.println("Error al enviar el mensaje: " + e.getMessage());
        }
    }

    //----------------------------------------------------------
    
    public static void main(String[] args) throws IOException {
        MyThread hilo = new MyThread();
        hilo.start();
        
        Scanner scanner = new Scanner(System.in);
        String user = args[0];
	USER = user;
        String ip = "239.0.0.0";
        int puerto = 5000;
        System.out.println("Bienvenido " + user);
        System.out.println("Ingresa tu mensaje");
        while(true){
            String mensaje = scanner.nextLine();
            byte[] buffer = mensaje.getBytes();
	    byte[] name = user.getBytes();
            enviarMensaje(buffer, ip, puerto);
	    enviarNombre(name,ip, puerto);
        }
    }
}
