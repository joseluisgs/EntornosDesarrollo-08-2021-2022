import DI.cafetera.CafeterasComponent;
import DI.cafetera.DaggerCafeterasComponent;
import DI.casa.CasasComponent;
import DI.casa.DaggerCasasComponent;
import DI.named.DaggerMyAppComponent;
import DI.persona.PersonasControllerFactory;
import DI.vehiculos.DaggerVehiclesComponent;
import DI.vehiculos.VehiclesComponent;
import model.persona.controllers.PersonaController;
import model.persona.model.Persona;
import model.persona.repositories.PersonaRepository;
import model.persona.services.PersonaDataBaseStorage;
import model.persona.services.PersonaFileStorage;
import model.presenter.MyView;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hola DI con Dagger 2! ");
        System.out.println();

        testVehiculos();

        testCasas();

        testCafeteras();

        testMVC();

        testPresenter();

        testNamed();

    }

    private static void testNamed() {
        System.out.println("Named");
        var myApp = DaggerMyAppComponent.create().build();
        System.out.println(myApp.toString());
        System.out.println(myApp.myCollaborator());
        System.out.println(myApp.apiService());
        System.out.println();
    }

    private static void testPresenter() {
        System.out.println("MyView");
        // Cuando lo creemos ya está todo inyectado!!!
        // Esto es útil dependiendo del contexto
        MyView myView = new MyView();
        System.out.println(myView);
        System.out.println();
    }


    private static void testCafeteras() {
        System.out.println("Cafeteras");
        CafeterasComponent cafeterasComponent = DaggerCafeterasComponent.create();
        var cafetera1 = cafeterasComponent.build();
        System.out.println(cafetera1.toString());
        cafetera1.servir();
        System.out.println();
        var cafetera2 = cafeterasComponent.build();
        System.out.println(cafetera2.toString());
        cafetera2.servir();
        System.out.println();
    }

    private static void testCasas() {
        System.out.println("Casas");
        CasasComponent casasComponent = DaggerCasasComponent.create();
        var casa1 = casasComponent.build();
        System.out.println(casa1.toString());
        var casa2 = casasComponent.build();
        System.out.println(casa2.toString());
        System.out.println();
    }

    private static void testVehiculos() {
        System.out.println("Vehiculos");
        // Obtenemos nuestro componente, que nos crea los objetos
        VehiclesComponent vehiclesComponent = DaggerVehiclesComponent.create();
        // Obtenemos el objeto que queremos
        var car1 = vehiclesComponent.build();
        // Mostramos el objeto
        System.out.println(car1.toString());
        var car2 = vehiclesComponent.build();
        System.out.println(car2.toString());
        System.out.println();
    }

    private static void testMVC() {
        /**
         * Como vemos lo importante de las ID es que no acoplamos el código
         * pudiendo modificar sola la capa que necesitemos
         */
        System.out.println("Personas: Model->Controller->Repository->Storage(Database|File)");
        Persona p = new Persona("Juan", "Perez", "123456789");
        System.out.println(p);
        System.out.println();
        System.out.println("Sin Dagger");
        PersonaController contRepoStorageBD = new PersonaController(new PersonaRepository(new PersonaDataBaseStorage()));
        System.out.println(contRepoStorageBD);
        PersonaController contRepoStorageFile = new PersonaController(new PersonaRepository(new PersonaFileStorage()));
        System.out.println(contRepoStorageFile);

        var resBD = contRepoStorageBD.save(p);
        System.out.println("Resultado BD: " + resBD);
        var resFile = contRepoStorageFile.save(p);
        System.out.println("Resultado File: " + resFile);
        System.out.println();

        System.out.println("Con Dagger");
        contRepoStorageBD = PersonasControllerFactory.create().withDBStorage();
        System.out.println(contRepoStorageBD);
        contRepoStorageFile = PersonasControllerFactory.create().withFileStorage();
        System.out.println(contRepoStorageFile);
        resBD = contRepoStorageBD.save(p);
        System.out.println("Resultado BD: " + resBD);
        resFile = contRepoStorageFile.save(p);
        System.out.println("Resultado File: " + resFile);
        System.out.println();

    }
}
