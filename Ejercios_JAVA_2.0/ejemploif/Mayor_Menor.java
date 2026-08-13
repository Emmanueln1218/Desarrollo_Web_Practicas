package ejemploif;
import java.util.Scanner;

//2. Dados 10 números, se pide obtener en número menor y mayor

public class Mayor_Menor {
    public static void main(String[] args) {
        // 1. Inicialización de variables
        int numeroMenor = Integer.MAX_VALUE;
        int numeroMayor = Integer.MIN_VALUE;
        Scanner scanner = new Scanner(System.in);

        // 2. Ciclo para solicitar los 10 números
        for (int i = 0; i < 10; i++) {
            System.out.print("Ingrese el número " + (i + 1) + ": ");
            int numero = scanner.nextInt();

            // 3. Evaluaciones condicionales
            if (numero < numeroMenor) {
                numeroMenor = numero;
            }
            if (numero > numeroMayor) {
                numeroMayor = numero;
            }
        }

        // 4. Impresión de resultados
        System.out.println("El número menor es: " + numeroMenor);
        System.out.println("El número mayor es: " + numeroMayor);

        // 5.
        scanner.close();
    } //
} //

