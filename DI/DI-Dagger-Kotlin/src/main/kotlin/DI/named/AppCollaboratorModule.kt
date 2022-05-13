package DI.named

import dagger.Binds
import dagger.Module
import models.named.AppCollaborator
import models.named.IAppCollaborator
import javax.inject.Singleton

@Module
interface AppCollaboratorModule {

    // Si queremos que sea Singletonm podemos usar @Singleton
    // aquí o en la clase
    // Si lo ponemos en la clase es para todas
    // Si lo ponemos aquí es para este contexto
    @Singleton
    @Binds
    fun provideAppCollaborator(impl: AppCollaborator): IAppCollaborator
}