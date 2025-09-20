package main.truco.controller;

import main.truco.models.Card;
import main.truco.models.Player;
import main.truco.services.Match;
import main.truco.services.Round;

public class TrucoGame {
    private Match match;
    private Round round;

    public void startGame() {

        match = new Match(15); // la hacemos a 15 puntos

        Player p1 = new Player("JUGADOR1");
        Player p2 = new Player("JUGADOR2");

        match.addPlayer(p1);
        match.addPlayer(p2);
        match.start();

        round = new Round(match.getPlayers(), 1);
    }

    public void playTurn(int cardIndex) {
        Player current = round.getCurrentPlayer();
        Card played = round.playCard(cardIndex);
        System.out.println(current.getName() + " played: " + played);
    }

    public void showStatus() {

        System.out.println("=== ESTADO DE LA PARTIDA ===");
        for (Player player : match.getPlayers()) {
            System.out.println(player.getName() + " | PUNTOS: " + player.getScore());
            System.out.println("MANO N°: " + player.getHand().size());
            System.out.println("CARTAS: " + player.getHand().getCards());
        }

        System.out.println("RONDA N°: " + round.getRoundNumber());

        if (round.isOver()) {
            System.out.println("RONDA PERDIDA!");
        } else {
            System.out.println("RONDA EN PROCESO");
        }

        if (match.hasWinner()) {
            System.out.println("GANADOR: " + match.getWinner().getName());
        } else {
            System.out.println("SIN GANADOR AUN");
        }
    }
}
