package es.joseluisgs.dam.repositories.acuerdos;

import es.joseluisgs.dam.models.Acuerdo;
import es.joseluisgs.dam.repositories.CRUDRepository;

import java.sql.SQLException;

public interface IAcuerdoRepository extends CRUDRepository<Acuerdo, Integer> {

    void clearAll() throws SQLException;
}
