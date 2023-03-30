
import java.util.*;
import java.io.*;
import java.net.*;

public class Tarea2 {

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.print("Introduzca la dirección IP: ");
        int numNodo = myObj.nextInt(); //Obtenemos el numero del nodo como parametro
        switch (numNodo) {
            case 0:
            try {
                // Creamos el socket del servidor
                ServerSocket serverSocket = new ServerSocket(8080);
                
                // Esperamos la conexión del cliente
                System.out.println("Esperando la conexión del cliente...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde la dirección: " + clientSocket.getInetAddress().getHostAddress());
                
                // Obtenemos los streams de entrada y salida del socket
                DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                
                // Leemos las matrices del cliente
                double[][] An = readMatrix(inputStream);
                double[][] B1 = readMatrix(inputStream);
                double[][] B2 = readMatrix(inputStream);
                double[][] B3 = readMatrix(inputStream);
                
                // Multiplicamos An x B1, An x B2 y An x B3
                double[][] Cn = multiplyMatrices(An, B1);
                double[][] Cn1 = multiplyMatrices(An, B2);
                double[][] Cn2 = multiplyMatrices(An, B3);
                
                // Enviamos las matrices al cliente
                sendMatrix(outputStream, Cn);
                sendMatrix(outputStream, Cn1);
                sendMatrix(outputStream, Cn2);
                
                // Cerramos los streams y el socket
                inputStream.close();
                outputStream.close();
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error en el servidor: " + e.getMessage());
            }
                break;
            case 1:
                //acciones para el nodo 1
                try {
                    // Definir los parametros de la matriz
                    int N = 9;
                    int N_tercio = N/3;
                    double[][] A = new double[N][N];
                    double[][] B = new double[N][N];
                    double[][] C = new double[N][N];
                    double[][] BT = new double[N][N];
                    double[][] A1 = new double[N_tercio][N];
                    double[][] A2 = new double[N_tercio][N];
                    double[][] A3 = new double[N_tercio][N];
                    double[][] C1 = new double[N_tercio][N_tercio];
                    double[][] C2 = new double[N_tercio][N_tercio];
                    double[][] C3 = new double[N_tercio][N_tercio];
                    double[][] C4 = new double[N_tercio][N_tercio];
                    double[][] C5 = new double[N_tercio][N_tercio];
                    double[][] C6 = new double[N_tercio][N_tercio];
                    double[][] C7 = new double[N_tercio][N_tercio];
                    double[][] C8 = new double[N_tercio][N_tercio];
                    double[][] C9 = new double[N_tercio][N_tercio];
        
                    // Inicializar las matrices A y B con valores aleatorios
                    for (int i = 0; i < N; i++) {
                        for (int j = 0; j < N; j++) {
                            A[i][j] = Math.random();
                            B[i][j] = Math.random();
                        }
                    }
        
                    // Calcular la matriz C como A*B
                    for (int i = 0; i < N; i++) {
                        for (int j = 0; j < N; j++) {
                            for (int k = 0; k < N; k++) {
                                C[i][j] += A[i][k] * B[k][j];
                            }
                        }
                    }
        
                    // Calcular la matriz BT como la transpuesta de B
                    for (int i = 0; i < N; i++) {
                        for (int j = 0; j < N; j++) {
                            BT[i][j] = B[j][i];
                        }
                    }
        
                    // Dividir la matriz BT en tres partes de N/3 renglones y N columnas
                    for (int i = 0; i < N_tercio; i++) {
                        for (int j = 0; j < N; j++) {
                            A1[i][j] = BT[i][j];
                            A2[i][j] = BT[i+N_tercio][j];
                            A3[i][j] = BT[i+(2*N_tercio)][j];
                        }
                    }
        
                    // Crear las direcciones IP de los tres nodos
                    InetAddress dirNodo1 = InetAddress.getByName("51.142.126.228");
                    InetAddress dirNodo2 = InetAddress.getByName("20.229.86.175");
                    InetAddress dirNodo3 = InetAddress.getByName("20.21.96.158");
        
                    // Conectar al nodo 1 y enviar la submatriz A1
                    Socket socketNodo1 = new Socket(dirNodo1, 8080);
                    ObjectOutputStream outNodo1 = new ObjectOutputStream(socketNodo1.getOutputStream());
                    outNodo1.writeObject(A1);
                    ObjectInputStream inNodo1 = new ObjectInputStream(socketNodo1.getInputStream());
                    C1 = (double[][]) inNodo1.readObject();
                    C2 = (double[][]) inNodo1.readObject();
                    C3 = (double[][]) inNodo1.readObject();
                    socketNodo1.close();
        
                    // Conectar al nodo 2 y enviar la submatriz A2
                    Socket socketNodo2 = new Socket(dirNodo2, 8080);
                    ObjectOutputStream outNodo2 = new ObjectOutputStream(socketNodo2.getOutputStream());
                    outNodo2.writeObject(A2);
                    ObjectInputStream inNodo2 = new ObjectInputStream(socketNodo2.getInputStream());
                    C4 = (double[][]) inNodo2.readObject();
                    C5 = (double[][]) inNodo2.readObject();
                    C6 = (double[][]) inNodo2.readObject();
                    socketNodo2.close();
        
                    // Conectar al nodo 3 y enviar la submatriz A3
                    Socket socketNodo3 = new Socket(dirNodo3, 8080);
                    ObjectOutputStream outNodo3 = new ObjectOutputStream(socketNodo3.getOutputStream());
                    outNodo3.writeObject(A3);
                    ObjectInputStream inNodo3 = new ObjectInputStream(socketNodo3.getInputStream());
                    C7 = (double[][]) inNodo3.readObject();
                    C8 = (double[][]) inNodo3.readObject();
                    C9 = (double[][]) inNodo3.readObject();
                    socketNodo3.close();
        
                    // Unir las submatrices C1-C9 para formar la matriz C completa
                    for (int i = 0; i < N_tercio; i++) {
                        for (int j = 0; j < N_tercio; j++) {
                            C[i][j] = C1[i][j];
                            C[i][j+N_tercio] = C2[i][j];
                            C[i][j+(2*N_tercio)] = C3[i][j];
                            C[i+N_tercio][j] = C4[i][j];
                            C[i+N_tercio][j+N_tercio] = C5[i][j];
                            C[i+N_tercio][j+(2*N_tercio)] = C6[i][j];
                            C[i+(2*N_tercio)][j] = C7[i][j];
                            C[i+(2*N_tercio)][j+N_tercio] = C8[i][j];
                            C[i+(2*N_tercio)][j+(2*N_tercio)] = C9[i][j];
                        }
                    }
                    // Imprimir la matriz C completa
                    for (int i = 0; i < N; i++) {
                        for (int j = 0; j < N; j++) {
                            System.out.print(C[i][j] + " ");
                        }
                        System.out.println();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;

            case 2:
                //acciones para el nodo 2
                try {
                    // Creamos el socket del servidor
                    ServerSocket serverSocket = new ServerSocket(8080);
                    
                    // Esperamos la conexión del cliente
                    System.out.println("Esperando la conexión del cliente...");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Cliente conectado desde la dirección: " + clientSocket.getInetAddress().getHostAddress());
                    
                    // Obtenemos los streams de entrada y salida del socket
                    DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                    
                    // Leemos las matrices del cliente
                    double[][] An = readMatrix(inputStream);
                    double[][] B1 = readMatrix(inputStream);
                    double[][] B2 = readMatrix(inputStream);
                    double[][] B3 = readMatrix(inputStream);
                    
                    // Multiplicamos An x B1, An x B2 y An x B3
                    double[][] Cn = multiplyMatrices(An, B1);
                    double[][] Cn1 = multiplyMatrices(An, B2);
                    double[][] Cn2 = multiplyMatrices(An, B3);
                    
                    // Enviamos las matrices al cliente
                    sendMatrix(outputStream, Cn);
                    sendMatrix(outputStream, Cn1);
                    sendMatrix(outputStream, Cn2);
                    
                    // Cerramos los streams y el socket
                    inputStream.close();
                    outputStream.close();
                    clientSocket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Error en el servidor: " + e.getMessage());
                }
                break;


            case 3:
                //acciones para el nodo 3
                try {
                    // Creamos el socket del servidor
                    ServerSocket serverSocket = new ServerSocket(8080);
                    
                    // Esperamos la conexión del cliente
                    System.out.println("Esperando la conexión del cliente...");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Cliente conectado desde la dirección: " + clientSocket.getInetAddress().getHostAddress());
                    
                    // Obtenemos los streams de entrada y salida del socket
                    DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                    
                    // Leemos las matrices del cliente
                    double[][] An = readMatrix(inputStream);
                    double[][] B1 = readMatrix(inputStream);
                    double[][] B2 = readMatrix(inputStream);
                    double[][] B3 = readMatrix(inputStream);
                    
                    // Multiplicamos An x B1, An x B2 y An x B3
                    double[][] Cn = multiplyMatrices(An, B1);
                    double[][] Cn1 = multiplyMatrices(An, B2);
                    double[][] Cn2 = multiplyMatrices(An, B3);
                    
                    // Enviamos las matrices al cliente
                    sendMatrix(outputStream, Cn);
                    sendMatrix(outputStream, Cn1);
                    sendMatrix(outputStream, Cn2);
                    
                    // Cerramos los streams y el socket
                    inputStream.close();
                    outputStream.close();
                    clientSocket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Error en el servidor: " + e.getMessage());
                }
                break;


            default:
                System.out.println("Número de nodo no válido");
        }
    }


    public static double[][] readMatrix(DataInputStream inputStream) throws IOException {
        // Leemos las dimensiones de la matriz
        int n = inputStream.readInt();
        int m = inputStream.readInt();
        
        // Creamos la matriz
        double[][] matrix = new double[n][m];
        
        // Leemos los elementos de la matriz
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = inputStream.readDouble();
            }
        }
        
        return matrix;
    }
    
    public static void sendMatrix(DataOutputStream outputStream, double[][] matrix) throws IOException {
        // Enviamos las dimensiones de la matriz
        int n = matrix.length;
        int m = matrix[0].length;
        outputStream.writeInt(n);
        outputStream.writeInt(m);
        
        // Enviamos los elementos de la matriz
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                outputStream.writeDouble(matrix[i][j]);
            }
        }
    }
    
    public static double[][] multiplyMatrices(double[][] A, double[][] B) {
        // Multiplicamos las matrices A y B
        int n = A.length;
        int m = B[0].length;
        int p = B.length;
        double[][] C = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < p; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    public static double[][] readMatrix(DataInputStream inputStream) throws IOException {
        // Leemos las dimensiones de la matriz
        int n = inputStream.readInt();
        int m = inputStream.readInt();
        
        // Creamos la matriz
        double[][] matrix = new double[n][m];
        
        // Leemos los elementos de la matriz
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = inputStream.readDouble();
            }
        }
        
        return matrix;
    }
    
    public static void sendMatrix(DataOutputStream outputStream, double[][] matrix) throws IOException {
        // Enviamos las dimensiones de la matriz
        int n = matrix.length;
        int m = matrix[0].length;
        outputStream.writeInt(n);
        outputStream.writeInt(m);
        
        // Enviamos los elementos de la matriz
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                outputStream.writeDouble(matrix[i][j]);
            }
        }
    }
    
    public static double[][] multiplyMatrices(double[][] A, double[][] B) {
        // Multiplicamos las matrices A y B
        int n = A.length;
        int m = B[0].length;
        int p = B.length;
        double[][] C = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < p; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }
}

    

