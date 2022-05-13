package es.joseluisgs.dam.SI2;

public class FileRepository implements IRepository {
    @Override
    public void save(String data) {
        System.out.println("Repository Saving data FileStorage: " + data);
    }
}
