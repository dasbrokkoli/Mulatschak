package itp.project.popups;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.mulatschak.*;

import java.util.ArrayList;

public class PopupKartentausch extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {

    ImageView card1, card2, card3, card4, card5, delete, move, eyeBtn;
    ImageButton change, undo;
    ArrayList<ImageView> cardsToChange;
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_kartentausch);

        cardsToChange = new ArrayList<>();

        count = 0;
        //zeigt das Atout an
        Playground.showAtout();

        eyeBtn = findViewById(R.id.eyeBtn);
//        eyeBtn.setOnTouchListener(Listeners.newListener(this));
        eyeBtn.setOnClickListener(Listeners.newOnClickListener(this));


        //Karten des Spielers anzeigen
        card1 = findViewById(R.id.card1);
        card1.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(0).getPicture());
        System.out.println("card1: " + Playground.getCardFromView(card1).getColor());

        card2 = findViewById(R.id.card2);
        card2.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(1).getPicture());
        System.out.println("card2: " + Playground.getCardFromView(card2).getColor());

        card3 = findViewById(R.id.card3);
        card3.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(2).getPicture());
        System.out.println("card3: " + Playground.getCardFromView(card3).getColor());

        card4 = findViewById(R.id.card4);
        card4.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(3).getPicture());
        System.out.println("card4: " + Playground.getCardFromView(card4).getColor());

        card5 = findViewById(R.id.card5);
        card5.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(4).getPicture());
        System.out.println("card5: " + Playground.getCardFromView(card5).getColor());

        card1.setOnTouchListener(this);
        card2.setOnTouchListener(this);
        card3.setOnTouchListener(this);
        card4.setOnTouchListener(this);
        card5.setOnTouchListener(this);

        //Ok Button
        change = findViewById(R.id.change);
        change.setOnClickListener(view -> {
            //Es können nicht 4 Karten getauscht werden
            if (count != 4) {
                //startActivity(new Intent(PopupKartentausch.this, Playground.class));
                for (ImageView imageView : cardsToChange) {
                    changeCard(imageView);
                    //cardsToChange.get(i).setVisibility(View.INVISIBLE);
                }
                if(count == 5) {
                    startActivity(new Intent(PopupKartentausch.this, PopupCard6.class));
                } else {
                    synchronized (Playground.playThread) {
                        Playground.playThread.notify();
                    }
                }
                finish();
            }
        });

        //Löschen Button
        delete = findViewById(R.id.delete);
        delete.setOnDragListener(this);

        //Zurück Button
        undo = findViewById(R.id.undo);
        undo.setOnClickListener(view -> {
            //Die Letzte Karte wird wieder sichtbar gemacht
            if (count > 0) {
                cardsToChange.get(count - 1).setVisibility(View.VISIBLE);
                //Und aus dem Array gelöscht
                cardsToChange.remove(count - 1);
                count--;
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
        //Punkte des Spielers
        int userPoints = Algorithm.getPoints().get(0);

        //Unter 4 Punkten darf der Spieler keine Karten tauschen
        if (userPoints < 4) {
            Toast t = Toast.makeText
                    (getApplicationContext(),
                            "Sie haben unter 3 Punkte und dürfen keinen Karten mehr tauschen!",
                            Toast.LENGTH_LONG);
            t.show();
        } else {
            if (event.getAction() == DragEvent.ACTION_DRAG_ENDED) {//Karte in den Mistkübel gezogen
                if (event.getResult()) {
                    move.setVisibility(View.INVISIBLE);//unsichtbar setzten
                    //changeCard(move);//Karte tauschen
                    cardsToChange.add(count, move);
                    count++;
                }
            }
        }
        return true;

    }

    /**
     * Die ImageView der Karte, die getauscht werden soll wird übergeben.
     * Dann im Algorithmus die Karte  tauschen
     *
     * @param v - Karte
     */
    public void changeCard(ImageView v) {
        Card change = Playground.getCardFromView(v);
        Playground.getPlayer(1).changeCard(change, 5);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}