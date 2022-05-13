package DI.personas.controllers

import DI.personas.modules.PersonasDBModule
import DI.personas.modules.PersonasRepositoryModule
import dagger.Component
import models.personas.controllers.PersonaController

import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        PersonasDBModule::class,
        PersonasRepositoryModule::class
    ]
)
interface PersonasDBController {
    fun build(): PersonaController
}
