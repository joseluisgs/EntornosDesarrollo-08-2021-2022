package es.joseluisgs.dam.models;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class Backup {
    private final String createdAt = LocalDateTime.now().toString();
    private List<Pais> paises;
    private List<Acuerdo> acuerdos;

    public List<Pais> getPaises() {
        return paises;
    }

    public List<Acuerdo> getAcuerdos() {
        return acuerdos;
    }
}
