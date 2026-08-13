package dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Patrón Facade + Singleton: Interfaz única para obtener conexiones.
 * Delega al ConnectionPool (patrón Object Pool).
 * Mantiene compatibilidad con código existente.
 */
public class ConnectionManager {
    private static ConnectionManager instance;
    private final ConnectionPool pool;

    private ConnectionManager() {
        this.pool = ConnectionPool.getInstance();
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            synchronized (ConnectionManager.class) {
                if (instance == null) {
                    instance = new ConnectionManager();
                }
            }
        }
        return instance;
    }

    /**
     * Obtiene una conexión del pool.
     */
    public Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    /**
     * Retorna una conexión al pool (debe llamarse en finally o try-with-resources).
     */
    public void releaseConnection(Connection conn) {
        pool.releaseConnection(conn);
    }

    /**
     * Cierra el pool (llamar al finalizar la app).
     */
    public void closePool() {
        pool.closeAll();
    }

    public DatabaseConfig getConfig() {
        return DatabaseConfig.getInstance();
    }

    public int getAvailableConnections() { return pool.getAvailableCount(); }
    public int getUsedConnections() { return pool.getUsedCount(); }
}

