package main.truco.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cards;

    public Deck(){
        this.cards = new ArrayList<>();
        reset();
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public List<Card> deal(int n){

        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < n && !cards.isEmpty(); i++){
            hand.add(cards.remove(0));
        }
        return hand;
    }

    public void reset(){

        cards.clear();
        String[] names = {"1","2","3","4","5","6","7","10","11","12"};

        for (Suit suit : Suit.values()){
            for (String name : names){
                int value = Integer.parseInt(name);
                cards.add(new Card(suit, value, name));
            }
        }
    }

    public List<Card> getCards(){
        return cards;
    }

    public int size(){
        return cards.size();
    }
}
