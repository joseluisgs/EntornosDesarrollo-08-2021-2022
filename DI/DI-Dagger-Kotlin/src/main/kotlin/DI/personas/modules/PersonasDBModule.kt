package DI.personas.modules

import dagger.Binds
import dagger.Module
import models.personas.services.IPersonaStorage
import models.personas.services.PersonaDataBaseStorage
import javax.inject.Singleton


@Module
interface PersonasDBModule {
    @Singleton
    @Binds
    fun bindPersonaDBStorage(impl: PersonaDataBaseStorage): IPersonaStorage
}
