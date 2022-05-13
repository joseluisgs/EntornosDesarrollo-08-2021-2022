package DI.cafetera;

import dagger.Binds;
import dagger.Module;
import model.cafetera.Calentador;
import model.cafetera.CalentadorElectrico;

import javax.inject.Singleton;

/**
 * Puedo crear un módulo especifico para cada clase que necesite inyectar y luego acoplarlos
 * en el módulo principal.
 * De esta manera puedo tener un módulo para cada clase que necesite inyectar dependiendo del tipo
 */

/**
 * Siempre que uses @Binds, debe tener @Inject el constructor
 * de la dependencia
 */

@Module
interface CalentadorModule {
    // De esta manera cuando necesitemos un calentador, inyectamos este
    @Binds
    @Singleton
    // Ademas siempre el mismo objeto
    Calentador bindCalentador(CalentadorElectrico impl);
}