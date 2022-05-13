package es.joseluisgs.dam.SI;

public class Controller {
    private Repository repository;

    // Por constructor, se puede hacer con setter
    public Controller(Repository repository) {
        this.repository = repository;
    }

    public void saveData(String data) {
        System.out.println("Controller Saving data: " + data);
        repository.save(data);
    }
}
