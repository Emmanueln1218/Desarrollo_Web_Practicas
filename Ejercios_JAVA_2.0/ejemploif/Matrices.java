package ejemploif;

import java.util.Scanner;


public class Matrices {
    public static void main(String[] args) {
        //5. Ingrese dos matrices de orden 3 x 3 y calcule:


        Scanner scanner5 = new Scanner(System.in);
        int[][] matriz1 = new int[3][3];
        int[][] matriz2 = new int[3][3];

        System.out.println("Ingrese los elementos de la primera matriz 3x3:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("Matriz1[" + i + "][" + j + "]: ");
                matriz1[i][j] = scanner5.nextInt();
            }
        }

        System.out.println("Ingrese los elementos de la segunda matriz 3x3:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("Matriz2[" + i + "][" + j + "]: ");
                matriz2[i][j] = scanner5.nextInt();
            }
        }

//• Su producto de cada matriz por un número dado
        System.out.print("Ingrese el número para multiplicar las matrices: ");
        int numeroMultiplicar = scanner5.nextInt();

        System.out.println("Producto de matriz1 por " + numeroMultiplicar + ":");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(matriz1[i][j] * numeroMultiplicar + " ");
            }
            System.out.println();
        }

        System.out.println("Producto de matriz2 por " + numeroMultiplicar + ":");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(matriz2[i][j] * numeroMultiplicar + " ");
            }
            System.out.println();
        }

//• la suma de las dos matrices
        System.out.println("Suma de las dos matrices:");
        int[][] sumaMatrices = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sumaMatrices[i][j] = matriz1[i][j] + matriz2[i][j];
                System.out.print(sumaMatrices[i][j] + " ");
            }
            System.out.println();
        }

// el producto de las dos matrices
        System.out.println("Producto de las dos matrices:");
        int[][] productoMatrices = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                productoMatrices[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    productoMatrices[i][j] += matriz1[i][k] * matriz2[k][j];
                }
                System.out.print(productoMatrices[i][j] + " ");
            }
            System.out.println();
        }

    }
}