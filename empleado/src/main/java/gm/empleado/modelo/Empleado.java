package gm.empleado.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpleado;

    private String nombreEmpleado;
    private String departamento;
    private Double sueldo;

    @Enumerated(EnumType.STRING)
    private EstatusEmpleado estatus = EstatusEmpleado.ACTIVO;
}