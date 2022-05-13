package models.cars

import javax.inject.Inject


data class Car
@Inject constructor(val engine: Engine, val brand: Brand)