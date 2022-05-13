package es.joseluisgs.dam.repositories.acuerdos;

import es.joseluisgs.dam.controllers.DataBaseManager;
import es.joseluisgs.dam.models.Acuerdo;
import es.joseluisgs.dam.models.LineaAcuerdo;
import es.joseluisgs.dam.models.Pais;
import es.joseluisgs.dam.utilities.DataBase;
import es.joseluisgs.dam.utilities.DataDB;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AcuerdoRepositoryTest {
    // Lo que vamos a testear, SUT = System Under Test
    AcuerdoRepository acuerdosRepository = new AcuerdoRepository(DataBaseManager.getInstance());

    // Vamos a tener un acuerdo
    Acuerdo acuerdoTest = new Acuerdo(0, "AcuerdoTest", LocalDateTime.now(),
            List.of(
                    new LineaAcuerdo(
                            new Pais()
                                    .nombre("Prueba")
                                    .codigo("PR")
                                    .idioma("PruebaIdioma")
                                    .moneda("PruebaDolar")
                                    .capital("PruebaCapital")
                                    .presupuesto(100000),
                            LocalDateTime.now().getYear()
                    )
            ));

    @BeforeAll
    static void setUpAll() {
        // Inicializamos la base de datos, estructura de tablas...
        DataBase.init();
    }

    @BeforeEach
    void setUp() throws SQLException {
        // En cada test borrramos los datos de la tabla paises
        DataBase.deleteAll();
        DataDB.insertAcuerdoTest(acuerdoTest);
    }

    @Test
    void findById() throws SQLException {
        Optional<Acuerdo> acuerdoOp = acuerdosRepository.findById(acuerdoTest.getId());
        assertAll(
                () -> assertTrue(acuerdoOp.isPresent()),
                () -> assertEquals(acuerdoOp.get().getId(), acuerdoTest.getId()),
                () -> assertEquals(acuerdoOp.get().getNombre(), acuerdoTest.getNombre()),
                () -> assertEquals(acuerdoOp.get().getLineas().size(), acuerdoTest.getLineas().size())
        );
    }

    @Test
    public void findByIdNoExiste() throws SQLException {
        Optional<Acuerdo> acuerdoOp = acuerdosRepository.findById(0);
        assertAll(
                () -> assertFalse(acuerdoOp.isPresent())
        );
    }

    @Test
    void findAll() throws SQLException {
        var res = acuerdosRepository.findAll();
        assertAll(
                () -> assertEquals(res.size(), 1),
                () -> assertEquals(res.get(0).getId(), acuerdoTest.getId()),
                () -> assertEquals(res.get(0).getNombre(), acuerdoTest.getNombre())
        );
    }

    @Test
    void save() throws SQLException {
        var res = acuerdosRepository.save(acuerdoTest).get();
        Optional<Acuerdo> acuerdoOp = acuerdosRepository.findById(res.getId());
        assertAll(
                () -> assertEquals(res.getNombre(), acuerdoTest.getNombre()),
                () -> assertEquals(res.getLineas().size(), acuerdoTest.getLineas().size()),
                () -> assertTrue(acuerdoOp.isPresent()),
                () -> assertEquals(acuerdoOp.get().getNombre(), acuerdoTest.getNombre())
        );
    }

    @Test
    void update() throws SQLException {
        acuerdoTest.setNombre("AcuerdoTestUpdate");
        var res = acuerdosRepository.update(acuerdoTest.getId(), acuerdoTest).get();
        Optional<Acuerdo> acuerdoOp = acuerdosRepository.findById(acuerdoTest.getId());
        assertAll(
                () -> assertEquals(res.getNombre(), acuerdoTest.getNombre()),
                () -> assertEquals(res.getLineas().size(), acuerdoTest.getLineas().size()),
                () -> assertTrue(acuerdoOp.isPresent()),
                () -> assertEquals(acuerdoOp.get().getNombre(), acuerdoTest.getNombre()),
                () -> assertEquals(acuerdoOp.get().getLineas().size(), acuerdoTest.getLineas().size())
        );
    }

    @Test
    void delete() throws SQLException {
        // Borramos el paisTest
        var res = acuerdosRepository.delete(acuerdoTest.getId()).get();
        // Comprobamos que se ha borrado correctamente
        Optional<Acuerdo> acuerdoOp = acuerdosRepository.findById(acuerdoTest.getId());
        assertAll(
                () -> assertEquals(res.getId(), acuerdoTest.getId()),
                () -> assertEquals(res.getNombre(), acuerdoTest.getNombre()),
                () -> assertEquals(res.getLineas().size(), acuerdoTest.getLineas().size()),
                () -> assertFalse(acuerdoOp.isPresent())
        );
    }

    @Test
    void clearAll() throws SQLException {
        acuerdosRepository.clearAll();
        assertAll(
                () -> assertEquals(acuerdosRepository.findAll().size(), 0)
        );
    }
}