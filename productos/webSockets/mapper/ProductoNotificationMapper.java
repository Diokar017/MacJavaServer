package productos.webSockets.mapper;




import org.springframework.stereotype.Component;
import productos.models.Producto;
import productos.webSockets.dto.ProductoNotificationResponse;

@Component
public class ProductoNotificationMapper {
    public ProductoNotificationResponse toProductNotificationDto(Producto producto) {
        return new ProductoNotificationResponse(
                producto.getId(),
                producto.getNomPlato(),
                producto.getPrecio(),
                producto.getGluten()
        );
    }
}