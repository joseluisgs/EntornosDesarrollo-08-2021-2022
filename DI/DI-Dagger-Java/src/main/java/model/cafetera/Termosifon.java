package model.cafetera;

import javax.inject.Inject;
import java.util.UUID;

public class Termosifon implements Bomba {
    private final Calentador calentador;
    private final String id = UUID.randomUUID().toString();

    @Inject
    Termosifon(Calentador calentador) {
        this.calentador = calentador;
    }

    @Override
    public void bombear() {
        if (calentador.estaCaliente()) {
            System.out.println("=> => bombeando => =>");
        }
    }

    @Override
    public String toString() {
        return "Termosifon{" +
                "id='" + id + '\'' +
                ", calentador=" + calentador +
                '}';
    }
}
