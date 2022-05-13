package DI.cafetera;

import dagger.Binds;
import dagger.Module;
import model.cafetera.Bomba;
import model.cafetera.Termosifon;

@Module
abstract class BombaModule {
    @Binds // No devuelvo nada, solo bindeo el objeto,por eso no privider
    abstract Bomba providePump(Termosifon bomba);
}