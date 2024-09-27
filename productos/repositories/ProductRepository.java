package productos.repositories;

import productos.models.Producto;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductRepository implements CrudRepository {

    private final Map<Long, Producto> productos;
    private Long nextId;

    public ProductRepository() {
        this.productos = new LinkedHashMap<>();
        productos.put(1L, new Producto());
        productos.put(2L, new Producto());
        productos.put(3L, new Producto());
        productos.put(4L, new Producto());
        productos.put(5L, new Producto());
        productos.put(6L, new Producto());
        productos.put(7L, new Producto());
        productos.put(8L, new Producto());
        this.nextId = 9L;
    }

    @Override
    public void createProduct(Producto producto) {
        producto.setId(nextId);
        productos.put(nextId, producto);
        nextId++;
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return Optional.ofNullable(productos.get(id));
    }

    @Override
    public List<Producto> findAll() {
        return new ArrayList<>(productos.values());
    }

    @Override
    public void save(Long id, Producto producto) {
        producto.setId(id);
        productos.put(id, producto);
    }

    @Override
    public void deleteById(Long id) {
        productos.remove(id);
    }

    @Override
    public void deleteAll() {
        productos.clear();
    }
}
