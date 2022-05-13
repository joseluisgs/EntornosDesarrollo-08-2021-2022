package es.joseluisgs.dam.repositories;

import es.joseluisgs.dam.models.Persona;

import java.util.*;

public class PersonasRepository implements IPersonaRepository {
    // Voy a usar un árbol, en memoria...
    private final Map<Long, Persona> personas = new TreeMap<>();

    @Override
    public List<Persona> findAll() {
        System.out.println("PersonaRepository.findAll");
        return new ArrayList<>(personas.values());
    }

    @Override
    public Optional<Persona> findById(Long id) {
        System.out.println("PersonaRepository.findById");
        return Optional.ofNullable(personas.get(id));
    }

    @Override
    public Optional<Persona> save(Persona entity) {
        System.out.println("PersonaRepository.save");
        if (personas.containsKey(entity.getId())) {
            return Optional.empty();
        } else {
            personas.put(entity.getId(), entity);
            return Optional.of(entity);
        }
    }

    @Override
    public Optional<Persona> update(Persona entity) {
        System.out.println("PersonaRepository.update");
        if (personas.containsKey(entity.getId())) {
            personas.put(entity.getId(), entity);
            return Optional.of(entity);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Persona> update(Long id, Persona entity) {
        System.out.println("PersonaRepository.update with id");
        if (personas.containsKey(id)) {
            personas.put(id, entity);
            return Optional.of(entity);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Persona> delete(Persona entity) {
        System.out.println("PersonaRepository.delete");
        if (personas.containsKey(entity.getId())) {
            personas.remove(entity.getId());
            return Optional.of(entity);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Persona> deleteById(Long id) {
        System.out.println("PersonaRepository.deleteById");
        if (personas.containsKey(id)) {
            Persona persona = personas.remove(id);
            return Optional.of(persona);
        } else {
            return Optional.empty();
        }
    }

    public void deleteAll() {
        System.out.println("PersonaRepository.deleteAll");
        personas.clear();
    }
}
