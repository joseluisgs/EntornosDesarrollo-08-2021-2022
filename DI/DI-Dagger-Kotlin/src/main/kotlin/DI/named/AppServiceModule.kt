package DI.named

import dagger.Module
import dagger.Provides
import models.named.IApiService
import models.named.services.ApiServiceProduction
import models.named.services.ApiServiceTest
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppServiceModule {
    /**
     * En ocasiones necesitaremos inyectar distintas implementaciones
     * de un interface por lo que usaremos varios métodos @Provides
     * anotándolos con @Named
     */

    @Provides
    @Singleton
    @Named("Production")
    fun provideApiProductionService(): IApiService = ApiServiceProduction()

    @Provides
    @Singleton
    @Named("Test")
    fun provideApiTestService(): IApiService = ApiServiceTest()

}