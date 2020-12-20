package itp.project.mulatschak;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import itp.project.Enums.Colors;

/**
 * Initialisiert die Karten für alle vier Spiel beim Aufruf.
 * Der Weli wird durch eine eigene Methode hinzugefügt.
 *
 * @author Maria
 * @version 7-12-2020
 */
public class HoldingCards {
    private List<Card> allCards;

    private List<Card> player1;
    private List<Card> player2;
    private List<Card> player3;
    private List<Card> player4;

    public static boolean weliSetted;

    /**
     * Konstruktor
     */
    public HoldingCards(ArrayList<Card> allCards) {
        HoldingCards.weliSetted = false;
        this.allCards = allCards;

        this.player1 = new ArrayList<Card>();
        initPlayer1();

        this.player2 = new ArrayList<Card>();
        initPlayer2();

        this.player3 = new ArrayList<Card>();
        initPlayer3();

        this.player4 = new ArrayList<Card>();
        initPlayer4();
    }

    //Player 1
    public void initPlayer1() {
        Random r = new Random();
        int zz;
        Card c;
        //Colors color = null;
        for (int i = 0; i < 5; i++) {
            zz = r.nextInt(33);
            c = this.allCards.get(zz);

            //wenn der Weli ausgeteilt wird
            if(zz == 32) { HoldingCards.weliSetted = true;}

            //zu Player1 hinzufügen
            this.player1.add(c);
        }

    }

    public ArrayList<Card> getPlayer1() {
        return (ArrayList<Card>) this.player1;
    }

    //Player 2
    public void initPlayer2() {
        Random r = new Random();
        int zz;
        Card c;
        //Colors color = null;
        boolean used = false;

        for (int i = 0; i < 5; i++) {
            zz = r.nextInt(33); // Index 0 ... 32
            c = this.allCards.get(zz);

            //Player 1 prüfen
            for (int j = 0; j < this.player1.size(); j++) {
                Card check = this.player1.get(j);
                //wenn eine Card mit dem selben Wert vorhanden ist
                if ((check.getValue() == c.getValue()) && (check.getColor() == c.getColor())) {
                    used = true;
                    c = null;
                }
            }

            if (used) {
                i--; //In diesem DL wird keine Card zugewiesen und der DL wird wiederholt
            } else {
                //wenn der Weli ausgeteilt wird
                if(zz == 32) { HoldingCards.weliSetted = true;}

                //zu Player2 hinzufügen
                this.player2.add(c);
            }
        }
    }

    public ArrayList<Card> getPlayer2() {
        return (ArrayList<Card>) this.player2;
    }

    //Player 3
    public void initPlayer3() {
        Random r = new Random();
        int zz, zz2;
        Card c;
        Colors color = null;
        boolean used = false;

        for (int i = 0; i < 5; i++) {
            zz = r.nextInt(33); // Index 0 ... 32
            c = this.allCards.get(zz);

            //Player 1 prüfen
            for (int j = 0; j < this.player1.size(); j++) {
                Card check = this.player1.get(j);
                //wenn eine Card mit dem selben Wert vorhanden ist
                if ((check.getValue() == c.getValue()) && (check.getColor() == c.getColor())) {
                    used = true;
                    c = null;
                }
            }

            //Player 2 prüfen
            for (int j = 0; j < this.player2.size(); j++) {
                Card check = this.player2.get(j);
                //wenn eine Card mit dem selben Wert vorhanden ist
                if ((check.getValue() == c.getValue()) && (check.getColor() == c.getColor())) {
                    used = true;
                    c = null;
                }
            }

            if (used) {
                i--; //In diesem DL wird keine Card zugewiesen und der DL wird wiederholt
            } else {
                //wenn der Weli ausgeteilt wird
                if(zz == 32) { HoldingCards.weliSetted = true;}

                //zu Player3 hinzufügen
                this.player3.add(c);
            }
        }
    }

    public ArrayList<Card> getPlayer3() {
        return (ArrayList<Card>) this.player3;
    }

    //Player 4
    public void initPlayer4() {
        Random r = new Random();
        int zz, zz2;
        Card c;
        Colors color = null;
        boolean used = false;

        for (int i = 0; i < 5; i++) {
            zz = r.nextInt(33); // Index 0 ... 32
            c = this.allCards.get(zz);

            //Player 1 prüfen
            for (int j = 0; j < this.player1.size(); j++) {
                Card check = this.player1.get(j);
                //wenn eine Card mit dem selben Wert vorhanden ist
                if ((check.getValue() == c.getValue()) && (check.getColor() == c.getColor())) {
                    used = true;
                    c = null;
                }
            }

            //Player 2 prüfen
            for (int j = 0; j < this.player2.size(); j++) {
                Card check = this.player2.get(j);
                //wenn eine Card mit dem selben Wert vorhanden ist
                if ((check.getValue() == c.getValue()) && (check.getColor() == c.getColor())) {
                    used = true;
                    c = null;
                }
            }

            //Player 3 prüfen
            for (int j = 0; j < this.player3.size(); j++) {
                Card check = this.player3.get(j);
                //wenn eine Card mit dem selben Wert vorhanden ist
                if ((check.getValue() == c.getValue()) && (check.getColor() == c.getColor())) {
                    used = true;
                    c = null;
                }
            }

            if (used) {
                i--; //In diesem DL wird keine Card zugewiesen und der DL wird wiederholt
            } else {
                //wenn der Weli ausgeteilt wird
                if(zz == 32) { HoldingCards.weliSetted = true;}

                //zu Player4 hinzufügen
                this.player4.add(c);
            }
        }
    }

    public ArrayList<Card> getPlayer4() {
        return (ArrayList<Card>) this.player4;
    }

    /**
     * Fügt den Weli bei einem ausgewählten Player hinzu
     */
    public void setWeli(int player) {
        Card weli = this.allCards.get(32);
        switch (player) {
            case 1:
                this.player1.add(weli);
                break;

            case 2:
                this.player2.add(weli);
                break;

            case 3:
                this.player3.add(weli);
                break;

            case 4:
                this.player4.add(weli);
                break;
        }

    }


    /**
     * tauscht die Karten
     *
     */
    public void changeCardPlayer1(ArrayList<Card> oldCards, ArrayList<Card> newCards ) {
        //noch nicht implementiert
    }

    /**
     *  entfernt Karten --> wenn Spieler Karten tauschen möchte, aber nicht mehr genug da sind
     *
     */
    public void deleteCardPlayer1(ArrayList<Card> cards) {
        //noch nicht implementiert
    }
}