package model.persona.controllers;

import model.persona.model.Persona;
import model.persona.repositories.IPersonaRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class PersonaController {
    private final IPersonaRepository personaRepository;
    private final String id = UUID.randomUUID().toString();

    @Inject
    public PersonaController(IPersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public Persona save(Persona persona) {
        System.out.println("PersonaController.save() --> " + persona);
        return personaRepository.save(persona);
    }

    @Override
    public String toString() {
        return "PersonaController{" +
                "id='" + id + '\'' +
                ", personaRepository=" + personaRepository +
                '}';
    }
}
