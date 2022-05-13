package model.named;

import javax.inject.Inject;
import java.util.UUID;

public class AppCollaborator implements IAppCollaborator {
    private final String id = UUID.randomUUID().toString();

    @Inject
    public AppCollaborator() {
    }

    @Override
    public String getName() {
        return "AppCollaborator id: " + id;
    }

    @Override
    public String toString() {
        return "AppCollaborator{" +
                "id='" + id + '\'' +
                '}';
    }
}
