package DI.persona.modules;

import dagger.Binds;
import dagger.Module;
import model.persona.services.IPersonaStorage;
import model.persona.services.PersonaFileStorage;

import javax.inject.Singleton;

@Module
public interface PersonasFileModule {
    @Singleton
    @Binds
    IPersonaStorage bindPersonaDBStorage(PersonaFileStorage impl);
}
