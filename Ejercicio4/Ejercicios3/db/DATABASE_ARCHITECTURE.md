# Arquitectura de Base de Datos - Patrones de Diseño

## Descripción General
La capa de acceso a datos implementa varios patrones de diseño para garantizar una conexión robusta, reutilizable y centralizada a SQL Server.

## Patrones Implementados

### 1. **Configuration Pattern** (DatabaseConfig.java)
**Propósito:** Centralizar y gestionar toda la configuración de la base de datos.

**Características:**
- Lee la configuración desde `db/db.properties`
- Usa valores por defecto si el archivo no existe
- Proporciona métodos getter/setter para todos los parámetros
- Implementa Singleton para garantizar una única instancia

**Parámetros configurables:**
- `server`: Servidor SQL Server (default: localhost)
- `port`: Puerto (default: 1433)
- `database`: Nombre de base de datos (default: MascotasDB)
- `user`: Usuario SQL (default: sa)
- `password`: Contraseña
- `encrypt`: Usar encriptación SSL (default: false)
- `poolSize`: Tamaño del pool de conexiones (default: 10)
- `connectionTimeout`: Timeout en ms para obtener conexión (default: 5000)

**Uso:**
```java
DatabaseConfig config = DatabaseConfig.getInstance();
String url = config.buildUrl();
```

---

### 2. **Object Pool Pattern** (ConnectionPool.java)
**Propósito:** Reutilizar conexiones en lugar de crear nuevas cada vez (mejor rendimiento).

**Características:**
- Mantiene un conjunto de conexiones pre-creadas
- Proporciona conexiones disponibles cuando se solicitan
- Retorna conexiones al pool cuando se liberan
- Verifica que las conexiones sigan abiertas antes de reutilizar
- Thread-safe usando synchronized

**Ventajas:**
- Mayor rendimiento (no crea conexiones constantemente)
- Mejor uso de recursos
- Control de número máximo de conexiones simultáneas

**Ciclo de vida:**
1. Pool inicializado con N conexiones (poolSize)
2. `getConnection()`: obtiene del pool o espera si está vacío
3. `releaseConnection()`: devuelve la conexión al pool
4. `closeAll()`: cierra todas las conexiones

**Uso:**
```java
ConnectionPool pool = ConnectionPool.getInstance();
Connection conn = pool.getConnection();
// usar conn...
pool.releaseConnection(conn);
```

---

### 3. **Facade + Singleton Pattern** (ConnectionManager.java)
**Propósito:** Proporcionar una interfaz única y simplificada para acceder a conexiones.

**Características:**
- Delega al ConnectionPool (patrón Facade)
- Mantiene compatibilidad con código existente
- Singleton para garantizar instancia única
- Acceso a la configuración centralizada

**Uso:**
```java
Connection conn = ConnectionManager.getInstance().getConnection();
// usar conn...
```

**Métodos disponibles:**
- `getConnection()`: obtiene conexión del pool
- `releaseConnection(conn)`: retorna conexión al pool
- `closePool()`: cierra todas las conexiones
- `getConfig()`: accede a DatabaseConfig
- `getAvailableConnections()`: número de conexiones disponibles
- `getUsedConnections()`: número de conexiones en uso

---

### 4. **Factory Pattern** (RepositoryFactory.java)
**Propósito:** Centralizar la creación de instancias DAO.

**Características:**
- Proporciona punto único de acceso a todos los DAOs
- Facilita cambios de implementación
- Facilita testing (mock objects)
- Aplicación del principio Dependency Inversion

**DAOs disponibles:**
- `ClienteDAO`: Operaciones con Clientes
- `ProductoDAO`: Operaciones con Productos
- `MamiferoDAO`: Operaciones con Mascotas (Perros/Gatos)
- `PedidoDAO`: Operaciones con Pedidos

**Uso:**
```java
RepositoryFactory factory = RepositoryFactory.getInstance();
ClienteDAO clienteDao = factory.getClienteDAO();
ProductoDAO productoDao = factory.getProductoDAO();
```

---

### 5. **Data Access Object (DAO) Pattern**
Cada DAO implementa operaciones CRUD:
- `insert(entity)`: Crear
- `listAll()`: Leer todos
- `findById(id)`: Buscar uno
- `update(entity)`: Actualizar
- `delete(id)`: Eliminar

---

### 6. **Transaction Pattern** (SqlServerPedidoDAO.java)
**Propósito:** Garantizar consistencia en operaciones complejas.

**Características (en creación de Pedidos):**
- `setAutoCommit(false)`: Inicia transacción
- Inserta pedido y obtiene ID generado
- Inserta todos los items del pedido
- Actualiza stock de productos
- `commit()`: Si todo es exitoso
- `rollback()`: Si hay error
- `setAutoCommit(true)`: Restaura estado original

**Garantía:** O todos los cambios se persisten, o ninguno (ACID).

---

## Flujo de Inicialización

```
Aplicación inicia
    ↓
AppGui crea RepositoryFactory
    ↓
RepositoryFactory crea ConnectionManager
    ↓
ConnectionManager crea ConnectionPool
    ↓
ConnectionPool crea DatabaseConfig
    ↓
DatabaseConfig lee db/db.properties
    ↓
ConnectionPool inicializa N conexiones
    ↓
Listo para usar: DAOs acceden a pool bajo demanda
```

---

## Configuración Requerida

### Archivo: `db/db.properties`
```properties
server=localhost
port=1433
database=MascotasDB
user=sa
password=your_password
encrypt=false
poolSize=10
connectionTimeout=5000
```

### Archivo: `db/schema.sql`
Contiene la definición de tablas y datos de ejemplo.

---

## Ventajas de esta Arquitectura

✅ **Centralizado:** Un solo lugar para configuración y conexiones  
✅ **Reutilizable:** Pool de conexiones mejora rendimiento  
✅ **Flexible:** Fácil cambiar implementación de DAOs  
✅ **Seguro:** Thread-safe y manejo de transacciones  
✅ **Mantenible:** Patrones conocidos y documentados  
✅ **Escalable:** Pool permite controlar recursos  

---

## Ejemplo: Crear un Pedido

```java
// Obtener DAOs desde factory
RepositoryFactory factory = RepositoryFactory.getInstance();
PedidoDAO pedidoDao = factory.getPedidoDAO();

// Crear pedido con items
Pedido pedido = new Pedido(clienteId);
pedido.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
pedido.addItem(new PedidoItem(productoId, cantidad, precio));

// Insertar (transacción automática)
int pedidoId = pedidoDao.insert(pedido);
// Stock se actualiza automáticamente dentro de la transacción
```

---

## Internamente en el DAO

```java
// SqlServerPedidoDAO.insert() ejecuta:
1. Obtiene conexión del pool
2. Inicia transacción (autoCommit = false)
3. INSERT INTO Pedidos...
4. Para cada item:
   - INSERT INTO PedidoItems...
   - UPDATE Productos SET Stock - ?...
5. Si todo OK: commit()
6. Si error: rollback()
7. Retorna conexión al pool
```

---

## Para Agregar Nuevo DAO

1. Crear interfaz `NuevoDAO.java` con métodos CRUD
2. Crear `SqlServerNuevoDAO.java` implementando la interfaz
3. En `RepositoryFactory.getInstance()`: instanciar el nuevo DAO
4. Agregar getter `getNuevoDAO()`
5. En `AppGui` (o donde sea necesario): obtener via factory

```java
NuevoDAO dao = factory.getNuevoDAO();
```

---

## Consideraciones de Performance

- **Pool Size:** Aumentar si hay muchas operaciones concurrentes
- **Connection Timeout:** Ajustar según latencia de red
- **useStatement.RETURN_GENERATED_KEYS:** Para obtener IDs generados
- **Prepared Statements:** Previenen SQL injection

---

**Autor:** Sistema de Tienda y Mascotas  
**Fecha:** 2026  
**Versión:** 1.0
