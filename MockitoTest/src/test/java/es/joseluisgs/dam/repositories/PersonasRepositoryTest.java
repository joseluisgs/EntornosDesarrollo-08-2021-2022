package es.joseluisgs.dam.repositories;

import es.joseluisgs.dam.models.Persona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonasRepositoryTest {
    // System Under Test
    private final PersonasRepository personasRepository = new PersonasRepository();

    // Me creo una persona para probar
    private final Persona pTest = new Persona(1L, "Chuck Norris", 69);

    @BeforeEach
    void setUp() {
        // Elimino los datos que haya para empezar desde cero y no tener nada contaminado
        personasRepository.deleteAll();
    }

    @Test
    void save() {
        // Guardo la persona
        var resOpt = personasRepository.save(pTest);
        var res = resOpt.get();

        // Compruebo que se ha guardado
        assertAll(
                () -> assertTrue(resOpt.isPresent()),
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );
    }

    @Test
    void saveExisteId() {
        // inserto
        personasRepository.save(pTest);
        // intento insertar otra vez
        var res = personasRepository.save(pTest);

        assertFalse(res.isPresent());
    }

    @Test
    void findAll() {
        var resVacia = personasRepository.findAll();
        // inserto una persona
        personasRepository.save(pTest);
        var resConDatos = personasRepository.findAll();
        assertAll(
                () -> assertEquals(0, resVacia.size()),
                () -> assertEquals(1, resConDatos.size()),
                () -> assertEquals(pTest, resConDatos.get(0))
        );
    }

    @Test
    void findById() {
        // inserto una persona
        personasRepository.save(pTest);
        var resOpt = personasRepository.findById(pTest.getId());
        var res = resOpt.get();
        assertAll(
                () -> assertTrue(resOpt.isPresent()),
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );
    }

    @Test
    void findByIdNoExiste() {
        // Como no insertado no hay nada
        var res = personasRepository.findById(pTest.getId());

        assertFalse(res.isPresent());
    }

    @Test
    void update() {
        // inserto
        personasRepository.save(pTest);
        // actualizo
        pTest.setNombre("Chuck Prueba");
        pTest.setEdad(96);
        var resOpt = personasRepository.update(pTest);
        var res = resOpt.get();
        assertAll(
                () -> assertTrue(resOpt.isPresent()),
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );
    }

    @Test
    void updateNoExiste() {
        var res = personasRepository.update(pTest);

        assertTrue(res.isEmpty());
    }

    @Test
    void updateById() {
        // inserto
        personasRepository.save(pTest);
        // actualizo
        pTest.setNombre("Chuck Prueba");
        pTest.setEdad(96);
        var resOpt = personasRepository.update(pTest.getId(), pTest);
        var res = resOpt.get();

        assertAll(
                () -> assertTrue(resOpt.isPresent()),
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );
    }

    @Test
    void updateByIdNoExiste() {
        var res = personasRepository.update(pTest.getId(), pTest);

        assertTrue(res.isEmpty());
    }

    @Test
    void delete() {
        // inserto
        personasRepository.save(pTest);
        // borro
        var resOpt = personasRepository.delete(pTest);
        var res = resOpt.get();

        assertAll(
                () -> assertTrue(resOpt.isPresent()),
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );
    }

    @Test
    void deleteNoExiste() {
        var res = personasRepository.delete(pTest);

        assertTrue(res.isEmpty());
    }

    @Test
    void deleteById() {
        // inserto
        personasRepository.save(pTest);
        // borro
        var resOpt = personasRepository.deleteById(pTest.getId());
        var res = resOpt.get();
        assertAll(
                () -> assertTrue(resOpt.isPresent()),
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );
    }

    @Test
    void deleteByIdNoExiste() {
        var res = personasRepository.deleteById(99L);

        assertTrue(res.isEmpty());
    }
}