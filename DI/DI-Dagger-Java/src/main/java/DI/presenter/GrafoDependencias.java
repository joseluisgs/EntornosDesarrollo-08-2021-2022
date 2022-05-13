package DI.presenter;

import dagger.Component;
import model.presenter.MyView;

/**
 * Otra cosa que podemos hacer es definir
 * Ahora solo nos queda construir el grafo definido por los diversos
 *
 * @Inject. Para ello crearemos un interface anotado con
 * @Component y un método inject para cada
 * una de las raíces de nuestro grafo.
 * Con esto también resumiríamos el problema de la inyección de dependencias de varios tipos
 * Creando solo la especifica y diccindo que clase puede obtenerla.
 */
@Component
public interface GrafoDependencias {
    void inject(MyView myClass);
}
