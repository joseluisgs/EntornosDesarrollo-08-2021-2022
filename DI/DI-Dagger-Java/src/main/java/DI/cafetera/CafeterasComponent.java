package DI.cafetera;

import dagger.Component;
import model.cafetera.Cafetera;

import javax.inject.Singleton;

@Singleton // Siempre es la misma instancia.
// Los módulos que vamos a usar
@Component(modules = {
        CalentadorModule.class,
        BombaModule.class
})
public interface CafeterasComponent {
    Cafetera build();
}
