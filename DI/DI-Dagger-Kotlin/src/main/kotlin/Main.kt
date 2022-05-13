import DI.cafeteras.DaggerCafeterasComponent
import DI.cars.DaggerCarsComponent
import DI.casas.DaggerCasasComponent
import DI.named.DaggerMyAppComponent
import DI.personas.PersonasControllerFactory
import models.personas.controllers.PersonaController
import models.personas.model.Persona
import models.personas.repositories.PersonaRepository
import models.personas.services.PersonaDataBaseStorage
import models.personas.services.PersonaFileStorage
import models.presenter.MyView


fun main(args: Array<String>) {
    println("Hola Dagger en Kotlin")
    testVehiculos()
    testCasas()
    testCafeteras()
    testMVC()
    testPresenter()
    testNamed()

}

private fun testVehiculos() {
    println("Vehiculos")
    // Obtenemos nuestro componente, que nos crea los objetos
    val carsComponent = DaggerCarsComponent.create()
    // Obtenemos el objeto que queremos
    val car1 = carsComponent.build()
    // Mostramos el objeto
    println(car1)
    car1.engine.start()
    val car2 = carsComponent.build()
    println(car2)
    car2.engine.stop()
    println()
}

private fun testCasas() {
    println("Casas")
    val casasComponent = DaggerCasasComponent.create()
    val casa1 = casasComponent.build()
    println(casa1)
    val casa2 = casasComponent.build()
    println(casa2)
    casa2.entrar()
    casa2.ventilar()
    println()
}

private fun testCafeteras() {
    println("Cafeteras")
    val cafeterasComponent = DaggerCafeterasComponent.create()
    val cafetera1 = cafeterasComponent.build()
    println(cafetera1)
    cafetera1.servir()
    println()
    val cafetera2 = cafeterasComponent.build()
    println(cafetera2)
    cafetera2.servir()
    println()
}

private fun testMVC() {
    /**
     * Como vemos lo importante de las ID es que no acoplamos el código
     * pudiendo modificar sola la capa que necesitemos
     */
    println("Personas: Model->Controller->Repository->Storage(Database|File)")
    val p = Persona(nombre = "Juan", apellido = "Perez", dni = "12345678")
    println(p)
    println()
    println("Sin Dagger")
    var contRepoStorageBD = PersonaController(PersonaRepository(PersonaDataBaseStorage()))
    println(contRepoStorageBD)
    var contRepoStorageFile = PersonaController(PersonaRepository(PersonaFileStorage()))
    println(contRepoStorageFile)
    var resBD = contRepoStorageBD.save(p)
    println("Resultado BD: $resBD")
    var resFile = contRepoStorageFile.save(p)
    println("Resultado File: $resFile")
    println()
    println("Con Dagger")
    contRepoStorageBD = PersonasControllerFactory.withDBStorage()
    println(contRepoStorageBD)
    contRepoStorageFile = PersonasControllerFactory.withFileStorage()
    println(contRepoStorageFile)
    resBD = contRepoStorageBD.save(p)
    println("Resultado BD: $resBD")
    resFile = contRepoStorageFile.save(p)
    println("Resultado File: $resFile")
    println()
}

private fun testPresenter() {
    println("MyView")
    // Cuando lo creemos ya está todo inyectado!!!
    // Esto es útil dependiendo del contexto
    val myView = MyView()
    println(myView)
    println()
}

private fun testNamed() {
    println("Named")
    var myApp = DaggerMyAppComponent.create().build()
    println(myApp)
    println(myApp.myCollaborator())
    println(myApp.apiService())
    println()
    myApp = DaggerMyAppComponent.create().build()
    println(myApp)
    println(myApp.myCollaborator())
    println(myApp.apiService())
    println()
}

