package itp.project.popups;


import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import itp.project.mulatschak.*;

import java.util.ArrayList;

public class Popup_Card6 extends AppCompatActivity {
    Card card1, card2, card3, card4, card5, card6;
    ArrayList<Card> cardsToChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_card6);

        cardsToChange = new ArrayList<>();

        //Karten auslesen
        Card card1 = Playground.getPlayer(1).getHoldingCards().get(0);
        this.cardsToChange.add(card1);
        Card card2 = Playground.getPlayer(1).getHoldingCards().get(1);
        this.cardsToChange.add(card2);
        Card card3 = Playground.getPlayer(1).getHoldingCards().get(2);
        this.cardsToChange.add(card3);
        Card card4 = Playground.getPlayer(1).getHoldingCards().get(3);
        this.cardsToChange.add(card4);
        Card card5 = Playground.getPlayer(1).getHoldingCards().get(4);
        this.cardsToChange.add(card5);

        //6. Karte hinzufügen
        Playground.getPlayer(1).addCard6();
        Card card6 = Playground.getPlayer(1).getHoldingCards().get(5);
        this.cardsToChange.add(card6);

        //ImageViews erstellen
        //Kartenbilder zuweisen
        //onTouch Listener setzen
        //Karte wegwerfen
        //Popup schließen
        //weiterspielen
    }

//    public void generateCard6() {
//
//    }

    /**
     * Die ImageView der Karte, die getauscht werden soll wird übergeben.
     * Dann im Algorithmus die Karte  tauschen
     *
     * @param v - Karte
     */
    public void changeCard(ImageView v, int anz) {
        Card change = Playground.getCardfromView(v);
        Playground.getPlayer(1).changeCard(change, anz);
    }
}