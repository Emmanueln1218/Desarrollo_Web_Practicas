package ejemploif;

import java.util.Scanner;

public class CalcularN {
    public static void main(String[] args) {

//Ejercicios

//1. Dados 10 números, se pide calcular la suma y el promedio aritmético


        int suma = 0;
        int promedio = 0;
        Scanner numeros = new Scanner(System.in);

        for (int i = 0; i < 10; i++) {
            System.out.print("Ingrese el número " + (i + 1) + ": ");
            int numero = numeros.nextInt();
            suma += numero;
        }
        promedio = suma / 10;

        System.out.println("La suma es: " + suma);
        System.out.println("El promedio es: " + promedio);


    }
}









