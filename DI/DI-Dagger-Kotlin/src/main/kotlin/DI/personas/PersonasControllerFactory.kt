package DI.personas

import DI.personas.controllers.DaggerPersonasDBController
import DI.personas.controllers.DaggerPersonasFileController
import models.personas.controllers.PersonaController


object PersonasControllerFactory {
    fun withDBStorage(): PersonaController {
        return DaggerPersonasDBController.create().build()
    }

    fun withFileStorage(): PersonaController {
        return DaggerPersonasFileController.create().build()
    }
}
