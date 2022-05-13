package es.joseluisgs.dam.controllers;

import es.joseluisgs.dam.exceptions.AcuerdoException;
import es.joseluisgs.dam.models.Acuerdo;
import es.joseluisgs.dam.models.LineaAcuerdo;
import es.joseluisgs.dam.models.Pais;
import es.joseluisgs.dam.repositories.acuerdos.AcuerdoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Isolated;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
// podemos simular como queremos, porque es complicado los valores. Mostrar en clase
// @MockitoSettings(strictness = Strictness.LENIENT)
@Isolated
class AcuerdoControllerMockTest {
    // La dependencia
    @Mock
    AcuerdoRepository acuerdoRepository;
    // SUT: System Under Test, con dependencias mockeadas
    @InjectMocks
    AcuerdoController acuerdoController;

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
                                    .presupuesto(10_0000),
                            LocalDateTime.now().getYear()
                    )
            ));


    @Test
    void createAcuerdo() throws SQLException {
        String nombre = acuerdoTest.getNombre();
        Pais paisTest = acuerdoTest.getLineas().get(0).getPais();

        // en caso que es muy dificil llamar a las cosas que se hacen en el test
        //when(acuerdoRepository.save(acuerdoTest)).thenReturn(acuerdoTest);
        // doReturn(acuerdoTest).when(acuerdoRepository).save(acuerdoTest);
        // Usaremos any!!, que le da iggual lo que sea, que devuleve lo que queramos... es la manera más fácil
        when(acuerdoRepository.save(any(Acuerdo.class))).thenReturn(Optional.ofNullable(acuerdoTest));

        var res = acuerdoController.createAcuerdo(nombre, List.of(paisTest));

        assertAll(
                () -> assertEquals(nombre, res.getNombre()),
                () -> assertEquals(1, res.getLineas().size()),
                () -> assertEquals(paisTest.getNombre(), res.getLineas().get(0).getPais().getNombre())
        );

        verify(acuerdoRepository, times(1))
                .save(any(Acuerdo.class));

    }

    @Test
    void getAcuerdoById() throws SQLException, AcuerdoException {
        when(acuerdoRepository.findById(1)).thenReturn(Optional.of(acuerdoTest));

        var res = acuerdoController.getAcuerdoById(1);

        assertAll(
                () -> assertEquals(acuerdoTest.getNombre(), res.getNombre()),
                () -> assertEquals(1, res.getLineas().size()),
                () -> assertEquals(acuerdoTest.getLineas().get(0).getPais().getNombre(), res.getLineas().get(0).getPais().getNombre())
        );

        verify(acuerdoRepository, times(1))
                .findById(1);
    }

    @Test
    void getAcuerdoByIdExceptionNoExiste() throws SQLException {
        when(acuerdoRepository.findById(anyInt())).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> acuerdoController.getAcuerdoById(2));

        verify(acuerdoRepository, times(1))
                .findById(anyInt());
    }

    @Test
    void getAllAcuerdos() throws SQLException {
        when(acuerdoRepository.findAll()).thenReturn(List.of(acuerdoTest));

        var res = acuerdoController.getAllAcuerdos();

        assertAll(
                () -> assertEquals(1, res.size()),
                () -> assertEquals(acuerdoTest.getNombre(), res.get(0).getNombre()),
                () -> assertEquals(1, res.get(0).getLineas().size()),
                () -> assertEquals(acuerdoTest.getLineas().get(0).getPais().getNombre(), res.get(0).getLineas().get(0).getPais().getNombre())
        );

        verify(acuerdoRepository, times(1))
                .findAll();
    }


    @Test
    void deleteAcuerdo() throws SQLException {
        when(acuerdoRepository.delete(1)).thenReturn(Optional.ofNullable(acuerdoTest));

        var res = acuerdoController.deleteAcuerdo(1);

        assertAll(
                () -> assertEquals(acuerdoTest.getNombre(), res.getNombre()),
                () -> assertEquals(1, res.getLineas().size()),
                () -> assertEquals(acuerdoTest.getLineas().get(0).getPais().getNombre(), res.getLineas().get(0).getPais().getNombre())
        );

        verify(acuerdoRepository, times(1))
                .delete(1);
    }

    @Test
    void deleteAcuerdoExceptionNoExiste() throws SQLException {
        when(acuerdoRepository.delete(anyInt())).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> acuerdoController.deleteAcuerdo(anyInt()));

        verify(acuerdoRepository, times(1))
                .delete(anyInt());
    }

    @Test
    void updateAcuerdo() throws SQLException {
        when(acuerdoRepository.update(acuerdoTest.getId(), acuerdoTest)).thenReturn(Optional.ofNullable(acuerdoTest));

        var res = acuerdoController.updateAcuerdo(acuerdoTest.getId(), acuerdoTest);

        assertAll(
                () -> assertEquals(acuerdoTest.getNombre(), res.getNombre()),
                () -> assertEquals(1, res.getLineas().size()),
                () -> assertEquals(acuerdoTest.getLineas().get(0).getPais().getNombre(), res.getLineas().get(0).getPais().getNombre())
        );

        verify(acuerdoRepository, times(1))
                .update(acuerdoTest.getId(), acuerdoTest);
    }

    @Test
    void updateAcuerdoExceptionNoExiste() throws SQLException {
        when(acuerdoRepository.update(1, acuerdoTest)).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> acuerdoController.updateAcuerdo(1, acuerdoTest));

        verify(acuerdoRepository, times(1))
                .update(1, acuerdoTest);
    }
}