package es.joseluisgs.dam.utilities;

import es.joseluisgs.dam.controllers.DataBaseManager;
import es.joseluisgs.dam.models.Acuerdo;
import es.joseluisgs.dam.models.LineaAcuerdo;
import es.joseluisgs.dam.models.Pais;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DataDB {
    // Insertamos un dato de prueba, aquí vemos un acoplamiento...
    // Si no quiseramos esto, podríamos usar Order de test
    // Lo ideal es meterlo en el script, e iniciarlo cada vez...
    public static void insertPaisTest(Pais paisTest) throws SQLException {
        String query = "INSERT INTO pais VALUES (null, ?, ?, ?, ?, ?, ?)";
        DataBaseManager db = DataBaseManager.getInstance();
        db.open();
        Optional<ResultSet> res = db.insert(query, paisTest.getNombre(), paisTest.getCodigo(), paisTest.getIdioma(), paisTest.getMoneda(),
                paisTest.getCapital(), paisTest.getPresupuesto());
        if (res.get().first()) {
            paisTest.setId(res.get().getInt(1));
        }
        db.close();
    }

    public static void insertAcuerdoTest(Acuerdo acuerdoTest) throws SQLException {
        // OJO pongo null en el primer parámetro porque no inserto el ID, este se genera automático. Mira el DBMAnager
        String query = "INSERT INTO acuerdo VALUES (null, ?, ?, ?)";
        DataBaseManager db = DataBaseManager.getInstance();
        // Es una transacción, por lo que si falla alguna de las dos, se cancela todo
        db.open();
        db.beginTransaction();
        ResultSet res = db.insert(query, acuerdoTest.getNombre(), acuerdoTest.getFecha(), acuerdoTest.getAportacion())
                .orElseThrow(() -> new SQLException("Error al insertar acuerdo"));

        // Para obtener su ID que ha generado la BD
        if (res.first()) {
            acuerdoTest.setId(res.getInt(1));
            // Ahora salvamos toda las lineas de acuerdo...
            for (LineaAcuerdo linea : acuerdoTest.getLineas()) {
                query = "INSERT INTO linea_acuerdo VALUES (?, ?, ?, ?)";
                db.insert(query, acuerdoTest.getId(), linea.getPais().getId(), linea.getAño(), linea.getSubvencion())
                        .orElseThrow(() -> new SQLException("Error al insertar linea de acuerdo"));
            }

            // Y finalmente cerramos la conexión y devolvemos el acuerdo
            db.commit();
            db.close();
        }
        db.rollback();
    }
}
