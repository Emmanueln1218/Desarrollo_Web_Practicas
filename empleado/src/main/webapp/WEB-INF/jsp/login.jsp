<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Sistema de Gestión de Empleados</title>

    <!-- Bootstrap 5.3.3 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #0f172a 0%, #1e293b 100%);
            min-height: 100vh;
        }
        .login-card {
            background: #ffffff;
            border-radius: 1rem;
            box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.3);
        }
        .brand-header {
            color: #f8fafc;
        }
        .form-control:focus {
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.15);
        }
    </style>
</head>
<body class="d-flex align-items-center justify-content-center py-5">

<div class="container">
    <!-- Encabezado del Sistema -->
    <div class="text-center mb-4 brand-header">
        <div class="d-inline-flex align-items-center justify-content-center bg-primary bg-opacity-20 text-primary p-3 rounded-circle mb-3">
            <i class="bi bi-people-fill fs-2 text-white"></i>
        </div>
        <h2 class="fw-bold tracking-tight">Sistema de Gestión de Empleados</h2>
        <p class="text-slate-400 opacity-75">Ingresa tus credenciales para acceder al panel</p>
    </div>

    <!-- Tarjeta de Login -->
    <div class="row justify-content-center">
        <div class="col-12 col-sm-10 col-md-8 col-lg-5 col-xl-4">
            <div class="card login-card border-0 p-3 p-sm-4">
                <div class="card-body">
                    <h4 class="fw-bold text-slate-800 text-center mb-4">Iniciar Sesión</h4>

                    <!-- Alerta de Error -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show d-flex align-items-center py-2 px-3 small mb-4" role="alert">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i>
                            <div>${error}</div>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="<c:url value='/login'/>" method="POST">

                        <!-- Usuario -->
                        <div class="mb-3">
                            <label for="username" class="form-label fw-medium text-secondary">Usuario</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light border-end-0 text-muted">
                                    <i class="bi bi-person"></i>
                                </span>
                                <input type="text" class="form-control border-start-0 ps-0 bg-light" id="username" name="username" placeholder="Nombre de usuario" required>
                            </div>
                        </div>

                        <!-- Contraseña -->
                        <div class="mb-4">
                            <label for="password" class="form-label fw-medium text-secondary">Contraseña</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light border-end-0 text-muted">
                                    <i class="bi bi-lock"></i>
                                </span>
                                <input type="password" class="form-control border-start-0 ps-0 bg-light" id="password" name="password" placeholder="••••••••" required>
                            </div>
                        </div>

                        <!-- Botón Ingresar -->
                        <button type="submit" class="btn btn-primary w-100 py-2 fw-semibold rounded-2 shadow-sm">
                            <i class="bi bi-box-arrow-in-right me-2"></i>Ingresar
                        </button>
                    </form>
                </div>
            </div>

            <!-- Footer Informativo -->
            <div class="text-center mt-4 text-white-50 small">
                &copy; ${year != null ? year : '2026'} Sistema de Desarrollado por EM DIGITAL. Todos los derechos reservados.
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>