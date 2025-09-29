package main.truco.services;

import main.truco.models.Card;
import main.truco.models.Player;
import main.truco.models.Suit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameDealer {
    private List<Card> deck;

    public GameDealer() {
        initializeDeck();
    }

    private void initializeDeck() {
        deck = new ArrayList<>();


        for (Suit suit : Suit.values()) {
            for (int value = 1; value <= 7; value++) {
                String name = getCardName(value);
                deck.add(new Card(suit, value, name));
            }

            deck.add(new Card(suit, 10, "Sota"));
            deck.add(new Card(suit, 11, "Caballo"));
            deck.add(new Card(suit, 12, "Rey"));
        }
    }

    private String getCardName(int value) {
        switch (value) {
            case 1: return "Uno";
            case 2: return "Dos";
            case 3: return "Tres";
            case 4: return "Cuatro";
            case 5: return "Cinco";
            case 6: return "Seis";
            case 7: return "Siete";
            default: return String.valueOf(value);
        }
    }

    public void shuffleAndDeal(List<Player> players) {

        Collections.shuffle(deck);
        for (Player player : players) {
            player.getHand().getCards().clear();
        }
        int cardIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (Player player : players) {
                if (cardIndex < deck.size()) {
                    player.getHand().getCards().add(deck.get(cardIndex));
                    cardIndex++;
                }
            }
        }
    }

    public int calculateEnvido(Player player) {
        List<Card> cards = player.getHand().getCards();
        int maxEnvido = 0;

        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                Card card1 = cards.get(i);
                Card card2 = cards.get(j);

                if (card1.getSuit() == card2.getSuit()) {
                    int envido = card1.envidoValue() + card2.envidoValue() + 20;
                    maxEnvido = Math.max(maxEnvido, envido);
                } else {
                    int envido = Math.max(card1.envidoValue(), card2.envidoValue());
                    maxEnvido = Math.max(maxEnvido, envido);
                }
            }
        }

        if (maxEnvido == 0) {
            for (Card card : cards) {
                maxEnvido = Math.max(maxEnvido, card.envidoValue());
            }
        }

        return maxEnvido;
    }

    public Player getEnvidoWinner(List<Player> players) {
        Player winner = null;
        int maxEnvido = -1;

        for (Player player : players) {
            int playerEnvido = calculateEnvido(player);
            if (playerEnvido > maxEnvido) {
                maxEnvido = playerEnvido;
                winner = player;
            }
        }

        return winner;
    }
}