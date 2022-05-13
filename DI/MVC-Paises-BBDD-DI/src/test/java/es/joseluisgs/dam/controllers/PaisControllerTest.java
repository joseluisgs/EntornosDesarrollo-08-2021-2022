package es.joseluisgs.dam.controllers;

import es.joseluisgs.dam.exceptions.PaisException;
import es.joseluisgs.dam.models.Pais;
import es.joseluisgs.dam.repositories.paises.PaisRepository;
import es.joseluisgs.dam.utilities.DataBase;
import es.joseluisgs.dam.utilities.DataDB;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Isolated;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Isolated
class PaisControllerTest {
    // La dependencia
    PaisRepository paisRepository = PaisRepository.getInstance(DataBaseManager.getInstance());
    // SUT: System Under Test
    PaisController paisController = new PaisController(paisRepository);

    // Vamos a tener un pais...
    Pais paisTest = new Pais()
            .nombre("Prueba")
            .codigo("PR")
            .idioma("PruebaIdioma")
            .moneda("PruebaDolar")
            .capital("PruebaCapital")
            .presupuesto(100000);

    @BeforeAll
    static void setUpAll() {
        // Inicializamos la base de datos, estructura de tablas...
        DataBase.init();
    }

    @BeforeEach
    void setUp() throws SQLException {
        // En cada test borrramos los datos de la tabla paises
        DataBase.deleteAll();
        DataDB.insertPaisTest(paisTest);
    }

    @Test
    void createPais() throws SQLException, PaisException {
        // Creamos un pais
        // Creamos un nuevo pais
        Pais pais = new Pais()
                .nombre("NuevoPais")
                .codigo("NP")
                .idioma("NuevoIdioma")
                .moneda("NuevoDolar")
                .capital("NuevoCapital")
                .presupuesto(100000);
        // Lo insertamos en la base de datos
        var res = paisController.createPais(pais);
        // Comprobamos que se ha guardado correctamente
        Optional<Pais> paisOp = paisRepository.findByNombre(res.getNombre());
        assertAll(
                () -> assertEquals(res.getNombre(), pais.getNombre()),
                () -> assertEquals(res.getCodigo(), pais.getCodigo()),
                () -> assertTrue(paisOp.isPresent()),
                () -> assertEquals(paisOp.get().getNombre(), pais.getNombre())
        );
    }

    @Test
    void createPaisException() {
        // Creamos un pais
        // Lo insertamos en la base de datos y comprpobamos la exepción
        // Forma A: Solo casamos con el tipo de Excepción
        assertThrows(PaisException.class, () -> paisController.createPais(paisTest));
        // Forma B
        var exception = assertThrows(PaisException.class, () -> {
            paisController.createPais(paisTest);
        });
        // Podemos incluso testear el mensaje de la excepción
        assertEquals("Ya existe un pais con ese nombre", exception.getMessage());
    }

    @Test
    void getPaisByNombre() throws SQLException, PaisException {
        var res = paisController.getPaisByNombre(paisTest.getNombre());
        assertAll(
                () -> assertEquals(res.getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.getCodigo(), paisTest.getCodigo()),
                () -> assertEquals(res.getIdioma(), paisTest.getIdioma()),
                () -> assertEquals(res.getMoneda(), paisTest.getMoneda())
                //...
        );
    }

    @Test
    void getPaisByNombreException() throws SQLException, PaisException {
        var exception = assertThrows(PaisException.class, () -> {
            paisController.getPaisByNombre("PruebaError");
        });
        assertEquals("No existe el pais PruebaError", exception.getMessage());
    }

    @Test
    void getPaisById() throws SQLException, PaisException {
        // Obtenemos el pais por su id
        var res = paisController.getPaisById(paisTest.getId());
        assertAll(
                () -> assertEquals(res.getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.getCodigo(), paisTest.getCodigo()),
                () -> assertEquals(res.getIdioma(), paisTest.getIdioma()),
                () -> assertEquals(res.getMoneda(), paisTest.getMoneda())
                //...
        );
    }

    @Test
    void getPaisByIdException() throws SQLException, PaisException {
        var exception = assertThrows(PaisException.class, () -> {
            paisController.getPaisById(0);
        });
        assertEquals("No existe el pais con id 0", exception.getMessage());
    }

    @Test
    void getAllPaises() throws SQLException {
        // Obtenemos todos los paises
        var res = paisController.getAllPaises();
        assertAll(
                () -> assertEquals(res.size(), 1),
                () -> assertEquals(res.get(0).getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.get(0).getCodigo(), paisTest.getCodigo()),
                () -> assertEquals(res.get(0).getIdioma(), paisTest.getIdioma()),
                () -> assertEquals(res.get(0).getMoneda(), paisTest.getMoneda())
                //...
        );
    }

    @Test
    void updatePais() throws SQLException, PaisException {
        // Cambiamos los datos del Pais Test
        paisTest.setNombre("NuevoNombre");
        paisTest.setCodigo("NC");
        paisTest.setIdioma("NuevoIdioma");
        paisTest.setMoneda("NuevaMoneda");
        paisTest.setCapital("NuevaCapital");
        paisTest.setPresupuesto(100000);
        // Lo actualizamos en la base de datos
        var res = paisController.updatePais(paisTest.getId(), paisTest);
        // Comprobamos que se ha actualizado correctamente
        assertAll(
                () -> assertEquals(res.getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.getCodigo(), paisTest.getCodigo()),
                () -> assertEquals(res.getIdioma(), paisTest.getIdioma()),
                () -> assertEquals(res.getMoneda(), paisTest.getMoneda())
                //...
        );
    }

    @Test
    void updatePaisExceptionNoExiste() throws PaisException {
        // Cambiamos los datos del Pais Test
        paisTest.setNombre("NuevoNombre");
        paisTest.setCodigo("NC");
        paisTest.setIdioma("NuevoIdioma");
        paisTest.setMoneda("NuevaMoneda");
        paisTest.setCapital("NuevaCapital");
        paisTest.setPresupuesto(100000);
        // Lo actualizamos en la base de datos
        var exception = assertThrows(SQLException.class, () -> {
            paisController.updatePais(0, paisTest);
        });
        // El problema de testear los mensajes es que pueden cambiar...
        assertEquals("Error al actualizar pais. Pais con id 0 no encontrado", exception.getMessage());
        // Para algunos casos es mejor...
        assertThrows(SQLException.class, () -> paisController.updatePais(0, paisTest));
    }

    @Test
    void updatePaisExceptionNombreRepetido() throws PaisException, SQLException {
        Pais pais = new Pais()
                .nombre("NuevoPais")
                .codigo("NP")
                .idioma("NuevoIdioma")
                .moneda("NuevoDolar")
                .capital("NuevoCapital")
                .presupuesto(100000);
        // Lo insertamos en la base de datos
        var res = paisController.createPais(pais);
        // Actualizamos los datos del pais
        pais.setNombre(paisTest.getNombre());
        // Lo actualizamos en la base de datos
        var exception = assertThrows(PaisException.class, () -> {
            paisController.updatePais(pais.getId(), pais);
        });
        // El problema de testear los mensajes es que pueden cambiar...
        assertEquals("Ya existe un pais con el nombre " + paisTest.getNombre(), exception.getMessage());
        // Para algunos casos es mejor...
        assertThrows(PaisException.class, () -> paisController.updatePais(pais.getId(), pais));
    }

    @Test
    void deletePais() throws SQLException, PaisException {
        // Elimnamos el pais
        var res = paisController.deletePais(paisTest.getNombre());
        // Comprobamos que se ha eliminado correctamente
        // No hace falta capturar el mensaje... solo la excepción
        var exception = assertThrows(PaisException.class, () -> {
            paisController.getPaisByNombre(paisTest.getNombre());
        });
        assertAll(
                () -> assertEquals(res.getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.getCodigo(), paisTest.getCodigo()),
                () -> assertEquals(res.getIdioma(), paisTest.getIdioma()),
                // ...
                // No hace falta si no queremos comprobar el mensaje de la excepción, solo saber si se lanza
                () -> assertThrows(PaisException.class, () -> paisController.getPaisByNombre(paisTest.getNombre())),
                () -> assertEquals("No existe el pais " + paisTest.getNombre(), exception.getMessage())
        );
    }

    @Test
    void deletePaisException() throws SQLException, PaisException {
        var exception = assertThrows(PaisException.class, () -> {
            paisController.deletePais("PruebaError");
        });
        assertEquals("No existe el pais PruebaError", exception.getMessage());
    }

    @Test
    void checkPaisDataNombreException() throws SQLException, PaisException {
        var exception = assertThrows(PaisException.class, () -> {
            paisController.createPais(new Pais());
        });
        assertEquals("El nombre del pais no puede estar vacio", exception.getMessage());
    }

    @Test
    void checkPaisDataCodigoException() throws SQLException, PaisException {
        var exception = assertThrows(PaisException.class, () -> {
            paisController.createPais(new Pais().nombre("NuevoPais"));
        });
        assertEquals("El codigo del pais no puede estar vacio", exception.getMessage());
    }

    @Test
    void checkPaisDataIdiomaException() throws SQLException, PaisException {
        var exception = assertThrows(PaisException.class, () -> {
            paisController.createPais(new Pais().nombre("NuevoPais").codigo("NP"));
        });
        assertEquals("El idioma del pais no puede estar vacio", exception.getMessage());
    }

    @Test
    void checkPaisDataMonedaException() throws SQLException, PaisException {
        var exception = assertThrows(PaisException.class, () -> {
            paisController.createPais(new Pais().nombre("NuevoPais").codigo("NP").idioma("NuevoIdioma"));
        });
        assertEquals("La moneda del pais no puede estar vacia", exception.getMessage());
    }

    @Test
    void checkPaisDataCapitalException() throws SQLException, PaisException {
        var exception = assertThrows(PaisException.class, () -> {
            paisController.createPais(new Pais()
                    .nombre("NuevoPais")
                    .codigo("NP")
                    .idioma("NuevoIdioma")
                    .moneda("NuevaMoneda"));
        });
        assertEquals("La capital del pais no puede estar vacia", exception.getMessage());
    }
}