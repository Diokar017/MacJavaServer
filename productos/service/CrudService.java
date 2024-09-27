package productos.service;
import java.util.List;
import java.util.Optional;

public interface CrudService<T> {

    List<T> findAll();

    Optional<T> findById(Long id);

    T save(T entity);

    void deleteById(Long id);

    void deleteAll();


}
