package service;

import java.util.ArrayList;
import model.Mamifero;

public class MascotaManager {
    private final ArrayList<Mamifero> mascotas = new ArrayList<>();

    public void add(Mamifero m) { mascotas.add(m); }

    public ArrayList<Mamifero> list() { return mascotas; }

    public Mamifero findByName(String name) {
        for (Mamifero m : mascotas) {
            if (m.getNombre().equalsIgnoreCase(name)) return m;
        }
        return null;
    }

    public boolean removeByName(String name) {
        return mascotas.removeIf(m -> m.getNombre().equalsIgnoreCase(name));
    }
}
