package itp.project.mulatschak;

import itp.project.Enums.Colors;
import itp.project.Exceptions.TwoSameHighestTricksException;
import itp.project.Exceptions.WhatTheFuckHowException;

import android.graphics.Color;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Algorithm {

    private static int dealer;
    private static int[] tricks = new int[4];
    private final List<Card> cards;
    private List<Card> holdingCards;
    private static int winChance;
    private int player;
    public static Colors atout;

    public Algorithm(List<Card> cards, List<Card> holdingCards, int player) {
        this.cards = cards;
        this.holdingCards = holdingCards;
        this.player = player;
    }

    public static int getHighestTrickIndex() {
        int largest = Integer.MIN_VALUE;
        int largestIndex = -1;

        for (int i = 0; i < tricks.length; i++) {
            if (tricks[i] > largest) {
                largest = tricks[i];
                largestIndex = i;
            } else if (tricks[i] == largest) {
                if (i == dealer) {
                    largestIndex = i;
                } else {
                    return -1;
                }
            }
        }
        return largestIndex;
    }

    public Card getResponseCard(Card inputCard) {
        this.setValues();
        this.setHoldingValues();
        setWinChance();

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

    private static void setWinChance() {
        switch (Objects.requireNonNull(Playground.getDifficulty())) {
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

    public boolean hasAdoutPermission() throws TwoSameHighestTricksException {
        if (getHighestTrickIndex() == player) {
            return true;
        } else if (getHighestTrickIndex() == -1) {
            throw new TwoSameHighestTricksException();
        }
        return false;
    }

    public void setHoldingCards(List<Card> holdingCards) {
        this.holdingCards = holdingCards;
    }

    /**
     * Es wird geprüft ob der Benutzer den Weli ziehen darf.
     * Dazu wird das Int-Attribut dealer verwendet.
     *
     * @return ob der Spieler den Weli ziehen darf
     */
    public static boolean WeliZiehen() {
        if (dealer < 4) {
            dealer += 1;
        } else {
            dealer = 1;
        }

        boolean gamer = false;
        switch (dealer) {
            case 1:
                //Spieler
                gamer = true;
                break;
            case 2:
            case 3:
            case 4:
                //KI 1-3
                gamer = false;
                break;
        }
        return gamer;
    }

    /**
     * Zu Rundenbeginn wird die Zufallszahl für den aktuellen Dealer
     * (der, der den Weli abheben darf) ermittelt.
     */
    public static void rundenbeginn() {
        Random r = new Random();
        dealer = 1 + r.nextInt(4);
    }

    public int getTrick() {
        return tricks[player];
    }

    public void setTrick(int i) {
        tricks[player] = i;
    }

    /**
     * Aus den Handkarten wird zuerst die größte Gesamtsumme der Farben ermittelt. Danach wird berechnet in welcher
     * {@link Colors} mehr high-valued {@link Card} liegen. Aus dieser ergibt sich die Atoutfarbe. Bei Farben mit
     * derselben Gesamtsumme, wird die Anzahl der hohen Karten ermittelt.
     *
     * @param playersCardsAtout Spielerhand des Spielers mit gewonnener Stichansage
     * @return Atoutfarbe
     */
    private static Colors getAtoutFromPlayers(List<Card> playersCardsAtout) throws WhatTheFuckHowException {
        int anzahlHerz = 0;
        int anzahlSchelle = 0;
        int anzahlBlatt = 0;
        int anzahlEichel = 0;
        final int HERZ = 0;
        final int SCHELLE = 1;
        final int BLATT = 2;
        final int EICHEL = 3;
        int[] high_cards = new int[4];
        Colors atout = null;
        for (Card cards : playersCardsAtout) {
            switch (cards.getColor()) {
                case HERZ:
                    anzahlHerz += cards.getValue();
                    if(cards.getValue() > 4){
                        high_cards[HERZ]++;
                    }
                    break;
                case SCHELLE:
                    anzahlSchelle += cards.getValue();
                    if(cards.getValue() > 4){
                        high_cards[SCHELLE]++;
                    }
                    break;
                case BLATT:
                    anzahlBlatt += cards.getValue();
                    if(cards.getValue() > 4){
                        high_cards[BLATT]++;
                    }
                    break;
                case EICHEL:
                    anzahlEichel += cards.getValue();
                    if(cards.getValue() > 4){
                        high_cards[EICHEL]++;
                    }
                    break;
            }
        }
        int highestValue = Math.max(anzahlHerz, Math.max(anzahlSchelle, Math.max(anzahlBlatt, anzahlEichel)));
        if (highestValue == anzahlHerz) {
            if (anzahlHerz == anzahlSchelle) {
                return high_cards[HERZ] > high_cards[SCHELLE] ? Colors.HERZ : Colors.SCHELLE;
            } else if (anzahlHerz == anzahlBlatt) {
                return high_cards[HERZ] > high_cards[BLATT] ? Colors.HERZ : Colors.BLATT;
            } else if (anzahlHerz == anzahlEichel) {
                return high_cards[HERZ] > high_cards[EICHEL] ? Colors.HERZ : Colors.EICHEL;
            }
        }
        if (highestValue == anzahlSchelle) {
            if (anzahlSchelle == anzahlBlatt) {
                return high_cards[SCHELLE] > high_cards[BLATT] ? Colors.SCHELLE : Colors.BLATT;
            } else if (anzahlSchelle == anzahlEichel) {
                return high_cards[SCHELLE] > high_cards[EICHEL] ? Colors.SCHELLE : Colors.EICHEL;
            }
        }
        if (highestValue == anzahlBlatt) {
            if (anzahlBlatt == anzahlEichel){
                return high_cards[BLATT] > high_cards[EICHEL] ? Colors.BLATT : Colors.EICHEL;
            }
        }
        if (highestValue == anzahlEichel) {
            return Colors.EICHEL;
        }
        throw new WhatTheFuckHowException();
    }
}
