package es.joseluisgs.dam.repositories.paises;

import es.joseluisgs.dam.models.Pais;
import es.joseluisgs.dam.repositories.CRUDRepository;

import java.sql.SQLException;
import java.util.Optional;

// Toda nueva funcionalidad se extiende de la interfaz SOLID
public interface IPaisRepository extends CRUDRepository<Pais, Integer> {

    Optional<Pais> findByNombre(String nombre) throws SQLException;

    void clearAll() throws SQLException;

}
