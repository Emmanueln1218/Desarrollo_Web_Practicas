package dao;

/**
 * Patrón Factory: Centraliza la creación de instancias DAO.
 * Proporciona un único punto de acceso para obtener DAOs.
 * Facilita cambios de implementación y testing.
 */
public class RepositoryFactory {
    private static RepositoryFactory instance;
    
    private final ClienteDAO clienteDao;
    private final ProductoDAO productoDao;
    private final MamiferoDAO mamiferoDao;
    private final PedidoDAO pedidoDao;

    private RepositoryFactory() {
        // Inicializar con las implementaciones SQL Server
        this.clienteDao = new SqlServerClienteDAO();
        this.productoDao = new SqlServerProductoDAO();
        this.mamiferoDao = new SqlServerMamiferoDAO();
        this.pedidoDao = new SqlServerPedidoDAO();
        System.out.println("[RepositoryFactory] DAOs inicializados con SQL Server");
    }

    public static RepositoryFactory getInstance() {
        if (instance == null) {
            synchronized (RepositoryFactory.class) {
                if (instance == null) {
                    instance = new RepositoryFactory();
                }
            }
        }
        return instance;
    }

    public ClienteDAO getClienteDAO() { return clienteDao; }
    public ProductoDAO getProductoDAO() { return productoDao; }
    public MamiferoDAO getMamiferoDAO() { return mamiferoDao; }
    public PedidoDAO getPedidoDAO() { return pedidoDao; }
}
