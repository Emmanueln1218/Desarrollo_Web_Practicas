# MotoCitas

Sistema de gestión de citas para un taller de motocicletas.

## Documentación

- **Requisitos completos**: [DOCUMENTO_REQUISITOS.md](DOCUMENTO_REQUISITOS.md)
- **Diagrama de base de datos**: [er_diagram.md](er_diagram.md)
- **Documentación de APIs**: [API_DOCS.md](API_DOCS.md)
- **Guía de instalación**: [INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md)

## Estructura del Proyecto

```
.
├── src/main/java/com/motocitas/
│   ├── model/            # Entidades JPA
│   ├── repository/       # Repositorios Spring Data
│   ├── controller/       # Controladores REST
│   ├── service/          # Lógica de negocio y validaciones
│   ├── dto/              # Data Transfer Objects
│   ├── exception/        # Manejo de excepciones
│   └── MotoCitasApplication.java
├── src/main/resources/
│   ├── static/           # Frontend HTML/CSS/JS
│   └── application.properties
└── pom.xml               # Dependencias Maven
```

## Validaciones de Negocio Implementadas

✅ **Citas**
- Evita citas duplicadas en el mismo horario para la misma motocicleta
- Valida que la motocicleta pertenezca al cliente
- Calcula automáticamente el total desde el precio del servicio

✅ **Pagos**
- No permite pagos para citas canceladas
- Valida que el monto no exceda el total de la cita
- Valida métodos de pago válidos (CASH, CARD, TRANSFER)

✅ **General**
- Borrado lógico con campo `active`
- Manejo centralizado de excepciones
- Búsquedas y filtros en cada módulo

## Inicio Rápido

1. **Configurar base de datos MySQL** y crear la base `motocitas`
2. **Actualizar credenciales** en `src/main/resources/application.properties`
3. **Compilar**:
   ```bash
   mvn clean package
   ```
4. **Ejecutar**:
   ```bash
   java -jar target/motocitas-0.0.1-SNAPSHOT.jar
   ```
5. **Acceder**: http://localhost:8080

Ver [INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md) para instrucciones detalladas.

## APIs Disponibles

Endpoints principales (ver [API_DOCS.md](API_DOCS.md) para todos):

```
POST   /api/clients           - Crear cliente
GET    /api/clients           - Listar clientes
PUT    /api/clients/{id}      - Actualizar cliente
DELETE /api/clients/{id}      - Desactivar cliente

POST   /api/motorcycles       - Crear motocicleta
GET    /api/motorcycles       - Listar motocicletas
...

POST   /api/services          - Crear servicio
GET    /api/services          - Listar servicios
...

POST   /api/appointments      - Crear cita
GET    /api/appointments      - Listar citas
PUT    /api/appointments/{id} - Actualizar cita
DELETE /api/appointments/{id} - Cancelar cita

POST   /api/payments          - Registrar pago
GET    /api/payments          - Listar pagos
DELETE /api/payments/{id}     - Anular pago
```

## Próximas Fases

- Autenticación y roles (Admin, Recepción, Mecánico)
- Dashboard con métricas y gráficas
- Reportes PDF
- Interfaz frontend mejorada
- Tests y CI/CD

---

**MotoCitas v0.0.1** — © 2026 WAGNER DIGITAL
