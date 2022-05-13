package es.joseluisgs.dam.SI2;

public class DBRepository implements IRepository {
    @Override
    public void save(String data) {
        System.out.println("Repository Saving data in DataBase: " + data);
    }
}
