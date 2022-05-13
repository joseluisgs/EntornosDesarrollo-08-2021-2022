package model.casa;

import javax.inject.Inject;
import java.util.UUID;

// @Singleton // Veremos si funciona...
public class Casa {
    private final String id = UUID.randomUUID().toString();

    private final Ventana ventana;
    private final Puerta puerta;

    @Inject
    public Casa() {
        ventana = new Ventana();
        puerta = new Puerta();
    }

    public void entrar() {
        puerta.abrir();
        puerta.cerrar();
    }

    public void ventilar() {
        ventana.abrir();
    }

    @Override
    public String toString() {
        return "Casa{" +
                "id='" + id + '\'' +
                ", ventana=" + ventana +
                ", puerta=" + puerta +
                '}';
    }
}
