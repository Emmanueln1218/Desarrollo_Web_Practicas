package comando;

import interfaces.Comando;

public class SalirCommand implements Comando {
    @Override
    public void ejecutar() {
        System.out.println("Saliendo del sistema...");
        System.exit(0);
    }
}