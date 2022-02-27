package repositorio;

import modelo.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {
    List<T>listar();
    T porId(Long id);
    void guardar (T t);
    void eliminar (Long id);


}
