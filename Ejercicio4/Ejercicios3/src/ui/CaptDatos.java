package ui;

import java.util.Scanner;
import model.gato;
import model.perro;

//creamos la clase CaptDatos para capturar los datos de las mascotas (Singleton)
public class CaptDatos {

    private static CaptDatos instance = null;
    public Scanner sc; // Scanner compartido de la aplicación

    private CaptDatos() { this.sc = new Scanner(System.in); }

    public static CaptDatos getInstance() {
        if (instance == null) instance = new CaptDatos();
        return instance;
    }

    // helpers con validaciones simples
    public String readString(String prompt, int maxLen) {
        String val;
        do {
            System.out.print(prompt);
            val = sc.nextLine().trim();
            if (val.length() == 0) System.out.println("No puede estar vacío.");
            else if (val.length() > maxLen) System.out.println("Máximo " + maxLen + " caracteres.");
            else break;
        } while (true);
        return val;
    }

    public float readFloat(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { float v = Float.parseFloat(s); return v; }
            catch (Exception e) { System.out.println("Entrada inválida. Ingrese un número."); }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { double v = Double.parseDouble(s); return v; }
            catch (Exception e) { System.out.println("Entrada inválida. Ingrese un número."); }
        }
    }

    // capturas
    public perro capturaPerro() {
        System.out.println("\n--- Captura de datos del Perro ---");
        String nombre = readString("Nombre: ", 30);
        String raza = readString("Raza: ", 20);
        String fecha = readString("Fecha nacimiento: ", 10);
        float peso = readFloat("Peso: ");
        String lugar = readString("Lugar de entrenamiento: ", 30);
        String tipo = "Perro";
        return new perro(nombre, raza, tipo, fecha, peso, lugar);
    }

    public gato capturaGato() {
        System.out.println("\n--- Captura de datos del Gato ---");
        String nombre = readString("Nombre: ", 30);
        String raza = readString("Raza: ", 20);
        String fecha = readString("Fecha nacimiento: ", 10);
        float peso = readFloat("Peso: ");
        double altura = readDouble("Altura de salto: ");
        String tipo = "Gato";
        return new gato(nombre, raza, tipo, fecha, peso, altura);
    }
}
