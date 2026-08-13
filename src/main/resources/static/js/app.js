const API_BASE = 'http://localhost:8080/api';

// Utilidades
function showAlert(message, type = 'success') {
    const alert = document.createElement('div');
    alert.className = `alert alert-${type}`;
    alert.textContent = message;
    document.body.insertBefore(alert, document.body.firstChild);
    setTimeout(() => alert.remove(), 3000);
}

function showSection(sectionId) {
    document.querySelectorAll('.content-section').forEach(s => s.classList.remove('active'));
    document.getElementById(sectionId).classList.add('active');
    document.querySelectorAll('.nav-btn').forEach(b => b.classList.remove('active'));
    event.target.classList.add('active');
}

// === CLIENTS ===
async function loadClients() {
    try {
        const res = await fetch(`${API_BASE}/clients`);
        const clients = await res.json();
        const list = document.getElementById('clientsList');
        list.innerHTML = '';
        clients.forEach(c => {
            const li = document.createElement('li');
            li.innerHTML = `
                <div>
                    <strong>${c.name}</strong><br/>
                    <small>${c.phone || ''} | ${c.email || ''}</small>
                </div>
                <div class="item-actions">
                    <button class="btn-sm btn-delete" onclick="deleteClient(${c.id})">Eliminar</button>
                </div>
            `;
            list.appendChild(li);
        });
        updateSelectOptions('clients', clients, 'clientId');
    } catch (e) {
        showAlert('Error cargando clientes', 'error');
    }
}

async function deleteClient(id) {
    if (confirm('¿Eliminar cliente?')) {
        try {
            await fetch(`${API_BASE}/clients/${id}`, {method: 'DELETE'});
            showAlert('Cliente eliminado');
            loadClients();
        } catch (e) {
            showAlert('Error eliminando cliente', 'error');
        }
    }
}

// === MOTORCYCLES ===
async function loadMotorcycles() {
    try {
        const res = await fetch(`${API_BASE}/motorcycles`);
        const motorcycles = await res.json();
        const list = document.getElementById('motorcyclesList');
        list.innerHTML = '';
        motorcycles.forEach(m => {
            const li = document.createElement('li');
            li.innerHTML = `
                <div>
                    <strong>${m.plate}</strong> - ${m.brand} ${m.model} (${m.year})<br/>
                    <small>Cliente ID: ${m.client?.id}</small>
                </div>
                <div class="item-actions">
                    <button class="btn-sm btn-delete" onclick="deleteMotorcycle(${m.id})">Eliminar</button>
                </div>
            `;
            list.appendChild(li);
        });
    } catch (e) {
        showAlert('Error cargando motocicletas', 'error');
    }
}

async function deleteMotorcycle(id) {
    if (confirm('¿Eliminar motocicleta?')) {
        try {
            await fetch(`${API_BASE}/motorcycles/${id}`, {method: 'DELETE'});
            showAlert('Motocicleta eliminada');
            loadMotorcycles();
        } catch (e) {
            showAlert('Error eliminando motocicleta', 'error');
        }
    }
}

// === SERVICES ===
async function loadServices() {
    try {
        const res = await fetch(`${API_BASE}/services`);
        const services = await res.json();
        const list = document.getElementById('servicesList');
        list.innerHTML = '';
        services.forEach(s => {
            const li = document.createElement('li');
            li.innerHTML = `
                <div>
                    <strong>${s.name}</strong><br/>
                    <small>Precio: $${s.price.toFixed(2)}</small>
                </div>
                <div class="item-actions">
                    <button class="btn-sm btn-delete" onclick="deleteService(${s.id})">Eliminar</button>
                </div>
            `;
            list.appendChild(li);
        });
        updateSelectOptions('services', services, 'serviceId');
    } catch (e) {
        showAlert('Error cargando servicios', 'error');
    }
}

async function deleteService(id) {
    if (confirm('¿Eliminar servicio?')) {
        try {
            await fetch(`${API_BASE}/services/${id}`, {method: 'DELETE'});
            showAlert('Servicio eliminado');
            loadServices();
        } catch (e) {
            showAlert('Error eliminando servicio', 'error');
        }
    }
}

// === APPOINTMENTS ===
async function loadAppointments() {
    try {
        const res = await fetch(`${API_BASE}/appointments`);
        const appointments = await res.json();
        const list = document.getElementById('appointmentsList');
        list.innerHTML = '';
        appointments.forEach(a => {
            const li = document.createElement('li');
            li.innerHTML = `
                <div>
                    <strong>Cita #${a.id}</strong><br/>
                    <small>${new Date(a.scheduledAt).toLocaleString()}</small><br/>
                    <small>Estado: <span style="color: ${a.status === 'PENDING' ? '#ff9800' : '#4CAF50'}">${a.status}</span> | Total: $${a.total.toFixed(2)}</small>
                </div>
                <div class="item-actions">
                    <button class="btn-sm btn-delete" onclick="deleteAppointment(${a.id})">Cancelar</button>
                </div>
            `;
            list.appendChild(li);
        });
    } catch (e) {
        showAlert('Error cargando citas', 'error');
    }
}

async function deleteAppointment(id) {
    if (confirm('¿Cancelar cita?')) {
        try {
            await fetch(`${API_BASE}/appointments/${id}`, {method: 'DELETE'});
            showAlert('Cita cancelada');
            loadAppointments();
        } catch (e) {
            showAlert('Error cancelando cita', 'error');
        }
    }
}

// === PAYMENTS ===
async function loadPayments() {
    try {
        const res = await fetch(`${API_BASE}/payments`);
        const payments = await res.json();
        const list = document.getElementById('paymentsList');
        list.innerHTML = '';
        payments.forEach(p => {
            const li = document.createElement('li');
            li.innerHTML = `
                <div>
                    <strong>Pago #${p.id}</strong><br/>
                    <small>Cita #${p.appointment?.id} | Monto: $${p.amount.toFixed(2)} | Método: ${p.method}</small><br/>
                    <small>Fecha: ${new Date(p.paidAt).toLocaleString()}</small>
                </div>
            `;
            list.appendChild(li);
        });
    } catch (e) {
        showAlert('Error cargando pagos', 'error');
    }
}

// === UTILIDADES ===
function updateSelectOptions(type, items, idAttr) {
    if (type === 'clients') {
        updateSelectById('motorcycleClient', items);
        updateSelectById('appointmentClient', items);
    } else if (type === 'services') {
        updateSelectById('appointmentService', items);
    }
}

function updateSelectById(elementId, items) {
    const select = document.getElementById(elementId);
    if (select) {
        const currentValue = select.value;
        select.innerHTML = '<option value="">Seleccionar...</option>';
        items.forEach(item => {
            const opt = document.createElement('option');
            opt.value = item.id;
            opt.textContent = item.name || item.plate || `Cliente ${item.id}`;
            select.appendChild(opt);
        });
        select.value = currentValue;
    }
}

// === EVENT LISTENERS ===
document.getElementById('clientForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = {
        name: document.getElementById('clientName').value,
        phone: document.getElementById('clientPhone').value,
        email: document.getElementById('clientEmail').value
    };
    try {
        const res = await fetch(`${API_BASE}/clients`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert('Cliente creado');
            e.target.reset();
            loadClients();
        } else {
            showAlert((await res.json()).message, 'error');
        }
    } catch (e) {
        showAlert('Error creando cliente', 'error');
    }
});

document.getElementById('motorcycleForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = {
        client: {id: document.getElementById('motorcycleClient').value},
        plate: document.getElementById('motorcyclePlate').value,
        brand: document.getElementById('motorcycleBrand').value,
        model: document.getElementById('motorcycleModel').value,
        year: document.getElementById('motorcycleYear').value
    };
    try {
        const res = await fetch(`${API_BASE}/motorcycles`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert('Motocicleta creada');
            e.target.reset();
            loadMotorcycles();
        } else {
            showAlert((await res.json()).message, 'error');
        }
    } catch (e) {
        showAlert('Error creando motocicleta', 'error');
    }
});

document.getElementById('serviceForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = {
        name: document.getElementById('serviceName').value,
        price: parseFloat(document.getElementById('servicePrice').value)
    };
    try {
        const res = await fetch(`${API_BASE}/services`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert('Servicio creado');
            e.target.reset();
            loadServices();
        } else {
            showAlert((await res.json()).message, 'error');
        }
    } catch (e) {
        showAlert('Error creando servicio', 'error');
    }
});

document.getElementById('appointmentForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = {
        clientId: parseInt(document.getElementById('appointmentClient').value),
        motorcycleId: parseInt(document.getElementById('appointmentMotorcycle').value),
        serviceId: parseInt(document.getElementById('appointmentService').value),
        scheduledAt: document.getElementById('appointmentDate').value + ':00'
    };
    try {
        const res = await fetch(`${API_BASE}/appointments`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert('Cita agendada');
            e.target.reset();
            loadAppointments();
        } else {
            const error = await res.json();
            showAlert(error.message || 'Error creando cita', 'error');
        }
    } catch (e) {
        showAlert('Error creando cita', 'error');
    }
});

document.getElementById('paymentForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = {
        appointmentId: parseInt(document.getElementById('paymentAppointment').value),
        amount: parseFloat(document.getElementById('paymentAmount').value),
        method: document.getElementById('paymentMethod').value
    };
    try {
        const res = await fetch(`${API_BASE}/payments`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        if (res.ok) {
            showAlert('Pago registrado');
            e.target.reset();
            loadPayments();
        } else {
            const error = await res.json();
            showAlert(error.message || 'Error registrando pago', 'error');
        }
    } catch (e) {
        showAlert('Error registrando pago', 'error');
    }
});

// === INICIAR ===
document.addEventListener('DOMContentLoaded', () => {
    loadClients();
    loadMotorcycles();
    loadServices();
    loadAppointments();
    loadPayments();
    
    // Recargar datos cada 30 segundos
    setInterval(() => {
        loadClients();
        loadMotorcycles();
        loadServices();
        loadAppointments();
        loadPayments();
    }, 30000);
});
