package models.personas.repositories

import models.personas.model.Persona

interface IPersonaRepository : CrudRepository<Persona, String>
