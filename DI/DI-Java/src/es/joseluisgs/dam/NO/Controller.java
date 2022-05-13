package es.joseluisgs.dam.NO;

public class Controller {
    private Repository repository = new Repository();

    public void saveData(String data) {
        System.out.println("Controller Saving data: " + data);
        repository.save(data);
    }
}
