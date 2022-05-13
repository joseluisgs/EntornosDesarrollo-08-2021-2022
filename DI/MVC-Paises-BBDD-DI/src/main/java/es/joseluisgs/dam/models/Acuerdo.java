package es.joseluisgs.dam.models;

import es.joseluisgs.dam.utils.Formatter;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Acuerdo {
    private final LocalDateTime fecha;
    private String nombre;
    private double aportacion;
    private List<LineaAcuerdo> lineas;
    private int id;


    public Acuerdo(int id, String nombre, LocalDateTime fecha, List<LineaAcuerdo> lineas) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.lineas = lineas;
        this.aportacion = calcularAportacion();
    }

    public Acuerdo(int id, String nombre, LocalDateTime fecha, double aportacion, List<LineaAcuerdo> lineas) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.lineas = lineas;
        this.aportacion = aportacion;
    }

    private double calcularAportacion() {
        double total = 0;
        for (LineaAcuerdo linea : lineas) {
            total += linea.getSubvencion();
        }
        return total;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        // Puedes imprimir listas como Arrays.toString(lista))
        return "Acuerdo{" + "id=" + id + ", nombre=" + nombre + ", fecha="
                + Formatter.dateParser(fecha) + ", lineas=" + lineas + ", aportacion=" + Formatter.moneyParser(aportacion) + '}';
    }

}
