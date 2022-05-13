package models.personas.repositories

import models.personas.model.Persona
import models.personas.services.IPersonaStorage
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


/**
 * El repositorio almacena los datos de las personas.
 */
@Singleton
class PersonaRepository
@Inject constructor(
    private val storage: IPersonaStorage
) : IPersonaRepository {
    private val id = UUID.randomUUID().toString()

    override fun save(entity: Persona): Persona {
        println("PersonaRepository.save() -->$entity")
        return storage.save(entity)
    }

    override fun toString() = "PersonaRepository(storage=$storage, id='$id')"

}
