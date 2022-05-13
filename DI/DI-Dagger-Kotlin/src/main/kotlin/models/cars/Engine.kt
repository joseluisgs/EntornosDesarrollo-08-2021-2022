package models.cars

import java.util.*

//@Inject No lo usamos porque lo haremos en el módulo
data class Engine(val id: String = UUID.randomUUID().toString()) {

    fun start() {
        println("Engine started")
    }

    fun stop() {
        println("Engine stopped")
    }
}
