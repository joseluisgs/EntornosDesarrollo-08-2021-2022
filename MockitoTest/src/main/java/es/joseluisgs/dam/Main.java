package es.joseluisgs.dam;

import es.joseluisgs.dam.controllers.PersonasController;
import es.joseluisgs.dam.errors.PersonaException;
import es.joseluisgs.dam.models.Persona;
import es.joseluisgs.dam.repositories.PersonasRepository;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello Mockito!");

        // Creo el controlador con sus dependencias inyectadas: Clase concreta.
        PersonasController personasController = new PersonasController(new PersonasRepository());

        // Vamos a insertar algunas personas.
        Persona p1 = new Persona(1L, "Goku", 33);
        Persona p2 = new Persona(2L, "Vegeta", 40);
        Persona p3 = new Persona(3L, "Piccolo", 34);
        Persona p4 = new Persona(4L, "Gohan", 23);
        Persona p5 = new Persona(4L, "Error", 23);

        // Inserto las personas.
        System.out.println("Insertando personas...");
        try {
            personasController.store(p1);
            personasController.store(p2);
            personasController.store(p3);
            personasController.store(p4);
            personasController.store(p5);
        } catch (PersonaException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Personas insertadas.");
        System.out.println();

        // Obteniendo todas las personas.
        System.out.println("Obteniendo todas las personas...");
        var personas = personasController.getAll();
        System.out.println(personas);
        System.out.println("Personas obtenidas.");
        /*for (var persona : personas) {
            System.out.println(persona);
        }*/
        System.out.println();

        // Obteniendo una persona por su id.
        System.out.println("Obteniendo una persona por su id...");
        try {
            var persona = personasController.getById(p1.getId());
            System.out.println(persona);
            persona = personasController.getById(99L);
        } catch (PersonaException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Persona obtenida.");
        System.out.println();

        // Actualizando una persona.
        System.out.println("Actualizando una persona...");
        p3.setNombre("Goten");
        p3.setEdad(13);
        try {
            // Una opción es pasar el objeto completo.
            personasController.replace(p3);
            // Otra opción es pasar el id y el objeto completo.
            var persona = personasController.replace(p3.getId(), p3);
            System.out.println(persona);
            personasController.replace(99L, p3);
        } catch (PersonaException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Persona actualizada.");
        System.out.println();

        // Borrando una persona.
        System.out.println("Borrando una persona...");
        try {
            // Pasando el objeto completo.
            personasController.remove(p3);
            // Pasando el id.
            var persona = personasController.remove(p3.getId());
            System.out.println(persona);
            persona = personasController.remove(99L);

        } catch (PersonaException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Persona borrada.");
        System.out.println();

        // Obteniendo todas las personas.
        System.out.println("Obteniendo todas las personas...");
        personas = personasController.getAll();
        for (Persona persona : personas) {
            System.out.println(persona);
        }
        System.out.println("Personas obtenidas.");


    }
}
