package model.casa;

import javax.inject.Inject;
import java.util.UUID;

public class Ventana {
    private final String id = UUID.randomUUID().toString();

    @Inject // Esta vez lo hago aquí porque no voy a usar un módulo
    public Ventana() {

    }

    public void abrir() {
        System.out.println("Abriendo ventana");
    }

    public void cerrar() {
        System.out.println("Cerrando ventana");
    }

    @Override
    public String toString() {
        return "Ventana{" +
                "id='" + id + '\'' +
                '}';
    }

}
