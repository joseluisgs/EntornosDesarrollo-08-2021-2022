package DI.cafeteras

import dagger.Component
import models.cafeteras.Cafetera

import javax.inject.Singleton


@Singleton // Siempre es la misma instancia.
// Los módulos que vamos a usar
@Component(modules = [CalentadoresModule::class, BombasModule::class])
interface CafeterasComponent {
    fun build(): Cafetera
}
