# Documento de Requisitos — MotoCitas

## 1. Introducción

MotoCitas es un sistema de gestión de citas para talleres de motocicletas que permite:
- Registrar y gestionar clientes
- Administrar motocicletas de los clientes
- Catalogar servicios disponibles
- Agendar citas de mantenimiento y reparación
- Registrar y controlar pagos

## 2. Requisitos Funcionales

### RF-001: Gestión de Clientes
- **Descripción**: Crear, leer, actualizar y eliminar (borrado lógico) clientes
- **Datos**: Nombre, Email, Teléfono, Documento de identificación
- **Búsqueda**: Por nombre

### RF-002: Gestión de Motocicletas
- **Descripción**: Registrar motocicletas asociadas a clientes
- **Datos**: Placa, Marca, Modelo, Año, Cliente propietario
- **Validación**: Cada moto debe estar asociada a un cliente válido
- **Búsqueda**: Por placa

### RF-003: Gestión de Servicios
- **Descripción**: Administrar servicios disponibles en el taller
- **Datos**: Nombre del servicio, Precio
- **Ejemplos**: Cambio de aceite, Reparación de frenos, Alineación, etc.

### RF-004: Agendar Citas
- **Descripción**: Permitir agendar citas para clientes
- **Datos**: Cliente, Motocicleta, Servicio, Fecha/Hora
- **Validaciones**:
  - La motocicleta debe pertenecer al cliente
  - No permitir citas duplicadas en el mismo horario (ventana de 1 hora)
  - El estado por defecto es PENDING
  - El total se calcula automáticamente desde el precio del servicio
- **Estados**: PENDING, COMPLETED, CANCELED

### RF-005: Registro de Pagos
- **Descripción**: Registrar pagos de citas completadas
- **Datos**: Cita, Monto, Método de pago, Fecha
- **Validaciones**:
  - No permitir pagos para citas canceladas
  - El monto no puede exceder el total de la cita
  - El monto debe ser positivo
  - Métodos válidos: CASH, CARD, TRANSFER
- **Métodos de pago**: Efectivo, Tarjeta, Transferencia

### RF-006: Búsquedas y Filtros
- Búsqueda de clientes por nombre
- Búsqueda de motocicletas por placa
- Búsqueda de servicios por nombre
- Listado de citas por rango de fechas
- Listado de pagos por cita

### RF-007: Borrado Lógico
- **Descripción**: No eliminar registros físicamente, marcar como inactivos
- **Implementación**: Campo `active = false`
- **Afecta a**: Clientes, Motocicletas, Servicios, Citas, Pagos

## 3. Requisitos No-Funcionales

### RNF-001: Performance
- Respuestas de API en menos de 500ms para operaciones CRUD
- Soporte para 1000+ clientes y 5000+ citas

### RNF-002: Disponibilidad
- Disponibilidad de al menos 99% en horario comercial
- Recuperación automática ante errores

### RNF-003: Seguridad
- Validaciones en servidor
- Manejo seguro de excepciones
- Datos sensibles no en logs

### RNF-004: Escalabilidad
- Arquitectura modular
- Fácil separación de servicios en el futuro

## 4. Tecnología

### Backend
- **Lenguaje**: Java 17+
- **Framework**: Spring Boot 3.1.4
- **Base de datos**: MySQL 8.0+
- **Build Tool**: Maven 3.6+

### Frontend
- **HTML5**, **CSS3**, **JavaScript** (sin frameworks)
- **Conexión**: REST API JSON
- **Estilo**: Responsive design

## 5. Modelo de Datos

### Tablas

**clients** (Clientes)
- id (PK)
- name (string)
- email (string)
- phone (string)
- document (string)
- active (boolean, default: true)
- created_at (timestamp)

**motorcycles** (Motocicletas)
- id (PK)
- client_id (FK)
- plate (string, unique)
- brand (string)
- model (string)
- year (int)
- active (boolean, default: true)
- created_at (timestamp)

**services** (Servicios)
- id (PK)
- name (string)
- price (decimal)
- active (boolean, default: true)

**appointments** (Citas)
- id (PK)
- client_id (FK)
- motorcycle_id (FK)
- service_id (FK)
- scheduled_at (datetime)
- status (string: PENDING, COMPLETED, CANCELED)
- total (decimal)
- active (boolean, default: true)
- created_at (timestamp)

**payments** (Pagos)
- id (PK)
- appointment_id (FK)
- amount (decimal)
- method (string: CASH, CARD, TRANSFER)
- paid_at (timestamp)
- active (boolean, default: true)

## 6. Interfaz de Usuario

### Páginas Principales
1. **Dashboard**: Bienvenida e indicadores generales
2. **Clientes**: CRUD de clientes
3. **Motocicletas**: CRUD de motocicletas
4. **Servicios**: CRUD de servicios
5. **Citas**: Agendar y gestionar citas
6. **Pagos**: Registrar y listar pagos

### Componentes
- Navegación superior
- Formularios responsivos
- Listados con acciones
- Alertas de feedback
- Búsquedas en tiempo real

## 7. API REST

### Endpoints

```
# Clientes
GET    /api/clients          - Listar
GET    /api/clients?q=nombre - Buscar
POST   /api/clients          - Crear
GET    /api/clients/{id}     - Obtener
PUT    /api/clients/{id}     - Actualizar
DELETE /api/clients/{id}     - Eliminar (borrado lógico)

# Motocicletas
GET    /api/motorcycles      - Listar
POST   /api/motorcycles      - Crear
PUT    /api/motorcycles/{id} - Actualizar
DELETE /api/motorcycles/{id} - Eliminar

# Servicios
GET    /api/services         - Listar
POST   /api/services         - Crear
PUT    /api/services/{id}    - Actualizar
DELETE /api/services/{id}    - Eliminar

# Citas
GET    /api/appointments     - Listar
POST   /api/appointments     - Crear
PUT    /api/appointments/{id} - Actualizar
DELETE /api/appointments/{id} - Cancelar

# Pagos
GET    /api/payments         - Listar
POST   /api/payments         - Crear
DELETE /api/payments/{id}    - Eliminar
```

## 8. Códigos de Error

| Código | Mensaje | Descripción |
|--------|---------|-------------|
| INVALID_CLIENT | Cliente no válido | El cliente especificado no existe |
| INVALID_MOTORCYCLE | Motocicleta no válida | La motocicleta especificada no existe |
| INVALID_SERVICE | Servicio no válido | El servicio especificado no existe |
| MOTO_NOT_BELONG_TO_CLIENT | La motocicleta no pertenece al cliente | Relación cliente-moto inválida |
| DUPLICATE_APPOINTMENT | Ya existe una cita para esta motocicleta en ese horario | Cita duplicada |
| CANCELED_APPOINTMENT | No se puede registrar pago para cita cancelada | Intento de pago en cita cancelada |
| AMOUNT_EXCEEDS_TOTAL | El monto no puede exceder el total de la cita | Pago mayor al total |
| INVALID_AMOUNT | El monto debe ser mayor a 0 | Monto no válido |
| INVALID_METHOD | Método de pago no válido | Solo CASH, CARD, TRANSFER |
| NOT_FOUND | Recurso no encontrado | ID no existe |
| CLIENT_NOT_FOUND | Cliente no encontrado | ID de cliente no existe |
| MOTORCYCLE_NOT_FOUND | Motocicleta no encontrada | ID de moto no existe |
| SERVICE_NOT_FOUND | Servicio no encontrado | ID de servicio no existe |
| APPOINTMENT_NOT_FOUND | Cita no encontrada | ID de cita no existe |

## 9. Casos de Uso

### Caso 1: Agendar Cita
1. Cliente ingresa a la plataforma
2. Selecciona su cliente registrado
3. Selecciona su motocicleta
4. Elige un servicio
5. Selecciona fecha y hora
6. Sistema valida que no exista cita duplicada
7. Sistema crea la cita y muestra confirmación

### Caso 2: Registrar Pago
1. Cliente solicita registrar pago de cita
2. Usuario selecciona la cita
3. Ingresa monto y método de pago
4. Sistema valida que la cita no esté cancelada
5. Sistema valida que el monto no exceda el total
6. Sistema registra el pago

### Caso 3: Buscar Cliente
1. Usuario accede a gestión de clientes
2. Ingresa parte del nombre
3. Sistema busca y filtra por nombre
4. Muestra resultados en tiempo real

## 10. Versión y Cronograma

- **Versión**: 0.0.1-SNAPSHOT
- **Fecha de Inicio**: Agosto 2026
- **Fases**:
  1. MVP Base (CRUD + Validaciones básicas)
  2. Mejoras UI/UX
  3. Autenticación y Roles
  4. Dashboard y Reportes
  5. Integraciones (Notificaciones, Pagos)

---

**Documento versión 1.0**
