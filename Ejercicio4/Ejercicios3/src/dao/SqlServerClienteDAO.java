package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;

public class SqlServerClienteDAO implements ClienteDAO {
    @Override
    public int insert(Cliente c) throws Exception {
        String sql = "INSERT INTO Clientes (Nombre, Email, Telefono, Direccion) VALUES (?,?,?,?)";
        try (Connection con = ConnectionManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNombre()); ps.setString(2, c.getEmail()); ps.setString(3, c.getTelefono()); ps.setString(4, c.getDireccion());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) { int id = keys.getInt(1); c.setId(id); return id; } }
        }
        return -1;
    }

    @Override
    public List<Cliente> listAll() throws Exception {
        String sql = "SELECT * FROM Clientes";
        List<Cliente> out = new ArrayList<>();
        try (Connection con = ConnectionManager.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente(); c.setId(rs.getInt("Id")); c.setNombre(rs.getString("Nombre")); c.setEmail(rs.getString("Email")); c.setTelefono(rs.getString("Telefono")); c.setDireccion(rs.getString("Direccion"));
                out.add(c);
            }
        }
        return out;
    }

    @Override
    public Cliente findById(int id) throws Exception {
        String sql = "SELECT * FROM Clientes WHERE Id = ?";
        try (Connection con = ConnectionManager.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) { Cliente c = new Cliente(); c.setId(rs.getInt("Id")); c.setNombre(rs.getString("Nombre")); c.setEmail(rs.getString("Email")); c.setTelefono(rs.getString("Telefono")); c.setDireccion(rs.getString("Direccion")); return c; } }
        }
        return null;
    }

    @Override
    public boolean update(Cliente c) throws Exception {
        String sql = "UPDATE Clientes SET Nombre=?, Email=?, Telefono=?, Direccion=? WHERE Id = ?";
        try (Connection con = ConnectionManager.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre()); ps.setString(2, c.getEmail()); ps.setString(3, c.getTelefono()); ps.setString(4, c.getDireccion()); ps.setInt(5, c.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM Clientes WHERE Id = ?";
        try (Connection con = ConnectionManager.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        }
    }
}
