package factory;

import interfaces.EstrategiaLiteral;
import modelo.Estudiante;
import java.util.List;

public class ReporteFactory {
    public static String generarReporte(String curso, String mes, List<Estudiante> estudiantes, EstrategiaLiteral estrategiaLiteral) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nColegio Dios es bueno.\n");
        sb.append("Reporte de Calificaciones de ").append(mes).append("\n");
        sb.append("Curso: ").append(curso).append("\n");
        sb.append("================================================\n");
        sb.append(String.format("%-12s %-12s %-10s %-7s %-10s %-9s %-10s %-8s%n",
                "Nombre", "Apellido", "Matemática", "Lengua", "Naturales", "Sociales", "Promedio", "Literal"));
        sb.append("------------------------------------------------\n");

        for (Estudiante e : estudiantes) {
            double promedio = e.calcularPromedio();
            String literal = estrategiaLiteral.calcularLiteral(promedio);
            sb.append(String.format("%-12s %-12s %-10d %-7d %-10d %-9d %-10.1f %-8s%n",
                    e.getNombre(), e.getApellido(),
                    e.getMatematicas(), e.getLengua(), e.getNaturales(), e.getSociales(),
                    promedio, literal));
        }

        sb.append("================================================\n");
        sb.append("Total de estudiantes: ").append(estudiantes.size()).append("\n\n");
        return sb.toString();
    }
}