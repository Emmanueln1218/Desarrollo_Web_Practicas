import java.util.ArrayList;
  //creamos la clase Main para ejecutar el programa
public class Main {
    public static void main(String[] args) {

        //creamos un objeto de la clase CaptDatos para capturar los datos de las mascotas
        CaptDatos cap = new CaptDatos();

        int opcion = 0;
        String opcionStr; //variable para capturar la opcion del menu como String y luego validarla

        ArrayList<Mamifero> mascotas = new ArrayList<>();  //creamos un ArrayList para guardar las mascotas capturadas

                //menu principal para capturar las mascotas y mostrar sus datos
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Agregar Perro");
            System.out.println("2. Agregar Gato");
            System.out.println("3. Mostrar todas las mascotas");
            System.out.println("4. Buscar mascota por nombre");
            System.out.println("5. Editar mascota");
            System.out.println("6. Eliminar mascota");
            System.out.println("7. salir");
            System.out.print("seleccione una opcion valida: ");

            opcionStr = cap.sc.nextLine();  //capturamos la opcion del menu como String para luego validarla

            if (!opcionStr.matches("[0-9]+")) {
                System.out.println("Entrada invalida. Debe ser un numero.");  //validamos que la opcion sea un numero
                continue; // vuelve al menu sin cerrar el programa
            }

            opcion = Integer.parseInt(opcionStr); //convertimos la opcion a entero para luego usarla en el switch

            switch (opcion) { //usamos un switch para manejar las opciones del menu


                //cada caso del switch corresponde a una opcion del menu y ejecuta el codigo correspondiente
                case 1 -> { //si el usuario selecciona la opcion 1, se ejecuta el codigo para capturar los datos del perro y agregarlo a la lista de mascotas
                    perro perro = cap.capturaPerro();
                    mascotas.add(perro);
                    System.out.println("\nEl perro dice:"); 
                    perro.comunicarse();
                    break;
                }

                case 2 -> { //si el usuario selecciona la opcion 2, se ejecuta el codigo para capturar los datos del gato y agregarlo a la lista de mascotas
                    gato gato = cap.capturaGato();
                    mascotas.add(gato);
                    System.out.println("\nEl gato dice:");
                    gato.comunicarse();
                    break;
                }

                case 3 -> {  //si el usuario selecciona la opcion 3, se ejecuta el codigo para mostrar todas las mascotas registradas
                    if (mascotas.isEmpty()) {
                        System.out.println("No hay mascotas guardadas.");
                    } else {
                        System.out.println("Mascotas registradas:");
                        for (int i = 0; i < mascotas.size(); i++) {
                            Mamifero mascota = mascotas.get(i);
                            System.out.println((i + 1) + ". " + mascota);
                        }
                    }
                }

                case 4 -> { //si el usuario selecciona la opcion 4, se ejecuta el codigo para buscar una mascota por su nombre y mostrar sus datos
                    System.out.print("Ingrese el nombre de la mascota a buscar: ");
                    String nombreBusqueda = cap.sc.nextLine();
                    boolean encontrado = false;
                    for (Mamifero mascota : mascotas) {
                        if (mascota.getNombre().equalsIgnoreCase(nombreBusqueda)) {
                            System.out.println("Mascota encontrada: " + mascota.getNombre());
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Mascota no encontrada."); //si no se encuentra la mascota, se muestra un mensaje de error
                    }
                    break;
                }

                case 5 -> { //si el usuario selecciona la opcion 5, se ejecuta el codigo para editar los datos de una mascota buscando por su nombre
                    System.out.print("Ingrese el nombre de la mascota a editar: ");
                    String nombreEdicion = cap.sc.nextLine();
                    boolean encontrado = false;
                    for (Mamifero mascota : mascotas) {
                        if (mascota.getNombre().equalsIgnoreCase(nombreEdicion)) {
                            System.out.println("Editando mascota: " + mascota.getNombre());
                            // Aquí podrías agregar opciones para editar atributos específicos
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Mascota no encontrada.");
                    }
                    break; //este break es para salir del case 5 y volver al menu principal
                }

                case 6 -> { //si el usuario selecciona la opcion 6, se ejecuta el codigo para eliminar una mascota buscando por su nombre
                    System.out.print("Ingrese el nombre de la mascota a eliminar: ");
                    String nombreEliminacion = cap.sc.nextLine();
                    boolean eliminado = mascotas.removeIf(mascota -> mascota.getNombre().equalsIgnoreCase(nombreEliminacion));
                    if (eliminado) {
                        System.out.println("Mascota eliminada exitosamente.");
                    } else {
                        System.out.println("Mascota no encontrada.");
                    }
                    break;
                }
                case 7 -> System.out.println("Saliendo del programa. ¡Hasta luego!"); //si el usuario selecciona la opcion 7, se ejecuta el codigo para salir del programa

                default -> System.out.println("Opción no válida."); //si el usuario selecciona una opcion que no esta en el menu, se muestra un mensaje de error y se vuelve a mostrar el menu
            }

        } while (opcion != 3); //el menu se repetira hasta que el usuario seleccione la opcion 3 para mostrar todas las mascotas registradas,
        // ya que es una opcion que se puede usar para verificar que las otras opciones hayan funcionado correctamente
    }
}
