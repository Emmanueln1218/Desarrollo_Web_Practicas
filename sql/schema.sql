-- MotoCitas Database Schema

DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS motorcycles;
DROP TABLE IF EXISTS clients;

-- Clientes
CREATE TABLE clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),
    document VARCHAR(20),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Motocicletas
CREATE TABLE motorcycles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT NOT NULL,
    plate VARCHAR(50) UNIQUE NOT NULL,
    brand VARCHAR(100),
    model VARCHAR(100),
    year INT,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id)
);

-- Servicios
CREATE TABLE services (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) DEFAULT 0.00,
    active BOOLEAN DEFAULT TRUE
);

-- Citas
CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT NOT NULL,
    motorcycle_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    scheduled_at DATETIME NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    total DECIMAL(10,2) DEFAULT 0.00,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (motorcycle_id) REFERENCES motorcycles(id),
    FOREIGN KEY (service_id) REFERENCES services(id)
);

-- Pagos
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    method VARCHAR(50),
    paid_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);

-- Índices
CREATE INDEX idx_motorcycles_client_id ON motorcycles(client_id);
CREATE INDEX idx_motorcycles_plate ON motorcycles(plate);
CREATE INDEX idx_appointments_client_id ON appointments(client_id);
CREATE INDEX idx_appointments_motorcycle_id ON appointments(motorcycle_id);
CREATE INDEX idx_appointments_scheduled_at ON appointments(scheduled_at);
CREATE INDEX idx_payments_appointment_id ON payments(appointment_id);

-- Datos de ejemplo
INSERT INTO clients (name, email, phone, document) VALUES 
('Juan Pérez', 'juan@example.com', '3001234567', '12345678'),
('María García', 'maria@example.com', '3009876543', '87654321');

INSERT INTO motorcycles (client_id, plate, brand, model, year) VALUES 
(1, 'ABC-123', 'Honda', 'CB500', 2022),
(1, 'ABC-124', 'Yamaha', 'MT-07', 2020),
(2, 'XYZ-789', 'Kawasaki', 'Ninja', 2021);

INSERT INTO services (name, price) VALUES 
('Cambio de aceite', 50000),
('Revisión de frenos', 75000),
('Alineación de ruedas', 100000),
('Lavado y detallado', 45000),
('Reparación motor', 250000);
