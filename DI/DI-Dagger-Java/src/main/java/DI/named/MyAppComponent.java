package DI.named;

import dagger.Component;
import model.named.MyApp;

import javax.inject.Singleton;

/**
 * Siempre puedes llamar a esto como más te guste, como quieras...
 * para luego recuperarlo como.
 */
@Singleton
@Component(modules = {
        AppServiceModule.class,
        AppCollaboratorModule.class
})
public interface MyAppComponent {
    MyApp build(); // También puedes llamarlo como quieras

    // O simplemente si no hacemos la inyección en el constructor
    // Esto es interesante en sitios donde no hay cosntructores
    // Como Android y sus actividades
    // void inject(MyApp Class);
}
