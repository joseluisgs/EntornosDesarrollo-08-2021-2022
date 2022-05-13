package DI.casa;

import dagger.Component;
import model.casa.Casa;

import javax.inject.Singleton;

@Singleton // Siempre es la misma instancia.
@Component // (modules = VehiclesModule.class) // Modulo donde obtendremos las cosas.
public interface CasasComponent {
    // Cuando llamemos a buildCar, nos devolverá un objeto Car con las dependencias que nosotros
    // Le hemos indicados en el Módulo.
    Casa build();
}
