package dao;

import java.util.List;
import model.Mamifero;

public interface MamiferoDAO {
    int insert(Mamifero m) throws Exception;
    List<Mamifero> listAll() throws Exception;
    Mamifero findById(int id) throws Exception;
    Mamifero findByName(String name) throws Exception;
    boolean update(Mamifero m) throws Exception;
    boolean delete(int id) throws Exception;
}
