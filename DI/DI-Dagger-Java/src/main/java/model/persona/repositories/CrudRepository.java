package model.persona.repositories;

public interface CrudRepository<T, ID> {
    T save(T entity);

    // ... más métodos
}
