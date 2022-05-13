package DI.cafeteras

import dagger.Binds
import dagger.Module
import models.cafeteras.Bomba
import models.cafeteras.Termosifon


@Module
internal abstract class BombasModule {
    @Binds // No devuelvo nada, solo bindeo el objeto,por eso no privider
    abstract fun providePump(bomba: Termosifon): Bomba
}