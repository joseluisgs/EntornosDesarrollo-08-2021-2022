package DI.named;

import dagger.Module;
import dagger.Provides;
import model.named.IApiService;
import model.named.services.ApiServiceProduction;
import model.named.services.ApiServiceTest;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class AppServiceModule {

    /**
     * En ocasiones necesitaremos inyectar distintas implementaciones
     * de un interface por lo que usaremos varios métodos @Provides
     * anotándolos con @Named
     */
    @Provides
    @Singleton
    @Named("Production")
    IApiService provideApiProductionService() {
        return new ApiServiceProduction();
    }

    @Provides
    @Singleton
    @Named("Test")
    IApiService provideApiTestService() {
        return new ApiServiceTest();
    }
}
