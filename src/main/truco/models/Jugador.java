package main.truco.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Jugador {
    protected final String nombre;
    protected final List<Carta> mano = new ArrayList<>();
    protected int puntaje = 0;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() { return nombre; }
    public List<Carta> getMano() { return mano; }
    public int getPuntaje() { return puntaje; }
    public void sumarPuntos(int puntos) { this.puntaje += puntos; }

    public void recibirCarta(Carta c) { if (c != null) mano.add(c); }
    public void limpiarMano() { mano.clear(); }

    public void mostrarMano() {
        System.out.println(nombre + " - Mano:");
        for (int i = 0; i < mano.size(); i++) {
            System.out.println("  " + i + ": " + mano.get(i));
        }
    }

    public int calcularEnvido() {
        int mejor = 0;
        for (int i = 0; i < mano.size(); i++) {
            for (int j = i + 1; j < mano.size(); j++) {
                Carta a = mano.get(i), b = mano.get(j);
                if (a.getPalo() == b.getPalo()) {
                    int va = valorEnvido(a) + valorEnvido(b) + 20;
                    if (va > mejor) mejor = va;
                }
            }
        }
        if (mejor == 0) {
            for (Carta c : mano) {
                int v = valorEnvido(c);
                if (v > mejor) mejor = v;
            }
        }
        return mejor;
    }

    private int valorEnvido(Carta c) {
        return (c.getNumero() >= 10) ? 0 : c.getNumero();
    }

    public abstract Carta jugarCarta(Scanner sc);
    public abstract String decidirCantarEnvido(Scanner sc);
    public abstract String responderEnvido(Scanner sc, int stake, int cantoValor);
    public abstract String decidirCantarTruco(Scanner sc, int valorTrucoActual);
    public abstract String responderTruco(Scanner sc, int valorTrucoActual, String canto);
}