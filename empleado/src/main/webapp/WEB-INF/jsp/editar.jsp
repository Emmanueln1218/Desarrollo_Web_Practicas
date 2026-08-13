<%@include file="comunes/cabecero.jsp"%>
<%@include file="comunes/navegacion.jsp"%>

<div class="container">
    <!-- Título superior con el estilo de tu proyecto -->
    <div class="text-center" style="margin: 30px">
        <h3>Editar Empleado</h3>
    </div>

    <!-- Formulario para Spring Boot -->
    <div class="row justify-content-center">
        <div<form action="<c:url value='/editar'/>" method="POST">

                <!-- Campo Oculto ID (OBLIGATORIO para que Hibernate sepa qué registro actualizar) -->
                <input type="hidden" name="idEmpleado" value="${empleado.idEmpleado}" />

                <!-- Nombre -->
                <div class="mb-3">
                    <label for="nombreEmpleado" class="form-label fw-bold">Nombre Completo</label>
                    <input type="text"
                           class="form-control"
                           id="nombreEmpleado"
                           name="nombreEmpleado"
                           value="${empleado.nombreEmpleado}"
                           required />
                </div>

                <!-- Departamento -->
                <div class="mb-3">
                    <label for="departamento" class="form-label fw-bold">Departamento</label>
                    <input type="text"
                           class="form-control"
                           id="departamento"
                           name="departamento"
                           value="${empleado.departamento}"
                           required />label>
                      </div>
                <!-- Sueldo -->
                <div class="mb-3">
                    <label for="sueldo" class="form-label fw-bold">Sueldo (RD$)</label>
                    <input type="number"
                           step="0.01"
                           class="form-control"
                           id="sueldo"
                           name="sueldo"
                           value="${empleado.sueldo}"
                           required />
                </div>

                <!-- Botones -->
                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-check-circle me-1"></i> Guardar Cambios
                    </button>
                    <a href="<c:url value='/'/>" class="btn btn-outline-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="comunes/pie-pagina.jsp"%>