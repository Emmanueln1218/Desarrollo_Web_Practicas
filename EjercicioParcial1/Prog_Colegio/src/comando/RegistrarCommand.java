package comando;

import interfaces.Comando;
import servicio.RegistroService;

public class RegistrarCommand implements Comando {
    private final RegistroService registroService;

    public RegistrarCommand(RegistroService registroService) {
        this.registroService = registroService;
    }

    @Override
    public void ejecutar() {
        registroService.registrar();
    }
}