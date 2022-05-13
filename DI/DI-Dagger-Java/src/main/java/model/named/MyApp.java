package model.named;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

public class MyApp {
    private final String id = UUID.randomUUID().toString();
    private final IAppCollaborator collaborator;
    private final IApiService apiService;

    // Aquí si hacemos la inyección de dependencias
    // En el constructor de la clase
    // si le decimos el Named, podrá saber cual inyectar...
    // De esta manera podrá inyectar una instancia de la clase u otra
    @Inject
    public MyApp(
            IAppCollaborator collaborator,
            @Named("Production") IApiService apiService
    ) {
        this.collaborator = collaborator;
        this.apiService = apiService;
    }


    public String myCollaborator() {
        return collaborator.getName();
    }

    public String apiService() {
        return apiService.getData();
    }

    @Override
    public String toString() {
        return "MyApp{" +
                "id='" + id + '\'' +
                ", collaborator=" + collaborator +
                ", apiService=" + apiService +
                '}';
    }
}
