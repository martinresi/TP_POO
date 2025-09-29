package main.truco.models;

import main.truco.services.Hand;
import java.util.List;

public class Player {
    private String name;
    private int score;
    private Hand hand;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addPoints(int points) {
        this.score += points;
    }

    public Hand getHand() {
        return hand;
    }

    public void newHand(List<Card> cards) {
        this.hand = new Hand(cards);
    }

    public void addCard(Card card) {
        hand.addCard(card);
    }

    public void addScore(int points) {
        this.score += points;
    }

    public Card playCard(int index) {
        return hand.playCard(index);
    }

    public boolean hasCards() {
        return !hand.isEmpty();
    }

    @Override
    public String toString() {
        return "Player: " + name + " | Score: " + score + " | Hand: " + hand;
    }
}
