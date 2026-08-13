package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Producto;

public class SqlServerProductoDAO implements ProductoDAO {
    @Override
    public int insert(Producto p) throws Exception {
        String sql = "INSERT INTO Productos (Nombre, Descripcion, Precio, Stock) VALUES (?,?,?,?)";
        try (Connection con = ConnectionManager.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre()); ps.setString(2, p.getDescripcion()); ps.setDouble(3, p.getPrecio()); ps.setInt(4, p.getStock());
            ps.executeUpdate(); try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) { int id = keys.getInt(1); p.setId(id); return id; } }
        }
        return -1;
    }

    @Override
    public List<Producto> listAll() throws Exception {
        String sql = "SELECT * FROM Productos";
        List<Producto> out = new ArrayList<>();
        try (Connection con = ConnectionManager.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { Producto p = new Producto(); p.setId(rs.getInt("Id")); p.setNombre(rs.getString("Nombre")); p.setDescripcion(rs.getString("Descripcion")); p.setPrecio(rs.getDouble("Precio")); p.setStock(rs.getInt("Stock")); out.add(p); }
        }
        return out;
    }

    @Override
    public Producto findById(int id) throws Exception {
        String sql = "SELECT * FROM Productos WHERE Id = ?";
        try (Connection con = ConnectionManager.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); try (ResultSet rs = ps.executeQuery()) { if (rs.next()) { Producto p = new Producto(); p.setId(rs.getInt("Id")); p.setNombre(rs.getString("Nombre")); p.setDescripcion(rs.getString("Descripcion")); p.setPrecio(rs.getDouble("Precio")); p.setStock(rs.getInt("Stock")); return p; } }
        }
        return null;
    }

    @Override
    public boolean update(Producto p) throws Exception {
        String sql = "UPDATE Productos SET Nombre=?, Descripcion=?, Precio=?, Stock=? WHERE Id = ?";
        try (Connection con = ConnectionManager.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre()); ps.setString(2, p.getDescripcion()); ps.setDouble(3, p.getPrecio()); ps.setInt(4, p.getStock()); ps.setInt(5, p.getId()); return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM Productos WHERE Id = ?";
        try (Connection con = ConnectionManager.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { ps.setInt(1, id); return ps.executeUpdate() > 0; }
    }
}
