package main.truco.services;

import java.util.Arrays;
import java.util.List;
import main.truco.models.Card;
import main.truco.models.Player;
import main.truco.models.Suit;

public class TrucoEvaluator {

    public int getTrucoValue(Card card) {

        switch (card.getValue()) {
            case 1:
                if (card.getSuit() == Suit.ESPADA) return 14;
                if (card.getSuit() == Suit.PALO) return 13;
                return 8;
            case 2: return 9;
            case 3: return 10;
            case 4: return 1;
            case 5: return 2;
            case 6: return 3;
            case 7:
                if (card.getSuit() == Suit.ESPADA) return 12;
                if (card.getSuit() == Suit.ORO) return 11;
                return 4;
            case 10: return 5;
            case 11: return 6;
            case 12: return 7;
            default: return 0;
        }
    }

    public Card getBestTrucoCard(Player player) {
        Card bestCard = null;
        int bestValue = -1;

        for (Card card : player.getHand().getCards()) {
            int currentValue = getTrucoValue(card);
            if (currentValue > bestValue) {
                bestValue = currentValue;
                bestCard = card;
            }
        }

        return bestCard;
    }

    public Card getBestTrucoCardInRound(Card card1, Card card2) {
        Card bestCard = null;
        int bestValue = -1;

        for (Card card : Arrays.asList(card1, card2)) {
            int currentValue = getTrucoValue(card);
            if (currentValue > bestValue) {
                bestValue = currentValue;
                bestCard = card;
            }
        }

        return bestCard;
    }

 
}
