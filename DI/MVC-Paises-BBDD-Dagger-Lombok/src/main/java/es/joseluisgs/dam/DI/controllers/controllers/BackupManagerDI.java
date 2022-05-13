package es.joseluisgs.dam.DI.controllers.controllers;

import dagger.Component;
import es.joseluisgs.dam.DI.modules.AcuerdoRepositoryModule;
import es.joseluisgs.dam.DI.modules.DataBaseManagerModule;
import es.joseluisgs.dam.DI.modules.PaisRepositoryModule;
import es.joseluisgs.dam.DI.modules.StorageBackupModule;
import es.joseluisgs.dam.controllers.BackupManager;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        PaisRepositoryModule.class,
        AcuerdoRepositoryModule.class,
        StorageBackupModule.class,
        DataBaseManagerModule.class
})

public interface BackupManagerDI {
    BackupManager build();
}
