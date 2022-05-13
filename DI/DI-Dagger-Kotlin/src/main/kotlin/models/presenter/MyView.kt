package models.presenter

import DI.presenter.DaggerGrafoDependencias
import java.util.*
import javax.inject.Inject


class MyView {
    private val id = UUID.randomUUID().toString()

    @Inject
    lateinit var presenter: Presenter

    // Aquí no hacemos la inyección en el constructor si no más adelante
    // por eso necesitamos el metodo inject
    // Esto es interesante en sitios donde no hay cosntructores
    // Como Android y sus actividades
    init {
        DaggerGrafoDependencias.create().inject(this)
    }
    
    override fun toString(): String {
        return "MyView{" +
                "presenter=" + presenter +
                ", id='" + id + '\'' +
                '}'
    }
}
