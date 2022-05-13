package es.joseluisgs.dam.DI.controllers.controllers;

import dagger.Component;
import es.joseluisgs.dam.DI.modules.AcuerdoRepositoryModule;
import es.joseluisgs.dam.DI.modules.DataBaseManagerModule;
import es.joseluisgs.dam.controllers.AcuerdoController;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AcuerdoRepositoryModule.class,
        DataBaseManagerModule.class
})

public interface AcuerdoControllerDI {
    AcuerdoController build();
}
