package DI.named

import dagger.Component
import models.named.MyApp
import javax.inject.Singleton

@Singleton
@Component(
    modules =
    [
        AppServiceModule::class,
        AppCollaboratorModule::class
    ]
)
interface MyAppComponent {
    fun build(): MyApp // También puedes llamarlo como quieras
    // O simplemente si no hacemos la inyección en el constructor
    // Esto es interesante en sitios donde no hay cosntructores
    // Como Android y sus actividades
    // void inject(MyApp Class);
}
