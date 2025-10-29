package main.truco.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazo {
    private final List<Carta> cartas = new ArrayList<>();

    public Mazo() {
        for (Palo palo : Palo.values()) {
            for (int n = 1; n <= 12; n++) {
                if (n == 8 || n == 9) continue;
                cartas.add(new Carta(n, palo));
            }
        }
        barajar();
    }

    public void barajar() {
        Collections.shuffle(cartas);
    }

    public Carta robar() {
        if (cartas.isEmpty()) return null;
        return cartas.remove(0);
    }
}