package models.personas.controllers

import models.personas.model.Persona
import models.personas.repositories.IPersonaRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PersonaController
@Inject constructor(
    private val personaRepository: IPersonaRepository
) {
    private val id = UUID.randomUUID().toString()

    fun save(persona: Persona): Persona {
        println("PersonaController.save() --> $persona")
        return personaRepository.save(persona)
    }

    override fun toString() = "PersonaController(personaRepository=$personaRepository, id='$id')"
}
