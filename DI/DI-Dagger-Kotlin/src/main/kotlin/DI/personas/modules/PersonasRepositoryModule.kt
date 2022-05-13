package DI.personas.modules

import dagger.Binds
import dagger.Module
import models.personas.repositories.IPersonaRepository
import models.personas.repositories.PersonaRepository
import javax.inject.Singleton


@Module
interface PersonasRepositoryModule {
    // No devuelvo nada, solo bindeo el objeto,por eso no privider
    @Singleton
    @Binds
    fun bindPersonaRepository(impl: PersonaRepository): IPersonaRepository
}
