//ServerT2
import java.io.*;
import java.net.*;
public class Server {
 public static void main(String[] args) throws IOException {
 if (args.length != 1) {
 System.err.println("Uso: java Server <puerto>");
 System.exit(1);
 }
 int puerto = Integer.parseInt(args[0]);
 ServerSocket serverSocket = new ServerSocket(puerto);
 while (true) {
 Socket clientSocket = serverSocket.accept();
 System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());
 new Thread(new ClientHandler(clientSocket)).start();
 }
 }
 private static class ClientHandler implements Runnable {
 private Socket clientSocket;
 public ClientHandler(Socket clientSocket) {
 this.clientSocket = clientSocket;
 }
 public void run() {
 try {
 // Obtener los flujos de entrada y salida para el cliente
 ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
 ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
 // Recibir las matrices Ai, BT1, BT2 y BT3 del cliente
 double[][] Ai = (double[][]) in.readObject();
 double[][] BT1 = (double[][]) in.readObject();
 double[][] BT2 = (double[][]) in.readObject();
 double[][] BT3 = (double[][]) in.readObject();
 // Calcular C1, C2 y C3
 int N = 12;
 double[][] C1 = new double[N/3][N/3];
 double[][] C2 = new double[N/3][N/3];
 double[][] C3 = new double[N/3][N/3];
 for (int i = 0; i < N/3; i++) {
 for (int j = 0; j < N/3; j++) {
 for (int k = 0; k < N; k++) {
 C1[i][j] += Ai[i][k] * BT1[j][k];
 C2[i][j] += Ai[i][k] * BT2[j][k];
 C3[i][j] += Ai[i][k] * BT3[j][k];
 }
 }
 }
 // Enviar las matrices C1, C2 y C3 al cliente
 out.writeObject(C1);
 out.writeObject(C2);
 out.writeObject(C3);
 // Cerrar los flujos y el socket
 out.close();
 in.close();
 clientSocket.close();
 } catch (IOException e) {
 System.err.println("Excepción de E/S en el cliente: " + e.getMessage());
 } catch (ClassNotFoundException e) {
 System.err.println("Clase no encontrada en el cliente: " + e.getMessage());
 }
 }
 }
}
