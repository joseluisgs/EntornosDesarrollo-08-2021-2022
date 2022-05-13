package DI.vehiculos;

import dagger.Component;
import model.vehiculos.Car;

import javax.inject.Singleton;

/**
 * Es un componente. Es decir, es nuestro Facilitador, el que nos permite al Cosumidor (Car), obtener
 * Dependencias de nuestros Provedores (Modulos). Es decir,  el Facilitador, que utiliza Componentes, los cuáles se
 * encargan de permitir el acceso a las dependencias creadas para su uso a los Consumidores.
 * Indica cuales son las dependencias que van a estar a disposición de los Consumidores (Activity, Fragment, otra clase)
 * a través de Módulos u Objetos.
 */
@Singleton // Siempre es la misma instancia.
@Component(modules = VehiclesModule.class) // Modulo donde obtendremos las cosas.
public interface VehiclesComponent {
    // Cuando llamemos a buildCar, nos devolverá un objeto Car con las dependencias que nosotros
    // Le hemos indicados en el Módulo.
    Car build();
}
