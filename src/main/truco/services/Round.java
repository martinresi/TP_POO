package main.truco.services;

import main.truco.models.Card;
import main.truco.models.Player;
import java.util.List;

public class Round {
    private List<Player> players;
    private int currentTurn;
    private int roundNumber;

    public Round(List<Player> players, int roundNumber) {
        this.players = players;
        this.currentTurn = 0;      // empieza en el primer jugador
        this.roundNumber = roundNumber;
    }

    public Player getCurrentPlayer() {
        return players.get(currentTurn);
    }

    public Card playCard(int cardIndex) {
        Player player = getCurrentPlayer();
        Card playedCard = player.playCard(cardIndex);
        System.out.println(player.getName() + " played " + playedCard);
        nextTurn();
        return playedCard;
    }

    private void nextTurn() {
        currentTurn = (currentTurn + 1) % players.size();
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public boolean isOver() {
        return players.stream().allMatch(p -> p.getHand().isEmpty());
    }
}