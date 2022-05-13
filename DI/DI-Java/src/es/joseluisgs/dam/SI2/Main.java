package es.joseluisgs.dam.SI2;

public class Main {

    public static void main(String[] args) {
        // Ya no depende de una clase, si no de un comportamiento
        // Es decir, el comportamiento es el que se encarga de ejecutar
        // Y con ello podemos cambiar donde se almacena sin cambiar el controlador
        Controller controller = new Controller(new FileRepository());
        controller.saveData("Dato a salvar");

        controller = new Controller(new DBRepository());
        controller.saveData("Dato a salvar");
    }
}
