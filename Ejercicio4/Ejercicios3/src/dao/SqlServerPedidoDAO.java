package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Pedido;
import model.PedidoItem;

public class SqlServerPedidoDAO implements PedidoDAO {
    @Override
    public int insert(Pedido p) throws Exception {
        String insertPedido = "INSERT INTO Pedidos (ClienteId, FechaPedido, Total) VALUES (?,?,?)";
        String insertItem = "INSERT INTO PedidoItems (PedidoId, ProductoId, Cantidad, PrecioUnitario) VALUES (?,?,?,?)";
        String updateStock = "UPDATE Productos SET Stock = Stock - ? WHERE Id = ? AND Stock >= ?";
        try (Connection con = ConnectionManager.getInstance().getConnection()) {
            try {
                con.setAutoCommit(false);
                try (PreparedStatement ps = con.prepareStatement(insertPedido, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, p.getClienteId());
                    ps.setTimestamp(2, p.getFechaCreacion() != null ? p.getFechaCreacion() : new Timestamp(System.currentTimeMillis()));
                    ps.setDouble(3, p.getTotal());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) {
                            int pedidoId = keys.getInt(1);
                            p.setId(pedidoId);
                            // insert items
                            try (PreparedStatement psItem = con.prepareStatement(insertItem, Statement.RETURN_GENERATED_KEYS);
                                 PreparedStatement psUpd = con.prepareStatement(updateStock)) {
                                for (PedidoItem it : p.getItems()) {
                                    psItem.setInt(1, pedidoId);
                                    psItem.setInt(2, it.getProductoId());
                                    psItem.setInt(3, it.getCantidad());
                                    psItem.setDouble(4, it.getPrecioUnitario());
                                    psItem.executeUpdate();

                                    // update stock, ensure enough stock
                                    psUpd.setInt(1, it.getCantidad());
                                    psUpd.setInt(2, it.getProductoId());
                                    psUpd.setInt(3, it.getCantidad());
                                    int updated = psUpd.executeUpdate();
                                    if (updated == 0) {
                                        throw new SQLException("No hay stock suficiente para el producto id=" + it.getProductoId());
                                    }
                                }
                            }
                            con.commit();
                            return pedidoId;
                        } else {
                            throw new SQLException("No se pudo obtener Id del pedido");
                        }
                    }
                }
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    @Override
    public List<Pedido> listAll() throws Exception {
        String sql = "SELECT * FROM Pedidos ORDER BY FechaPedido DESC";
        List<Pedido> out = new ArrayList<>();
        try (Connection con = ConnectionManager.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("Id"));
                p.setClienteId(rs.getInt("ClienteId"));
                p.setTotal(rs.getDouble("Total"));
                p.setFechaCreacion(rs.getTimestamp("FechaPedido"));
                // load items
                List<PedidoItem> items = new ArrayList<>();
                try (PreparedStatement psi = con.prepareStatement("SELECT * FROM PedidoItems WHERE PedidoId = ?")) {
                    psi.setInt(1, p.getId());
                    try (ResultSet rsi = psi.executeQuery()) {
                        while (rsi.next()) {
                            PedidoItem it = new PedidoItem();
                            it.setId(rsi.getInt("Id"));
                            it.setPedidoId(rsi.getInt("PedidoId"));
                            it.setProductoId(rsi.getInt("ProductoId"));
                            it.setCantidad(rsi.getInt("Cantidad"));
                            it.setPrecioUnitario(rsi.getDouble("PrecioUnitario"));
                            items.add(it);
                        }
                    }
                }
                p.setItems(items);
                out.add(p);
            }
        }
        return out;
    }

    @Override
    public Pedido findById(int id) throws Exception {
        String sql = "SELECT * FROM Pedidos WHERE Id = ?";
        try (Connection con = ConnectionManager.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pedido p = new Pedido();
                    p.setId(rs.getInt("Id"));
                    p.setClienteId(rs.getInt("ClienteId"));
                    p.setTotal(rs.getDouble("Total"));
                    p.setFechaCreacion(rs.getTimestamp("FechaPedido"));
                    List<PedidoItem> items = new ArrayList<>();
                    try (PreparedStatement psi = con.prepareStatement("SELECT * FROM PedidoItems WHERE PedidoId = ?")) {
                        psi.setInt(1, p.getId());
                        try (ResultSet rsi = psi.executeQuery()) {
                            while (rsi.next()) {
                                PedidoItem it = new PedidoItem();
                                it.setId(rsi.getInt("Id"));
                                it.setPedidoId(rsi.getInt("PedidoId"));
                                it.setProductoId(rsi.getInt("ProductoId"));
                                it.setCantidad(rsi.getInt("Cantidad"));
                                it.setPrecioUnitario(rsi.getDouble("PrecioUnitario"));
                                items.add(it);
                            }
                        }
                    }
                    p.setItems(items);
                    return p;
                }
            }
        }
        return null;
    }
}
