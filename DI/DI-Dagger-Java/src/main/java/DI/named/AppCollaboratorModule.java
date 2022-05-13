package DI.named;

import dagger.Binds;
import dagger.Module;
import model.named.AppCollaborator;
import model.named.IAppCollaborator;

import javax.inject.Singleton;

@Module
interface AppCollaboratorModule {

    @Singleton
    @Binds
    IAppCollaborator provideAppCollaborator(AppCollaborator impl);
}
