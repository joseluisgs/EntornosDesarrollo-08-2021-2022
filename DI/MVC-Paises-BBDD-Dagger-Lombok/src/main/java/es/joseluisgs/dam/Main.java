package es.joseluisgs.dam;

import es.joseluisgs.dam.controllers.DataBaseManager;
import es.joseluisgs.dam.utils.ApplicationProperties;
import es.joseluisgs.dam.views.PaisView;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Ejemplo de Patrón MVC para CRUD de países.
 * Siguiendo los diagramas de Secuencias en Clase
 * Modelo: Pais, gestionador por PaisRepository
 * Vista: La propia consola: comunicacion con el usuario
 * Controlador: PaisController, controla, cómo y de qué manera el modelo, repositorio y la vista interactúan
 * Como vista que soy, soy lo último y gestiono las excepciones con Try/Catch
 */
public class Main {
    public static void main(String[] args) {
        checkServer();
        initData();
        PaisView view = PaisView.getInstance();
        view.menu();

    }

    private static void checkServer() {
        System.out.println("Comprobamos la conexión al Servidor BD");
        DataBaseManager controller = DataBaseManager.getInstance();
        try {
            controller.open();
            Optional<ResultSet> rs = controller.select("SELECT 'Hello world'");
            if (rs.isPresent()) {
                rs.get().first();
                controller.close();
                System.out.println("Conexión con la Base de Datos realizada con éxito");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar al servidor de Base de Datos: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void initData() {
        ApplicationProperties properties = new ApplicationProperties();
        boolean init = Boolean.parseBoolean(properties.readProperty("database.initdata"));
        if (init) {
            System.out.println("Iniciamos los datos de ejemplo de la Base de Datos");
            DataBaseManager controller = DataBaseManager.getInstance();
            String dataPath = "sql" + File.separator + "init-db.sql";
            try {
                var sqlFile = Main.class.getClassLoader().getResource(dataPath).getPath();
                System.out.println(dataPath);
                controller.open();
                controller.initData(sqlFile, false);
                controller.close();
                System.out.println("Datos inicializados con éxito");
            } catch (SQLException e) {
                System.err.println("Error al conectar al servidor de Base de Datos: " + e.getMessage());
                System.exit(1);
            } catch (FileNotFoundException e) {
                System.err.println("Error al leer el fichero de datos iniciales: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
