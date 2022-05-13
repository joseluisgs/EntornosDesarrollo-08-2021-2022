package DI.persona.modules;

import dagger.Binds;
import dagger.Module;
import model.persona.services.IPersonaStorage;
import model.persona.services.PersonaDataBaseStorage;

import javax.inject.Singleton;

@Module
public interface PersonasDBModule {
    @Singleton
    @Binds
    IPersonaStorage bindPersonaDBStorage(PersonaDataBaseStorage impl);
}
