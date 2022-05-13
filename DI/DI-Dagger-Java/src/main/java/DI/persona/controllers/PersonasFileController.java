package DI.persona.controllers;

import DI.persona.modules.PersonasFileModule;
import DI.persona.modules.PersonasRepositoryModule;
import dagger.Component;
import model.persona.controllers.PersonaController;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        PersonasFileModule.class,
        PersonasRepositoryModule.class
})
public interface PersonasFileController {
    PersonaController build();
}
