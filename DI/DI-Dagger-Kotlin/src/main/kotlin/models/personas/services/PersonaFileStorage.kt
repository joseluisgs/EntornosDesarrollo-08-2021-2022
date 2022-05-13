package models.personas.services

import models.personas.model.Persona
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PersonaFileStorage
@Inject constructor() : IPersonaStorage {
    private val id = UUID.randomUUID().toString()
    override fun save(item: Persona): Persona {
        println("PersonaFileStorage.save() --> $item")
        return item
    }

    override fun toString() = "PersonaFileStorage(id='$id')"
}
