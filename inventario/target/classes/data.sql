-- ============================================
-- Usuario administrador por defecto
-- Usuario: admin
-- Contraseña: admin
-- ============================================

INSERT IGNORE INTO usuarios (username, password, nombre_completo, email, rol, activo)
VALUES (
    'admin',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'Administrador del Sistema',
    'admin@inventario.com',
    'ROLE_ADMIN',
    true
);

-- ============================================
-- Categorías de ejemplo (opcional)
-- ============================================

INSERT IGNORE INTO categorias (nombre, descripcion) VALUES
('Electrónica', 'Dispositivos electrónicos y accesorios'),
('Oficina', 'Artículos de oficina y papelería'),
('Limpieza', 'Productos de aseo y limpieza'),
('Herramientas', 'Herramientas manuales y eléctricas');

-- ============================================
-- Productos de ejemplo (opcional)
-- ============================================

INSERT IGNORE INTO productos (nombre, descripcion, sku, precio, cantidad, stock_minimo, categoria_id, fecha_creacion)
VALUES
('Mouse Inalámbrico', 'Mouse óptico inalámbrico 2.4GHz', 'ELEC-001', 15.99, 50, 10, 1, NOW()),
('Teclado Mecánico', 'Teclado mecánico RGB', 'ELEC-002', 79.99, 25, 5, 1, NOW()),
('Resmas de Papel', 'Papel bond A4 500 hojas', 'OFIC-001', 5.50, 100, 20, 2, NOW()),
('Escoba', 'Escoba de plástico resistente', 'LIMP-001', 3.99, 3, 5, 3, NOW());