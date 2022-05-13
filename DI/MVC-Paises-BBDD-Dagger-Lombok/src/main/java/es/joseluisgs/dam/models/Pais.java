package es.joseluisgs.dam.models;

import es.joseluisgs.dam.utils.Formatter;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo. Clase que representa un país. POJO.
 * Se hace lo más limpia posible para que sirva a numerosos casos. No se implementa excepciones ni comparables
 * Es un POJO de "libro"
 */

@Data
@NoArgsConstructor
@Builder
public class Pais {
    private int id = 0;
    private String nombre;
    private String codigo;
    private String idioma;
    private String moneda;
    private String capital;
    private double presupuesto;


    public Pais(String nombre, String codigo, String idioma, String moneda, String capital) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.idioma = idioma;
        this.moneda = moneda;
        this.capital = capital;
        // Voy a dejar el presupuesto aleatorio
        this.presupuesto = Math.random() * 1000000;
    }

    public Pais(int id, String nombre, String codigo, String idioma, String moneda, String capital, double presupuesto) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.idioma = idioma;
        this.moneda = moneda;
        this.capital = capital;
        this.presupuesto = presupuesto;
    }

    @Override
    public String toString() {
        return "Pais{" + "id=" + id + ", nombre=" + nombre + ", codigo=" + codigo + ", idioma=" + idioma + ", moneda=" + moneda + ", capital=" + capital + ", presupuesto=" + Formatter.moneyParser(presupuesto) + '}';
    }


    public Pais nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public Pais codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public Pais idioma(String idioma) {
        this.idioma = idioma;
        return this;
    }

    public Pais moneda(String moneda) {
        this.moneda = moneda;
        return this;
    }

    public Pais capital(String capital) {
        this.capital = capital;
        return this;
    }

    public Pais presupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
        return this;
    }
    
    public String toString(String separator) {
        return id + separator + nombre + separator + codigo + separator + idioma + separator + moneda + separator + capital + separator + presupuesto;
    }
}

