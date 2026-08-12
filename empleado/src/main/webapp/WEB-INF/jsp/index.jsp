<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<%@include file="comunes/cabecero.jsp"%>
<%@include file="comunes/navegacion.jsp"%>

<div class="container my-4">

    <!-- Encabezado de Bienvenida -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold text-dark mb-1">
                <i class="bi bi-people-fill text-primary me-2"></i>Gestión de Empleados
            </h3>
            <p class="text-muted mb-0">Administra el personal, busca por nombre o filtra por departamento.</p>
        </div>
        <div>
            <a href="<c:url value='/exportar-excel'>
                    <c:param name='nombre' value='${nombreBuscado}'/>
                    <c:param name='departamento' value='${departamentoSeleccionado}'/>
                 </c:url>"
               class="btn btn-outline-success shadow-sm"
               title="Exportar a Excel">
                <i class="bi bi-file-earmark-excel-fill me-1"></i> Exportar Excel
            </a>
            <a href="<c:url value='/agregar'/>" class="btn btn-primary shadow-sm">
                <i class="bi bi-person-plus-fill me-1"></i> Nuevo Empleado
            </a>
        </div>
    </div>

    <!-- Tarjetas de Métricas (Dashboard) -->
    <div class="row g-3 mb-4">

        <!-- Tarjeta 1: Total Empleados -->
        <div class="col-md-4">
            <div class="card border-0 shadow-sm bg-white rounded-3 h-100">
                <div class="card-body d-flex align-items-center">
                    <div class="bg-primary bg-opacity-10 text-primary p-3 rounded-circle me-3">
                        <i class="bi bi-people-fill fs-3"></i>
                    </div>
                    <div>
                        <h6 class="text-muted fw-normal mb-1 small text-uppercase tracking-wider">Total Empleados</h6>
                        <h3 class="fw-bold text-dark mb-0">${totalEmpleados}</h3>
                    </div>
                </div>
            </div>
        </div>

        <!-- Tarjeta 2: Presupuesto Total de Nómina -->
        <div class="col-md-4">
            <div class="card border-0 shadow-sm bg-white rounded-3 h-100">
                <div class="card-body d-flex align-items-center">
                    <div class="bg-success bg-opacity-10 text-success p-3 rounded-circle me-3">
                        <i class="bi bi-cash-stack fs-3"></i>
                    </div>
                    <div>
                        <h6 class="text-muted fw-normal mb-1 small text-uppercase tracking-wider">Gasto Total Nómina</h6>
                        <h3 class="fw-bold text-success mb-0">
                            <fmt:formatNumber pattern="RD$ #,##0.00" value="${gastoTotalSueldos}"/>
                        </h3>
                    </div>
                </div>
            </div>
        </div>

        <!-- Tarjeta 3: Sueldo Promedio -->
        <div class="col-md-4">
            <div class="card border-0 shadow-sm bg-white rounded-3 h-100">
                <div class="card-body d-flex align-items-center">
                    <div class="bg-info bg-opacity-10 text-info p-3 rounded-circle me-3">
                        <i class="bi bi-graph-up-arrow fs-3"></i>
                    </div>
                    <div>
                        <h6 class="text-muted fw-normal mb-1 small text-uppercase tracking-wider">Sueldo Promedio</h6>
                        <h3 class="fw-bold text-info mb-0">
                            <fmt:formatNumber pattern="RD$ #,##0.00" value="${promedioSueldo}"/>
                        </h3>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <!-- Formulario de Filtros y Búsqueda -->
    <div class="card border-0 shadow-sm mb-4">
        <div class="card-body p-3 bg-white rounded">
            <form action="<c:url value='/'/>" method="GET" class="row g-2 align-items-center">

                <!-- Campo de texto: Búsqueda por Nombre -->
                <div class="col-md-5">
                    <div class="input-group">
                        <span class="input-group-text bg-light border-end-0 text-muted">
                            <i class="bi bi-search"></i>
                        </span>
                        <input type="text"
                               name="nombre"
                               class="form-control border-start-0 bg-light"
                               placeholder="Buscar por nombre..."
                               value="${nombreBuscado}">
                    </div>
                </div>

                <!-- Menú desplegable: Filtro por Departamento -->
                <div class="col-md-4">
                    <select name="departamento" class="form-select bg-light">
                        <option value="">-- Todos los Departamentos --</option>
                        <c:forEach var="dep" items="${departamentos}">
                            <option value="${dep}" ${dep eq departamentoSeleccionado ? 'selected' : ''}>
                                ${dep}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Botones de Acción -->
                <div class="col-md-3 d-flex gap-2">
                    <button type="submit" class="btn btn-primary w-100 fw-semibold">
                        <i class="bi bi-funnel-fill me-1"></i> Filtrar
                    </button>
                    <a href="<c:url value='/'/>" class="btn btn-outline-secondary" title="Limpiar Filtros">
                        <i class="bi bi-arrow-counterclockwise"></i>
                    </a>
                </div>

            </form>
        </div>
    </div>

    <!-- Tabla de Empleados -->
    <div class="card border-0 shadow-sm">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col" class="text-center ps-3">ID</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">Departamento</th>
                            <th scope="col">Estatus</th>
                            <th scope="col" class="text-end">Sueldo</th>
                            <th scope="col" class="text-center pe-3">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty empleados}">
                                <c:forEach var="empleado" items="${empleados}">
                                    <tr>
                                        <!-- ID -->
                                        <td class="text-center fw-bold text-muted ps-3">
                                            #${empleado.idEmpleado}
                                        </td>

                                        <!-- Nombre -->
                                        <td class="fw-semibold text-dark">
                                            ${empleado.nombreEmpleado}
                                        </td>

                                        <!-- Departamento -->
                                        <td>
                                            <span class="badge bg-info bg-opacity-10 text-info border border-info border-opacity-25 px-2 py-1 fs-6">
                                                ${empleado.departamento}
                                            </span>
                                        </td>

                                        <!-- Estatus (Badge dinámico) -->
                                        <td>
                                            <span class="badge bg-${empleado.estatus.colorBadge} bg-opacity-10 text-${empleado.estatus.colorBadge} border border-${empleado.estatus.colorBadge} border-opacity-25 px-2 py-1">
                                                <i class="bi bi-circle-fill me-1 small"></i>${empleado.estatus.etiqueta}
                                            </span>
                                        </td>

                                        <!-- Sueldo Formateado -->
                                        <td class="text-end fw-bold text-success">
                                            <fmt:formatNumber pattern="RD$ #,##0.00" value="${empleado.sueldo}"/>
                                        </td>

                                        <!-- Botones de Acción -->
                                        <td class="text-center pe-3">
                                            <div class="btn-group btn-group-sm" role="group">
                                                <!-- URL Editar -->
                                                <c:url var="urlEditar" value="/editar">
                                                    <c:param name="idEmpleado" value="${empleado.idEmpleado}"/>
                                                </c:url>
                                                <a href="${urlEditar}" class="btn btn-outline-warning" title="Editar">
                                                    <i class="bi bi-pencil-square"></i>
                                                </a>

                                                <!-- URL Eliminar / Baja -->
                                                <c:url var="urlEliminar" value="/eliminar">
                                                    <c:param name="idEmpleado" value="${empleado.idEmpleado}"/>
                                                </c:url>
                                                <a href="${urlEliminar}"
                                                   class="btn btn-outline-danger btn-sm"
                                                   title="Dar de Baja"
                                                   onclick="return confirm('¿Confirmas dar de baja a ${empleado.nombreEmpleado}? El registro se conservará como inactivo.');">
                                                    <i class="bi bi-person-x-fill me-1"></i> Dar de baja
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="6" class="text-center py-4 text-muted">
                                        <i class="bi bi-inbox fs-2 d-block mb-2"></i>
                                        No se encontraron empleados registrados o coincidentes con la búsqueda.
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>

<%@include file="comunes/pie-pagina.jsp"%>
