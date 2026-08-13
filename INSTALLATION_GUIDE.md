# Guía de Instalación y Ejecución — MotoCitas

## Requisitos Previos

- **Java 17+** (JDK)
  - Verificar: `java -version`
  - Descargar: https://www.oracle.com/java/technologies/downloads/#java17

- **Maven 3.6+**
  - Verificar: `mvn -version`
  - Descargar: https://maven.apache.org/download.cgi

- **MySQL 8.0+**
  - Descargar: https://dev.mysql.com/downloads/mysql/
  - O usar Docker: `docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:8.0`

- **Git** (para clonar el repositorio)
  - Descargar: https://git-scm.com/

## Pasos de Instalación

### 1. Clonar el Repositorio (o descargar como ZIP)

```bash
git clone <your-repo-url>
cd MotoCitas
```

### 2. Crear Base de Datos MySQL

#### Opción A: Usando MySQL CLI

```bash
# Conectarse a MySQL
mysql -u root -p

# Ejecutar en MySQL prompt:
CREATE DATABASE motocitas;
USE motocitas;
SOURCE sql/schema.sql;
EXIT;
```

#### Opción B: Usando GUI (MySQL Workbench)

1. Abrir MySQL Workbench
2. Conectarse con usuario root
3. File → Open SQL Script → Seleccionar `sql/schema.sql`
4. Ejecutar (Ctrl+Shift+Enter)

#### Opción C: Automático (Java app)

Configurar en `application.properties`:
```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

Esto creará/recreará las tablas automáticamente (no recomendado para producción).

### 3. Configurar la Conexión a la Base de Datos

Editar `src/main/resources/application.properties`:

```properties
# URL de MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/motocitas?useSSL=false&serverTimezone=UTC

# Usuario MySQL
spring.datasource.username=root

# Contraseña MySQL (cambiar según tu instalación)
spring.datasource.password=

# Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Puerto del servidor
server.port=8080

# Logging
logging.level.com.motocitas=DEBUG
```

**Ajusta `spring.datasource.password` si tu MySQL tiene contraseña.**

### 4. Compilar el Proyecto

```bash
mvn clean install
```

O si prefieres solo compilar sin tests:

```bash
mvn clean install -DskipTests
```

Esto genera el JAR en: `target/motocitas-0.0.1-SNAPSHOT.jar`

### 5. Ejecutar la Aplicación

#### Opción A: Directamente con Java

```bash
java -jar target/motocitas-0.0.1-SNAPSHOT.jar
```

#### Opción B: Desde Maven

```bash
mvn spring-boot:run
```

Verás logs similares a:
```
...
Started MotoCitasApplication in 5.234 seconds
Tomcat started on port(s): 8080 (http) with context path ''
```

### 6. Verificar que Está Funcionando

#### Opción A: Abrir en Navegador
```
http://localhost:8080
```

Deberías ver el dashboard de MotoCitas.

#### Opción B: Probar API con cURL

```bash
# Listar clientes
curl http://localhost:8080/api/clients

# Crear cliente
curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","phone":"123456"}'
```

#### Opción C: Usar Postman
1. Importar: `MotoCitas.postman_collection.json` en Postman
2. Cambiar variable `base_url` si es necesario
3. Ejecutar requests

## Solución de Problemas

### Error: "Connection refused" (Puerto 3306)

**Problema**: MySQL no está ejecutándose

**Solución**:
- Windows: `net start MySQL80` (o el nombre de tu servicio)
- macOS: `brew services start mysql`
- Linux: `sudo systemctl start mysql`

### Error: "Access denied for user 'root'"

**Problema**: Contraseña incorrecta

**Solución**:
1. Verificar contraseña de MySQL
2. Actualizar `spring.datasource.password` en `application.properties`
3. Recompilar y ejecutar de nuevo

### Error: "Database 'motocitas' not found"

**Problema**: Base de datos no existe

**Solución**:
1. Crear la base de datos:
   ```bash
   mysql -u root -p -e "CREATE DATABASE motocitas;"
   ```
2. Ejecutar el script SQL:
   ```bash
   mysql -u root -p motocitas < sql/schema.sql
   ```

### Puerto 8080 Ya en Uso

**Problema**: Otro programa está usando puerto 8080

**Solución A**: Cambiar puerto en `application.properties`
```properties
server.port=8081
```

**Solución B**: Matar el proceso en el puerto
- Windows: `netstat -ano | findstr :8080` y luego `taskkill /PID <PID> /F`
- Linux/Mac: `lsof -i :8080` y luego `kill -9 <PID>`

### Error de Compilación Maven

**Problema**: Error durante `mvn clean install`

**Solución**:
```bash
# Limpiar caché de Maven
mvn clean
rm -rf ~/.m2/repository/com/motocitas

# Intentar de nuevo
mvn clean install -DskipTests
```

### Application.properties no se carga

**Problema**: Cambios en properties no toman efecto

**Solución**:
1. Detener la aplicación
2. Limpiar: `mvn clean`
3. Recompilar: `mvn install`
4. Ejecutar de nuevo

## Estructura de Carpetas después de la Instalación

```
MotoCitas/
├── src/
│   ├── main/
│   │   ├── java/com/motocitas/
│   │   │   ├── controller/       ← Controladores REST
│   │   │   ├── service/          ← Lógica de negocio
│   │   │   ├── repository/       ← Acceso a datos
│   │   │   ├── model/            ← Entidades JPA
│   │   │   ├── dto/              ← Data Transfer Objects
│   │   │   ├── exception/        ← Manejo de excepciones
│   │   │   └── MotoCitasApplication.java
│   │   ├── resources/
│   │   │   ├── static/           ← Frontend (HTML, CSS, JS)
│   │   │   └── application.properties
│   │   └── templates/
│   └── test/
├── sql/
│   └── schema.sql                ← Script de base de datos
├── target/                       ← Compilados (después de mvn clean)
│   └── motocitas-0.0.1-SNAPSHOT.jar
├── pom.xml                       ← Dependencias Maven
├── README.md
├── DOCUMENTO_REQUISITOS.md
├── API_DOCS.md
├── INSTALLATION_GUIDE.md
└── .gitignore
```

## Próximos Pasos

1. **Explorar la aplicación**: Acceder a http://localhost:8080
2. **Crear datos de prueba**: Usar el frontend o las APIs con cURL
3. **Revisar documentación**: Leer [API_DOCS.md](API_DOCS.md)
4. **Personalizar**: Ajustar colores, campos, etc. en `src/main/resources/static/`

## Detener la Aplicación

- Si está ejecutándose: **Ctrl+C** en la terminal
- Luego el servidor estará disponible nuevamente en unos segundos

## Restaurar la Base de Datos

Si necesitas resetear los datos:

```bash
mysql -u root -p motocitas < sql/schema.sql
```

Esto elimina e importa nuevamente la estructura y datos de ejemplo.

## Monitoreo

Ver logs en tiempo real (si lo necesitas):

```bash
# Aumentar verbosidad en application.properties
logging.level.root=DEBUG
logging.level.com.motocitas=DEBUG
logging.level.org.springframework.web=DEBUG

# Ejecutar
mvn spring-boot:run
```

## Actualizar Dependencias

```bash
mvn versions:display-updates
mvn versions:use-latest-releases
mvn clean install
```

---

**Guía de Instalación v1.0**
