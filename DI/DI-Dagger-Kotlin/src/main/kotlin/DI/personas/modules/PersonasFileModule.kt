package DI.personas.modules

import dagger.Binds
import dagger.Module
import models.personas.services.IPersonaStorage
import models.personas.services.PersonaFileStorage
import javax.inject.Singleton


@Module
interface PersonasFileModule {
    @Singleton
    @Binds
    fun bindPersonaDBStorage(impl: PersonaFileStorage): IPersonaStorage
}
