package DI.persona.controllers;

import DI.persona.modules.PersonasDBModule;
import DI.persona.modules.PersonasRepositoryModule;
import dagger.Component;
import model.persona.controllers.PersonaController;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        PersonasDBModule.class,
        PersonasRepositoryModule.class
})
public interface PersonasDBController {
    PersonaController build();
}
