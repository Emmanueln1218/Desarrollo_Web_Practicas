<%@include file="comunes/cabecero.jsp"%>
<%@include file="comunes/navegacion.jsp"%>

<%-- Definición limpia de las URLs con JSTL --%>
<c:url var="urlAgregar" value="/agregar"/>
<c:url var="urlInicio" value="/"/>

<div class="container">
    <div class="text-center" style="margin: 30px">
        <h3>Agregar Empleado</h3>
    </div>

    <div class="row justify-content-center">
        <div class="col-md-6">
            <!-- Formulario HTML estándar compatible con Spring Boot -->
            <form action="${urlAgregar}" method="post">

                <!-- Nombre del Empleado -->
                <div class="mb-3">
                    <label for="nombreEmpleado" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombreEmpleado" name="nombreEmpleado" placeholder="Ej. Juan Perez" required>
                </div>

                <!-- Departamento -->
                <div class="mb-3">
                    <label for="departamento" class="form-label">Departamento</label>
                    <input type="text" class="form-control" id="departamento" name="departamento" placeholder="Ej. Finanzas, TI" required>
                </div>

                <!-- Sueldo -->
                <div class="mb-3">
                    <label for="sueldo" class="form-label">Sueldo (RD$)</label>
                    <input type="number" step="0.01" class="form-control" id="sueldo" name="sueldo" placeholder="0.00" required>
                </div>

                <!-- Botones de Acción -->
                <div class="d-flex justify-content-between mt-4">
                    <a href="${urlInicio}" class="btn btn-secondary">Regresar</a>
                    <button type="submit" class="btn btn-primary">Agregar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@include file="comunes/pie-pagina.jsp"%>