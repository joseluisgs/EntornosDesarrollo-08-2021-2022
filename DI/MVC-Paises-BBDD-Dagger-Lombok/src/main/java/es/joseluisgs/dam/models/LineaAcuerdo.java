package es.joseluisgs.dam.models;

import es.joseluisgs.dam.utils.Formatter;
import lombok.Data;

@Data
public class LineaAcuerdo {
    private final Pais pais;
    private final int año;
    private final double subvencion;

    public LineaAcuerdo(Pais pais, int año) {
        this.pais = pais;
        this.año = año;
        this.subvencion = pais.getPresupuesto() * 0.25;
    }

    public LineaAcuerdo(Pais pais, int año, double subvencion) {
        this.pais = pais;
        this.año = año;
        this.subvencion = subvencion;
    }

    @Override
    public String toString() {
        return "LineaAcuerdo{" + "pais=" + pais.getNombre() + ", año=" + año + ", subvencion=" + Formatter.moneyParser(subvencion) + '}';
    }
}
