package model.persona.repositories;

import model.persona.model.Persona;
import model.persona.services.IPersonaStorage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

/**
 * El repositorio almacena los datos de las personas.
 */
@Singleton
public class PersonaRepository implements IPersonaRepository {
    private final IPersonaStorage storage;
    private final String id = UUID.randomUUID().toString();

    @Inject
    public PersonaRepository(IPersonaStorage storage) {
        this.storage = storage;
    }

    @Override
    public Persona save(Persona entity) {
        System.out.println("PersonaRepository.save() -->" + entity);
        return storage.save(entity);
    }

    @Override
    public String toString() {
        return "PersonaRepository{" +
                "id='" + id + '\'' +
                ", storage=" + storage +
                '}';
    }
}
