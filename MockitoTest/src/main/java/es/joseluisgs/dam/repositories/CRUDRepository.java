package es.joseluisgs.dam.repositories;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T, ID> {
    // Añade a todo optional, para que veas otro tipo de implementación...

    List<T> findAll();

    Optional<T> findById(ID id);

    Optional<T> save(T entity);

    Optional<T> update(T entity);

    Optional<T> update(ID id, T entity);

    Optional<T> delete(T entity);

    Optional<T> deleteById(ID id);

}
