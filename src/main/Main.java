package main;

import main.truco.controller.JuegoTruco;

import static main.truco.utils.Decoration.mostrarBienvenida;

public class Main {
    public static void main(String[] args) {
        mostrarBienvenida();
        JuegoTruco juego = new JuegoTruco();
        juego.menu();
    }
}