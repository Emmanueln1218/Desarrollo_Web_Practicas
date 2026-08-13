<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<%-- Definición de URLs seguras con JSTL --%>
<c:url var="urlInicio" value="/"/>
<c:url var="urlAgregar" value="/agregar"/>
<c:url var="urlLogout" value="/logout"/>

<!-- Barra de Navegación -->
<div class="container mb-4">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary rounded-bottom shadow-sm">
        <div class="container-fluid px-3">

            <!-- Marca / Nombre del Sistema -->
            <a class="navbar-brand fw-bold" href="${urlInicio}">
                Sistema de Gestión de Empleados
            </a>

            <!-- Botón Hamburguesa Móvil -->
            <button class="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarNav"
                    aria-controls="navbarNav"
                    aria-expanded="false"
                    aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- Menú Colapsable -->
            <div class="collapse navbar-collapse" id="navbarNav">
                <!-- Enlaces de la aplicación (Izquierda/Centro) -->
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="${urlInicio}">Inicio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${urlAgregar}">Agregar Empleado</a>
                    </li>
                </ul>

                <!-- Sección del Usuario / Cerrar Sesión (Derecha) -->
                <ul class="navbar-nav align-items-lg-center">
                    <c:if test="${not empty sessionScope.usuarioLogueado}">
                        <li class="nav-item">
                            <span class="nav-link text-white me-2">
                                Bienvenido, <strong>${sessionScope.usuarioLogueado.username}</strong>
                            </span>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-outline-light btn-sm px-3" href="${urlLogout}">
                                Cerrar Sesión
                            </a>
                        </li>
                    </c:if>
                </ul>
            </div>

        </div>
    </nav>
</div>