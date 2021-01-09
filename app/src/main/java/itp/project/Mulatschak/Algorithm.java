package itp.project.Mulatschak;

import android.view.View;
import itp.project.Enums.Colors;
import itp.project.Enums.Difficulty;
import itp.project.Enums.Values;
import itp.project.Exceptions.TwoSameHighestTricksException;
import itp.project.Exceptions.WhatTheFuckHowException;
import itp.project.Popups.PopupDifficulty;
import itp.project.Mulatschak.R;


import java.sql.SQLOutput;
import java.util.*;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Algorithm {

    private static int dealer;
    private static final int[] tricks = new int[4];
    private static List<Card> cards;
    private static int winChance;
    private final int player;
    //Attribut für die Kartenzuweisung
    private HoldingCards playerCards;
    private static Colors atout;
    private static final int[] madeTricks = new int[4];
    private static boolean doubleRound; //ob doppelte Runde (wenn Herz atout)
    private static boolean droppedOut; //ob der Spieler ausgestiegen ist oder nicht
    private static List<Integer> points = new ArrayList<>(); //fuer die Punktestaende der Spieler
    private boolean ausgestiegen;
    private int stiche;
    private static Difficulty difficulty;

    public boolean isKi() {
        return ki;
    }

    public void setKi(boolean ki) {
        this.ki = ki;
    }

    private boolean ki;

    public static Colors getAtout() {
        return atout;
    }

    public Algorithm(List<Card> cards, int player) {
        Algorithm.cards = cards;

        this.playerCards = new HoldingCards();
        this.playerCards.initPlayer(5);
        this.player = player;
        try {
            points.set(player - 1,21);
        } catch (IndexOutOfBoundsException e) {
            points.add(player - 1,21);
        }
        setAusgestiegen(false);
        setKi(true);
    }

    /**
     * @return the Index of the highest trick in tricks-Array (equals player - 1)
     */
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

    /**
     * @param inputCard the highest Card currently laying on the floor
     * @return the best {@link Card} the Computer could play in this move
     */
    public Card getResponseCard(Card inputCard) {
        this.setValues();
        this.setHoldingValues();
        this.setWinChance();

        boolean winMove = new Random().nextInt(101) < winChance;
        if (!winMove) {
            System.out.println("Random");
            int random = new Random().nextInt(playerCards.getCards().size());
            Card card = playerCards.getCards().get(random);
            playerCards.deleteHoldingCard(card);
            return card;
        }
        Card lowestCard = lowestCardValue(inputCard.getTempValue());
        if (lowestCard != null) {
            playerCards.deleteHoldingCard(lowestCard);
            return lowestCard;
        } else {
            return lowestCardValue();
        }
    }

    public static Difficulty getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(Difficulty difficulty) {
        Algorithm.difficulty = difficulty;
    }

    /**
     * @param cardsOnFloor all cards currently laying on the floor
     * @return the highest Card currently laying on the floor (could be used as parameter for {@link
     * Algorithm#getResponseCard}
     */
    public static Card getWinnerFromCards(Card... cardsOnFloor) {
        List<Card> cardsOnFloorList = Arrays.asList(cardsOnFloor.clone());
        List<Integer> valueList = new ArrayList<>();
        for (Card floorCard : cardsOnFloor) {
            for (Card card : cards) {
                if (card.getValue() == floorCard.getValue() && card.getColor() == floorCard.getColor()) {
                    floorCard.setTempValue(card.getTempValue());
                }
            }
            valueList.add(floorCard.getTempValue());
        }
        int highestIndex = valueList.indexOf(Collections.max(valueList));
        return cardsOnFloorList.get(highestIndex);
    }

    public void wonThisCard(){
        Playground.stitchesMade(this.player,++madeTricks[player-1]);
    }

    private Card lowestCardValue() {
        return lowestCardValue(-1);
    }

    private Card lowestCardValue(int moreThan) {
        Card lowestValue = null;
        for (Card card : playerCards.getCards()) {
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
        playerCards.deleteHoldingCard(lowestValue);
        return lowestValue;
    }

    public static void setAtout(Colors atout) {
        Algorithm.atout = atout;
    }

    private void setValues() {
        for (Card card : cards) {
            if (card.getColor() == Algorithm.getAtout()) {
                card.setTempValue(card.getValue() + 10);
            } else {
                card.setTempValue(card.getValue());
            }
        }
    }

    private void setHoldingValues() {
        for (Card holdCard : playerCards.getCards()) {
            for (Card card : cards) {
                if (card.getValue() == holdCard.getValue() && card.getColor() == holdCard.getColor()) {
                    holdCard.setTempValue(card.getTempValue());
                }
            }
        }
    }

    /**
     * Setzt die Change zu gewinnen, bei jeder Schwierigkeit anders. Dabei wird die Gewinnchance für die KIs festgelegt
     * (NICHT fuer Benutzer)
     */
    private void setWinChance() {

        switch (difficulty) {
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
        playerCards.setCards(holdingCards);
    }

    public List<Card> getHoldingCards() {
        return playerCards.getCards();
    }

    /**
     * Es wird geprüft ob der Benutzer den Weli ziehen darf. Dazu wird das Int-Attribut dealer verwendet.
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
     * Zu Rundenbeginn wird die Zufallszahl für den aktuellen Dealer (der, der den Weli abheben darf) ermittelt.
     * Außerdem wird doubleRound standardmaessig auf false gesetzt. Zusaetzlich werdem jedem Spieler 20 Punkte
     * zugeschrieben. Zu Rundenbeginn wird die Zufallszahl für den aktuellen Dealer (der, der den Weli abheben darf)
     * ermittelt.
     */
    public static void rundenbeginn() {
        Random r = new Random();
        dealer = 1 + r.nextInt(4);
        Arrays.fill(madeTricks, 0);
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
     * @return Atoutfarbe
     */
    public Colors getAtoutFromPlayers() throws WhatTheFuckHowException {
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
        for (Card cards : playerCards.getCards()) {
            switch (cards.getColor()) {
                case HERZ:
                    anzahlHerz += cards.getValue();
                    if (cards.getValue() > 4) {
                        high_cards[HERZ]++;
                    }
                    break;
                case SCHELLE:
                    anzahlSchelle += cards.getValue();
                    if (cards.getValue() > 4) {
                        high_cards[SCHELLE]++;
                    }
                    break;
                case BLATT:
                    anzahlBlatt += cards.getValue();
                    if (cards.getValue() > 4) {
                        high_cards[BLATT]++;
                    }
                    break;
                case EICHEL:
                    anzahlEichel += cards.getValue();
                    if (cards.getValue() > 4) {
                        high_cards[EICHEL]++;
                    }
                    break;
                default:
                    System.out.println("Weli vorhanden");
            }
        }
        int highestValue = Math.max(anzahlHerz, Math.max(anzahlSchelle, Math.max(anzahlBlatt, anzahlEichel)));
        System.out.println("highestValue: " + highestValue);
        System.out.println("anzahlHerz: " + anzahlHerz);
        System.out.println("anzahlSchelle: " + anzahlSchelle);
        System.out.println("anzahlBlatt: " + anzahlBlatt);
        System.out.println("anzahlEichel: " + anzahlBlatt);
        if (highestValue == anzahlHerz) {
            if (anzahlHerz == anzahlSchelle) {
                return high_cards[HERZ] > high_cards[SCHELLE] ? Colors.HERZ : Colors.SCHELLE;
            } else if (anzahlHerz == anzahlBlatt) {
                return high_cards[HERZ] > high_cards[BLATT] ? Colors.HERZ : Colors.BLATT;
            } else if (anzahlHerz == anzahlEichel) {
                return high_cards[HERZ] > high_cards[EICHEL] ? Colors.HERZ : Colors.EICHEL;
            }
            return Colors.HERZ;
        }
        if (highestValue == anzahlSchelle) {
            if (anzahlSchelle == anzahlBlatt) {
                return high_cards[SCHELLE] > high_cards[BLATT] ? Colors.SCHELLE : Colors.BLATT;
            } else if (anzahlSchelle == anzahlEichel) {
                return high_cards[SCHELLE] > high_cards[EICHEL] ? Colors.SCHELLE : Colors.EICHEL;
            }
            return Colors.SCHELLE;
        }
        if (highestValue == anzahlBlatt) {
            if (anzahlBlatt == anzahlEichel) {
                return high_cards[BLATT] > high_cards[EICHEL] ? Colors.BLATT : Colors.EICHEL;
            }
            return Colors.BLATT;
        }
        return Colors.EICHEL;
    }

    /**
     * Berechnet die Punkte nach jeder fertigen Runde:
     * speichert sie in der Attribut Liste points ab
     *
     * @param algo
     * @return scores -> Eine ArrayList mit all den Punkteständen
     */
    public static void scoring(Algorithm... algo) {
        int newPoints;
        int tempSticheAngesagt = 4;
        for (int i = 0; i < algo.length; i++) {
            newPoints = points.get(i); //Die Punktestaende von davor aufrufen und abspeichern
            algo[i].getTrick();     //Die Stiche holen

            //Sieger ermitteln

            //Angesagte Stiche
            int saidStitches = tempSticheAngesagt;
            //Gemachte stiche
            CharSequence tmp = (Playground.stitches[i].getText());
            String tmp2 = tmp.toString();
            int madeStitches = Integer.parseInt(tmp2);

            //Stiche vergleichen (angesagt vs gemacht)
            if (droppedOut == true) {
                newPoints = newPoints + 1; //Wenn der Spieler ausgestiegen ist, erhoeht sich der Punktestand um 1
            } else if (saidStitches > madeStitches) {
                newPoints += 10; //Wenn mehr angesagt wurden als gemacht
            } else if ((saidStitches == 0) && (madeStitches == 0)) {
                newPoints += 5; //Wenn keine angesagt und keine gemacht wurden
            } else {
                newPoints -= madeStitches; //Sonst schreibt man die gemachten Stiche runter

                if (droppedOut == true) {
                    newPoints = newPoints + 1; //Wenn der Spieler ausgestiegen ist, erhoeht sich der Punktestand um 1
                }
                if (doubleRound == true) {

                    if (atout == Colors.HERZ) {
                        newPoints = newPoints * 2; //Wenn Atout Herz zählt die Runde doppelt
                    }
                    points.set(i, newPoints);
                }
            }
        }
    }



    /**
     * anzNew entspricht der GESAMTEN Kartenanzahl, also auch inkl. der nicht-getauschten Karten
     *
     */
    public void changeCard(Card oldCard, int anzNew) {
        this.playerCards.changeCard(oldCard, anzNew);
    }

    /**
     * ZUM TESTEN - kann später evtl entfernt werden: Gibt alle gehaltenen Karten zurück
     */

    public List<Card> getPlayerCards() {
        return this.playerCards.getCards();
    }



    /**
     * Gibt die Gesamtstichansage basierend der Spielkarten eines Spielers zurück. Die Stiche beziehen sich dabei auf: -
     * Weli - Daus (Atoutfarbe), Daus - König (Atoutfarbe)
     *
     * @return Gesamtstichansage
     */
    public int getStiche() {
        int stiche = 0;
        for (Card card : this.playerCards.getCards()) {
            if (card.getColor() == Colors.WELI) stiche++;
            if (card.getColor() == atout) {
                if (card.getValue() == Values.DAUS || card.getValue() == Values.KOENIG) stiche++;
            } else {
                if (card.getValue() == Values.DAUS) stiche++;
            }
        }
        return stiche;

    }

    public static List<Integer> getPoints(){
        return points;
    }

    public void setStichePlayer1(int stiche) {
        this.stiche = stiche;
    }

    public static int getDealer() {
        return dealer;
    }

    public boolean istAusgestiegen() {
        return ausgestiegen;
    }

    public void setAusgestiegen(boolean ausgestiegen) {
        this.ausgestiegen = ausgestiegen;
    }

    public String getHoldingCardsString() {
        StringBuilder sb = new StringBuilder("");
        for(Card card : playerCards.getCards()){
            sb.append(card.getColor()).append(card.getValue()).append(" ");
        }
        return sb.toString();
    }

    public String getName() {
        return String.valueOf(player);
    }
}
