package itp.project.Popups;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.Mulatschak.Card;
import itp.project.Mulatschak.Listeners;
import itp.project.Mulatschak.Playground;
import itp.project.Mulatschak.R;

public class Popup_kartentausch extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {

    ImageView card1, card2, card3, card4, card5, delete, move, eyeBtn;
    ImageButton change;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_kartentausch);

        count = 0;

        eyeBtn = findViewById(R.id.eyeBtn);
        eyeBtn.setOnTouchListener(Listeners.newListener(this));


        //Karten des Spielers anzeigen
        card1 = findViewById(R.id.card1);
        card1.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(0).getPicture());
        System.out.println("card1: " + Playground.getCardfromView(card1).getColor());

        card2 = findViewById(R.id.card2);
        card2.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(1).getPicture());
        System.out.println("card2: " + Playground.getCardfromView(card2).getColor());

        card3 = findViewById(R.id.card3);
        card3.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(2).getPicture());
        System.out.println("card3: " + Playground.getCardfromView(card3).getColor());

        card4 = findViewById(R.id.card4);
        card4.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(3).getPicture());
        System.out.println("card4: " + Playground.getCardfromView(card4).getColor());

        card5 = findViewById(R.id.card5);
        card5.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(4).getPicture());
        System.out.println("card5: " + Playground.getCardfromView(card5).getColor());

        card1.setOnTouchListener(this);
        card2.setOnTouchListener(this);
        card3.setOnTouchListener(this);
        card4.setOnTouchListener(this);
        card5.setOnTouchListener(this);

        //Ok Button
        change = findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Es können nicht 4 Karten getauscht werden
                if (count != 4) {
//                    startActivity(new Intent(Popup_kartentausch.this, Playground.class));
                    synchronized (Playground.playThread) {
                        Playground.playThread.notify();
                    }
                    finish();
                }
            }
        });

        //Löschen Button
        delete = findViewById(R.id.delete);
        delete.setOnDragListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        move = (ImageView) v;
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                v);
        ClipData data = ClipData.newPlainText("", "");
        v.startDrag(data, shadowBuilder, v, 0);
        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                //Karte in den Mistkübel gezogen
                if (event.getResult()) {
                    move.setVisibility(View.INVISIBLE);//unsichtbar setzten
                    changeCard(move);//Karte tauschen
                    count++;
                }
            default:
                break;
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
        Card change = Playground.getCardfromView(v);
        Playground.getPlayer(1).changeCard(change, 5);
    }

}