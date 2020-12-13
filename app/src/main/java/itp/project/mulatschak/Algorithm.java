package itp.project.mulatschak;

import java.util.List;
import java.util.Random;

public class Algorithm {

    private final List<Card> cards;
    private List<Card> holdingCards;
    private int winChance;

    public Algorithm(List<Card> cards, List<Card> holdingCards) {
        this.cards = cards;
        this.holdingCards = holdingCards;
    }

    public Card getResponseCard(Card inputCard) {
        this.setValues();
        this.setHoldingValues();
        this.setWinChance();

        boolean winMove = new Random().nextInt(101) < winChance;
        if (!winMove) {
            return holdingCards.get(new Random().nextInt(holdingCards.size()));
        }
        Card lowestCard = lowestCardValue(inputCard.getTempValue());
        if (lowestCard != null) {
            return lowestCard;
        } else {
            return lowestCardValue();
        }
    }

    private Card lowestCardValue() {
        return lowestCardValue(-1);
    }

    private Card lowestCardValue(int moreThan) {
        Card lowestValue = null;
        for (Card card : holdingCards) {
            if (lowestValue == null) lowestValue = card;
            if (card.getTempValue() < lowestValue.getTempValue()) {
                if (card.getTempValue() > moreThan) {
                    lowestValue = card;
                }
            }
        }
        assert lowestValue != null;
        if (lowestValue.getTempValue() < moreThan) {
            return null;
        }
        return lowestValue;
    }

    private void setValues() {
        for (Card card : cards) {
            if (card.getColor() == Playground.getAdout()) {
                card.setTempValue(card.getValue() + 10);
            } else {
                card.setTempValue(card.getValue());
            }
        }
    }

    private void setHoldingValues() {
        for (Card holdCard : holdingCards) {
            for (Card card : cards) {
                if (card.getValue() == holdCard.getValue() && card.getColor() == holdCard.getColor()) {
                    holdCard.setTempValue(card.getTempValue());
                }
            }
        }
    }

    private void setWinChance() {
        switch (Playground.getDifficulty()) {
            case EASY:
                winChance = 25;
                break;
            case MEDIUM:
                winChance = 50;
                break;
            case HARD:
                winChance = 85;
                break;
            case UNBEATABLE:
                winChance = 100;
                break;
        }
    }

    public void setHoldingCards(List<Card> holdingCards) {
        this.holdingCards = holdingCards;
    }

    /**
     * Aus den Handkarten wird zuerst die größte Gesamtsumme der Farben ermittelt. Danach wird berechnet in welcher
     * {@link Colors} mehr high-valued {@link Card} liegen. Aus dieser ergibt sich die Atoutfarbe. Bei Farben mit
     * derselben Gesamtsumme, wird die Anzahl der hohen Karten ermittelt.
     *
     * @param playersCardsAtout Spielerhand des Spielers mit gewonnener Stichansage
     * @return Atoutfarbe
     */
    private Colors getAtoutFromPlayers(List<Card> playersCardsAtout) {
        int anzahlHerz = 0;
        int anzahlSchelle = 0;
        int anzahlBlatt = 0;
        int anzahlEichel = 0;
        Colors atout = null;
        for (Card cards : playersCardsAtout) {
            switch (cards.getColor()) {
                case HERZ:
                    anzahlHerz += cards.getValue();
                    break;
                case SCHELLE:
                    anzahlSchelle += cards.getValue();
                    break;
                case BLATT:
                    anzahlBlatt += cards.getValue();
                    break;
                case EICHEL:
                    anzahlEichel += cards.getValue();
                    break;
            }
        }
        int highestValue = Math.max(anzahlHerz, Math.max(anzahlSchelle, Math.max(anzahlBlatt, anzahlEichel)));
        if (highestValue == anzahlHerz) atout = Colors.HERZ;
        if (highestValue == anzahlSchelle) atout = Colors.SCHELLE;
        if (highestValue == anzahlBlatt) atout = Colors.BLATT;
        if (highestValue == anzahlEichel) atout = Colors.EICHEL;
        return atout;
    }
}
