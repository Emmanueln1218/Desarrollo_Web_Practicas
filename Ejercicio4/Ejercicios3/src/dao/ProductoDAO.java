package dao;

import java.util.List;
import model.Producto;

public interface ProductoDAO {
    int insert(Producto p) throws Exception;
    List<Producto> listAll() throws Exception;
    Producto findById(int id) throws Exception;
    boolean update(Producto p) throws Exception;
    boolean delete(int id) throws Exception;
}
