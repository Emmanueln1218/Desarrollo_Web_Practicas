package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Mamifero;
import model.gato;
import model.perro;

public class SqlServerMamiferoDAO implements MamiferoDAO {

    @Override
    public int insert(Mamifero m) throws Exception {
        String sql = "INSERT INTO Mascotas (Nombre, Raza, TipoAnimal, FechaNacimiento, Peso, LugarEntrenamiento, AlturaSalto) VALUES (?,?,?,?,?,?,?)";
        try (Connection c = ConnectionManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getRaza());
            ps.setString(3, m.getTipoAnimal());
            ps.setString(4, m.getfNacimiento());
            ps.setDouble(5, m.getPeso());
            if (m instanceof perro) ps.setString(6, ((perro) m).getLgEntrenamiento()); else ps.setNull(6, Types.NVARCHAR);
            if (m instanceof gato) ps.setDouble(7, ((gato) m).gethSalto()); else ps.setNull(7, Types.DOUBLE);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    m.setId(id);
                    return id;
                }
            }
        }
        return -1;
    }

    @Override
    public List<Mamifero> listAll() throws Exception {
        String sql = "SELECT * FROM Mascotas";
        List<Mamifero> list = new ArrayList<>();
        try (Connection c = ConnectionManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Mamifero m = mapRow(rs);
                list.add(m);
            }
        }
        return list;
    }

    @Override
    public Mamifero findById(int id) throws Exception {
        String sql = "SELECT * FROM Mascotas WHERE Id = ?";
        try (Connection c = ConnectionManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public Mamifero findByName(String name) throws Exception {
        String sql = "SELECT * FROM Mascotas WHERE Nombre = ?";
        try (Connection c = ConnectionManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public boolean update(Mamifero m) throws Exception {
        String sql = "UPDATE Mascotas SET Nombre=?, Raza=?, TipoAnimal=?, FechaNacimiento=?, Peso=?, LugarEntrenamiento=?, AlturaSalto=? WHERE Id=?";
        try (Connection c = ConnectionManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getRaza());
            ps.setString(3, m.getTipoAnimal());
            ps.setString(4, m.getfNacimiento());
            ps.setDouble(5, m.getPeso());
            if (m instanceof perro) ps.setString(6, ((perro) m).getLgEntrenamiento()); else ps.setNull(6, Types.NVARCHAR);
            if (m instanceof gato) ps.setDouble(7, ((gato) m).gethSalto()); else ps.setNull(7, Types.DOUBLE);
            ps.setInt(8, m.getId());
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM Mascotas WHERE Id = ?";
        try (Connection c = ConnectionManager.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            int r = ps.executeUpdate();
            return r > 0;
        }
    }

    private Mamifero mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("Id");
        String nombre = rs.getString("Nombre");
        String raza = rs.getString("Raza");
        String tipo = rs.getString("TipoAnimal");
        String fecha = rs.getString("FechaNacimiento");
        float peso = (float) rs.getDouble("Peso");
        String lugar = rs.getString("LugarEntrenamiento");
        double altura = rs.getDouble("AlturaSalto");
        Mamifero m;
        if ("Perro".equalsIgnoreCase(tipo)) {
            perro p = new perro(nombre, raza, tipo, fecha, peso, lugar);
            p.setId(id);
            m = p;
        } else {
            gato g = new gato(nombre, raza, tipo, fecha, peso, altura);
            g.setId(id);
            m = g;
        }
        return m;
    }
}
