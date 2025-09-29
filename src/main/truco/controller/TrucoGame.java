package main.truco.controller;

import main.truco.models.Card;
import main.truco.models.Player;
import main.truco.services.GameDealer;
import main.truco.services.Match;
import main.truco.services.Round;
import java.util.Scanner;

public class TrucoGame {
    private Match match;
    private Round round;
    private Scanner scanner;
    private int roundNumber;
    private GameDealer dealer;

    public TrucoGame() {
        this.scanner = new Scanner(System.in);
        this.roundNumber = 1;
        this.dealer = new GameDealer();
    }

    public void startGame() {
        match = new Match(15);
        Player p1 = new Player("JUGADOR_1");
        Player p2 = new Player("JUGADOR_2");
        match.addPlayer(p1);
        match.addPlayer(p2);
        match.start();

        while (!match.hasWinner()) {
            playRound();
        }

        showGameResults();
    }

    private void playRound() {

        showRoundHeader();
        dealer.shuffleAndDeal(match.getPlayers());
        round = new Round(match.getPlayers(), roundNumber);
        showStatus();

        if (askForEnvido()) {
            processEnvido();
        }

        if (match.hasWinner()) {
            return;
        }

        int trucoPoints = askForTruco();
        Player roundWinner = playCards();

        if (roundWinner != null) {
            roundWinner.addScore(trucoPoints);
            System.out.println("\n" + roundWinner.getName() + " gana la ronda y suma " + trucoPoints + " puntos!");
        }

        roundNumber++;
        showStatus();
        waitForNextRound();
    }

    private void showRoundHeader() {
        System.out.println("\n==================================================");
        System.out.println("INICIANDO RONDA " + roundNumber);
        System.out.println("==================================================");
    }

    private boolean askForEnvido() {
        System.out.println("\n¿Alguien quiere cantar ENVIDO? (s/n): ");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("s") || response.equals("si");
    }

    private void processEnvido() {
        System.out.println("\nCALCULANDO ENVIDO");

        for (Player player : match.getPlayers()) {
            int envidoPoints = dealer.calculateEnvido(player);
            System.out.println(player.getName() + " tiene " + envidoPoints + " de envido");
        }

        Player envidoWinner = dealer.getEnvidoWinner(match.getPlayers());
        int envidoValue = 2;

        envidoWinner.addScore(envidoValue);
        System.out.println(envidoWinner.getName() + " gana el envido y suma " + envidoValue + " puntos!");
    }

    private int askForTruco() {
        System.out.println("\n¿Alguien quiere cantar TRUCO? (s/n): ");
        String response = scanner.nextLine().toLowerCase();

        if (response.equals("s") || response.equals("si")) {
            System.out.println("TRUCO CANTADO! Esta ronda vale 2 puntos");
            return 2;
        }
        return 1;
    }

    private Player playCards() {
        System.out.println("\nJUGANDO LAS CARTAS");
        Player winner = match.getPlayers().get((int) (Math.random() * 2));
        System.out.println("(Simulado) " + winner.getName() + " gana las cartas");
        return winner;
    }

    private void waitForNextRound() {
        if (!match.hasWinner()) {
            System.out.println("\nPresiona Enter para la siguiente ronda...");
            scanner.nextLine();
        }
    }

    private void showGameResults() {
        System.out.println("\nJUEGO TERMINADO!");
        System.out.println("GANADOR: " + match.getWinner().getName());
        System.out.println("PUNTUACION FINAL:");
        for (Player player : match.getPlayers()) {
            System.out.println(player.getName() + ": " + player.getScore() + " puntos");
        }
    }

    public void showStatus() {
        System.out.println("\n========================================");
        System.out.println("ESTADO DE LA PARTIDA");
        System.out.println("========================================");

        for (Player player : match.getPlayers()) {
            System.out.println(player.getName());
            System.out.println("   PUNTOS: " + player.getScore() + "/" + match.getTargetScore());

            if (player.getHand() != null && player.getHand().getCards() != null) {
                System.out.println("   CARTAS EN MANO: " + player.getHand().getCards().size());
                System.out.println("   CARTAS: " + player.getHand().getCards());

                if (!player.getHand().getCards().isEmpty()) {
                    System.out.print("   VALORES ENVIDO: ");
                    for (Card card : player.getHand().getCards()) {
                        System.out.print(card.envidoValue() + " ");
                    }
                    System.out.println("(Mejor envido: " + dealer.calculateEnvido(player) + ")");
                }
            } else {
                System.out.println("   CARTAS EN MANO: 0");
            }
            System.out.println();
        }

        System.out.println("RONDA N°: " + (round != null ? round.getRoundNumber() : roundNumber));
        System.out.println("OBJETIVO: " + match.getTargetScore() + " puntos");

        if (round != null && round.isOver()) {
            System.out.println("RONDA TERMINADA");
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