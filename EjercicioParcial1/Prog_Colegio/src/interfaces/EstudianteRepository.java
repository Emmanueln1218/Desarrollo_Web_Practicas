package interfaces;

import modelo.Estudiante;
import java.io.IOException;
import java.util.List;

public interface EstudianteRepository {
    void guardar(List<Estudiante> estudiantes, String curso, String mes) throws IOException;
    List<Estudiante> cargar(String curso, String mes) throws IOException;
}