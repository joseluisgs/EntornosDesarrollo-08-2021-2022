package es.joseluisgs.dam.SI2;

public class Controller {
    private IRepository repository;

    // Por constructor, se puede hacer con setter
    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void saveData(String data) {
        System.out.println("Controller Saving data: " + data);
        repository.save(data);
    }
}
