package es.joseluisgs.dam.controllers;

import es.joseluisgs.dam.exceptions.AcuerdoException;
import es.joseluisgs.dam.models.Acuerdo;
import es.joseluisgs.dam.models.LineaAcuerdo;
import es.joseluisgs.dam.models.Pais;
import es.joseluisgs.dam.repositories.acuerdos.IAcuerdoRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AcuerdoController {
    private final IAcuerdoRepository acuerdosRepository;

    public AcuerdoController(IAcuerdoRepository acuerdosRepository) {
        this.acuerdosRepository = acuerdosRepository;
    }

    /**
     * Crea un acuerdo con los datos pasados por parámetro.
     *
     * @param nombre Nombre del acuerdo.
     * @param paises Paises que participan en el acuerdo.
     * @return El acuerdo creado.
     * @throws SQLException Si hay un error en la base de datos.
     */
    public Acuerdo createAcuerdo(String nombre, List<Pais> paises) throws SQLException {
        List<LineaAcuerdo> lineas = new ArrayList<>();
        for (Pais pais : paises) {
            lineas.add(new LineaAcuerdo(pais, LocalDate.now().getYear()));
        }
        Acuerdo acuerdo = new Acuerdo(0, nombre, LocalDateTime.now(), lineas);
        return acuerdosRepository.save(acuerdo);
    }

    /**
     * Obtiene un acuerdo por su id.
     *
     * @param id Id del acuerdo.
     * @return El acuerdo.
     * @throws AcuerdoException Si no existe el acuerdo.
     * @throws SQLException     Si hay un error en la base de datos.
     */
    public Acuerdo getAcuerdoById(int id) throws AcuerdoException, SQLException {
        return acuerdosRepository.findById(id).orElseThrow(() -> new AcuerdoException("No existe el acuerdo con id " + id));
    }

    /**
     * Obtiene todos los acuerdos.
     *
     * @return Lista de acuerdos.
     * @throws SQLException Si hay un error en la base de datos.
     */
    public List<Acuerdo> getAllAcuerdos() throws SQLException {
        return acuerdosRepository.findAll();
    }

    /**
     * Elimina un acuerdo por su id.
     *
     * @param id Id del acuerdo.
     * @return El acuerdo eliminado.
     * @throws SQLException Si hay un error en la base de datos.
     */
    public Acuerdo deleteAcuerdo(int id) throws SQLException {
        return acuerdosRepository.delete(id);
    }

    /**
     * Actualiza un acuerdo.
     *
     * @param id      Id del acuerdo.
     * @param acuerdo Acuerdo con los nuevos datos.
     * @return El acuerdo actualizado.
     * @throws SQLException Si hay un error en la base de datos.
     */
    public Acuerdo updateAcuerdo(int id, Acuerdo acuerdo) throws SQLException {
        return acuerdosRepository.update(id, acuerdo);
    }
}
