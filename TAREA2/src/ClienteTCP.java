//ClienteT2
import java.io.*;
import java.net.*;
public class ClienteTCP {
 public static void main(String[] args) throws Exception {
 String ipNodo1 = args[0];
 String ipNodo2 = args[1];
 String ipNodo3 = args[2];
 int N = 3000;
 double[][] A = new double[N][N];
 double[][] B = new double[N][N];
 double[][] BT = new double[N][N];
 double[][] C = new double[N][N];
double[][] C1 = null;
 double[][] C2 = null;
 double[][] C3 = null;
 double[][] C4 = null;
 double[][] C5 = null;
 double[][] C6 = null;
 double[][] C7 = null;
 double[][] C8 = null;
 double[][] C9 = null;
 // Inicializar matrices A y B
 for(int i = 0; i < N; i++) {
 for(int j = 0; j < N; j++) {
 A[i][j] = 2*i+j;
 B[i][j] = 3*i-j;
 BT[j][i] = B[i][j];
 }
 }
 // Dividir matrices A y BT en tres partes
 int n = N / 3;
 double[][] A1 = new double[n][N];
 double[][] A2 = new double[n][N];
 double[][] A3 = new double[n][N];
 double[][] BT1 = new double[n][N];
 double[][] BT2 = new double[n][N];
 double[][] BT3 = new double[n][N];
 for(int i = 0; i < n; i++) {
 System.arraycopy(A[i], 0, A1[i], 0, N);
 System.arraycopy(A[i+n], 0, A2[i], 0, N);
 System.arraycopy(A[i+2*n], 0, A3[i], 0, N);
 System.arraycopy(BT[i], 0, BT1[i], 0, N);
 System.arraycopy(BT[i+n], 0, BT2[i], 0, N);
 System.arraycopy(BT[i+2*n], 0, BT3[i], 0, N);
 }
 // Conectar a los nodos
 Socket nodo1 = new Socket(ipNodo1, 50000);
 Socket nodo2 = new Socket(ipNodo2, 50001);
 Socket nodo3 = new Socket(ipNodo3, 50002);
 // Enviar matrices A1, BT1, BT2 y BT3 al nodo 1
 ObjectOutputStream out1 = new ObjectOutputStream(nodo1.getOutputStream());
 out1.writeObject(A1);
 out1.writeObject(BT1);
 out1.writeObject(BT2);
 out1.writeObject(BT3);
 // Enviar matrices A2, BT1, BT2 y BT3 al nodo 2
 ObjectOutputStream out2 = new ObjectOutputStream(nodo2.getOutputStream());
 out2.writeObject(A2);
 out2.writeObject(BT1);
 out2.writeObject(BT2);
 out2.writeObject(BT3);
 // Enviar matrices A3, BT1, BT2 y BT3 al nodo 3
 ObjectOutputStream out3 = new ObjectOutputStream(nodo3.getOutputStream());
 out3.writeObject(A3);
 out3.writeObject(BT1);
 out3.writeObject(BT2);
 out3.writeObject(BT3);
 // Recibir matrices C1, C2 y C3 del nodo 1
 try {
 ObjectInputStream in1 = new ObjectInputStream(nodo1.getInputStream());
 C1 = (double[][]) in1.readObject();
 C2 = (double[][]) in1.readObject();
 C3 = (double[][]) in1.readObject();
 } catch (IOException | ClassNotFoundException e) {
 e.printStackTrace();
 }
 // Recibir matrices C4, C5 y C6 del nodo 2
 try {
 ObjectInputStream in2 = new ObjectInputStream(nodo2.getInputStream());
 C4 = (double[][]) in2.readObject();
 C5 = (double[][]) in2.readObject();
 C6 = (double[][]) in2.readObject();
 } catch (IOException | ClassNotFoundException e) {
 e.printStackTrace();
 }
 // Recibir matrices C7, C8 y C9 del nodo 3
 try {
 ObjectInputStream in3 = new ObjectInputStream(nodo3.getInputStream());
 C7 = (double[][]) in3.readObject();
 C8 = (double[][]) in3.readObject();
 C9 = (double[][]) in3.readObject();
 } catch (IOException | ClassNotFoundException e) {
 e.printStackTrace();
 }
 // Obtener la matriz C a partir de las matrices C1, C2, C3, C4, C5, C6, C7, C8 y C9
 for (int i = 0; i < N; i++) {
 for (int j = 0; j < N; j++) {
 if (i < N/3 && j < N/3) {
 C[i][j] = C1[i][j];
 } else if (i < N/3 && j < 2*N/3) {
 C[i][j] = C2[i][j-N/3];
 } else if (i < N/3 && j < N) {
 C[i][j] = C3[i][j-2*N/3];
 } else if (i < 2*N/3 && j < N/3) {
 C[i][j] = C4[i-N/3][j];
 } else if (i < 2*N/3 && j < 2*N/3) {
 C[i][j] = C5[i-N/3][j-N/3];
 } else if (i < 2*N/3 && j < N) {
 C[i][j] = C6[i-N/3][j-2*N/3];
 } else if (i < N && j < N/3) {
 C[i][j] = C7[i-2*N/3][j];
 } else if (i < N && j < 2*N/3) {
 C[i][j] = C8[i-2*N/3][j-N/3];
 } else if (i < N && j < N) {
 C[i][j] = C9[i-2*N/3][j-2*N/3];
 }
 }
 }
 // Calcular el checksum de la matriz C
 double checksum = 0;
 for (int i = 0; i < N; i++) {
 for (int j = 0; j < N; j++) {
 checksum += C[i][j];
 }
 }
 // Desplegar el checksum de la matriz C
 System.out.println("Checksum de la matriz C: " + checksum);
 // Si N=12 entonces desplegar las matrices A, B y C
 if (N == 12) {
 System.out.println("Matriz A:");
 printMatrix(A);
 System.out.println("Matriz B:");
 printMatrix(B);
 System.out.println("Matriz C:");
 printMatrix(C);
 }
 }
 // Función para imprimir una matriz
 public static void printMatrix(double[][] matrix) {
 for (int i = 0; i < matrix.length; i++) {
 for (int j = 0; j < matrix[0].length; j++) {
 System.out.print(matrix[i][j] + " ");
 }
 System.out.println();
 }
 }
}
