package model;

import java.util.Objects;

public abstract class Mamifero implements IMamiferos {

    // atributos
    protected int id;
    protected String nombre;
    protected String raza;
    protected String tipoAnimal;
    protected String fNacimiento;
    protected float peso;

    // constructor
    public Mamifero(String nombre, String raza, String tipoAnimal, String fNacimiento, float peso) {
        this.nombre = nombre;
        this.raza = raza;
        this.tipoAnimal = tipoAnimal;
        this.fNacimiento = fNacimiento;
        this.peso = peso;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // getters y setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }
    public void setRaza(String raza) { this.raza = raza; }
    public String getRaza() { return raza; }
    public void setTipoAnimal(String tipoAnimal) { this.tipoAnimal = tipoAnimal; }
    public String getTipoAnimal() { return tipoAnimal; }
    public void setfNacimiento(String fNacimiento) { this.fNacimiento = fNacimiento; }
    public String getfNacimiento() { return fNacimiento; }
    public void setPeso(float peso) { this.peso = peso; }
    public float getPeso() { return peso; }

    // métodos abstractos
    public abstract void comer();

    // retorna el tipo del animal (por ejemplo "Perro", "Gato")
    public abstract String tipoAnimal();

    @Override
    public abstract void comunicarse();

    @Override
    public String toString() {
        return "Id: " + id + ", Nombre: " + Objects.toString(nombre, "-") +
                ", Raza: " + Objects.toString(raza, "-") +
                ", Tipo Animal: " + Objects.toString(tipoAnimal, "-") +
                ", Fecha Nac.: " + Objects.toString(fNacimiento, "-") +
                ", Peso: " + peso;
    }
}
