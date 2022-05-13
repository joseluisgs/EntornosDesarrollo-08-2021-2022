package es.joseluisgs.dam.DI.modules;

import dagger.Module;
import dagger.Provides;
import es.joseluisgs.dam.controllers.DataBaseManager;

import javax.inject.Singleton;

@Module
public class DataBaseManagerModule {

    @Provides
    @Singleton
    DataBaseManager provideDataBaseManager() {
        return DataBaseManager.getInstance();
    }
}
