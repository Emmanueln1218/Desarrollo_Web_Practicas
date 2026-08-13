package menu;

import interfaces.Comando;
import interfaces.EstudianteRepository;
import interfaces.EstrategiaLiteral;
import repository.ArchivoEstudianteRepository;
import estrategia.LiteralPorRango;
import servicio.RegistroService;
import servicio.ReporteService;
import comando.RegistrarCommand;
import comando.ReporteCommand;
import comando.SalirCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal {
    private static MenuPrincipal instancia;
    private final Map<String, Comando> comandos;
    private final Scanner scanner;

    private MenuPrincipal() {
        scanner = new Scanner(System.in);
        comandos = new HashMap<>();

        EstudianteRepository repository = new ArchivoEstudianteRepository();
        EstrategiaLiteral estrategiaLiteral = new LiteralPorRango();
        RegistroService registroService = new RegistroService(repository, scanner);
        ReporteService reporteService = new ReporteService(repository, estrategiaLiteral);

        comandos.put("1", new RegistrarCommand(registroService));
        comandos.put("2", new ReporteCommand(reporteService, scanner));
        comandos.put("3", new SalirCommand());
        comandos.put("ESC", new SalirCommand());
    }

    public static MenuPrincipal getInstance() {
        if (instancia == null) {
            instancia = new MenuPrincipal();
        }
        return instancia;
    }

    public void iniciar() {
        System.out.println("================================================");
        System.out.println("COLEGIO DIOS ES BUENO");
        System.out.println("SISTEMA DE CALIFICACIONES");
        System.out.println("================================================");

        while (true) {
            mostrarOpciones();
            String input = scanner.nextLine().trim().toUpperCase();

            Comando comando = comandos.get(input);
            if (comando != null) {
                comando.ejecutar();
                if (comando instanceof SalirCommand) break;
            } else {
                System.out.println("Opción no válida. Presione 1, 2, 3 o ESC.\n");
            }
        }
        scanner.close();
    }

    private void mostrarOpciones() {
        System.out.println("================================================");
        System.out.println("1- Registro de calificaciones");
        System.out.println("2- Reporte calificaciones por mes");
        System.out.println("3- Presione <ESC> para salir");
        System.out.println("================================================");
        System.out.print("Elija la opción deseada y pulse <ENTER>: ");
    }
}