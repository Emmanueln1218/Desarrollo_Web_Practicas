# API Documentation — MotoCitas

## Validaciones de Negocio Implementadas

### Citas (Appointments)
- **No duplicadas**: Evita citas para la misma motocicleta en la misma hora (ventana de 1 hora).
- **Relación válida**: La motocicleta debe pertenecer al cliente.
- **Servicio válido**: El servicio debe existir en el sistema.
- **Total automático**: Se calcula automáticamente desde el precio del servicio.

### Pagos (Payments)
- **No pagar canceladas**: No se registran pagos para citas con estado CANCELED.
- **Monto válido**: El monto debe ser positivo y no exceder el total de la cita.
- **Método válido**: Solo CASH, CARD, TRANSFER.

### Clientes y Motocicletas
- **Borrado lógico**: Campo `active = false` para desactivar sin eliminar.

## Endpoints

### Clientes
```
GET  /api/clients                     - Listar clientes
GET  /api/clients?q=nombre            - Buscar por nombre
GET  /api/clients/{id}                - Obtener cliente por ID
POST /api/clients                     - Crear cliente
PUT  /api/clients/{id}                - Actualizar cliente
DEL  /api/clients/{id}                - Desactivar cliente
```

**Body POST/PUT:**
```json
{
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "phone": "3001234567",
  "document": "12345678"
}
```

### Motocicletas
```
GET  /api/motorcycles                 - Listar motocicletas
GET  /api/motorcycles?q=placa         - Buscar por placa
GET  /api/motorcycles/{id}            - Obtener motocicleta por ID
POST /api/motorcycles                 - Crear motocicleta
PUT  /api/motorcycles/{id}            - Actualizar motocicleta
DEL  /api/motorcycles/{id}            - Desactivar motocicleta
```

**Body POST/PUT:**
```json
{
  "client": {"id": 1},
  "plate": "ABC-123",
  "brand": "Honda",
  "model": "CB500",
  "year": 2022
}
```

### Servicios
```
GET  /api/services                    - Listar servicios
GET  /api/services?q=nombre           - Buscar por nombre
GET  /api/services/{id}               - Obtener servicio por ID
POST /api/services                    - Crear servicio
PUT  /api/services/{id}               - Actualizar servicio
DEL  /api/services/{id}               - Desactivar servicio
```

**Body POST/PUT:**
```json
{
  "name": "Cambio de aceite",
  "price": 50000
}
```

### Citas
```
GET  /api/appointments                - Listar citas
GET  /api/appointments/{id}           - Obtener cita por ID
POST /api/appointments                - Crear cita
PUT  /api/appointments/{id}           - Actualizar cita (estado, etc.)
DEL  /api/appointments/{id}           - Cancelar cita
```

**Body POST (CreateAppointmentRequest):**
```json
{
  "clientId": 1,
  "motorcycleId": 1,
  "serviceId": 1,
  "scheduledAt": "2026-08-15T10:00:00"
}
```

**Body PUT (Actualizar estado):**
```json
{
  "status": "COMPLETED"
}
```

### Pagos
```
GET  /api/payments                    - Listar pagos
GET  /api/payments/{id}               - Obtener pago por ID
POST /api/payments                    - Registrar pago
DEL  /api/payments/{id}               - Anular pago
```

**Body POST (CreatePaymentRequest):**
```json
{
  "appointmentId": 1,
  "amount": 50000,
  "method": "CASH"  // CASH, CARD, TRANSFER
}
```

## Manejo de Errores

Las excepciones retornan un JSON con siguiente estructura:

```json
{
  "error": true,
  "code": "ERROR_CODE",
  "message": "Descripción del error"
}
```

### Códigos de Error

| Código | Mensaje | HTTP Status |
|--------|---------|------------|
| INVALID_CLIENT | Cliente no válido | 400 |
| INVALID_MOTORCYCLE | Motocicleta no válida | 400 |
| INVALID_SERVICE | Servicio no válido | 400 |
| MOTO_NOT_BELONG_TO_CLIENT | La motocicleta no pertenece al cliente | 400 |
| DUPLICATE_APPOINTMENT | Cita duplicada en horario | 400 |
| CANCELED_APPOINTMENT | No se puede pagar cita cancelada | 400 |
| AMOUNT_EXCEEDS_TOTAL | Monto excede el total | 400 |
| INVALID_AMOUNT | Monto no válido (debe ser > 0) | 400 |
| INVALID_METHOD | Método de pago no válido | 400 |
| NOT_FOUND | Recurso no encontrado | 400 |
| CLIENT_NOT_FOUND | Cliente no encontrado | 400 |
| MOTORCYCLE_NOT_FOUND | Motocicleta no encontrada | 400 |
| SERVICE_NOT_FOUND | Servicio no encontrado | 400 |
| APPOINTMENT_NOT_FOUND | Cita no encontrada | 400 |

## Ejemplos de Uso

### Crear Cliente
```bash
curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Pérez",
    "phone": "3001234567",
    "email": "juan@example.com",
    "document": "12345678"
  }'
```

**Respuesta (201):**
```json
{
  "id": 1,
  "name": "Juan Pérez",
  "phone": "3001234567",
  "email": "juan@example.com",
  "document": "12345678",
  "active": true,
  "createdAt": "2026-08-12T15:30:00"
}
```

### Crear Motocicleta
```bash
curl -X POST http://localhost:8080/api/motorcycles \
  -H "Content-Type: application/json" \
  -d '{
    "client": {"id": 1},
    "plate": "ABC-123",
    "brand": "Honda",
    "model": "CB500",
    "year": 2022
  }'
```

### Crear Servicio
```bash
curl -X POST http://localhost:8080/api/services \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Cambio de aceite",
    "price": 50000
  }'
```

### Agendar Cita
```bash
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": 1,
    "motorcycleId": 1,
    "serviceId": 1,
    "scheduledAt": "2026-08-15T10:00:00"
  }'
```

**Respuesta (201):**
```json
{
  "id": 1,
  "client": {
    "id": 1,
    "name": "Juan Pérez"
  },
  "motorcycle": {
    "id": 1,
    "plate": "ABC-123"
  },
  "serviceItem": {
    "id": 1,
    "name": "Cambio de aceite",
    "price": 50000
  },
  "scheduledAt": "2026-08-15T10:00:00",
  "status": "PENDING",
  "total": 50000,
  "active": true,
  "createdAt": "2026-08-12T15:35:00"
}
```

### Registrar Pago
```bash
curl -X POST http://localhost:8080/api/payments \
  -H "Content-Type: application/json" \
  -d '{
    "appointmentId": 1,
    "amount": 50000,
    "method": "CASH"
  }'
```

**Respuesta (201):**
```json
{
  "id": 1,
  "appointment": {
    "id": 1
  },
  "amount": 50000,
  "method": "CASH",
  "paidAt": "2026-08-12T15:40:00",
  "active": true
}
```

### Intentar Pago Mayor al Total (Error)
```bash
curl -X POST http://localhost:8080/api/payments \
  -H "Content-Type: application/json" \
  -d '{
    "appointmentId": 1,
    "amount": 100000,
    "method": "CARD"
  }'
```

**Respuesta (400):**
```json
{
  "error": true,
  "code": "AMOUNT_EXCEEDS_TOTAL",
  "message": "El monto no puede exceder el total de la cita"
}
```

### Intentar Crear Cita Duplicada (Error)
```bash
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": 1,
    "motorcycleId": 1,
    "serviceId": 1,
    "scheduledAt": "2026-08-15T10:30:00"
  }'
```

**Respuesta (400):**
```json
{
  "error": true,
  "code": "DUPLICATE_APPOINTMENT",
  "message": "Ya existe una cita para esta motocicleta en ese horario"
}
```

## CORS

La API está configurada con `@CrossOrigin(origins = "*")` en todos los controladores para permitir solicitudes desde cualquier origen. Para producción, restringir a dominios específicos.

## Rate Limiting

No implementado en v0.0.1. Considerar para versiones futuras.

## Autenticación

No implementada en v0.0.1. Versiones futuras incluirán JWT o similar.

---

**API Documentation v1.0**
