package repository;

import interfaces.EstudianteRepository;
import modelo.Estudiante;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoEstudianteRepository implements EstudianteRepository {
    private static final String DATA_DIR = "./datos/";

    public ArchivoEstudianteRepository() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    private String obtenerNombreArchivo(String curso, String mes) {
        return DATA_DIR + curso + "_" + mes + ".txt";
    }

    @Override
    public void guardar(List<Estudiante> estudiantes, String curso, String mes) throws IOException {
        String fileName = obtenerNombreArchivo(curso, mes);
        try (FileWriter fw = new FileWriter(fileName, true);
             PrintWriter pw = new PrintWriter(fw)) {
            for (Estudiante e : estudiantes) {
                pw.printf("%s|%s|%d|%d|%d|%d%n",
                        e.getApellido(), e.getNombre(),
                        e.getMatematicas(), e.getLengua(),
                        e.getNaturales(), e.getSociales());
            }
        }
    }

    @Override
    public List<Estudiante> cargar(String curso, String mes) throws IOException {
        String fileName = obtenerNombreArchivo(curso, mes);
        File file = new File(fileName);
        if (!file.exists()) return new ArrayList<>();

        List<Estudiante> estudiantes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split("\\|");
                if (parts.length != 6) continue;
                try {
                    String apellido = parts[0];
                    String nombre = parts[1];
                    int mat = Integer.parseInt(parts[2]);
                    int len = Integer.parseInt(parts[3]);
                    int nat = Integer.parseInt(parts[4]);
                    int soc = Integer.parseInt(parts[5]);
                    estudiantes.add(new Estudiante(nombre, apellido, mat, len, nat, soc));
                } catch (NumberFormatException ignored) { }
            }
        }
        return estudiantes;
    }
}