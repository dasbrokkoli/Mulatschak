package itp.project.Mulatschak;

import itp.project.Enums.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Maria
 * @version 7-12-2020
 */
public class HoldingCards {
    public static List<Card> allCards;
    public static boolean weliOccured;

    private List<Card> player;

    public HoldingCards() {
        player = new ArrayList<>();
    }

    /**
     * Methode für den Kartentausch
     * löscht die übergebene Card un weißt dem Spieler eine neue zu
     * <p>
     * anzNew entspricht der GESAMTEN Kartenanzahl, also auch inkl. der nicht-getauschten Karten
     */
    public void changeCard(Card oldCard, int anzNew) {
        //Card wird entfernt
        deleteHoldingCard(oldCard);

        //und eine neue hinzugefügt
        initPlayer(anzNew);
    }

    /**
     * Initialisiert die Karten für einen Spieler
     */
    public void initPlayer(int cardAnz) {
        Random r = new Random();
        int zz;
        Card card;

        //Fünf Karten werden zugewiesen
        while (this.player.size() < cardAnz) {
            if (HoldingCards.allCards.isEmpty()) {
                System.err.println("Alle Karten wurde ausgeteilt"); //Später evtl. entfernen
                break;
            }

            //eine zufällige Karte wird ausgewählt
            zz = r.nextInt(HoldingCards.allCards.size());

            //neue Card wird erstellt
            card = HoldingCards.allCards.get(zz);

            if (card.getColor() == Colors.WELI) {
                setWeliStatus(true);
            }

            //Karte wird dem Spieler zugewiesen
            this.player.add(card);

            //Card wird entfernt
            deleteAllCard(card);
        }
    }

    /**
     * entfernt eine Karte z.B. wenn sie ausgespielt wird
     */
    public void deleteAllCard(Card card) {
        boolean removed = HoldingCards.allCards.remove(card);
        if (!removed) {
            System.err.println("Upsi :(");
        }
    }

    public void deleteHoldingCard(Card card) {
        boolean removed = player.remove(card);
        if (!removed) {
            System.err.println("Upsi :(");
        }
    }

    /**
     * fügt eine Card hinzu z.B. Weli
     */
    public void addHoldingCard() {
        int index = HoldingCards.allCards.size() - 1;
        Card card = HoldingCards.allCards.get(index);
        this.player.add(card);
        deleteAllCard(card);
    }

    public static List<Card> getAllCards() {
        return HoldingCards.allCards;
    }

    /**
     * Weißt allCards die Karten zu
     * Wird nur beim Start einer neuen Runde aufgerufen
     *
     * @param all
     */
    public static void setAllCards(List<Card> all) {
        HoldingCards.allCards = new ArrayList<>();
        HoldingCards.allCards.addAll(all);
    }

    public List<Card> getCards() {
        return this.player;
    }

    public void setCards(List<Card> holdingCards) {
        this.player = holdingCards;
    }

    public Colors[] getColors() {
        Colors[] arr = new Colors[this.player.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = this.player.get(i).getColor();
        }

        return arr;
    }

    public int[] getValue() {
        int[] arr = new int[this.player.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = this.player.get(i).getValue();
        }

        return arr;
    }

    public boolean getWeliStatus() {
        return HoldingCards.weliOccured;
    }

    /**
     * Verändert den Weli Status
     */
    public static void setWeliStatus(boolean status) {
        HoldingCards.weliOccured = status;
    }


}