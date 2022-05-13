package model.vehiculos;

import javax.inject.Inject;

/**
 * Clase coche
 * Tiene dos dependencias, Engine y Brand
 * Es nuestro consumidor de dependencias
 */
public class Car {

    private Engine engine;
    private Brand brand;

    // De esta manera indicamos que las dependencias serán inyectadas mediante el constructor
    @Inject
    public Car(Engine engine, Brand brand) {
        this.engine = engine;
        this.brand = brand;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", brand=" + brand +
                '}';
    }

    // getters and setters

}