package es.joseluisgs.dam.SI;

public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller(new Repository());
        controller.saveData("Dato a salvar");

        // Y si queremos salvar en otro medio???
    }
}
