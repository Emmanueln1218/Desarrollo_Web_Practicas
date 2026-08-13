package dao;

import java.util.List;
import model.Pedido;

public interface PedidoDAO {
    int insert(Pedido p) throws Exception;
    List<Pedido> listAll() throws Exception;
    Pedido findById(int id) throws Exception;
}
