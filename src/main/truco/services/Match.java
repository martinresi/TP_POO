package main.truco.services;


import main.truco.models.Deck;
import main.truco.models.Player;
import java.util.ArrayList;
import java.util.List;

public class Match {

    private List<Player> players;
    private Deck deck;
    private int pointsToWin;

    public Match(int pointsToWin) {
        this.players = new ArrayList<>();
        this.deck = new Deck();
        this.pointsToWin = pointsToWin;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void start() {
        System.out.println("RONDA COMIENZA " + pointsToWin + " PUNTOS PARA EL GANADOR!");
        nextRound();
    }

    public void nextRound() {
        deck.reset();
        deck.shuffle();

        for (Player player : players) {
            player.newHand(deck.deal(3));
        }
        System.out.println("NUEVA RONDA");
    }

    public boolean hasWinner() {
        return players.stream().anyMatch(p -> p.getScore() >= pointsToWin);
    }

    public Player getWinner() {
        return players.stream()
                .filter(p -> p.getScore() >= pointsToWin)
                .findFirst()
                .orElse(null);
    }

    public List<Player> getPlayers() {
        return players;
    }

}
