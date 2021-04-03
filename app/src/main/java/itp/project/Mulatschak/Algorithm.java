package itp.project.Mulatschak;

import itp.project.Enums.Colors;
import itp.project.Enums.Difficulty;
import itp.project.Enums.Values;
import itp.project.Exceptions.TwoSameHighestTricksException;
import itp.project.Exceptions.WinException;
import itp.project.Popups.PopupAtout;

import javax.annotation.Nullable;
import java.util.*;

public class Algorithm {

    private static final Integer MAX_POINTS = 21;
    private static final int[] tricks = new int[4];
    private static final List<Integer> points = new ArrayList<>(); //fuer die Punktestaende der Spieler
    private static int dealer;
    private static List<Card> cards;
    private static int winChance;
    private static Colors atout;
    private static Difficulty difficulty;
    private final int player;
    //Attribut für die Kartenzuweisung
    private final HoldingCards playerCards;
    private boolean ausgestiegen;
    private int stiche;
    private boolean ki;

    public Algorithm(List<Card> cards, int player) {
        Algorithm.cards = cards;

        this.playerCards = new HoldingCards();
        this.playerCards.initPlayer(5);
        this.player = player;
        if (points.size() < 4) {
            try {
                points.set(player - 1, MAX_POINTS);
                System.out.println("Punkte für Spieler " + (player - 1) + " wurden gesetzt");
            } catch (IndexOutOfBoundsException e) {
                points.add(player - 1, MAX_POINTS);
            }
        }
        setAusgestiegen(false);
        setKi(true);
    }

    public void setAusgestiegen(boolean ausgestiegen) {
        this.ausgestiegen = ausgestiegen;
    }

    /**
     * @param cardsOnFloor all cards currently laying on the floor
     * @return the highest Card currently laying on the floor (could be used as parameter for {@link
     * Algorithm#getResponseCard}
     */
    public synchronized static Card getWinnerFromCards(Card... cardsOnFloor) {
        List<Card> cardsOnFloorList = Arrays.asList(cardsOnFloor.clone());
        List<Integer> valueList = new ArrayList<>();
        synchronized (cardsOnFloorList) {
            synchronized (valueList) {
                System.out.println("Auswahl der Gewinnerkarte aus " + cardsOnFloor.length + " Karten");
                for (Card floorCard : cardsOnFloor) {
                    for (Card card : cards) {
                        if (card.getValue() == floorCard.getValue() && card.getColor() == floorCard.getColor()) {
                            floorCard.setTempValue(card.getTempValue());
                        }
                    }
                    valueList.add(floorCard.getTempValue());
                }
            }
        }
        if (valueList.isEmpty()) {
            return null;
        }
        int highestIndex = valueList.indexOf(Collections.max(valueList));
        return cardsOnFloorList.get(highestIndex);
    }

    /**
     * Es wird geprüft ob der Benutzer den Weli ziehen darf. Dazu wird das Int-Attribut dealer verwendet.
     *
     * @return ob der Spieler den Weli ziehen darf
     */
    public synchronized static boolean WeliZiehen() {
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
    public synchronized static void rundenbeginn() {
        Random r = new Random();
        dealer = 1 + r.nextInt(4);
        Arrays.fill(tricks, 0);
        Collections.fill(points, MAX_POINTS);
    }

    /**
     * Berechnet die Punkte nach jeder fertigen Runde:
     * speichert sie in der Attribut Liste points ab
     *
     * @param algo
     * @return scores -> Eine ArrayList mit all den Punkteständen
     */
    public synchronized static void scoring(Algorithm... algo) throws WinException {
        int oldPoints;
        int diffPoints;
        //Angesagte Stiche
        Map<Integer, Integer> highestStitches = Playground.getHighestStich();

        for (int i = 0; i < algo.length; i++) {
            oldPoints = points.get(i); //Die Punktestaende von davor aufrufen und abspeichern
            diffPoints = 0;

            //Gemachte stiche
            CharSequence tmp = (Playground.stitches[i].getText());
            String tmp2 = tmp.toString();
            int madeStitches = Integer.parseInt(tmp2);

            if (highestStitches.containsKey(i)) {
                System.out.println("Player " + i + " hat die höchsten Stiche");
                int saidStitches = highestStitches.get(i);
                if (saidStitches > madeStitches) {
                    System.out.println("Stiche nicht erreicht " + madeStitches + " < " + saidStitches);
                    diffPoints -= 10; //Wenn mehr angesagt wurden als gemacht
                } else {
                    System.out.println("Stiche erreicht " + madeStitches + " => " + saidStitches);
                    diffPoints += madeStitches;
                }
            } else if (i == 0 && PopupAtout.alreadyLeft) {
                System.out.println("Spieler ist heimgegangen");
                diffPoints -= 1; //Wenn der Spieler ausgestiegen ist, erhoeht sich der Punktestand um 1

            } else if (madeStitches == 0) {
                System.out.println("Spieler " + i + " hat keine Stiche gemacht");
                diffPoints -= 5; //Wenn keine angesagt und keine gemacht wurden

            } else {
                System.out.println("Spieler " + i + " hat " + madeStitches + " Punkte runtergeschrieben");
                diffPoints += madeStitches; //Sonst schreibt man die gemachten Stiche runter

            }
            if (atout == Colors.HERZ) {
                System.out.println("Doppelte Runde");
                diffPoints *= 2; //Wenn Atout Herz zählt die Runde doppelt
            }

            System.out.println("Alte Punkte: " + oldPoints);
            System.out.println("Punktedifferenz für Spieler " + i + ": " + diffPoints);
            oldPoints -= diffPoints;
            System.out.println("Neue Punkte für Spieler " + i + ": " + oldPoints);
            points.set(i, oldPoints);
        }
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i) <= 0) {
                throw new WinException(i);
            }
        }
    }

    public static Colors getAtout() {
        return atout;
    }

    public static int getDealer() {
        return dealer;
    }

    /**
     * anzNew entspricht der GESAMTEN Kartenanzahl, also auch inkl. der nicht-getauschten Karten
     */
    public void changeCard(Card oldCard, int anzNew) {
        this.playerCards.changeCard(oldCard, anzNew);
    }

    /**
     * @param inputCard the highest Card currently laying on the floor
     * @return the best {@link Card} the Computer could play in this move
     */
    public synchronized Card getResponseCard(@Nullable Card inputCard) {
        this.setValues();
        this.setHoldingValues();
        this.setWinChance();

        boolean winMove = new Random().nextInt(101) < winChance;

        //TODO make Algorithms play better cards if they're first
        if (inputCard == null) {
            winMove = false;
        }

        if (!winMove) {
            System.out.println("Random");
            int random = new Random().nextInt(playerCards.getCards().size());
            Card card = playerCards.getCards().get(random);
            playerCards.deleteHoldingCard(card);
            return card;
        }

        Card lowestCard = lowestCardValue(inputCard.getTempValue());
        if (lowestCard != null) {
            return lowestCard;
        } else {
            return lowestCardValue();
        }
    }

    private synchronized Card lowestCardValue() {
        return lowestCardValue(-1);
    }

    private synchronized Card lowestCardValue(int moreThan) {
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

    private synchronized void setValues() {
        for (Card card : cards) {
            if (card.getColor() == Algorithm.getAtout()) {
                card.setTempValue(card.getValue() + 10);
            } else {
                card.setTempValue(card.getValue());
            }
        }
    }

    public static Difficulty getDifficulty() {
        return difficulty;
    }

    public synchronized static void setAtout(Colors atout) {
        Algorithm.atout = atout;
    }

    private synchronized void setHoldingValues() {
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
    private synchronized void setWinChance() {

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

    public synchronized boolean hasAdoutPermission() throws TwoSameHighestTricksException {
        if (getHighestTrickIndex() == player) {
            return true;
        } else if (getHighestTrickIndex() == -1) {
            throw new TwoSameHighestTricksException();
        }
        return false;
    }

    public static void setDifficulty(Difficulty difficulty) {
        Algorithm.difficulty = difficulty;
    }

    public synchronized void wonThisCard() {
        Playground.stitchesMade(this.player, ++tricks[player - 1]);
    }

    /**
     * Aus den Handkarten wird zuerst die größte Gesamtsumme der Farben ermittelt. Danach wird berechnet in welcher
     * {@link Colors} mehr high-valued {@link Card} liegen. Aus dieser ergibt sich die Atoutfarbe. Bei Farben mit
     * derselben Gesamtsumme, wird die Anzahl der hohen Karten ermittelt.
     *
     * @return Atoutfarbe
     */
    public synchronized Colors getAtoutFromPlayers() {
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
     * @return the Index of the highest trick in tricks-Array (equals player - 1)
     */
    public synchronized static int getHighestTrickIndex() {
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

    public static List<Integer> getPoints() {
        return points;
    }

    public int getTrick() {
        return tricks[player - 1];
    }

    public List<Card> getHoldingCards() {
        return playerCards.getCards();
    }

    public void setHoldingCards(List<Card> holdingCards) {
        playerCards.setCards(holdingCards);
    }

    public synchronized String getHoldingCardsString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : playerCards.getCards()) {
            sb.append(card.getColor()).append(card.getValue()).append(" ");
        }
        return sb.toString();
    }

    public HoldingCards getHoldingClass() {
        return playerCards;
    }

    public String getName() {
        return String.valueOf(player);
    }

    /**
     * ZUM TESTEN - kann später evtl entfernt werden: Gibt alle gehaltenen Karten zurück
     */

    public List<Card> getPlayerCards() {
        return this.playerCards.getCards();
    }

    public void setTrick(int i) {
        tricks[player - 1] = i;
    }

    /**
     * Gibt die Gesamtstichansage basierend der Spielkarten eines Spielers zurück. Die Stiche beziehen sich dabei auf: -
     * Weli - Daus (Atoutfarbe), Daus - König (Atoutfarbe)
     *
     * @return Gesamtstichansage
     */
    public synchronized int getStiche() {
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

    public boolean isKi() {
        return ki;
    }

    public void setKi(boolean ki) {
        this.ki = ki;
    }

    public boolean istAusgestiegen() {
        return ausgestiegen;
    }
}
