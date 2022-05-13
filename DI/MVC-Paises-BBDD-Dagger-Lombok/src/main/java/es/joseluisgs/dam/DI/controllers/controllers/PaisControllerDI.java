package es.joseluisgs.dam.DI.controllers.controllers;

import dagger.Component;
import es.joseluisgs.dam.DI.modules.DataBaseManagerModule;
import es.joseluisgs.dam.DI.modules.PaisRepositoryModule;
import es.joseluisgs.dam.controllers.PaisController;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        PaisRepositoryModule.class,
        DataBaseManagerModule.class
})

public interface PaisControllerDI {
    PaisController build();
}
