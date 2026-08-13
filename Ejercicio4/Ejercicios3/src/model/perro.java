package model;

import java.util.Objects;

public class perro extends Mamifero{
    private String LgEntrenamiento; // atributo de la clase perro

    // constructor de la clase perro
    public perro(String nombre, String raza, String tipoAnimal, String fNacimiento, float peso,
                 String LgEntrenamiento ) {
        super(nombre, raza, tipoAnimal, fNacimiento, peso);
        this.LgEntrenamiento = LgEntrenamiento;
    }

    @Override
    public void comunicarse(){ // sobrescribimos el metodo comunicarse de la clase mamifero
        System.out.println("guau guau");
    }

    @Override
    public void comer(){ // sobrescribimos el metodo comer de la clase mamifero
        System.out.println("El perro esta comiendo");
    }

    @Override
    public String tipoAnimal() { return tipoAnimal; }

    // get y set de la clase perro
    public String getLgEntrenamiento() { return LgEntrenamiento; }
    public void setLgEntrenamiento(String LgEntrenamiento) { this.LgEntrenamiento = LgEntrenamiento; }

    @Override
    public String toString() {
        return super.toString() + ", Lugar entrenamiento: " + Objects.toString(LgEntrenamiento, "-");
    }
}
