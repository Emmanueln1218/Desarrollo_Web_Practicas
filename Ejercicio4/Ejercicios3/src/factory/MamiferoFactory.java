package factory;

import model.Mamifero;
import ui.CaptDatos;

// Factory to create Mamifero instances
public final class MamiferoFactory {

    private MamiferoFactory() { }

    public static Mamifero crearPerro() {
        return CaptDatos.getInstance().capturaPerro();
    }

    public static Mamifero crearGato() {
        return CaptDatos.getInstance().capturaGato();
    }
}
