package model.presenter;

import DI.presenter.DaggerGrafoDependencias;

import javax.inject.Inject;
import java.util.UUID;

public class MyView {
    private final String id = UUID.randomUUID().toString();
    @Inject
    Presenter presenter;

    // Aquí no hacemos la inyección en el constructor si no más adelante
    // por eso necesitamos el metodo inject
    // Esto es interesante en sitios donde no hay cosntructores
    // Como Android y sus actividades
    public MyView() {
        init();
    }

    public void init() {
        DaggerGrafoDependencias.create().inject(this);
    }

    @Override
    public String toString() {
        return "MyView{" +
                "presenter=" + presenter +
                ", id='" + id + '\'' +
                '}';
    }
}
