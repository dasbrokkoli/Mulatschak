package itp.project.Mulatschak;

import android.content.ClipData;
import android.content.Intent;
import android.view.View;
import android.view.*;
import android.widget.*;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import itp.project.Enums.Colors;
import itp.project.Popups.PopupStichansage;

import java.util.ArrayList;
import java.util.List;

public class Playground extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener{
    public static boolean alreadyLeft;
    
    //Atout
    public static Colors Atout = null;
    ImageView atout;

    //LogPopup
    Button showLogBtn;
    ImageButton closeLogView;
    PopupWindow logWindow;
    ConstraintLayout constraintLayout;

    //Cards
    ImageView card1, card2, card3,card4,card5, destination;
            static ImageView move;
    //Gemachte Stiche
    private static TextView stitches_pl1, stitches_pl2, stitches_pl3, stitches_pl4, pl1_announced,pl2_announced,pl3_announced,pl4_announced;
    //Liste für die Karten

    //Algorithmen für Spieler
    static Algorithm player1,player3, player2, player4;

    private static List<Card> inputCards;

    //View diffView = findViewById(R.layout.popup_difficulty);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);
        alreadyLeft = false;
        //Spieler
        austeilen();

        startActivity(new Intent(Playground.this, PopupStichansage.class));

        //Settings Button
        View settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Playground.this, SettingsActivity.class));
            }
        });

        //Tutorial Button
        View help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Playground.this, TutorialActivity.class));
            }
        });

        //Fuer die Schwierigkeit
        //diffView = findViewById(R.layout.popup_difficulty);

        //ImageView für Atout anzeigen
        atout = findViewById(R.id.atout);

        constraintLayout = (ConstraintLayout) findViewById(R.id.playgroundConstraintLayout);
        showLogBtn = findViewById(R.id.logButton);
        showLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) Playground.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View logView = layoutInflater.inflate(R.layout.popup_log, null);

                closeLogView = logView.findViewById(R.id.closeLogBtn);

                logWindow = new PopupWindow(logView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                logWindow.showAtLocation(constraintLayout, Gravity.CENTER,0,0);
                closeLogView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logWindow.dismiss();
                    }
                });
            }
        });

        //Karten aus dem Algorithmus


        //Cards
        card1 = findViewById(R.id.card1);
        card1.setOnTouchListener(this);
        card2 = findViewById(R.id.card);
        card2.setOnTouchListener(this);
        card3 = findViewById(R.id.card2);
        card3.setOnTouchListener(this);
        card4 = findViewById(R.id.card3);
        card4.setOnTouchListener(this);
        card5 = findViewById(R.id.card4);
        card5.setOnTouchListener(this);

        destination = findViewById(R.id.imageView);
        destination.setOnDragListener(this);

        //Stiche gemacht
        stitches_pl1 = findViewById(R.id.player_stitches);
        stitches_pl2 = findViewById(R.id.pl1_stitches);
        stitches_pl3 = findViewById(R.id.pl2_stitches);
        stitches_pl4 = findViewById(R.id.pl3_stitches);
        pl1_announced = findViewById(R.id.player_announced);
        pl2_announced = findViewById(R.id.pl1_announced);
        pl3_announced = findViewById(R.id.pl2_announced);
        pl4_announced = findViewById(R.id.pl3_announced);

        inputCards = new ArrayList<>();
    }

    /**
     * Im dafür vorgesehenen Feld wird das geählte Atout angezeigt.
     * Das Atout ist in der Konstante gespeichert welches angezeigt werden soll.
     */
    private void showAtout(){
        //Wenn noch kein Atout gespeichert ist wird ein leeres Feld angezeigt
        if(Atout == null){
            atout.setImageResource(R.drawable.empty);
        }else{
            //Das gespeicherte Atout wird angezeigt
            switch(Atout){
                case HERZ: atout.setImageResource(R.drawable.herz);
                    break;
                case BLATT: atout.setImageResource(R.drawable.blatt);
                    break;
                case EICHEL: atout.setImageResource(R.drawable.eiche);
                    break;
                case SCHELLE: atout.setImageResource(R.drawable.schelle);
                    break;
                default: atout.setImageResource(R.drawable.empty);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Neu austeilen wenn ausgestiegen
        if(alreadyLeft) {
            Playground.austeilen();
            startActivity(new Intent(Playground.this, PopupStichansage.class));
        }

        //Das Atout wird angezeigt
        showAtout();
        anzeigen();

        //Das Spiel beginnt
//        play();
    }

    /**
     * Gibt das Gespeicherte Atout als Color Object zurück;
     * @return - Atout
     */
    public static Colors getAdout() {
        return Atout;
    }

    /**
     * Eine Karte wird ausgespielt. Diese wird im Parameter übergeben.
     */
    public void playCard(Card card){
        //Methode im Algotithmus aufrufen

    }

    /**
     * Neue runde die Karten werden neu ausgeteilt.
     * Jeder Spieler bekommt einen neuen Algorithmus
     */
    public static void austeilen(){
        player1 = new Algorithm(MainActivity.getCards(), 1);
        player2 = new Algorithm(MainActivity.getCards(), 2);
        player3 = new Algorithm(MainActivity.getCards(), 3);
        player4 = new Algorithm(MainActivity.getCards(), 4);
    }

    /**
     * Die Karten des Spielers anzeigen.
     */
    public void anzeigen(){
        card2.setImageDrawable(player1.getHoldingCards().get(1).getPicture());
        card3.setImageDrawable(player1.getHoldingCards().get(2).getPicture());
        card4.setImageDrawable(player1.getHoldingCards().get(3).getPicture());
        card5.setImageDrawable(player1.getHoldingCards().get(4).getPicture());
        card1.setImageDrawable(player1.getHoldingCards().get(0).getPicture());
    }

    /**
     * Spieler 1 zurückgeben
     * @return - Player1
     */
    public static Algorithm getPlayer1(){
        return player1;
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
                //Karte in das Feld gezogen
                if (event.getResult()) {
                    destination.setImageDrawable(move.getDrawable());
                    move.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Geschafft", Toast.LENGTH_SHORT).show();
//                    play();
                }
            default:
                break;
        }
        return true;
    }


    /**
     * Die gemachten Stiche sollen im Playground angezeigt werden.
     * Dazu wird bei dem Spieler der gestochen hat die neue Stichanzahl angezeigt.
     * @param player - Spieler der den Zug gewonnen hat
     * @param count - neue Stichanzahl des Spielers
     */
    public static void stitchesMade(int player, int count){
        switch(player){
            case 1: stitches_pl1.setText(""+count);
                break;
            case 2: stitches_pl2.setText(count);
                break;
            case 3: stitches_pl3.setText(count);
                break;
            case 4: stitches_pl4.setText(count);
                break;
        }
    }

    /**
     * Die angesagten Stiche werden angezeigt
     * @param player - Spieler der Siche ansagt
     * @param stiche - angesagte der Stiche
     */
    public static void angesagteSticheAnzeigen(Algorithm player, int stiche){
        if (player1.equals(player)) {
            pl1_announced.setText(stiche);
        } else if (player2.equals(player)) {
            pl2_announced.setText(stiche);
        } else if (player3.equals(player)) {
            pl3_announced.setText(stiche);
        } else if (player4.equals(player)) {
            pl4_announced.setText(stiche);
        }
    }

    /**
     * Jeder Spieler spielt eine Karte.
     * Dann wird die beste Karte ausgewertet, in das Log eingetragen neu ausgeteilt und wieder die Stichansage aufgerufen.
     */
    private void play(){
        //Karte spieler
//        inputCards.add(player1.getHoldingCards().get(0));
//        inputCards.add(player2.getResponseCard(inputCards.get(0)));
//        inputCards.add(player3.getResponseCard(Algorithm.getWinnerFromCards((Card[]) inputCards.toArray())));
//        inputCards.add(player4.getResponseCard(Algorithm.getWinnerFromCards((Card[]) inputCards.toArray())));

        //Gewinnder ermitteln
        Card winner = Algorithm.getWinnerFromCards((Card[]) inputCards.toArray());
        Toast.makeText(getApplicationContext(),""+winner.getColor()+winner.getValue(), Toast.LENGTH_SHORT).show();

        //Log eintragen



        //neu austeilen
        austeilen();

        //Stichansage aufrufen
        startActivity(new Intent(Playground.this, PopupStichansage.class));
    }
}