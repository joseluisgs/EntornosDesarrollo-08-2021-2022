package es.joseluisgs.dam.utilities;

import es.joseluisgs.dam.Main;
import es.joseluisgs.dam.controllers.DataBaseManager;
import es.joseluisgs.dam.utils.ApplicationProperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class DataBase {

    /**
     * Inicia la base de datos con los datos de prueba si los hay, o la estructura de la tabla...
     */
    public static void init() {
        ApplicationProperties properties = new ApplicationProperties();
        boolean init = Boolean.parseBoolean(properties.readProperty("database.initdata"));
        if (init) {
            DataBaseManager controller = DataBaseManager.getInstance();
            String dataPath = "sql" + File.separator + "init-db.sql";
            try {
                var sqlFile = Main.class.getClassLoader().getResource(dataPath).getPath();
                controller.open();
                controller.initData(sqlFile, false);
                controller.close();
            } catch (SQLException e) {
                System.err.println("Error al conectar al servidor de Base de Datos: " + e.getMessage());
                System.exit(1);
            } catch (FileNotFoundException e) {
                System.err.println("Error al leer el fichero de datos iniciales: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    /**
     * Borra todas las tablas, que cuesta menos que tirar toda la base de datos y levantarla de nuevo
     *
     * @throws SQLException
     */
    public static void deleteAll() throws SQLException {
        DataBaseManager db = DataBaseManager.getInstance();
        String query = "DELETE FROM pais";
        db.open();
        db.delete(query);
        query = "DELETE FROM acuerdo";
        db.beginTransaction();
        db.delete(query);
        query = "DELETE FROM linea_acuerdo";
        db.delete(query);
        db.commit();
    }
}
