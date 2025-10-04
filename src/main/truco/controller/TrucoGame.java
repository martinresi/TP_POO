package main.truco.controller;

import java.util.Scanner;
import main.truco.models.Card;
import main.truco.models.Player;
import main.truco.services.GameDealer;
import main.truco.services.Match;
import main.truco.services.Round;
import main.truco.services.TrucoEvaluator;

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
        Player roundWinner = null;

        int handNumber = 1;

        Player player1 = match.getPlayers().get(0);
        Player player2 = match.getPlayers().get(1);

        Player handWinner1 = playHand(player1, player2, handNumber);

        if(!round.isTrucoAccepted()) {
            roundWinner = handWinner1;
            roundWinner.addScore(round.getTrucoPoints());
            roundNumber++;
            showStatus();
            waitForNextRound();
            return;
        }


        Player firstForSecond, secondForSecond;
        if (handWinner1 == null) { 
            firstForSecond = player1; 
            secondForSecond = player2;
        } else {
            firstForSecond = handWinner1; 
            secondForSecond = (handWinner1 == player1) ? player2 : player1;
        }
        Player handWinner2 = playHand(firstForSecond, secondForSecond, ++handNumber);
        if(!round.isTrucoAccepted()) {
            roundWinner = handWinner2;
            roundWinner.addScore(round.getTrucoPoints());
            roundNumber++;
            showStatus();
            waitForNextRound();
            return;
        }

        if (handWinner1 == handWinner2 && handWinner1 != null) {
            roundWinner = handWinner1;
        } else if (handWinner1 == null && handWinner2 != null) {
            roundWinner = handWinner2;
        } else if (handWinner2 == null && handWinner1 != null) {
            roundWinner = handWinner1;
        } else if (handWinner1 != null && handWinner2 != null && handWinner1 != handWinner2) {
            Player firstForThird = handWinner2; 
            Player secondForThird = (handWinner2 == player1) ? player2 : player1;
            Player handWinner3 = playHand(firstForThird, secondForThird, ++handNumber);
            if(!round.isTrucoAccepted()) {
                roundWinner = handWinner3;
                roundWinner.addScore(round.getTrucoPoints());
                roundNumber++;
                showStatus();
                waitForNextRound();
                return;
            }
            if (handWinner3 != null) roundWinner = handWinner3;
            else roundWinner = player1; // siempre el jugador 1 es la mano por defecto
        }
        if(roundWinner != null) {
            roundWinner.addScore(round.getTrucoPoints());
        }
        System.out.println("\n" + roundWinner.getName() + " gana la ronda y suma " + round.getTrucoPoints() + " puntos!");

        roundNumber++;
        showStatus();
        waitForNextRound();



    }

    private void showRoundHeader() {
        System.out.println("\n==================================================");
        System.out.println("INICIANDO RONDA " + roundNumber);
        System.out.println("==================================================");
    }

    private boolean askForEnvido(Player jugador) {
        System.out.println("\nJugador: " + jugador.getName() + " quiere cantar ENVIDO? (s/n): ");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("s") || response.equals("si");
    }

    private void processEnvido(Player jugadorQueCanta) {
    System.out.println("\n" + jugadorQueCanta.getName() + " canta ENVIDO!");


    Player oponente = match.getPlayers().get(0) == jugadorQueCanta 
            ? match.getPlayers().get(1) 
            : match.getPlayers().get(0);

    System.out.println(oponente.getName() + ", ¿aceptás el ENVIDO? (s/n): ");
    String respuesta = scanner.nextLine().trim().toLowerCase();

    if (respuesta.equals("s") || respuesta.equals("si")) {
        System.out.println("\n¡ENVIDO ACEPTADO!\nCalculando puntos...");

        
        for (Player p : match.getPlayers()) {
            int puntosEnvido = dealer.calculateEnvido(p);
            System.out.println(p.getName() + " tiene " + puntosEnvido + " de envido");
        }

        Player ganador = dealer.getEnvidoWinner(match.getPlayers());
        int puntosEnvido = 2; 
        ganador.addScore(puntosEnvido);

        System.out.println(ganador.getName() + " gana el envido y suma " + puntosEnvido + " puntos!");
    } else {

        System.out.println(oponente.getName() + " rechazó el envido.");
        jugadorQueCanta.addScore(1); 
        System.out.println(jugadorQueCanta.getName() + " suma 1 punto por envido no aceptado.");
    }
}

    private boolean askForTruco(Player player) {
        System.out.println("\n"+ player.getName() + " quiere cantar TRUCO? (s/n): ");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("s") || response.equals("si");
    }

    private boolean processTruco(Player player) {
        System.out.println("\n" + player.getName() + " canta TRUCO!");
        Player oponente = match.getPlayers().get(0) == player 
        ? match.getPlayers().get(1) 
        : match.getPlayers().get(0);

        System.out.println(oponente.getName() + ", ¿aceptás el TRUCO? (s/n): ");
        String response = scanner.nextLine().toLowerCase();

        if (response.equals("s") || response.equals("si")) {
            System.out.println("\n¡TRUCO ACEPTADO!\nLa mano vale 3 puntos.");
            round.setTrucoPoints(3);
            return true; 
        } else {
            System.out.println("\nTRUCO RECHAZADO.\n" + player.getName() + " suma 1 punto.");
            round.setTrucoAccepted(false); 
            return false; 
        }
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

    private Player playHand(Player j1, Player j2, int handNumber) {

            boolean trucoCalled = false;
            
            System.out.println("\n--- MANO " + handNumber + " ---");
    
             // Juega el primero
            System.out.print(j1.getHand().getCards());
            System.out.println("\n" + j1.getName() + ", elige carta (0.." + (j1.getHand().getCards().size()-1) + "): ");
            int idx = scanner.nextInt();
            scanner.nextLine(); // <--- descarta el ENTER pendiente

            Card c1 = j1.playCard(idx);
            if(handNumber == 1 && askForEnvido(j1)) {
                round.calledEnvido();
                processEnvido(j1);
            }
            if(!round.isTrucoCalled() && askForTruco(j1)){
                    round.calledTruco();
                    if (!processTruco(j1)){
                        return j1;              
                    }
            }
            
            System.out.println(j1.getName() + " juega: " + c1);

            
            System.out.print(j2.getHand().getCards());
            System.out.println("\n" + j2.getName() + ", elige carta (0.." + (j2.getHand().getCards().size()-1) + "): ");
            idx = scanner.nextInt();
            scanner.nextLine(); 
            Card c2 = j2.playCard(idx);
            if(handNumber == 1 && round.isEnvidoCalled() == false) {
                if (askForEnvido(j2)) {
                    round.calledEnvido();
                    processEnvido(j2);
                }
            }
            if(round.isTrucoCalled() == false && askForTruco(j2)) {
                    round.calledTruco();
                    if(!processTruco(j2)){
                        return j2;              
                    }
            }
            System.out.println(j2.getName() + " juega: " + c2);

            TrucoEvaluator evaluator = new TrucoEvaluator();
            int v1 = evaluator.getTrucoValue(c1);
            int v2 = evaluator.getTrucoValue(c2);

            if (v1 > v2) {
                System.out.println(j1.getName() + " gana la mano.");
                return j1;
            } else if (v2 > v1) {
                System.out.println(j2.getName() + " gana la mano.");
                return j2;
            } else {
                System.out.println("La mano es un empate.");
                return null; 
            }
}
   
}