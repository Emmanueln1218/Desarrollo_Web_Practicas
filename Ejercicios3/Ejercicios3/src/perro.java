

public class perro extends Mamifero{
    private String LgEntrenamiento; //atributo de la clase perro

    //constructor de la clase perro
    public perro(String nombre, String raza, String fNacimiento, float peso,
        String LgEntrenamiento ) {
        super(nombre, raza, fNacimiento, peso);
        this.LgEntrenamiento = LgEntrenamiento;

    //aqui los metodos de la clase perro
    }
    @Override
    public void comunicarse(){ //sobrescribimos el metodo comunicarse de la clase mamifero
        System.out.println("guau guau guau");
    }
    @Override
    public void comer(){ //sobrescribimos el metodo comer de la clase mamifero
        System.out.println("El perro esta comiendo");
    }
    //aqui los get y set de la clase perro
    public String getLgEntrenamiento() { //get de lugar de entrenamiento
        return LgEntrenamiento;
    }
    public void setLgEntrenamiento(String LgEntrenamiento) {
        this.LgEntrenamiento = LgEntrenamiento;  //set de lugar de entrenamiento
    }

}
