package es.joseluisgs.dam.DI.modules;

import dagger.Binds;
import dagger.Module;
import es.joseluisgs.dam.services.Storage.IStorageBackup;
import es.joseluisgs.dam.services.Storage.StorageBackupJsonFile;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public
interface StorageBackupModule {
    @Binds
    @Singleton
    @Named("json")
    IStorageBackup provideApiStorageBackupService(StorageBackupJsonFile impl);

}
