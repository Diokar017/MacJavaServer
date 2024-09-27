package productos.webSockets.dto;



public record ProductoNotificationResponse(
        Long id,
        String nomDelPlato,
        Double precio,
        boolean gluten
) {
}