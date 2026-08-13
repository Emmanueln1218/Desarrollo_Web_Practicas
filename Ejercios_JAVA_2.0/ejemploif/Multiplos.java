package ejemploif;
import java.util.Scanner;


//3. Dados 10 números y un divisor, se pide hallar los múltiplos del divisor en cada

import java.util.Scanner;

public class Multiplos {
    public static void main(String[] args) {
//número ingresado.


Scanner scanner3 = new Scanner(System.in);
System.out.print("Ingrese el divisor: ");
int divisor = scanner3.nextInt();

for (int i = 0; i < 10; i++) {
    System.out.print("Ingrese el número " + (i + 1) + ": ");
    int numero = scanner3.nextInt();
    if (numero % divisor == 0) {
        System.out.println(numero + " es múltiplo de " + divisor);
    } else {
        System.out.println(numero + " no es múltiplo de " + divisor);
    }
}

    }
}
