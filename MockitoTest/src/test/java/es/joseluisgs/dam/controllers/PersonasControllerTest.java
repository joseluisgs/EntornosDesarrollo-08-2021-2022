package es.joseluisgs.dam.controllers;

import es.joseluisgs.dam.errors.PersonaException;
import es.joseluisgs.dam.models.Persona;
import es.joseluisgs.dam.repositories.PersonasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonasControllerTest {
    // Me creo una persona para probar
    private final Persona pTest = new Persona(1L, "Chuck Norris", 69);
    // System Under Test
    // Aquí vamos a probar el controlador de personas
    // Pero le estamos inyectando la dependencia real... Por lo tanto a parte de un etst unitario
    // es un test de integración
    // ¿realmente para probar el controlado necesitamos una dependencia real?
    PersonasRepository personasRepository = new PersonasRepository();
    PersonasController personasController = new PersonasController(personasRepository);

    @BeforeEach
    void setUp() {
        // Elimino los datos que haya para empezar desde cero y no tener nada contaminado
        // aqui si necesito el repositorio real para hacer esto
        personasRepository.deleteAll();
    }

    @Test
    void getAll() {
        var resVacio = personasController.getAll();
        // inserto una persona, como el repositorio funciona... puedo usarlo para probar
        personasRepository.save(pTest);
        var resConDatos = personasController.getAll();

        assertAll(
                () -> assertEquals(0, resVacio.size()),
                () -> assertEquals(1, resConDatos.size()),
                () -> assertEquals(pTest, resConDatos.get(0))
        );
    }

    @Test
    void getById() throws PersonaException {
        // Salvo un dato en la base de datos
        personasRepository.save(pTest);

        var res = personasController.getById(pTest.getId());

        assertAll(
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );
    }

    @Test
    void getByIdExcepcionNoExiste() {
        // Si solo queremos saber si hay excepción
        assertThrows(PersonaException.class, () -> personasController.getById(pTest.getId()));
        // Si queremos recoger el mensaje de la excepción para ver si es el que queremos
        var res = assertThrows(PersonaException.class, () -> personasController.getById(pTest.getId()));

        assertEquals("Persona no encontrada con id: " + pTest.getId(), res.getMessage());
    }

    @Test
    void store() throws PersonaException {
        var res = personasController.store(pTest);
        assertAll(
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );
    }

    @Test
    void storeExcepcionIdDuplicado() {
        // insertamos una persona
        personasRepository.save(pTest);

        // probamos que se lanza una excepción al insertar otro con el mismo id
        // assertThrows(PersonaException.class, () -> personasController.store(pTest));
        // probamos que el mensaje de la excepción es el que queremos
        var res = assertThrows(PersonaException.class, () -> personasController.store(pTest));

        assertEquals("No se puede guardar. Persona ya existe con id: " + pTest.getId(), res.getMessage());
    }

    @Test
    void replace() throws PersonaException {
        // insertamos una persona
        personasRepository.save(pTest);
        // creamos una persona con el mismo id pero con un nombre distinto
        pTest.setNombre("Chuck Prueba");
        pTest.setEdad(96);

        var res = personasController.replace(pTest);

        assertAll(
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );
    }

    @Test
    void replaceExceptionNoExiste() {
        var res = assertThrows(PersonaException.class, () -> personasController.replace(pTest));
        assertEquals("No se puede actualizar. Persona no encontrada con id: " + pTest.getId(), res.getMessage());
    }

    @Test
    void replaceById() throws PersonaException {
        // insertamos una persona
        personasRepository.save(pTest);

        pTest.setNombre("Chuck Prueba");
        pTest.setEdad(96);

        var res = personasController.replace(pTest.getId(), pTest);

        assertAll(
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );
    }

    @Test
    void replaceByIdExceptionNoExiste() {
        var res = assertThrows(PersonaException.class, () -> personasController.replace(pTest.getId(), pTest));
        assertEquals("No se puede actualizar. Persona no encontrada con id: " + pTest.getId(), res.getMessage());
    }

    @Test
    void remove() throws PersonaException {
        // insertamos una persona
        personasRepository.save(pTest);

        var res = personasController.remove(pTest);

        assertAll(
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );
    }

    @Test
    void removeExceptionNoExiste() {
        var res = assertThrows(PersonaException.class, () -> personasController.remove(pTest));
        assertEquals("No se puede eliminar. Persona no encontrada con id: " + pTest.getId(), res.getMessage());
    }

    @Test
    void removeById() throws PersonaException {
        // insertamos una persona
        personasRepository.save(pTest);

        var res = personasController.remove(pTest.getId());

        assertAll(
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );
    }

    @Test
    void removeByIdExceptionNoExiste() {
        var res = assertThrows(PersonaException.class, () -> personasController.remove(pTest.getId()));
        assertEquals("No se puede eliminar. Persona no encontrada con id: " + pTest.getId(), res.getMessage());
    }
}