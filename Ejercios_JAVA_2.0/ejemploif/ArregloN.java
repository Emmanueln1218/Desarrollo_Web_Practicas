package ejemploif;
import java.util.Scanner;


//4. Ubique un número dado en un arreglo de 10 números ingresados con anterioridad,
//reporte también su posesión en el arreglo.



public class ArregloN {
    public static void main(String[] args) {



Scanner scanner4 = new Scanner(System.in);
int[] arreglo = new int[10];

for (int i = 0; i < 10; i++) {
    System.out.print("Ingrese el número " + (i + 1) + ": ");
    arreglo[i] = scanner4.nextInt();
}

System.out.print("Ingrese el número a buscar: ");
int numeroBuscar = scanner4.nextInt();
boolean encontrado = false;

for (int i = 1; i < 10; i++) {
    if (arreglo[i] == numeroBuscar) {
        System.out.println("El número " + numeroBuscar + " se encuentra en la posición " + (i + 1));
        encontrado = true;
    }
}

if (!encontrado) {
    System.out.println("El número " + numeroBuscar + " no se encuentra en el arreglo");
}


    }
}
