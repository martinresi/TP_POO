package main;

import main.truco.controller.TrucoGame;

public class Main {

    public static void main(String[] args) {
        TrucoGame game = new TrucoGame();
        game.startGame();
        game.showStatus();
    }
}