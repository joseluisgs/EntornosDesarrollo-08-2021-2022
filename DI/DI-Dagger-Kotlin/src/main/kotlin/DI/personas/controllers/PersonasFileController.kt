package DI.personas.controllers

import DI.personas.modules.PersonasFileModule
import DI.personas.modules.PersonasRepositoryModule
import dagger.Component
import models.personas.controllers.PersonaController

import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        PersonasFileModule::class,
        PersonasRepositoryModule::class
    ]
)
interface PersonasFileController {
    fun build(): PersonaController
}
