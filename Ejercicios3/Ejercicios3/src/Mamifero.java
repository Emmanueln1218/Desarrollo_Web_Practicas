public class Mamifero {

    //primero los atributos

    protected String nombre;
    protected String raza;
    protected String fNacimiento;
    protected float peso;

//luego el constructor
    public Mamifero(String nombre, String raza, String fNacimiento, float peso) {
        this.nombre = nombre;
        this.raza = raza;
        this.fNacimiento = fNacimiento;
        this.peso = peso;


    
        //luego los metodos get y set
    }
    public void setNombre(String nombre) { //set de nombre
        this.nombre = nombre;

    }
    public String getNombre() { //get de nombre
        return nombre;
    }
    public void setRaza(String raza) { //set de raza
        this.raza = raza;
    }
    public String getRaza() { //get de raza
        return raza;
    }
    public void setfNacimiento(String fNacimiento) { //set de fecha de nacimiento
        this.fNacimiento = fNacimiento;
    }
    public String getfNacimiento() { //get de fecha de nacimiento
        return fNacimiento;
    }
    public void setPeso(float peso) { //set de peso
        this.peso = peso;
    }
    public float getPeso() {  //    get de peso
        return peso;
    }

    //luego los metodos de la clase
    public void comer(){
        System.out.println("Esta Comiendo");  //metodo comer
    }
    public void comunicarse(){
        System.out.println("Esta comunicando"); //metodo comunicarse
    }
    
}




