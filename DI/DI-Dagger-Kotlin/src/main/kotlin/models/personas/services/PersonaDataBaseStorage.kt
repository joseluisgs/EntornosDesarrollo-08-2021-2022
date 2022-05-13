package models.personas.services

import models.personas.model.Persona
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PersonaDataBaseStorage
@Inject constructor() : IPersonaStorage {
    private val id = UUID.randomUUID().toString()

    override fun save(item: Persona): Persona {
        println("PersonaDataBaseStorage.save() --> $item")
        return item
    }

    override fun toString() = "PersonaDataBaseStorage(id='$id')"
}
