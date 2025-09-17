package main.truco.models;

import java.util.ArrayList;

public class Player {
    private String name;
    private int score;
    private ArrayList<Card> hand;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<>();
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

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addCard(Card card){
        if (hand.size() < 3) {
            hand.add(card);
        } else {
            throw new IllegalStateException("Hand is full");
        }
    }

    public Card playCard(int index){
        if (index >= 0 && index < hand.size()) {
            return hand.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid card index");
        }
    }

    @Override
    public String toString() {
        return "Player: " + name + " | Score: " + score;
    }


}
