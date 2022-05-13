package es.joseluisgs.dam.repositories.paises;

import es.joseluisgs.dam.controllers.DataBaseManager;
import es.joseluisgs.dam.models.Pais;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository para los paises siguiendo el TDA Mapa
 * Voy a usar un mapa y meteré la clave que es el ID
 * Con esto vemos lo sencillo que es usar mapas para este tipo de aplicaciones CRUD
 * Si lo hacemos bien, solo debo tocar el repositorio, porque tengo un buen diseño de capas
 */
public class PaisRepository implements IPaisRepository {
    private final DataBaseManager db;

    @Inject
    public PaisRepository(DataBaseManager dataBaseManager) {
        this.db = dataBaseManager;
    }


    /**
     * Busca un pais por su nombre
     *
     * @param nombre nombre del pais
     * @return el pais encontrado o null si no lo encuentra
     */
    public Optional<Pais> findByNombre(String nombre) throws SQLException {
        String query = "SELECT * FROM pais WHERE nombre = ?";
        db.open();
        ResultSet result = db.select(query, nombre).orElseThrow(() -> new SQLException("Error al consultar pais con nombre " + nombre));
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
            db.close();
            return Optional.of(pais);
        }
        return Optional.empty();
    }

    /**
     * Busca un pais por su id
     *
     * @param id id del pais
     * @return el pais encontrado o null si no lo encuentra
     */
    @Override
    public Optional<Pais> findById(Integer id) throws SQLException {
        String query = "SELECT * FROM pais WHERE id = ?";
        db.open();
        ResultSet result = db.select(query, id).orElseThrow(() -> new SQLException("Error al consultar pais con ID " + id));
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
            db.close();
            return Optional.of(pais);
        }
        return Optional.empty();
    }

    /**
     * Devuelve todos los paises
     *
     * @return lista de paises
     */
    @Override
    public List<Pais> findAll() throws SQLException {
        String query = "SELECT * FROM pais";
        db.open();
        ResultSet result = db.select(query).orElseThrow(() -> new SQLException("Error al obtener todos los paises"));
        ArrayList<Pais> list = new ArrayList<Pais>();
        while (result.next()) {
            list.add(
                    new Pais(
                            result.getInt("id"),
                            result.getString("nombre"),
                            result.getString("codigo"),
                            result.getString("idioma"),
                            result.getString("moneda"),
                            result.getString("capital"),
                            result.getDouble("presupuesto")
                    )
            );
        }
        db.close();
        return list;
    }

    /**
     * Añade un pais
     *
     * @param pais pais a añadir
     */
    @Override
    public Optional<Pais> save(Pais pais) throws SQLException {
        // OJO pongo null en el primer parámetro porque no inserto el ID, este se genera automático. Mira el DBMAnager
        String query = "INSERT INTO pais VALUES (null, ?, ?, ?, ?, ?, ?)";
        db.open();
        ResultSet res = db.insert(query, pais.getNombre(), pais.getCodigo(), pais.getIdioma(), pais.getMoneda(),
                pais.getCapital(), pais.getPresupuesto()).orElseThrow(() -> new SQLException("Error al insertar pais"));

        // Para obtener su ID que ha generado la BD
        if (res.first()) {
            pais.setId(res.getInt(1));
            // una vez insertado comprobamos que esta correcto para devolverlo
            db.close();
            return Optional.of(pais);
        }
        return Optional.empty();
    }

    /**
     * Actualiza un pais
     *
     * @param id   id del pais a actualizar
     * @param pais pais con los nuevos datos
     * @return el pais actualizado
     */
    @Override
    public Optional<Pais> update(Integer id, Pais pais) throws SQLException {
        this.findById(id).orElseThrow(() -> new SQLException("Error al actualizar pais. Pais con id " + id + " no encontrado"));
        // No dejamos tocar nunca el ID (o otros campos que no queremos que sean modificados)
        String query = "UPDATE pais SET nombre = ?, codigo = ?, idioma = ?, moneda = ?, capital = ?, presupuesto = ?" +
                "WHERE id = ?";
        db.open();
        int res = db.update(query, pais.getNombre(), pais.getCodigo(), pais.getIdioma(), pais.getMoneda(),
                pais.getCapital(), pais.getPresupuesto(), id);
        db.close();
        return Optional.of(pais);
    }

    /**
     * Elimina un pais dado el id
     *
     * @param id id del pais a eliminar
     * @return el pais eliminado o null si no lo encuentra
     */
    @Override
    public Optional<Pais> delete(Integer id) throws SQLException {
        Pais pais = this.findById(id).orElseThrow(() -> new SQLException("Error al eliminar pais. Pais con " + id + " no encontrado"));
        String query = "DELETE FROM pais WHERE id = ?";
        db.open();
        db.delete(query, id);
        db.close();
        return Optional.of(pais);
    }

    @Override
    public void clearAll() throws SQLException {
        String query = "DELETE FROM pais";
        db.open();
        db.delete(query);
        db.close();
    }

}
