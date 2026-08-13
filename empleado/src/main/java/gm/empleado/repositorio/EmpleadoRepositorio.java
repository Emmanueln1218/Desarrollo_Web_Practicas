package gm.empleado.repositorio;

import gm.empleado.modelo.Empleado;
import gm.empleado.modelo.EstatusEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpleadoRepositorio extends JpaRepository<Empleado, Integer> {

    // Búsqueda con filtros solo sobre empleados ACTIVOS
    @Query("SELECT e FROM Empleado e WHERE " +
            "(:nombre IS NULL OR LOWER(e.nombreEmpleado) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
            "(:departamento IS NULL OR :departamento = '' OR e.departamento = :departamento) AND " +
            "(:estatus IS NULL OR e.estatus = :estatus)")
    List<Empleado> buscarPorFiltros(@Param("nombre") String nombre,
                                    @Param("departamento") String departamento,
                                    @Param("estatus") EstatusEmpleado estatus);

    // Sumar nómina solo de los empleados que NO están en baja
    @Query("SELECT SUM(e.sueldo) FROM Empleado e WHERE e.estatus != gm.empleado.modelo.EstatusEmpleado.INACTIVO")
    Double sumSueldos();

    // Contar únicamente los ACTIVOS

    // Firma correcta
    long countByEstatus(EstatusEmpleado estatus);

    // Obtener departamentos únicos de personal ACTIVO
    @Query("SELECT DISTINCT e.departamento FROM Empleado e WHERE e.estatus = 'ACTIVO'")
    List<String> findDistinctDepartamentos();
    }