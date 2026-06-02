package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Patrón Configuration: Centraliza la configuración de la base de datos.
 * Lee desde db/db.properties o usa valores por defecto.
 * Soporta SQL Server Authentication y Windows Authentication.
 */
public class DatabaseConfig {
    private static DatabaseConfig instance;

    private String server = "ANONIMOUS";
    private int port = 54238;
    private String database = "MascotasDB";
    private String user = "";
    private String password = "";
    private boolean encrypt = true;
    private boolean trustServerCertificate = true;
    private boolean integratedSecurity = true;
    private int poolSize = 10;
    private int connectionTimeout = 5000;

    private DatabaseConfig() {
        loadFromProperties();
    }

    private void loadFromProperties() {
        Path p = findPropertiesPath();
        if (Files.exists(p)) {
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream(p.toFile())) {
                props.load(fis);
                server = props.getProperty("server", server);
                port = parsePort(props.getProperty("port"), port);
                database = props.getProperty("database", database);
                user = props.getProperty("user", user);
                password = props.getProperty("password", password);
                encrypt = Boolean.parseBoolean(props.getProperty("encrypt", String.valueOf(encrypt)));
                trustServerCertificate = Boolean.parseBoolean(props.getProperty("trustServerCertificate", String.valueOf(trustServerCertificate)));
                integratedSecurity = Boolean.parseBoolean(props.getProperty("integratedSecurity", String.valueOf(integratedSecurity)));
                poolSize = Integer.parseInt(props.getProperty("poolSize", String.valueOf(poolSize)));
                connectionTimeout = Integer.parseInt(props.getProperty("connectionTimeout", String.valueOf(connectionTimeout)));
                System.out.println("[DatabaseConfig] Loaded from " + p.toAbsolutePath());
                System.out.println("  - Server: " + server);
                System.out.println("  - Port: " + port);
                System.out.println("  - Database: " + database);
                System.out.println("  - Auth: " + (integratedSecurity ? "Windows" : "SQL Server"));
            } catch (IOException e) {
                System.err.println("Warning: no se pudo leer db/db.properties: " + e.getMessage());
            }
        } else {
            System.err.println("Warning: db/db.properties no encontrado, usando valores por defecto");
        }
    }

    private Path findPropertiesPath() {
        Path[] candidates = {
            Path.of("db", "db.properties"),
            Path.of("Ejercicios3", "db", "db.properties")
        };

        for (Path candidate : candidates) {
            if (Files.exists(candidate)) {
                return candidate;
            }
        }

        return candidates[0];
    }

    private int parsePort(String value, int defaultPort) {
        if (value == null || value.trim().isEmpty()) return defaultPort;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            System.err.println("Warning: puerto invalido en db.properties, usando " + defaultPort);
            return defaultPort;
        }
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) instance = new DatabaseConfig();
        return instance;
    }

    public String buildUrl() {
        String url = "jdbc:sqlserver://" + server;

        if (port > 0) {
            url += ":" + port;
        }

        url += String.format(";databaseName=%s;encrypt=%s;trustServerCertificate=%s",
            database, encrypt, trustServerCertificate);
        
        if (integratedSecurity) {
            url += ";integratedSecurity=true";
        }
        
        return url;
    }

    public String getServer() { return server; }
    public int getPort() { return port; }
    public String getDatabase() { return database; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
    public boolean isEncrypt() { return encrypt; }
    public boolean isTrustServerCertificate() { return trustServerCertificate; }
    public boolean isIntegratedSecurity() { return integratedSecurity; }
    public int getPoolSize() { return poolSize; }
    public int getConnectionTimeout() { return connectionTimeout; }

    public void setServer(String server) { this.server = server; }
    public void setPort(int port) { this.port = port; }
    public void setDatabase(String database) { this.database = database; }
    public void setUser(String user) { this.user = user; }
    public void setPassword(String password) { this.password = password; }
    public void setEncrypt(boolean encrypt) { this.encrypt = encrypt; }
    public void setTrustServerCertificate(boolean trustServerCertificate) { this.trustServerCertificate = trustServerCertificate; }
    public void setIntegratedSecurity(boolean integratedSecurity) { this.integratedSecurity = integratedSecurity; }
    public void setPoolSize(int poolSize) { this.poolSize = poolSize; }
    public void setConnectionTimeout(int connectionTimeout) { this.connectionTimeout = connectionTimeout; }
}
