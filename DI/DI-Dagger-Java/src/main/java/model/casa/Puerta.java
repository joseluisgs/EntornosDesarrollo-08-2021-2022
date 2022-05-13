package model.casa;

import javax.inject.Inject;
import java.util.UUID;

//@Singleton // Aquñi no hace nada, pero provemos!!!
public class Puerta {
    private final String id = UUID.randomUUID().toString();

    @Inject
    public Puerta() {

    }

    public void abrir() {
        System.out.println("Abriendo puerta");
    }

    public void cerrar() {
        System.out.println("Cerrando puerta");
    }

    @Override
    public String toString() {
        return "Puerta{" +
                "id='" + id + '\'' +
                '}';
    }
}
