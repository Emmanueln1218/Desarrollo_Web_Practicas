public class gato extends Mamifero {
    private  double hSalto; //atributo de la clase gato

    //constructor de la clase gato

    public gato(String nombre, String raza, String fNacimiento, float peso,
                double hSalto) {
        super(nombre, raza, fNacimiento, peso);
        this.hSalto = hSalto;

    //aqui los metodos de la clase gato
    }
    @Override
    public void comunicarse() { //sobrescribimos el metodo comunicarse de la clase mamifero
        System.out.println("Miau miau miau");
    }
    @Override
    public void comer(){ //sobrescribimos el metodo comer de la clase mamifero
        System.out.println("El gato esta comiendo");
    }
     //aqui los get y set de la clase gato
    public double gethSalto() { 
        return hSalto; //get de altura de salto
    }
    public void sethSalto(double hSalto) {
        this.hSalto = hSalto; //set de altura de salto
    }
    
}
