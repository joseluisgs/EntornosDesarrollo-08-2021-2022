package es.joseluisgs.dam.DI.modules;

import dagger.Binds;
import dagger.Module;
import es.joseluisgs.dam.repositories.paises.IPaisRepository;
import es.joseluisgs.dam.repositories.paises.PaisRepository;

import javax.inject.Singleton;

@Module
public interface PaisRepositoryModule {
    @Singleton
    @Binds
    IPaisRepository bindPaisRepository(PaisRepository impl);
}
