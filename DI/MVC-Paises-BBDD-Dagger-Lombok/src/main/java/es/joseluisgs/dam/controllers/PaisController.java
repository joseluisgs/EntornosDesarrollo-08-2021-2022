package es.joseluisgs.dam.controllers;

import es.joseluisgs.dam.exceptions.PaisException;
import es.joseluisgs.dam.models.Pais;
import es.joseluisgs.dam.repositories.paises.IPaisRepository;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

/**
 * Controlador de Gestion de Paises
 * Dependencias: PaisRepository
 * Es importante que lanzo las excepciones de PaisException
 * Que ya las recogerá la vista
 */
public class PaisController {
    // private static PaisController instance;
    private final IPaisRepository paisRepository;

    // Inyección de dependencias por controlador
    @Inject
    public PaisController(IPaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    /**
     * Crea un nuevo pais
     *
     * @param pais Pais a crear
     * @return Pais creado
     * @throws PaisException Si los datos del pais son incorrectos
     * @throws SQLException  Si hay algun error en la base de datos
     */
    public Pais createPais(Pais pais) throws PaisException, SQLException {
        // Comprobamos que no haya datos incorrectos, por si ha fallado la interfaz
        checkPaisData(pais);
        // comprobamos si existe, ya no hace falta un if, porque el optional nos lo devuelve
        var existe = paisRepository.findByNombre(pais.getNombre());
        if (existe.isEmpty()) {
            paisRepository.save(pais);
            return pais;
        } else {
            throw new PaisException("Ya existe un pais con ese nombre");
        }
    }

    /**
     * Comprueba que los datos del pais son correctos. No lo hacemos en el modelo para no contaminarlo.
     *
     * @param pais Pais a comprobar
     * @throws PaisException Si los datos son incorrectos
     */
    private void checkPaisData(Pais pais) throws PaisException {
        if (pais.getNombre() == null || pais.getNombre().trim().isEmpty()) {
            throw new PaisException("El nombre del pais no puede estar vacio");
        }
        if (pais.getCodigo() == null || pais.getCodigo().trim().isEmpty()) {
            throw new PaisException("El codigo del pais no puede estar vacio");
        }
        if (pais.getIdioma() == null || pais.getIdioma().trim().isEmpty()) {
            throw new PaisException("El idioma del pais no puede estar vacio");
        }
        if (pais.getMoneda() == null || pais.getMoneda().trim().isEmpty()) {
            throw new PaisException("La moneda del pais no puede estar vacia");
        }
        if (pais.getCapital() == null || pais.getCapital().trim().isEmpty()) {
            throw new PaisException("La capital del pais no puede estar vacia");
        }
    }

    /**
     * Devuelve el pais con el nombre indicado
     *
     * @param nombre nombre del pais
     * @return Pais con el nombre indicado
     * @throws PaisException si no existe el pais
     * @throws SQLException  si hay algun error en la base de datos
     */
    public Pais getPaisByNombre(String nombre) throws PaisException, SQLException {
        return paisRepository.findByNombre(nombre).orElseThrow(() -> new PaisException("No existe el pais " + nombre));
    }

    /**
     * Devuelve el pais con el id indicado
     *
     * @param id id del pais
     * @return Pais con el id indicado
     * @throws PaisException si no existe el pais
     * @throws SQLException  si hay algun error en la base de datos
     */
    public Pais getPaisById(int id) throws PaisException, SQLException {
        return paisRepository.findById(id).orElseThrow(() -> new PaisException("No existe el pais con id " + id));
    }

    /**
     * Devuelve todos los paises
     *
     * @return Lista de paises
     * @throws SQLException si hay algun error en la base de datos
     */
    public List<Pais> getAllPaises() throws SQLException {
        return paisRepository.findAll();
    }

    public Pais updatePais(int id, Pais pais) throws PaisException, SQLException {
        // Comprobamos los datos
        checkPaisData(pais);
        // Vemos si con los datos nuevos existe un pais que se llame igual
        var otro = paisRepository.findByNombre(pais.getNombre());
        // No es necesario porque si no es quien somos va a saltar la excepción
        // var paisActual = paisRepository.findById(id).orElseThrow(() -> new PaisException("No existe el pais con id " + id));

        // si no existe otro pais con el mismo nombre, actualizamos
        // o si simplemente los id son iguales, actualizamos, pues somos el mismo objeto, por eso hay "otro" ya
        // si no lo entiendes dale la vuelta al if
        if ((otro.isEmpty()) || (otro.get().getId() == pais.getId())) {
            // si no existe otro pais con el mismo nombre, lo actualizamos o somos nosotros por id
            return paisRepository.update(id, pais).get();
        } else {
            throw new PaisException("Ya existe un pais con el nombre " + pais.getNombre());
        }
    }

    /**
     * Elimina el pais con el nombre indicado
     *
     * @param nombre nombre del pais
     * @return Pais eliminado
     * @throws PaisException si no existe el pais
     * @throws SQLException  si hay un error en la base de datos
     */
    public Pais deletePais(String nombre) throws PaisException, SQLException {
        var pais = paisRepository.findByNombre(nombre).orElseThrow(() -> new PaisException("No existe el pais " + nombre));
        paisRepository.delete(pais.getId());
        return pais;
    }
}
