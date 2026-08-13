#!/bin/bash

# MotoCitas API Examples
# Script with cURL examples for all endpoints

BASE_URL="http://localhost:8080"

echo "═══════════════════════════════════════════════════════════"
echo "MotoCitas API Examples"
echo "═══════════════════════════════════════════════════════════"

# ========================
# CLIENTS
# ========================

echo ""
echo "📋 CLIENTES (Clients)"
echo "───────────────────────────────────────────────────────────"

echo ""
echo "1️⃣  Crear cliente"
curl -X POST "$BASE_URL/api/clients" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Pérez",
    "email": "juan@example.com",
    "phone": "3001234567",
    "document": "12345678"
  }' | jq '.'

echo ""
echo "2️⃣  Listar clientes"
curl -X GET "$BASE_URL/api/clients" | jq '.'

echo ""
echo "3️⃣  Buscar cliente por nombre"
curl -X GET "$BASE_URL/api/clients?q=Juan" | jq '.'

echo ""
echo "4️⃣  Obtener cliente por ID"
curl -X GET "$BASE_URL/api/clients/1" | jq '.'

echo ""
echo "5️⃣  Actualizar cliente"
curl -X PUT "$BASE_URL/api/clients/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Carlos Pérez",
    "email": "juancarlos@example.com",
    "phone": "3009876543",
    "document": "12345678"
  }' | jq '.'

# ========================
# MOTORCYCLES
# ========================

echo ""
echo "🏍️  MOTOCICLETAS (Motorcycles)"
echo "───────────────────────────────────────────────────────────"

echo ""
echo "1️⃣  Crear motocicleta"
curl -X POST "$BASE_URL/api/motorcycles" \
  -H "Content-Type: application/json" \
  -d '{
    "client": {"id": 1},
    "plate": "ABC-123",
    "brand": "Honda",
    "model": "CB500",
    "year": 2022
  }' | jq '.'

echo ""
echo "2️⃣  Listar motocicletas"
curl -X GET "$BASE_URL/api/motorcycles" | jq '.'

echo ""
echo "3️⃣  Buscar motocicleta por placa"
curl -X GET "$BASE_URL/api/motorcycles?q=ABC" | jq '.'

echo ""
echo "4️⃣  Obtener motocicleta por ID"
curl -X GET "$BASE_URL/api/motorcycles/1" | jq '.'

echo ""
echo "5️⃣  Actualizar motocicleta"
curl -X PUT "$BASE_URL/api/motorcycles/1" \
  -H "Content-Type: application/json" \
  -d '{
    "client": {"id": 1},
    "plate": "ABC-123",
    "brand": "Honda",
    "model": "CB600",
    "year": 2023
  }' | jq '.'

# ========================
# SERVICES
# ========================

echo ""
echo "🔧 SERVICIOS (Services)"
echo "───────────────────────────────────────────────────────────"

echo ""
echo "1️⃣  Crear servicio"
curl -X POST "$BASE_URL/api/services" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Cambio de aceite",
    "price": 50000
  }' | jq '.'

echo ""
echo "2️⃣  Listar servicios"
curl -X GET "$BASE_URL/api/services" | jq '.'

echo ""
echo "3️⃣  Buscar servicio por nombre"
curl -X GET "$BASE_URL/api/services?q=aceite" | jq '.'

echo ""
echo "4️⃣  Obtener servicio por ID"
curl -X GET "$BASE_URL/api/services/1" | jq '.'

echo ""
echo "5️⃣  Actualizar servicio"
curl -X PUT "$BASE_URL/api/services/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Cambio de aceite y filtro",
    "price": 65000
  }' | jq '.'

# ========================
# APPOINTMENTS
# ========================

echo ""
echo "📅 CITAS (Appointments)"
echo "───────────────────────────────────────────────────────────"

echo ""
echo "1️⃣  Agendar cita"
curl -X POST "$BASE_URL/api/appointments" \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": 1,
    "motorcycleId": 1,
    "serviceId": 1,
    "scheduledAt": "2026-08-15T10:00:00"
  }' | jq '.'

echo ""
echo "2️⃣  Listar citas"
curl -X GET "$BASE_URL/api/appointments" | jq '.'

echo ""
echo "3️⃣  Obtener cita por ID"
curl -X GET "$BASE_URL/api/appointments/1" | jq '.'

echo ""
echo "4️⃣  Actualizar estado de cita"
curl -X PUT "$BASE_URL/api/appointments/1" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "COMPLETED"
  }' | jq '.'

echo ""
echo "5️⃣  Cancelar cita"
curl -X DELETE "$BASE_URL/api/appointments/1" | jq '.'

# ========================
# PAYMENTS
# ========================

echo ""
echo "💳 PAGOS (Payments)"
echo "───────────────────────────────────────────────────────────"

echo ""
echo "1️⃣  Registrar pago"
curl -X POST "$BASE_URL/api/payments" \
  -H "Content-Type: application/json" \
  -d '{
    "appointmentId": 1,
    "amount": 50000,
    "method": "CASH"
  }' | jq '.'

echo ""
echo "2️⃣  Listar pagos"
curl -X GET "$BASE_URL/api/payments" | jq '.'

echo ""
echo "3️⃣  Obtener pago por ID"
curl -X GET "$BASE_URL/api/payments/1" | jq '.'

# ========================
# ERROR SCENARIOS
# ========================

echo ""
echo "⚠️  ESCENARIOS DE ERROR (Error Scenarios)"
echo "───────────────────────────────────────────────────────────"

echo ""
echo "1️⃣  Error: Monto excede el total"
curl -X POST "$BASE_URL/api/payments" \
  -H "Content-Type: application/json" \
  -d '{
    "appointmentId": 1,
    "amount": 100000,
    "method": "CARD"
  }' | jq '.'

echo ""
echo "2️⃣  Error: Método de pago inválido"
curl -X POST "$BASE_URL/api/payments" \
  -H "Content-Type: application/json" \
  -d '{
    "appointmentId": 1,
    "amount": 50000,
    "method": "BITCOIN"
  }' | jq '.'

echo ""
echo "3️⃣  Error: Cliente no encontrado"
curl -X POST "$BASE_URL/api/motorcycles" \
  -H "Content-Type: application/json" \
  -d '{
    "client": {"id": 999},
    "plate": "XYZ-999",
    "brand": "Unknown",
    "model": "Model",
    "year": 2026
  }' | jq '.'

echo ""
echo "═══════════════════════════════════════════════════════════"
echo "✅ API Examples Completed"
echo "═══════════════════════════════════════════════════════════"
