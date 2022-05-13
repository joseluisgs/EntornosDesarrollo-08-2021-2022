package es.joseluisgs.dam.repositories.acuerdos;

import es.joseluisgs.dam.controllers.DataBaseManager;
import es.joseluisgs.dam.models.Acuerdo;
import es.joseluisgs.dam.models.LineaAcuerdo;
import es.joseluisgs.dam.models.Pais;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AcuerdoRepository implements IAcuerdoRepository {
    private static AcuerdoRepository instance;
    private final DataBaseManager db;

    private AcuerdoRepository(DataBaseManager db) {

        this.db = db;
    }

    public static AcuerdoRepository getInstance(DataBaseManager dataBaseManager) {
        if (instance == null) {
            instance = new AcuerdoRepository(dataBaseManager);
        }
        return instance;
    }

    @Override
    public Optional<Acuerdo> findById(Integer id) throws SQLException {
        String query = "SELECT * FROM acuerdo WHERE id = ?";
        DataBaseManager db = DataBaseManager.getInstance();
        db.open();
        ResultSet result = db.select(query, id)
                .orElseThrow(() -> new SQLException("Error al consultar acuerdo con ID " + id));
        if (result.first()) {
            int idAcuerdo = result.getInt("id");
            String nombre = result.getString("nombre");
            LocalDateTime fecha = result.getObject("fecha", LocalDateTime.class);
            double aportacion = result.getDouble("aportacion");

            // Ahora obtenemos todas las lineas de acuerdo
            List<LineaAcuerdo> lineas = getLineasAcuerdos(db, id);
            db.close();
            // Creamos el acuerdo
            Acuerdo acuerdo = new Acuerdo(idAcuerdo, nombre, fecha, aportacion, lineas);
            return Optional.of(acuerdo);
        }
        return Optional.empty();
    }

    @Override
    public Acuerdo save(Acuerdo acuerdo) throws SQLException {
        // OJO pongo null en el primer parámetro porque no inserto el ID, este se genera automático. Mira el DBMAnager
        String query = "INSERT INTO acuerdo VALUES (null, ?, ?, ?)";
        DataBaseManager db = DataBaseManager.getInstance();
        // Es una transacción, por lo que si falla alguna de las dos, se cancela todo
        db.open();
        db.beginTransaction();
        ResultSet res = db.insert(query, acuerdo.getNombre(), acuerdo.getFecha(), acuerdo.getAportacion())
                .orElseThrow(() -> new SQLException("Error al insertar acuerdo"));

        // Para obtener su ID que ha generado la BD
        if (res.first()) {
            acuerdo.setId(res.getInt(1));
            // Ahora salvamos toda las lineas de acuerdo...
            query = "INSERT INTO linea_acuerdo VALUES (?, ?, ?, ?)";
            for (LineaAcuerdo linea : acuerdo.getLineas()) {
                db.insert(query, acuerdo.getId(), linea.getPais().getId(), linea.getAño(), linea.getSubvencion())
                        .orElseThrow(() -> new SQLException("Error al insertar linea de acuerdo"));
            }

            // Y finalmente cerramos la conexión y devolvemos el acuerdo
            db.commit();
            db.close();
            return acuerdo;
        }
        db.rollback();
        return null;
    }

    @Override
    public Acuerdo delete(Integer id) throws SQLException {
        // Si ytenemos el delete en ccascade genial, si no
        // Transacción y a borrar todo...
        // Primero lineas, y luego acuerdo
        Acuerdo acuerdo = findById(id).orElseThrow(() -> new SQLException("Error al borrar acuerdo. Acuerdo con ID " + id + " no encontrado"));
        DataBaseManager db = DataBaseManager.getInstance();
        db.open();
        String query = "DELETE FROM linea_acuerdo WHERE id_acuerdo = ?";
        db.beginTransaction();
        db.delete(query, id);
        query = "DELETE FROM acuerdo WHERE id = ?";
        db.delete(query, id);
        db.commit();
        db.close();
        return acuerdo;
    }

    @Override
    public List<Acuerdo> findAll() throws SQLException {
        String query = "SELECT * FROM acuerdo";
        DataBaseManager db = DataBaseManager.getInstance();
        db.open();
        ResultSet result = db.select(query).orElseThrow(() -> new SQLException("Error al obtener todos los acuerdos"));
        ArrayList<Acuerdo> list = new ArrayList<Acuerdo>();
        // Recorremos todos los acuerdos
        while (result.next()) {
            int idAcuerdo = result.getInt("id");
            String nombre = result.getString("nombre");
            LocalDateTime fecha = result.getObject("fecha", LocalDateTime.class);
            double aportacion = result.getDouble("aportacion");

            // Ahora obtenemos todas las lineas de acuerdo
            List<LineaAcuerdo> lineas = getLineasAcuerdos(db, idAcuerdo);
            // Ya tenemos todas las lineas de acuerdo, ahora creamos el acuerdo
            Acuerdo acuerdo = new Acuerdo(idAcuerdo, nombre, fecha, aportacion, lineas);
            list.add(acuerdo);
        } // Fin del bucle de Acuerdos
        db.close();
        return list;
    }

    @Override
    public Acuerdo update(Integer id, Acuerdo entity) throws SQLException {
        // No es la mejor manera de realizarlo, pero hasta que veamos otra cosa no me importa
        var acuerdo = findById(id).orElseThrow(() -> new SQLException("Error al actualizar acuerdo. Acuerdo con ID " + id + " no encontrado"));
        this.delete(id);
        return this.save(entity);
    }

    private List<LineaAcuerdo> getLineasAcuerdos(DataBaseManager db, Integer id) throws SQLException {
        ResultSet result;
        List<LineaAcuerdo> lineas = new ArrayList<>();
        String query = "SELECT * FROM linea_acuerdo WHERE id_acuerdo = ?";
        result = db.select(query, id).orElseThrow(() -> new SQLException("Error al consultar lineas de acuerdo con ID " + id));
        while (result.next()) {
            // Y ahora necesitamos el Pais :)
            Pais pais = this.getPais(db, result.getInt("id_pais"));
            // Obtenemos el resto de los datos de la linea de acuerdo
            int año = result.getInt("año");
            double subvencion = result.getDouble("subvencion");
            // Ya tenemos el pais, ahora creamos la linea de acuerdo
            LineaAcuerdo linea = new LineaAcuerdo(pais, año, subvencion);
            lineas.add(linea);

        }
        return lineas;
    }


    private Pais getPais(DataBaseManager db, int idPais) throws SQLException {
        String query = "SELECT * FROM pais WHERE id = ?";
        ResultSet result = db.select(query, idPais).orElseThrow(() -> new SQLException("Error al obtener pais con ID " + idPais));
        if (result.first()) {
            Pais pais = new Pais(
                    result.getInt("id"),
                    result.getString("nombre"),
                    result.getString("codigo"),
                    result.getString("idioma"),
                    result.getString("moneda"),
                    result.getString("capital"),
                    result.getDouble("presupuesto")
            );
            return pais;
        }
        return null;
    }

    @Override
    public void clearAll() throws SQLException {
        DataBaseManager db = DataBaseManager.getInstance();
        db.open();
        String query = "DELETE FROM acuerdo";
        db.beginTransaction();
        db.delete(query);
        query = "DELETE FROM linea_acuerdo";
        db.delete(query);
        db.commit();
        db.close();
    }
}
