package es.joseluisgs.dam.DI.modules;

import dagger.Binds;
import dagger.Module;
import es.joseluisgs.dam.repositories.acuerdos.AcuerdoRepository;
import es.joseluisgs.dam.repositories.acuerdos.IAcuerdoRepository;

import javax.inject.Singleton;

@Module
public interface AcuerdoRepositoryModule {
    @Singleton
    @Binds
    IAcuerdoRepository bindAcuerdoRepository(AcuerdoRepository impl);
}
