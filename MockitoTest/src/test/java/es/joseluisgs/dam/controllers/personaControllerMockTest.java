package es.joseluisgs.dam.controllers;

import es.joseluisgs.dam.errors.PersonaException;
import es.joseluisgs.dam.models.Persona;
import es.joseluisgs.dam.repositories.PersonasRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Para que funcione el mockito, hay que añadir la dependencia de MockitoExtension
@ExtendWith(MockitoExtension.class)
public class personaControllerMockTest {
    // No necesitamos la dependencia de Persona Controller...
    // podemos mockearla, es decir, simular su comportamiento.
    // Con esto va mucho mas rapido que con una dependencia real
    // Si fuera una base de datos te ahorras accer a ella
    // Ahora es un test unitario, pero no un test de integracion

    // Mi clase de test
    private final Persona pTest = new Persona(1L, "Chuck Norris", 69);
    @Mock
    PersonasRepository personasRepository;

    // Esto equivale a
    // private PersonasController personaController = new PersonasController(@Mock PersonasRepository personasRepository);
    // pero con la anotación @Mock, ya que la dependencia es una clase mock.
    // Le inyectamos la dependencia al controlador de personas
    // SUT (System Under Test)
    @InjectMocks
    PersonasController personasController;

    /**
     * Para testear vamos a usar la simulación de un escenario, simulando el comprotamiento
     * de nuestra dependencia o mock: when - then
     * Ademas podemos comprobar que se ha llamado a la dependencia con los argumentos que queremos
     * y el número de veces que se ha llamado.
     */

    @Test
    void getAll() {
        when(personasRepository.findAll()).thenReturn(List.of(pTest));

        var res = personasController.getAll();

        assertAll(
                () -> assertEquals(1, res.size()),
                () -> assertEquals(pTest, res.get(0))
        );

        // Comprobamos que se ha llamado a la dependencia con los argumentos que queremos
        // y el número de veces que se ha llamado
        verify(personasRepository, times(1)).findAll();
    }

    @Test
    void getById() throws PersonaException {
        when(personasRepository.findById(pTest.getId())).thenReturn(Optional.of(pTest));

        var res = personasController.getById(pTest.getId());

        assertAll(
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );

        verify(personasRepository, times(1)).findById(pTest.getId());
    }

    @Test
    void getByIdExcepcionNoExiste() {
        when(personasRepository.findById(pTest.getId())).thenReturn(Optional.empty());

        // Si queremos recoger el mensaje de la excepción para ver si es el que queremos
        var res = assertThrows(PersonaException.class, () -> personasController.getById(pTest.getId()));

        assertEquals("Persona no encontrada con id: " + pTest.getId(), res.getMessage());

        verify(personasRepository, times(1)).findById(pTest.getId());
    }

    @Test
    void store() throws PersonaException {
        when(personasRepository.save(pTest)).thenReturn(Optional.of(pTest));

        var res = personasController.store(pTest);

        assertAll(
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );

        verify(personasRepository, times(1)).save(pTest);
    }

    @Test
    void storeExcepcionIdDuplicado() {
        when(personasRepository.save(pTest)).thenReturn(Optional.empty());

        var res = assertThrows(PersonaException.class, () -> personasController.store(pTest));
        assertEquals("No se puede guardar. Persona ya existe con id: " + pTest.getId(), res.getMessage());

        verify(personasRepository, times(1)).save(pTest);
    }

    @Test
    void replace() throws PersonaException {
        when(personasRepository.update(any(Persona.class))).thenReturn(Optional.of(pTest));

        var res = personasController.replace(pTest);

        assertAll(
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );

        verify(personasRepository, times(1)).update(any(Persona.class));
    }

    @Test
    void replaceExceptionNoExiste() {
        when(personasRepository.update(pTest)).thenReturn(Optional.empty());

        var res = assertThrows(PersonaException.class, () -> personasController.replace(pTest));
        assertEquals("No se puede actualizar. Persona no encontrada con id: " + pTest.getId(), res.getMessage());

        verify(personasRepository, times(1)).update(pTest);
    }

    @Test
    void replaceById() throws PersonaException {
        when(personasRepository.update(pTest.getId(), pTest)).thenReturn(Optional.of(pTest));

        var res = personasController.replace(pTest.getId(), pTest);

        assertAll(
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );

        verify(personasRepository, times(1)).update(pTest.getId(), pTest);
    }

    @Test
    void replaceByIdExceptionNoExiste() {
        when(personasRepository.update(pTest.getId(), pTest)).thenReturn(Optional.empty());

        var res = assertThrows(PersonaException.class, () -> personasController.replace(pTest.getId(), pTest));
        assertEquals("No se puede actualizar. Persona no encontrada con id: " + pTest.getId(), res.getMessage());

        verify(personasRepository, times(1)).update(pTest.getId(), pTest);
    }

    @Test
    void remove() throws PersonaException {
        when(personasRepository.delete(pTest)).thenReturn(Optional.of(pTest));

        var res = personasController.remove(pTest);

        assertAll(
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );

        verify(personasRepository, times(1)).delete(pTest);
    }

    @Test
    void removeExceptionNoExiste() {
        when(personasRepository.delete(pTest)).thenReturn(Optional.empty());

        var res = assertThrows(PersonaException.class, () -> personasController.remove(pTest));
        assertEquals("No se puede eliminar. Persona no encontrada con id: " + pTest.getId(), res.getMessage());

        verify(personasRepository, times(1)).delete(pTest);
    }

    @Test
    void removeById() throws PersonaException {
        when(personasRepository.deleteById(pTest.getId())).thenReturn(Optional.of(pTest));

        var res = personasController.remove(pTest.getId());

        assertAll(
                () -> assertEquals(pTest, res),
                () -> assertEquals(pTest.getId(), res.getId()),
                () -> assertEquals(pTest.getNombre(), res.getNombre()),
                () -> assertEquals(pTest.getEdad(), res.getEdad())
        );

        verify(personasRepository, times(1)).deleteById(pTest.getId());
    }

    @Test
    void removeByIdExceptionNoExiste() {
        when(personasRepository.deleteById(pTest.getId())).thenReturn(Optional.empty());

        var res = assertThrows(PersonaException.class, () -> personasController.remove(pTest.getId()));
        assertEquals("No se puede eliminar. Persona no encontrada con id: " + pTest.getId(), res.getMessage());

        verify(personasRepository, times(1)).deleteById(pTest.getId());
    }
}
