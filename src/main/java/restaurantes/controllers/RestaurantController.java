package restaurantes.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import restaurantes.dto.NewRestaurantDTO;
import restaurantes.dto.UpdatedRestaurantDTO;
import restaurantes.models.Restaurant;
import restaurantes.servicios.RestaurantService;
import restaurantes.utils.PageResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador de la clase Restaurante de tipo RESTController
 * Se usa el repositorio de la clase y se inyecta mediante la anotacion Autowired
 */
@RestController
public class RestaurantController {
    RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service){
        this.service=service;
    }

    @GetMapping("/restaurantes")
    public ResponseEntity<PageResponse<Restaurant>> getRestaurants(
            @RequestParam(required=false) Optional<String> name,
            @RequestParam(required = false)Optional<String> number,
            @RequestParam(defaultValue = "false",required = false)Optional<Boolean> isDeleted,
            @RequestParam(required = false)Optional<String> adress,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
            ){
        Sort sort=direction.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Page<Restaurant> pageResult= service.findAll(name, number,isDeleted, adress, PageRequest.of(page,size,sort));
        return ResponseEntity.ok().body(PageResponse.of(pageResult,sortBy,direction));
    }

    @GetMapping ("/restaurantes/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/restaurantes")
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody NewRestaurantDTO restaurantDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(restaurantDTO));
    }

    @PutMapping("/restaurantes/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @Valid @RequestBody UpdatedRestaurantDTO restaurantDTO){
        return ResponseEntity.ok(service.update(id, restaurantDTO));
    }

    @DeleteMapping("/restaurantes/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationExceptions(MethodArgumentNotValidException excep){
        Map<String,String> errors = new HashMap<>();
        excep.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
