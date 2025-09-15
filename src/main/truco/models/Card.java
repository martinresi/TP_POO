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

    public int trucoValue(){
        return value;
    }

    public int envidoValue(){
        return value > 7 ? 0 : value;
    }

    public Suit getSuit(){
        return suit;
    }

    public void setSuit(Suit suit){
        this.suit = suit;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String toString(){
        return name + " of " + suit;
    }
}
