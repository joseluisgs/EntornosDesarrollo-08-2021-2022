package model.persona.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Con Lombok podemos crear getters y setters automáticamente.
 * así como tros métodos y patrones. Es recomendable solo si sabemos usarlo
 * https://projectlombok.org/features/all
 */
@Data
@AllArgsConstructor
public class Persona {
    private final String id = UUID.randomUUID().toString();
    private String nombre;
    private String apellido;
    private String dni;
}
