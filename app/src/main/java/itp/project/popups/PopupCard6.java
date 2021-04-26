package itp.project.popups;

import android.content.ClipData;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import itp.project.mulatschak.*;

import java.util.ArrayList;

public class PopupCard6 extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {
    ImageView card_view1, card_view2, card_view3, card_view4, card_view5, card_view6, move;
    ImageView wegwerfen = null;
    ImageButton delete, change, undo;
    ArrayList<Card> cardsToChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_card6);
        Listeners.newListener(this);

        cardsToChange = new ArrayList<>();

        //Karten auslesen
        Card c1 = Playground.getPlayer(1).getHoldingCards().get(0);
        this.cardsToChange.add(c1);
        Card c2 = Playground.getPlayer(1).getHoldingCards().get(1);
        this.cardsToChange.add(c2);
        Card c3 = Playground.getPlayer(1).getHoldingCards().get(2);
        this.cardsToChange.add(c3);
        Card c4 = Playground.getPlayer(1).getHoldingCards().get(3);
        this.cardsToChange.add(c4);
        Card c5 = Playground.getPlayer(1).getHoldingCards().get(4);
        this.cardsToChange.add(c5);

        //6. Karte hinzufügen
        Playground.getPlayer(1).addCard6();
        Card c6 = Playground.getPlayer(1).getHoldingCards().get(5);
        this.cardsToChange.add(c6);

        //ImageViews erstellen
        card_view1 = findViewById(R.id.card1);
        card_view1.setImageDrawable(c1.getPicture());

        card_view2 = findViewById(R.id.card2);
        card_view2.setImageDrawable(c2.getPicture());

        card_view3 = findViewById(R.id.card3);
        card_view3.setImageDrawable(c3.getPicture());

        card_view4 = findViewById(R.id.card4);
        card_view4.setImageDrawable(c4.getPicture());

        card_view5 = findViewById(R.id.card5);
        card_view5.setImageDrawable(c5.getPicture());

        card_view6 = findViewById(R.id.card6);
        card_view6.setImageDrawable(c6.getPicture());

        //onTouch Listener setzen
        card_view1.setOnTouchListener(this);
        card_view2.setOnTouchListener(this);
        card_view3.setOnTouchListener(this);
        card_view4.setOnTouchListener(this);
        card_view5.setOnTouchListener(this);
        card_view6.setOnTouchListener(this);

        //Löschen Button
        delete = findViewById(R.id.del_button);
        delete.setOnDragListener(this);

        //Ok Button
        change = findViewById(R.id.change);
        change.setOnClickListener(view -> {
            if (wegwerfen != null) {
                delCard(wegwerfen);
                finish();
            } else {
                Toast.makeText(this, "Please choose a Card to throw away", Toast.LENGTH_LONG).show();
                System.out.println("Du kannst leider keine 6 Karten behalten :)))))");
            }
        });

        //Zurück Button
        undo = findViewById(R.id.undo);
        undo.setOnClickListener(view -> {
            //Die Letzte Karte wird wieder sichtbar gemacht
            if (wegwerfen != null) {
                wegwerfen.setVisibility(View.VISIBLE);
                wegwerfen = null;
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        move = (ImageView) v;
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                v);
        ClipData data = ClipData.newPlainText("", "");
        v.startDragAndDrop(data, shadowBuilder, v, 0);
        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (event.getAction() == DragEvent.ACTION_DRAG_ENDED) {//Karte in den Mistkübel gezogen
            if (event.getResult()) {
                if (wegwerfen == null) {
                    move.setVisibility(View.INVISIBLE);//unsichtbar setzten
                    //changeCard(move);//Karte tauschen
                    //wegwerfen = move.getDrawable();
                    wegwerfen = move;
                } else {
                    Toast.makeText(this, "You can only throw 1 card away", Toast.LENGTH_LONG).show();
                    System.out.println("Du hast schon eine Karte weggeworfen");
                }

            }
        }
        return true;
    }

    public void delCard(ImageView v) {
        Card del = Playground.getCardFromView(v);
        Playground.getPlayer(1).deleteCard6(del);
    }
}

