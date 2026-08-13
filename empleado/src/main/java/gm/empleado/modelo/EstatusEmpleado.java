package gm.empleado.modelo;

import lombok.Getter;

@Getter
public enum EstatusEmpleado {
    ACTIVO("Activo", "success"),
    VACACIONES("En Vacaciones", "warning"),
    LICENCIA("De Licencia", "info"),
    INACTIVO("Inactivo / Baja", "danger");

    private final String etiqueta;
    private final String colorBadge;

    EstatusEmpleado(String etiqueta, String colorBadge) {
        this.etiqueta = etiqueta;
        this.colorBadge = colorBadge;
    }

}
