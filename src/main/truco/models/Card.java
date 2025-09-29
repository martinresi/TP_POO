package main.truco.models;


public class Card {

    private Suit suit;
    private int value;
    private String name;

    public Card(Suit suit, int value, String name){
        this.suit = suit;
        this.value = value;
        this.name = name;
    }

    public int envidoValue(){
        if (value >= 1 && value <= 7) {
            return value;
        } else {
            return 0;
        }
    }

    public Suit getSuit(){
        return suit;
    }

    public int getValue(){
        return value;
    }

    public String toString(){
        return name + " de " + suit;
    }
}
