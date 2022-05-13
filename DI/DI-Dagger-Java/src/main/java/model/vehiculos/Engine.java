package model.vehiculos;

import java.util.UUID;

public class Engine {

    private final String id = UUID.randomUUID().toString();

    //@Inject No lo usamos porque lo haremos en el módulo
    public Engine() {
    }

    public void start() {
        System.out.println("Engine started");
    }

    public void stop() {
        System.out.println("Engine stopped");
    }

    @Override
    public String toString() {
        return "Engine{" +
                "id='" + id + '\'' +
                '}';
    }
}
