package model;

public class gato extends Mamifero {
    private  double hSalto; // atributo de la clase gato

    // constructor de la clase gato
    public gato(String nombre, String raza, String tipoAnimal, String fNacimiento, float peso,
                double hSalto) {
        super(nombre, raza, tipoAnimal, fNacimiento, peso);
        this.hSalto = hSalto;
    }

    @Override
    public void comunicarse() { // sobrescribimos el metodo comunicarse de la clase mamifero
        System.out.println("miau miau");
    }

    @Override
    public void comer(){ // sobrescribimos el metodo comer de la clase mamifero
        System.out.println("El gato esta comiendo");
    }

    @Override
    public String tipoAnimal() { return tipoAnimal; }

    // get y set de la clase gato
    public double gethSalto() { return hSalto; }
    public void sethSalto(double hSalto) { this.hSalto = hSalto; }

    @Override
    public String toString() {
        return super.toString() + ", Altura salto: " + hSalto;
    }
}
