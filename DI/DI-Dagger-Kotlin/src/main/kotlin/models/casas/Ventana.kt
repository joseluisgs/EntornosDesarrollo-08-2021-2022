package models.casas

import java.util.*
import javax.inject.Inject


class Ventana
@Inject constructor() {
    val id: String = UUID.randomUUID().toString()
    private var isOpened: Boolean = false
    fun abrir() {
        println("Abriendo ventana")
        isOpened = true
    }

    fun cerrar() {
        println("Cerrando ventana")
        isOpened = false
    }

    override fun toString(): String {
        return "Ventana(id='$id', isOpened=$isOpened)"
    }
}