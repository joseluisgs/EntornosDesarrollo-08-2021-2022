package es.joseluisgs.dam.controllers;

import es.joseluisgs.dam.exceptions.AcuerdoException;
import es.joseluisgs.dam.models.Acuerdo;
import es.joseluisgs.dam.models.LineaAcuerdo;
import es.joseluisgs.dam.models.Pais;
import es.joseluisgs.dam.repositories.acuerdos.AcuerdoRepository;
import es.joseluisgs.dam.utilities.DataBase;
import es.joseluisgs.dam.utilities.DataDB;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Isolated;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Isolated
class AcuerdoControllerTest {
    // La dependencia
    AcuerdoRepository acuerdoRepository = AcuerdoRepository.getInstance(DataBaseManager.getInstance());
    // SUT: System Under Test
    AcuerdoController acuerdoController = new AcuerdoController(acuerdoRepository);

    // Vamos a tener un acuerdo
    Acuerdo acuerdoTest = new Acuerdo(0, "AcuerdoTest", LocalDateTime.now(), 1000.00,
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
    void createAcuerdo() throws SQLException {
        String nombre = "AcuerdoCreate";
        Pais paisTest = new Pais()
                .nombre("PruebaCreate")
                .codigo("PR")
                .idioma("PruebaCreateIdioma")
                .moneda("PruebaCreateDolar")
                .capital("PruebaCreateCapital")
                .presupuesto(100000);
        var res = acuerdoController.createAcuerdo(nombre, List.of(paisTest));
        assertAll(
                () -> assertEquals(nombre, res.getNombre()),
                () -> assertEquals(1, res.getLineas().size()),
                () -> assertEquals(paisTest.getNombre(), res.getLineas().get(0).getPais().getNombre())
        );
    }

    @Test
    void getAcuerdoById() throws SQLException, AcuerdoException {
        var res = acuerdoController.getAcuerdoById(acuerdoTest.getId());
        assertAll(
                () -> assertEquals(acuerdoTest.getNombre(), res.getNombre()),
                () -> assertEquals(acuerdoTest.getLineas().size(), res.getLineas().size()),
                () -> assertEquals(acuerdoTest.getLineas().get(0).getAño(), res.getLineas().get(0).getAño())
        );
    }

    @Test
    void getAcuerdoByIdExceptionNoEncontrado() {
        var exception = assertThrows(AcuerdoException.class, () -> {
            acuerdoController.getAcuerdoById(0);
        });
        assertEquals("No existe el acuerdo con id 0", exception.getMessage());

        // o
        assertThrows(AcuerdoException.class, () -> acuerdoController.getAcuerdoById(0));
    }

    @Test
    void getAllAcuerdos() throws SQLException {
        var res = acuerdoController.getAllAcuerdos();
        assertEquals(1, res.size());
    }

    @Test
    void deleteAcuerdo() throws SQLException {
        var res = acuerdoController.deleteAcuerdo(acuerdoTest.getId());
        assertAll(
                () -> assertEquals(acuerdoTest.getId(), res.getId()),
                () -> assertEquals(acuerdoTest.getNombre(), res.getNombre()),
                () -> assertEquals(acuerdoTest.getLineas().size(), res.getLineas().size()),
                () -> assertThrows(AcuerdoException.class, () -> acuerdoController.getAcuerdoById(acuerdoTest.getId()))
        );
    }

    @Test
    void deleteAcuerdoExceptionNoEncontrado() {
        var exception = assertThrows(SQLException.class, () -> {
            acuerdoController.deleteAcuerdo(0);
        });
        assertEquals("Error al borrar acuerdo. Acuerdo con ID 0 no encontrado", exception.getMessage());
    }

    @Test
    void updateAcuerdo() throws SQLException {
        acuerdoTest.setNombre("AcuerdoUpdate");
        var res = acuerdoController.updateAcuerdo(acuerdoTest.getId(), acuerdoTest);
        assertAll(
                () -> assertEquals(acuerdoTest.getId(), res.getId()),
                () -> assertEquals(acuerdoTest.getNombre(), res.getNombre()),
                () -> assertEquals(acuerdoTest.getLineas().size(), res.getLineas().size())
        );
    }

    @Test
    void updateAcuerdoExceptionNoEncontrado() {
        var exception = assertThrows(SQLException.class, () -> {
            acuerdoController.updateAcuerdo(0, acuerdoTest);
        });
        assertEquals("Error al actualizar acuerdo. Acuerdo con ID 0 no encontrado", exception.getMessage());
    }
}