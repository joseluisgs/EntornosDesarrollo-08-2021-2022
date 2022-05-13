package models.personas.model

import java.util.*

data class Persona(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val apellido: String,
    val dni: String,
)

