package DI.casas

import dagger.Component
import models.casas.Casa

import javax.inject.Singleton


@Singleton
@Component // (modules = VehiclesModule.class) // Modulo donde obtendremos las cosas.
interface CasasComponent {
    // Cuando llamemos a buildCar, nos devolverá un objeto Car con las dependencias que nosotros
    // Le hemos indicados en el Módulo.
    fun build(): Casa
}
