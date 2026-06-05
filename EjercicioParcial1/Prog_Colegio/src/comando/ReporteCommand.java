package comando;

import interfaces.Comando;
import servicio.ReporteService;
import java.util.Scanner;

public class ReporteCommand implements Comando {
    private final ReporteService reporteService;
    private final Scanner scanner;

    public ReporteCommand(ReporteService reporteService, Scanner scanner) {
        this.reporteService = reporteService;
        this.scanner = scanner;
    }

    @Override
    public void ejecutar() {
        reporteService.generarYMostrar(scanner);
    }
}