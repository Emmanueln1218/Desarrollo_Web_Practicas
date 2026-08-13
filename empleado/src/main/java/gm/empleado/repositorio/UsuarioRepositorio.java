package gm.empleado.repositorio;

import gm.empleado.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    Usuario findByUsernameAndPassword(String username, String password);
}