package model.persona.services;

public interface Storage<T> {
    T save(T item);
}
