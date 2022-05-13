package DI.cars

import dagger.Module
import dagger.Provides
import models.cars.Brand
import models.cars.Engine
import javax.inject.Singleton


/**
 * Con el módulo indicamos, cómo y de qué manera queremos inyectar, al no ser automático
 * Es el Proveedor, es el encargado de definir cómo se construyen las dependencias.
 * Identifica qué clases son las encargadas de construir dependencias
 *
 * @Provides Utilizado dentro de una clase con anotación @Module para indicar individualmente el objeto que provee una dependencia.
 * Se indica como anotación arriba de un método. ¿Por qué?
 * Los interfaces no se pueden construir.
 * Las clases de terceros no se pueden anotar.
 * Distintas implementaciones de un interface.
 * Si no queremos decir como se implementa podemos usar @Bind la cual devuelve un método abstracto.
 */

@Module
class CarsModule {
    // Devuleve siempre un objeto de tipo Engine (siempre nuevo)
    @Provides
    fun provideEngine() = Engine()

    // @Bind Engine bindEngine(ElectricEngine impl); // Si trabajamos con una interfaz
    // Devuelve un objeto de tipo Brand pero siempre el mismo, es singleton
    @Provides
    @Singleton
    fun provideBrand() = Brand("Coche Dagger DAM")
}
