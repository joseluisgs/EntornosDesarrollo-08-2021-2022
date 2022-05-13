package es.joseluisgs.dam.services.Storage;

public interface IStorage<T> {

    /**
     * Salva los elementos almacenados en item.
     *
     * @param item Elementos a almacenar.
     * @return true si se ha almacenado correctamente, false en caso contrario.
     */
    boolean save(T item);

    /**
     * Le el almacenado y lo devuelve en un item T.
     *
     * @return Elementos almacenados.
     */
    T load();

    /**
     * Devuelve el path de la ubicación del almacenado.
     *
     * @return Path de la ubicación del almacenado.
     */
    String getStoragePath();
}
