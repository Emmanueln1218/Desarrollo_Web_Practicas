import java.util.Scanner;

//creamos la clase CaptDatos para capturar los datos de las mascotas
public class CaptDatos {

    public Scanner sc = new Scanner(System.in); //creamos un objeto Scanner para capturar los datos de las mascotas

    //creamos un metodo para capturar los datos del perro y otro para capturar los datos del gato
    public perro capturaPerro() {
        System.out.println("\n--- Captura de datos del Perro ---");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();


        System.out.print("Raza: "); //capturamos la raza del perro
        String raza = sc.nextLine();

        System.out.print("Fecha nacimiento: ");
        String fecha = sc.nextLine(); //capturamos la fecha de nacimiento del perro

        System.out.print("Peso: ");
        float peso = sc.nextFloat(); //capturamos el peso del perro
        sc.nextLine();

        System.out.print("Lugar de entrenamiento: ");
        String lugar = sc.nextLine();   //capturamos el lugar de entrenamiento del perro

        return new perro(nombre, raza, fecha, peso, lugar); //retornamos un nuevo objeto perro con los datos capturados
    }
//
    public gato capturaGato() {
        System.out.println("\n--- Captura de datos del Gato ---");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();   //capturamos el nombre del gato

        System.out.print("Raza: ");
        String raza = sc.nextLine();  //capturamos la raza del gato

        System.out.print("Fecha nacimiento: ");
        String fecha = sc.nextLine();  //capturamos la fecha de nacimiento del gato

        System.out.print("Peso: ");
        float peso = sc.nextFloat(); //capturamos el peso del gato

        System.out.print("Altura de salto: ");
        double altura = sc.nextDouble();  //capturamos la altura de salto del gato
        sc.nextLine();

        return new gato(nombre, raza, fecha, peso, altura);  //y al final retornamos un nuevo objeto gato con los datos capturados
    }
}