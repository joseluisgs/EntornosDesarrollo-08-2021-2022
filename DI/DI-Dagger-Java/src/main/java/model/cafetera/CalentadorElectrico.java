package model.cafetera;

import javax.inject.Inject;
import java.util.UUID;

public class CalentadorElectrico implements Calentador {
    private final String id = UUID.randomUUID().toString();
    private boolean calentando; // true si esta calentando, false si esta apagado

    @Inject
    public CalentadorElectrico() {
    }

    @Override
    public void encender() {
        this.calentando = true;
        System.out.println("~ ~ calentando ~ ~ ~");
    }

    @Override
    public void apagar() {
        this.calentando = false;
    }

    @Override
    public boolean estaCaliente() {
        return calentando;
    }

    @Override
    public String toString() {
        return "CalentadorElectrico{" +
                "id='" + id + '\'' +
                ", calentando=" + calentando +
                '}';
    }
}
