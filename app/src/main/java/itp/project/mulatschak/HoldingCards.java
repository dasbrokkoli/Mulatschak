package itp.project.mulatschak;

import itp.project.Enums.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 *
 * @author Maria
 * @version 7-12-2020
 */
public class HoldingCards {
    public static List<Card> allCards;
    public static boolean weliOccured;

    private List<Card> player;

    /**
     * Weißt allCards die Karten zu
     * Wird nur beim Start einer neuen Runde aufgerufen
     * @param all
     */
    public static void setAllCards(List<Card> all) {
        HoldingCards.allCards = all;
    }

    /**
     * Verändert den Weli Status
     */
    public static void setWeliStatus(boolean status) {
        HoldingCards.weliOccured = status;
    }

    /**
     * löscht eine Card aus der globalen Liste
     * @param i
     */
    public static void deleteCards(int i) {
        HoldingCards.allCards.remove(i);
    }

    /**
     * Initialisiert die Karten für einen Spieler
     */
    public void initPlayer(int cardAnz) {
        Random r = new Random();
        int zz;
        Card card;

        //Fünf Karten werden zugewiesen
        x:while(this.player.size() < cardAnz) {
            if(HoldingCards.allCards.isEmpty()) {
                System.err.println("Alle Karten wurde ausgeteilt"); //Später evtl. entfernen
                break x;
            }

            //eine zufällige Karte wird ausgewählt
            zz = r.nextInt(HoldingCards.allCards.size());

            //neue Card wird erstellt
            card = HoldingCards.allCards.get(zz);

            //Card wird entfernt
            deleteCards(zz);

            //Karte wird dem Spieler zugewiesen
            this.player.add(card);
        }
    }

    /**
     * Methode für den Kartentausch
     * löscht alle getauschten Karten und weist dem Spieler neue Karten zu
     */
    public void changeCard(List<Card> oldCards, int anzNew) {
        //Card wird entfernt
        for(Card card: oldCards) {

        }

        //und neue werden hinzugefügt
        initPlayer(anzNew);
    }

    /**
     * entfernt eine Karte z.B. wenn sie ausgespielt wird
     *
     */
    public void deleteHoldingCard(Card card) {
        Boolean removed = HoldingCards.allCards.remove(card);
        if(!removed) { System.err.println("Ups...something went wrong :(");}
    }

    /**
     * fügt eine Card hinzu z.B. Weli
     *
     */
    public void addHoldingCard() {
        int index = HoldingCards.allCards.size()-1;
        Card card = HoldingCards.allCards.get(index);
        this.player.add(card);
        deleteCards(index);
    }
}