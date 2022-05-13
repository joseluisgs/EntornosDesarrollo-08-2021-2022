package model.presenter;

import javax.inject.Inject;
import java.util.UUID;

public class Presenter {
    private final String id = UUID.randomUUID().toString();
    private final Navigator navigator;

    @Inject
    public Presenter(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public String toString() {
        return "Presenter{" +
                "id='" + id + '\'' +
                ", navigator=" + navigator +
                '}';
    }
}
