package servicio;

import interfaces.EstrategiaLiteral;
import interfaces.EstudianteRepository;
import modelo.Estudiante;
import factory.ReporteFactory;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ReporteService {
    private final EstudianteRepository repository;
    private final EstrategiaLiteral estrategiaLiteral;

    public ReporteService(EstudianteRepository repository, EstrategiaLiteral estrategiaLiteral) {
        this.repository = repository;
        this.estrategiaLiteral = estrategiaLiteral;
    }

    public void generarYMostrar(Scanner scanner) {
        System.out.println("\n--- REPORTE DE CALIFICACIONES ---");
        System.out.print("Curso: ");
        String curso = scanner.nextLine().trim();
        System.out.print("Mes: ");
        String mes = scanner.nextLine().trim();

        try {
            List<Estudiante> estudiantes = repository.cargar(curso, mes);
            if (estudiantes.isEmpty()) {
                System.out.println("No hay calificaciones para " + curso + " en " + mes + ".\n");
                return;
            }
            estudiantes.sort(Comparator.comparing(Estudiante::getApellido));
            String reporte = ReporteFactory.generarReporte(curso, mes, estudiantes, estrategiaLiteral);
            System.out.print(reporte);
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
    }
}