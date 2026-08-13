-- Complete SQL Server schema for the store + mascotas
-- Run in SQL Server Management Studio (SSMS). Adjust file encoding to UTF-8 if needed.

IF DB_ID('MascotasDB') IS NOT NULL
BEGIN
    ALTER DATABASE MascotasDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE MascotasDB;
END
GO

CREATE DATABASE MascotasDB;
GO
USE MascotasDB;
GO

-- Clientes
CREATE TABLE Clientes (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Nombre NVARCHAR(100) NOT NULL,
    Email NVARCHAR(100) NULL,
    Telefono NVARCHAR(20) NULL,
    Direccion NVARCHAR(200) NULL,
    FechaRegistro DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
);
GO

CREATE INDEX IX_Clientes_Email ON Clientes(Email);
GO

-- Productos
CREATE TABLE Productos (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Nombre NVARCHAR(100) NOT NULL,
    Descripcion NVARCHAR(500) NULL,
    Precio DECIMAL(12,2) NOT NULL CHECK (Precio >= 0),
    Stock INT NOT NULL DEFAULT 0 CHECK (Stock >= 0),
    FechaAlta DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
);
GO

CREATE INDEX IX_Productos_Nombre ON Productos(Nombre);
GO

-- Pedidos (cabecera)
CREATE TABLE Pedidos (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    ClienteId INT NOT NULL REFERENCES Clientes(Id) ON DELETE CASCADE,
    FechaPedido DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    Total DECIMAL(14,2) NOT NULL DEFAULT 0 CHECK (Total >= 0),
    Estado NVARCHAR(30) NOT NULL DEFAULT 'Pendiente',
    DireccionEnvio NVARCHAR(200) NULL
);
GO

CREATE INDEX IX_Pedidos_Cliente ON Pedidos(ClienteId);
GO

-- Detalle de pedido
CREATE TABLE PedidoItems (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    PedidoId INT NOT NULL REFERENCES Pedidos(Id) ON DELETE CASCADE,
    ProductoId INT NOT NULL REFERENCES Productos(Id),
    Cantidad INT NOT NULL CHECK (Cantidad > 0),
    PrecioUnitario DECIMAL(12,2) NOT NULL CHECK (PrecioUnitario >= 0),
    Subtotal AS (Cantidad * PrecioUnitario) PERSISTED
);
GO

CREATE INDEX IX_PedidoItems_Pedido ON PedidoItems(PedidoId);
CREATE INDEX IX_PedidoItems_Producto ON PedidoItems(ProductoId);
GO

-- Tabla de mascotas (mantengo la original adaptada)
CREATE TABLE Mascotas (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Nombre NVARCHAR(30) NOT NULL,
    Raza NVARCHAR(20) NULL,
    TipoAnimal NVARCHAR(20) NULL,
    FechaNacimiento NVARCHAR(10) NULL,
    Peso FLOAT NULL,
    LugarEntrenamiento NVARCHAR(30) NULL,
    AlturaSalto FLOAT NULL
);
GO

-- Ejemplos de datos (opcionales)
INSERT INTO Clientes (Nombre, Email, Telefono, Direccion)
VALUES
('Juan Perez', 'juan.perez@example.com', '+573001234567', 'Calle Falsa 123'),
('Maria Gomez', 'maria.gomez@example.com', '+573009876543', 'Av. Siempre Viva 742');

INSERT INTO Productos (Nombre, Descripcion, Precio, Stock)
VALUES
('Croquetas Premium', 'Alimento balanceado para perros', 45.50, 100),
('Arena para gatos', 'Arena aglomerante 10kg', 20.00, 50);

-- Insert sample pedido
INSERT INTO Pedidos (ClienteId, Total, Estado, DireccionEnvio)
VALUES (1, 0, 'Pendiente', 'Calle Falsa 123');

INSERT INTO PedidoItems (PedidoId, ProductoId, Cantidad, PrecioUnitario)
VALUES (1, 1, 2, 45.50), (1, 2, 1, 20.00);

-- Actualizar total del pedido con la suma de subtotales
UPDATE P
SET Total = T.SumTotal
FROM Pedidos P
JOIN (
    SELECT PedidoId, SUM(Subtotal) AS SumTotal
    FROM PedidoItems
    GROUP BY PedidoId
) T ON P.Id = T.PedidoId;
GO

-- Fin del script
