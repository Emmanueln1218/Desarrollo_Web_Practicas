package gm.empleado.servicio;

import gm.empleado.modelo.Empleado;
import gm.empleado.modelo.EstatusEmpleado;
import gm.empleado.repositorio.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServicio {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    public List<Empleado> ListarEmpleados() {
        return empleadoRepositorio.findAll();
    }

    public Empleado buscarEmpleadoPorID(Integer idEmpleado) {
        return empleadoRepositorio.findById(idEmpleado).orElse(null);
    }

    public void guardarEmpleado(Empleado empleado) {
        if (empleado.getEstatus() == null) {
            empleado.setEstatus(EstatusEmpleado.ACTIVO);
        }

        // Evita que la BD falle si el sueldo llega nulo
        if (empleado.getSueldo() == null) {
            empleado.setSueldo(0.0);
        }

        empleadoRepositorio.save(empleado);
    }


    // Baja lógica (Soft Delete)
    public void eliminarEmpleado(Integer idEmpleado) {
        Empleado empleado = empleadoRepositorio.findById(idEmpleado).orElse(null);
        if (empleado != null) {
            empleado.setEstatus(EstatusEmpleado.INACTIVO);
            empleadoRepositorio.save(empleado);
        }
    }

    // Métodos para el buscador y filtro
    public List<String> obtenerDepartamentos() {
        return empleadoRepositorio.findDistinctDepartamentos();
    }

    public List<Empleado> buscarEmpleados(String nombre, String departamento) {
        // Le pasas el estatus ACTIVO (o null si deseas traer todos)
        return empleadoRepositorio.buscarPorFiltros(nombre, departamento, EstatusEmpleado.ACTIVO);
    }

    // Métodos para métricas
    public long obtenerTotalEmpleados() {
        // CAMBIA "ACTIVO" POR EstatusEmpleado.ACTIVO
        return empleadoRepositorio.countByEstatus(EstatusEmpleado.ACTIVO);
    }

    public Double obtenerGastoTotalSueldos() {
        Double total = empleadoRepositorio.sumSueldos();
        return (total != null) ? total : 0.0;
    }
}