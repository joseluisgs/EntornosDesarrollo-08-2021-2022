package DI.persona;

import DI.persona.controllers.DaggerPersonasDBController;
import DI.persona.controllers.DaggerPersonasFileController;
import model.persona.controllers.PersonaController;

public class PersonasControllerFactory {
    private static PersonasControllerFactory instance = null;

    private PersonasControllerFactory() {
    }

    public static PersonasControllerFactory create() {
        if (instance == null) {
            instance = new PersonasControllerFactory();
        }
        return instance;
    }

    public PersonaController withDBStorage() {
        return DaggerPersonasDBController.create().build();
    }

    public PersonaController withFileStorage() {
        return DaggerPersonasFileController.create().build();
    }
}
