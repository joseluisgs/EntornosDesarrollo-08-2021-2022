package es.joseluisgs.dam.controllers;

import es.joseluisgs.dam.errors.PersonaException;
import es.joseluisgs.dam.models.Persona;
import es.joseluisgs.dam.repositories.IPersonaRepository;

import java.util.List;

public class PersonasController {
    // Declaro la dependencia con la Interfaz IPersonaRepository
    // De esta manera si cambia una implementación a base de datos a mi no me afecta. Contrato!!!
    IPersonaRepository personaRepository;

    // Constructor con dependencia
    public PersonasController(IPersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // CRUD con controlador
    public List<Persona> getAll() {
        System.out.println("PersonaController.getAll()");
        return personaRepository.findAll();
    }

    public Persona getById(Long id) throws PersonaException {
        System.out.println("PersonaController.getById()");
        return personaRepository.findById(id)
                .orElseThrow(() -> new PersonaException("Persona no encontrada con id: " + id));
    }

    public Persona store(Persona persona) throws PersonaException {
        System.out.println("PersonaController.create()");
        return personaRepository.save(persona)
                .orElseThrow(() -> new PersonaException("No se puede guardar. Persona ya existe con id: " + persona.getId()));
    }

    public Persona replace(Persona persona) throws PersonaException {
        System.out.println("PersonaController.replace()");
        return personaRepository.update(persona)
                .orElseThrow(() -> new PersonaException("No se puede actualizar. Persona no encontrada con id: " + persona.getId()));
    }

    public Persona replace(Long id, Persona persona) throws PersonaException {
        return personaRepository.update(id, persona)
                .orElseThrow(() -> new PersonaException("No se puede actualizar. Persona no encontrada con id: " + id));
    }


    public Persona remove(Long id) throws PersonaException {
        System.out.println("PersonaController.remove()");
        return personaRepository.deleteById(id)
                .orElseThrow(() -> new PersonaException("No se puede eliminar. Persona no encontrada con id: " + id));
    }

    public Persona remove(Persona persona) throws PersonaException {
        System.out.println("PersonaController.remove()");
        return personaRepository.delete(persona)
                .orElseThrow(() -> new PersonaException("No se puede eliminar. Persona no encontrada con id: " + persona.getId()));
    }
}

