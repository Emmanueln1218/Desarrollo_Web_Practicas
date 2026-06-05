package servicio;

import interfaces.EstudianteRepository;
import modelo.Estudiante;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class RegistroService {
    private final EstudianteRepository repository;
    private final Scanner scanner;

    public RegistroService(EstudianteRepository repository, Scanner scanner) {
        this.repository = repository;
        this.scanner = scanner;
    }

    public void registrar() {
        System.out.println("\n--- REGISTRO DE CALIFICACIONES ---");
        System.out.print("Curso (ej. 1B, 2A): ");
        String curso = scanner.nextLine().trim();
        if (curso.isEmpty()) { System.out.println("Curso no válido."); return; }

        System.out.print("Mes (ej. Enero, Febrero): ");
        String mes = scanner.nextLine().trim();
        if (mes.isEmpty()) { System.out.println("Mes no válido."); return; }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();

        int matematicas = leerNota("Matemática");
        int lengua = leerNota("Lengua");
        int naturales = leerNota("Naturales");
        int sociales = leerNota("Sociales");

        Estudiante nuevo = new Estudiante(nombre, apellido, matematicas, lengua, naturales, sociales);
        try {
            repository.guardar(List.of(nuevo), curso, mes);
            System.out.println("Calificaciones registradas exitosamente.\n");
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    private int leerNota(String materia) {
        int nota = -1;
        while (nota < 0 || nota > 100) {
            System.out.print("Nota de " + materia + " (0-100): ");
            try {
                nota = Integer.parseInt(scanner.nextLine().trim());
                if (nota < 0 || nota > 100) System.out.println("Nota entre 0 y 100.");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero.");
            }
        }
        return nota;
    }
}