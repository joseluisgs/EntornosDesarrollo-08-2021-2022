package model.cafetera;

import dagger.Lazy;

import javax.inject.Inject;
import java.util.UUID;

public class Cafetera {
    // El conste de crear o enlazar una instancia de una clase que no se usa es alto
    // Podemos usar Lazy para que se haga solo si al final la usamos en el resto de los métodos
    // esto es la carga perezosa
    private final Lazy<Calentador> calentador; // Solo lo creamos cuando lo usemos... Carga perezosa
    private final Bomba bomba;
    private final String id = UUID.randomUUID().toString();

    @Inject
    Cafetera(Lazy<Calentador> calentador, Bomba bomba) {
        this.calentador = calentador;
        this.bomba = bomba;
    }

    public void servir() {
        calentador.get().encender(); // El get es porque es Lazy, solo lo creamos cuando lo usemos la 1ª vez, por eso hay que usar el get
        bomba.bombear();
        System.out.println("[_]P !Taza de Café! [_]P ");
        calentador.get().apagar();
    }

    @Override
    public String toString() {
        return "Cafetera{" +
                "calentador=" + calentador +
                ", bomba=" + bomba +
                ", id='" + id + '\'' +
                '}';
    }
}