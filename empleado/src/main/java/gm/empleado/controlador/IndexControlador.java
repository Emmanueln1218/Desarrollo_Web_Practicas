package gm.empleado.controlador;

import gm.empleado.modelo.Empleado;
import gm.empleado.servicio.EmpleadoServicio;
import gm.empleado.util.EmpleadoExcelGenerator;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexControlador {

    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    @Autowired
    private EmpleadoServicio empleadoServicio;

    // Ruta Inicio con protección de sesión + Filtros de Búsqueda
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String iniciar(@RequestParam(value = "nombre", required = false) String nombre,
                          @RequestParam(value = "departamento", required = false) String departamento,
                          HttpSession session,
                          ModelMap modelo) {

        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }

        // Obtener empleados según los filtros aplicados
        List<Empleado> empleados = empleadoServicio.buscarEmpleados(nombre, departamento);
        List<String> departamentos = empleadoServicio.obtenerDepartamentos();

        // Métricas del Dashboard
        long totalEmpleados = empleadoServicio.obtenerTotalEmpleados();
        Double gastoTotalSueldos = empleadoServicio.obtenerGastoTotalSueldos();
        double promedioSueldo = totalEmpleados > 0 ? (gastoTotalSueldos / totalEmpleados) : 0.0;

        // Enviar al JSP
        modelo.put("empleados", empleados);
        modelo.put("departamentos", departamentos);
        modelo.put("totalEmpleados", totalEmpleados);
        modelo.put("gastoTotalSueldos", gastoTotalSueldos);
        modelo.put("promedioSueldo", promedioSueldo);
        modelo.put("nombreBuscado", nombre);
        modelo.put("departamentoSeleccionado", departamento);

        return "index";
    }


    // Mostrar Agregar Empleado
    @RequestMapping(value = "/agregar", method = RequestMethod.GET)
    public String mostrarAgregar(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        return "agregar"; // agregar.jsp
    }

    // Procesar Agregar Empleado
    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public String agregar(@ModelAttribute("empleadoForma") Empleado empleado) {
        logger.info("Empleado a agregar: " + empleado);
        empleadoServicio.guardarEmpleado(empleado);
        return "redirect:/";
    }

    // Mostrar Editar Empleado
    @RequestMapping(value = "/editar", method = RequestMethod.GET)
    public String mostrarEditar(@RequestParam int idEmpleado, HttpSession session, ModelMap modelo) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }

        Empleado empleado = empleadoServicio.buscarEmpleadoPorID(idEmpleado);
        logger.info("Empleado a editar: " + empleado);
        modelo.put("empleado", empleado);
        return "editar"; // editar.jsp
    }

    // Procesar Editar Empleado
    @RequestMapping(value = "/editar", method = RequestMethod.POST)
    public String editar(@ModelAttribute("empleadoForma") Empleado empleado) {
        logger.info("Empleado a Guardar: " + empleado);
        empleadoServicio.guardarEmpleado(empleado);
        return "redirect:/";
    }

    // Eliminar Empleado
    @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
    public String eliminar(@RequestParam int idEmpleado, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }

        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(idEmpleado);
        empleadoServicio.eliminarEmpleado(empleado.getIdEmpleado());
        return "redirect:/";
    }
    @RequestMapping(value = "/exportar-excel", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportarExcel(@RequestParam(value = "nombre", required = false) String nombre,
                                                @RequestParam(value = "departamento", required = false) String departamento,
                                                HttpSession session) throws Exception {

        if (session.getAttribute("usuarioLogueado") == null) {
            return ResponseEntity.status(401).build();
        }

        List<Empleado> empleados = empleadoServicio.buscarEmpleados(nombre, departamento);
        byte[] excelBytes = EmpleadoExcelGenerator.generarReporteExcel(empleados);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "Lista_Empleados.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    
    }

}

