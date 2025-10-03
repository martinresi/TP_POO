package main.truco.services;

import java.util.List;
import main.truco.models.Card;
import main.truco.models.Player;

public class Round {
    private List<Player> players;
    private int currentTurn;
    private int roundNumber;
    private boolean isEnvidoCalled;
    private boolean isTrucoCalled = false;
    private boolean isTrucoAccepted = true;
    int trucoPoints = 1;

    public Round(List<Player> players, int roundNumber) {
        this.players = players;
        this.currentTurn = 0;      // empieza en el primer jugador
        this.roundNumber = roundNumber;
        this.isEnvidoCalled = false;
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

    public void calledEnvido() {
        isEnvidoCalled = true;
    }

    public boolean isEnvidoCalled() {
        return isEnvidoCalled;
    }

    public void calledTruco() {
        isTrucoCalled = true;
    }

    public boolean isTrucoCalled() {
        return isTrucoCalled;
    }

    public void setTrucoPoints(int points) {
        trucoPoints = points;
    }

    public int getTrucoPoints() {
        return trucoPoints;
    }

    public boolean isTrucoAccepted() {
        return isTrucoAccepted;
    }

    public void setTrucoAccepted(boolean accepted) {
        isTrucoAccepted = accepted;
    }



}