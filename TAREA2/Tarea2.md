# Tarea 2

# **Cliente TCP en Java**

Este es un programa cliente TCP en Java que cumple con las siguientes especificaciones:

- Sean A, B y C matrices cuadradas con dimensión N con elementos de tipo double.
- Las matrices A y B se inicializarán de la siguiente forma: A[i][j]=2*i+j, B[i][j]=3*i-j, para i=0 hasta N-1 y j=0 hasta N-1.
- Sea BT la transpuesta de B.
- La matriz A se divide en tres partes de N/3 renglones y N columnas, a éstas particiones les llamaremos A1, A2 y A3.
- La matriz BT se divide en tres partes de N/3 renglones y N columnas, a éstas particiones les llamaremos BT1, BT2 y BT3.
- Sean C1, C2, C3, C4, C5, C6, C7, C8 y C9 submatrices de C con N/3 renglones y N/3 columnas.
- El cliente se conectará a tres servidores TCP a los cuales llamaremos nodo 1, nodo 2 y nodo 3.
- El cliente deberá recibir como parámetros las direcciones IP de los tres nodos.
- Para enviar y recibir las matrices se deberán serializar.
- El cliente deberá hacer lo siguiente:
    1. Inicializará las matrices A y B.
    2. Enviará la matriz A1, BT1, BT2 y BT3 al nodo 1.
    3. Enviará la matriz A2, BT1, BT2 y BT3 al nodo 2.
    4. Enviará la matriz A3, BT1, BT2 y BT3 al nodo 3.
    5. Enviará las matrices C1, C2 y C3 del nodo 1.
    6. Recibirá las matrices C4, C5 y C6 del nodo 2.
    7. Recibirá las matrices C7, C8 y C9 del nodo 3.
    8. Obtendrá la matriz C a partir de las matrices C1, C2, C3, C4, C5, C6, C7, C8 y C9.
    9. Calculará el checksum de la matriz C. El ckecksum es la suma de todos los elementos de la matriz C.
    10. Desplegará el checksum de la matriz C.
    11. Si N=12 entonces el cliente desplegará las matrices A, B y C.

La función printMatrix recorre los elementos de la matriz e imprime cada uno en una línea separada. Si N es igual a 12, entonces se llamará a esta función para imprimir las matrices A, B y C.

Nota: si se desea mejorar la presentación de las matrices, se puede utilizar la clase DecimalFormat para imprimir los elementos con un número fijo de decimales.

# **Servidor TCP multithread en Java**

Este es un programa servidor TCP multithread en Java que cumple con las siguientes especificaciones:

- Sean Ai, BT1, BT2 y BT3 matrices con N/3 renglones y N columnas con elementos de tipo double.
- Sean C1, C2, C3, C4, C5, C6, C7, C8 y C9 matrices con N/3 renglones y N/3 columnas de tipo double.
- Para enviar y recibir las matrices se deber
