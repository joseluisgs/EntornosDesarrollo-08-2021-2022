package es.joseluisgs.dam.repositories.paises;

import es.joseluisgs.dam.controllers.DataBaseManager;
import es.joseluisgs.dam.models.Pais;
import es.joseluisgs.dam.utilities.DataBase;
import es.joseluisgs.dam.utilities.DataDB;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaisRepositoryTest {
    // Lo que vamos a testear, SUT = System Under Test
    PaisRepository paisRepository = PaisRepository.getInstance(DataBaseManager.getInstance());

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
    void findByNombre() throws SQLException {
        Optional<Pais> paisOp = paisRepository.findByNombre(paisTest.getNombre());
        assertAll(
                () -> assertTrue(paisOp.isPresent()),
                () -> assertEquals(paisOp.get().getId(), paisTest.getId()),
                () -> assertEquals(paisOp.get().getNombre(), paisTest.getNombre()),
                () -> assertTrue(paisOp.get().toString().equals(paisTest.toString()))
        );
    }

    @Test
    void findByNombreNoExiste() throws SQLException {
        Optional<Pais> paisOp = paisRepository.findByNombre("NoExiste");
        assertFalse(paisOp.isPresent());
    }

    @Test
    void findById() throws SQLException {
        Optional<Pais> paisOp = paisRepository.findById(paisTest.getId());
        assertAll(
                () -> assertTrue(paisOp.isPresent()),
                () -> assertEquals(paisOp.get().getId(), paisTest.getId()),
                () -> assertEquals(paisOp.get().getNombre(), paisTest.getNombre()),
                () -> assertTrue(paisOp.get().toString().equals(paisTest.toString()))
        );
    }

    @Test
    void findByIdNoExiste() throws SQLException {
        Optional<Pais> paisOp = paisRepository.findById(-99);
        assertFalse(paisOp.isPresent());
    }

    @Test
    void findAll() throws SQLException {
        var res = paisRepository.findAll();
        assertAll(
                () -> assertEquals(res.size(), 1),
                () -> assertTrue(res.contains(paisTest)),
                () -> assertEquals(res.get(0).getId(), paisTest.getId()),
                () -> assertEquals(res.get(0).getNombre(), paisTest.getNombre())
        );
    }

    @Test
    void save() throws SQLException {
        // Creamos un nuevo pais
        Pais pais = new Pais()
                .nombre("NuevoPais")
                .codigo("NP")
                .idioma("NuevoIdioma")
                .moneda("NuevoDolar")
                .capital("NuevoCapital")
                .presupuesto(100000);

        var res = paisRepository.save(pais);

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
    void update() throws SQLException {
        // Cambiamos los datos del Pais Test
        paisTest.setNombre("NuevoNombre");
        paisTest.setCodigo("NC");
        paisTest.setIdioma("NuevoIdioma");
        paisTest.setMoneda("NuevaMoneda");
        paisTest.setCapital("NuevaCapital");
        paisTest.setPresupuesto(100000);

        // Actualizamos el pais en la base de datos
        var res = paisRepository.update(paisTest.getId(), paisTest);

        // Comprobamos que se ha actualizado correctamente
        Optional<Pais> paisOp = paisRepository.findByNombre(paisTest.getNombre());
        assertAll(
                () -> assertEquals(res.getId(), paisTest.getId()),
                () -> assertEquals(res.getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.getCodigo(), paisTest.getCodigo()),
                () -> assertTrue(paisOp.isPresent()),
                () -> assertEquals(paisOp.get().getId(), paisTest.getId()),
                () -> assertEquals(paisOp.get().getNombre(), paisTest.getNombre()),
                () -> assertEquals(paisOp.get().toString(), paisTest.toString())
        );
    }

    @Test
    void delete() throws SQLException {
        // Borramos el paisTest
        var res = paisRepository.delete(paisTest.getId());
        // Comprobamos que se ha borrado correctamente
        Optional<Pais> paisOp = paisRepository.findByNombre(paisTest.getNombre());
        assertAll(
                () -> assertEquals(res.getId(), paisTest.getId()),
                () -> assertEquals(res.getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.getCodigo(), paisTest.getCodigo()),
                () -> assertFalse(paisOp.isPresent())
        );
    }

    @Test
    void clearAll() throws SQLException {
        paisRepository.clearAll();
        var res = paisRepository.findAll();
        assertEquals(res.size(), 0);
    }


}