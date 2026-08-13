package dao;

import java.util.List;
import model.Cliente;

public interface ClienteDAO {
    int insert(Cliente c) throws Exception;
    List<Cliente> listAll() throws Exception;
    Cliente findById(int id) throws Exception;
    boolean update(Cliente c) throws Exception;
    boolean delete(int id) throws Exception;
}
