package DI.persona.modules;

import dagger.Binds;
import dagger.Module;
import model.persona.repositories.IPersonaRepository;
import model.persona.repositories.PersonaRepository;

import javax.inject.Singleton;

@Module
public interface PersonasRepositoryModule {
    @Singleton
    @Binds
        // No devuelvo nada, solo bindeo el objeto,por eso no privider
    IPersonaRepository bindPersonaRepository(PersonaRepository impl);
}
