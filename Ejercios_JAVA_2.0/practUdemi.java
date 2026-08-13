public class practUdemi {
    public static void main(String[] args) {
        /*String Nombre_Completo = "Wagner Alcantara";
        int Edad = 24;
        var Kaltura_Metros = 2.44;
        String Pais_Origen = "Republica Dominican";
        char esCasado = 'S';

            // Ver posicion de caracteres en los indices
        var caracter = Nombre_Completo.charAt(9);
        System.out.println("El caracter 9 = " + caracter);

        var caracter2 = Nombre_Completo.charAt(2);
        System.out.println("El caracter 2 = " + caracter2);*/





        /*System.out.println(Nombre_Completo);
        System.out.println(Edad);
        System.out.println(Kaltura_Metros);
        System.out.println(Pais_Origen);

       var Edad1 = new String("Cadena");
        System.out.println("Edad1");

        String Cadena23 = """
                Hola
                como estas?""";
        System.out.println("Cadena23 = " + Cadena23);*/
        
        //Comparacion de cadenas en referencia a otra
        
        var cadena1 = "P3ractica";
        var cadena2 = "Practica";
        String cadena3 = new String("P3ractica"); // esto lo que hace es que asigna un nuevo espacio de
        // memoria y crea un nuevo objeto aunque tegna el mismo valor

        System.out.print("Ambos cadenas son iguales: ");
        System.out.println(cadena1 == cadena2);

        //comparar contenido usando el metodo equals
        System.out.print("El contenido de cadena1 es igual a cadena 3: ");
        System.out.println(cadena1.equals(cadena3));

    }
}