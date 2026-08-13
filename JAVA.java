

public class JAVA {
    
    public static void main(String[] args) {


    //Declare los  sigtes tipos de datos:

    //entero a
    int  a = 56;

    System.out.println("El valor de a es: " + a);

    //entero X, Y
    int  X = 10, Y = 23;

    System.out.println("El valor de X es: " + X);
    System.out.println("El valor de Y es: " + Y);

    //double x
    double x = 09.56;

    System.out.println("El valor de x es: " + x);

    //double X1, Y2
    double X1 = 73, Y2 = 90.45;

    System.out.println("El valor de X1 es: " + X1);
    System.out.println("El valor de Y2 es: " + Y2);

    //entero A, b, c con valores 5, 6, 7 respectivamente
    int  A = 5, b=6 , c = 7;

    System.out.println("El valor de A es: " + A);
    System.out.println("El valor de b es: " + b);
    System.out.println("El valor de c es: " + c);

    //logico sw con valor inicial falso
    boolean sw = false;


    //cadena cad con valor nulo
    String cad = null;


    //declare una constante para PI con valor 3.14
    final double PI = 3.14;
    System.out.println("El valor de PI es: " + PI);



    //2. ggConvertir cadena = “200” a un entero en la variable E
    String cadena  = "200";
    /*Conversion*/  int E = Integer.parseInt(cadena);
    
    System.out.println("El valor de E es: " + E);



    //3. Convertir cadena = “ 200 ” a un entero en la variable E1
        String CADENA = "200";
        /*Conversion*/ int E1 = Integer.parseInt(CADENA);

        System.out.println("El valor de E1 es: " + E1);



    //4. Convertir cadena = “200” a un float en la variable f1

    String CadenaS = "200";
    /*Conversion*/ float F1 = Float.parseFloat(CadenaS);

    System.out.println("El valor de F1 es: " + F1);



    //5. Convertir el float 23.84f a una cadena en la variable nomb
        float Nomb = 23.84f;
        /*Conversion*/ String nomb = Float.toString(Nomb);

        System.out.println("El valor de nomb es: " + nomb);



   // 6. Defina que es un dato tipo referencia, de tres ejemplos
    /*Un dato tipo referencia es un tipo de dato que almacena la direccion
    de memoria donde se encuentra el valor real del dato. En lugar de almacenar el valor directamente,
    almacena una referencia a ese valor. Ejemplos de datos tipo referencia en Java son:

    1. String: Es un tipo de dato que representa una secuencia de caracteres.
    2. Array: Es un tipo de dato que representa una coleccion de elementos del mismo tipo.
    3. Object: Es un tipo de dato que representa una instancia de una clase, que puede contener atributos y metodos.*/

    }
}





