package modelo;

import interfaces.Promediable;

public class Estudiante extends Persona implements Promediable {
    private final int matematicas;
    private final int lengua;
    private final int naturales;
    private final int sociales;
    private static final int TOTAL_MATERIAS = 4;

    public Estudiante(String nombre, String apellido, int matematicas, int lengua, int naturales, int sociales) {
        super(nombre, apellido);
        this.matematicas = matematicas;
        this.lengua = lengua;
        this.naturales = naturales;
        this.sociales = sociales;
    }

    public int getMatematicas() { return matematicas; }
    public int getLengua() { return lengua; }
    public int getNaturales() { return naturales; }
    public int getSociales() { return sociales; }

    @Override
    public double calcularPromedio() {
        // Manejo de división por cero (requerimiento)
        if (TOTAL_MATERIAS == 0) return 0;
        return (matematicas + lengua + naturales + sociales) / (double) TOTAL_MATERIAS;
    }
}