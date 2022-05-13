package es.joseluisgs.dam.controllers;

import es.joseluisgs.dam.exceptions.PaisException;
import es.joseluisgs.dam.models.Pais;
import es.joseluisgs.dam.repositories.paises.PaisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Isolated;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Isolated
class PaisControllerMockTest {
    // La dependencia
    @Mock
    PaisRepository paisRepository;
    // SUT: System Under Test, con dependencias mockeadas
    @InjectMocks
    PaisController paisController;

    // Vamos a tener un pais...
    Pais paisTest = new Pais()
            .nombre("Prueba")
            .codigo("PR")
            .idioma("PruebaIdioma")
            .moneda("PruebaDolar")
            .capital("PruebaCapital")
            .presupuesto(100000);

    @Test
    void createPais() throws SQLException, PaisException {
        // Simulamos el comportamiento del Mockito
        when(paisRepository.findByNombre(paisTest.getNombre())).thenReturn(Optional.empty());
        when(paisRepository.save(paisTest)).thenReturn(Optional.ofNullable(paisTest));

        // Lo insertamos en la base de datos
        var res = paisController.createPais(paisTest);
        assertAll(
                () -> assertEquals(res.getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.getCodigo(), paisTest.getCodigo())
        );

        // Podemos analizar cuantas veces se han llamado las cosas
        verify(paisRepository, times(1))
                .save(paisTest);
        verify(paisRepository, times(1))
                .findByNombre(paisTest.getNombre());
    }

    @Test
    void createPaisException() throws SQLException, PaisException {
        when(paisRepository.findByNombre(paisTest.getNombre())).thenReturn(Optional.of(paisTest));


        assertThrows(PaisException.class, () -> paisController.createPais(paisTest));

        verify(paisRepository, times(1))
                .findByNombre(paisTest.getNombre());
    }

    @Test
    void getPaisByNombre() throws SQLException, PaisException {
        when(paisRepository.findByNombre(paisTest.getNombre())).thenReturn(Optional.of(paisTest));

        var res = paisController.getPaisByNombre(paisTest.getNombre());

        assertAll(
                () -> assertEquals(res.getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.getCodigo(), paisTest.getCodigo()),
                () -> assertEquals(res.getIdioma(), paisTest.getIdioma())
        );

        verify(paisRepository, times(1))
                .findByNombre(paisTest.getNombre());
    }

    @Test
    void getPaisByNombreException() throws SQLException {
        when(paisRepository.findByNombre(paisTest.getNombre())).thenReturn(Optional.empty());

        assertThrows(PaisException.class, () -> paisController.getPaisByNombre(paisTest.getNombre()));

        // O
        var exception = assertThrows(PaisException.class, () -> {
            paisController.getPaisByNombre("PruebaError");
        });
        assertEquals("No existe el pais PruebaError", exception.getMessage());

        verify(paisRepository, times(1))
                .findByNombre(paisTest.getNombre());
    }

    @Test
    void getPaisById() throws SQLException, PaisException {
        when(paisRepository.findById(paisTest.getId())).thenReturn(Optional.of(paisTest));

        var res = paisController.getPaisById(paisTest.getId());

        assertAll(
                () -> assertEquals(res.getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.getCodigo(), paisTest.getCodigo())
        );

        verify(paisRepository, times(1))
                .findById(paisTest.getId());
    }

    @Test
    void getPaisByIdException() throws SQLException {
        when(paisRepository.findById(paisTest.getId())).thenReturn(Optional.empty());

        assertThrows(PaisException.class, () -> paisController.getPaisById(paisTest.getId()));

        verify(paisRepository, times(1))
                .findById(paisTest.getId());
    }

    @Test
    void getAllPaises() throws SQLException {
        when(paisRepository.findAll()).thenReturn(List.of(paisTest));

        var res = paisController.getAllPaises();

        assertAll(
                () -> assertEquals(res.size(), 1),
                () -> assertEquals(res.get(0).getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.get(0).getCodigo(), paisTest.getCodigo())
        );

        verify(paisRepository, times(1))
                .findAll();
    }

    @Test
    void updatePais() throws SQLException, PaisException {
        when(paisRepository.findByNombre(paisTest.getNombre())).thenReturn(Optional.of(paisTest));
        when(paisRepository.update(paisTest.getId(), paisTest)).thenReturn(Optional.ofNullable(paisTest));

        // Lo insertamos en la base de datos
        var res = paisController.updatePais(paisTest.getId(), paisTest);
        assertAll(
                () -> assertEquals(res.getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.getCodigo(), paisTest.getCodigo())
        );

        // Podemos analizar cuantas veces se han llamado las cosas
        verify(paisRepository, times(1))
                .findByNombre(paisTest.getNombre());
        verify(paisRepository, times(1))
                .update(paisTest.getId(), paisTest);

    }

    @Test
    void updatePaisExceptionNoExiste() throws SQLException {
        when(paisRepository.findByNombre(paisTest.getNombre())).thenReturn(Optional.empty());
        when(paisRepository.update(paisTest.getId(), paisTest)).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> paisController.updatePais(paisTest.getId(), paisTest));

        verify(paisRepository, times(1))
                .findByNombre(paisTest.getNombre());
        verify(paisRepository, times(1))
                .update(paisTest.getId(), paisTest);
    }

    @Test
    void updatePaisExceptionExisteOtroConIgualNombre() throws SQLException {
        var paisOtro = new Pais(3, paisTest.getNombre(), paisTest.getCodigo(), paisTest.getIdioma(), paisTest.getMoneda(), paisTest.getCapital(), paisTest.getPresupuesto());
        when(paisRepository.findByNombre(paisTest.getNombre())).thenReturn(Optional.of(paisOtro));
        // when(paisRepository.update(paisTest.getId(), paisTest)).thenThrow(SQLException.class);

        assertThrows(PaisException.class, () -> paisController.updatePais(paisTest.getId(), paisTest));

        var exception = assertThrows(PaisException.class, () -> {
            paisController.updatePais(paisTest.getId(), paisTest);
        });
        assertEquals("Ya existe un pais con el nombre " + paisTest.getNombre(), exception.getMessage());

        verify(paisRepository, times(2))
                .findByNombre(paisTest.getNombre());
    }

    @Test
    void deletePais() throws SQLException, PaisException {
        when(paisRepository.findByNombre(paisTest.getNombre())).thenReturn(Optional.of(paisTest));
        when(paisRepository.delete(paisTest.getId())).thenReturn(Optional.ofNullable(paisTest));

        var res = paisController.deletePais(paisTest.getNombre());

        assertAll(
                () -> assertEquals(res.getNombre(), paisTest.getNombre()),
                () -> assertEquals(res.getCodigo(), paisTest.getCodigo()),
                () -> assertEquals(res.getId(), paisTest.getId())
        );

        verify(paisRepository, times(1))
                .findByNombre(paisTest.getNombre());
        verify(paisRepository, times(1))
                .delete(paisTest.getId());
    }

    @Test
    void deletePaisException() throws SQLException {
        when(paisRepository.findByNombre(paisTest.getNombre())).thenReturn(Optional.empty());
        // when(paisRepository.delete(paisTest.getId())).thenThrow(SQLException.class);

        assertThrows(PaisException.class, () -> paisController.deletePais(paisTest.getNombre()));

        verify(paisRepository, times(1))
                .findByNombre(paisTest.getNombre());

    }

    @Test
    void checkPaisDataNombreException() {
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