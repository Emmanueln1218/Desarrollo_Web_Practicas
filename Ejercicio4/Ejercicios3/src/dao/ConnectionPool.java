package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza la creacion de conexiones SQL Server.
 * Los DAO cierran cada conexion con try-with-resources.
 */
public class ConnectionPool {
    private static ConnectionPool instance;
    private final DatabaseConfig config;
    private SQLException initializationException;

    private ConnectionPool() {
        this.config = DatabaseConfig.getInstance();
        testConnection();
    }

    private void loadDriver() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver JDBC de SQL Server. Verifica que lib/mssql-jdbc-13.4.0.jre11.jar este agregado al classpath de IntelliJ.", e);
        }
    }

    private void testConnection() {
        try (Connection ignored = createConnection()) {
            System.out.println("[ConnectionPool] Conexion de prueba inicializada correctamente");
        } catch (SQLException e) {
            initializationException = e;
            System.err.println("Error al inicializar conexion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Connection createConnection() throws SQLException {
        loadDriver();

        if (config.isIntegratedSecurity()) {
            return DriverManager.getConnection(config.buildUrl());
        }

        return DriverManager.getConnection(
            config.buildUrl(),
            config.getUser(),
            config.getPassword()
        );
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (initializationException != null) {
            throw new SQLException("No se pudo conectar a SQL Server. Causa: " + initializationException.getMessage(), initializationException);
        }

        return createConnection();
    }

    public void releaseConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
    }

    public void closeAll() {
        System.out.println("[ConnectionPool] No hay conexiones persistentes para cerrar");
    }

    public int getAvailableCount() { return 0; }
    public int getUsedCount() { return 0; }
}
