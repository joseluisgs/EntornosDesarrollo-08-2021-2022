package model.presenter;

import javax.inject.Inject;
import java.util.UUID;

public class Navigator {
    private final String id = UUID.randomUUID().toString();

    @Inject
    public Navigator() {
    }

    @Override
    public String toString() {
        return "Navigator{" +
                "id='" + id + '\'' +
                '}';
    }
}