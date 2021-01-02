package itp.project.Mulatschak;

import android.content.ClipData;
import android.content.Intent;
import android.view.View;
import android.view.*;
import android.widget.*;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import itp.project.Popups.PopupLog;
import itp.project.Popups.PopupStichansage;
import itp.project.Popups.Popup_atout;

import java.util.*;

public class Playground extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener{
//    public static boolean alreadyLeft;
    
    ImageView atout;

    //LogPopup
    Button showLogBtn;
    ImageButton closeLogView;
    PopupWindow logWindow;
    ConstraintLayout constraintLayout;

    //Cards
    ImageView card4, card1, card2, card3,card5, destination;
            static ImageView move;
    //Gemachte Stiche
    //public static TextView stitches_pl1, stitches_pl2, stitches_pl3, stitches_pl4;
    public static TextView[] stitches = new TextView[4];
    private static TextView pl1_announced,pl2_announced,pl3_announced,pl4_announced;
    //Liste für die Karten

    //Algorithmen für Spieler
    static Algorithm[] players = new Algorithm[4];

    private static List<Card> inputCards;

    //View diffView = findViewById(R.layout.popup_difficulty);

    //TODO Ausgespielte Karten von jedem Spieler in die Map eintragen (playerIndex, Ausgespielte Karte)
    private final BiMap<Integer,Card> cardsOnFloor = HashBiMap.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);
//        alreadyLeft = false;
        //Spieler
        austeilen();
        Algorithm.rundenbeginn();

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
        //LogPopup
        showLogBtn = findViewById(R.id.logButton);
        showLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Playground.this, PopupLog.class));
            }
        });

        //Karten aus dem Algorithmus


        //Cards
        card1 = findViewById(R.id.card1);
        card1.setOnTouchListener(this);
        card2 = findViewById(R.id.card2);
        card2.setOnTouchListener(this);
        card3 = findViewById(R.id.card3);
        card3.setOnTouchListener(this);
        card4 = findViewById(R.id.card4);
        card4.setOnTouchListener(this);
        card5 = findViewById(R.id.card5);
        card5.setOnTouchListener(this);

        destination = findViewById(R.id.imageView);
        destination.setOnDragListener(this);

        //Stiche gemacht
        pl1_announced = findViewById(R.id.player_announced);
        pl2_announced = findViewById(R.id.pl1_announced);
        pl3_announced = findViewById(R.id.pl2_announced);
        pl4_announced = findViewById(R.id.pl3_announced);
        //stitches_pl1 = findViewById(R.id.player_stitches);
        //stitches_pl2 = findViewById(R.id.pl1_stitches);
        //stitches_pl3 = findViewById(R.id.pl2_stitches);
        //stitches_pl4 = findViewById(R.id.pl3_stitches);

        stitches[0] = findViewById(R.id.player_stitches);
        stitches[1] = findViewById(R.id.pl1_stitches);
        stitches[2] = findViewById(R.id.pl2_announced);
        stitches[3] = findViewById(R.id.pl3_stitches);

        inputCards = new ArrayList<>();
    }

    /**
     * Im dafür vorgesehenen Feld wird das geählte Atout angezeigt.
     * Das Atout ist in der Konstante gespeichert welches angezeigt werden soll.
     */
    private void showAtout(){
        //Wenn noch kein Atout gespeichert ist wird ein leeres Feld angezeigt
        if(Algorithm.getAtout() == null){
            atout.setImageResource(R.drawable.empty);
        }else{
            //Das gespeicherte Atout wird angezeigt
            switch(Algorithm.getAtout()){
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
        if(Popup_atout.alreadyLeft) {
           neuAusteilen();
        }

        //Das Atout wird angezeigt
        showAtout();
        anzeigen();

        //Das Spiel beginnt
//        play();
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
        HoldingCards.setAllCards(MainActivity.getCards());
        players[0] = new Algorithm(MainActivity.getCards(), 1);
        players[1] = new Algorithm(MainActivity.getCards(), 2);
        players[2] = new Algorithm(MainActivity.getCards(), 3);
        players[3] = new Algorithm(MainActivity.getCards(), 4);
    }

    public void neuAusteilen(){
        austeilen();
        anzeigen();
        startActivity(new Intent(Playground.this, PopupStichansage.class));
    }

    /**
     * Die Karten des Spielers anzeigen.
     */
    public void anzeigen(){
        card1.setImageDrawable(players[0].getHoldingCards().get(0).getPicture());
        card2.setImageDrawable(players[0].getHoldingCards().get(1).getPicture());
        card3.setImageDrawable(players[0].getHoldingCards().get(2).getPicture());
        card4.setImageDrawable(players[0].getHoldingCards().get(3).getPicture());
        card5.setImageDrawable(players[0].getHoldingCards().get(4).getPicture());
    }

    /**
     * Spieler zurückgeben
     * @return - Player
     */
    public static Algorithm getPlayer(int playernumber){
        return players[playernumber-1];
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
                    cardsOnFloor.put(0, getCardfromView(move));
                    Toast.makeText(getApplicationContext(),getCardfromView(move).getColor() + "" + getCardfromView(move).getValue(), Toast.LENGTH_SHORT).show();
                    play();
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
            case 1: stitches[0].setText(""+count);
                break;
            case 2: stitches[1].setText(""+count);
                break;
            case 3: stitches[2].setText(""+count);
                break;
            case 4: stitches[4].setText(""+count);
                break;
        }

    }

    public void whichCardWon() {
        Card winner = Algorithm.getWinnerFromCards((Card[]) cardsOnFloor.values().toArray());
        int winnerIndex = cardsOnFloor.inverse().get(winner);
        players[winnerIndex].wonThisCard();
    }

    /**
     * Die angesagten Stiche werden angezeigt
     * @param player - Spieler der Siche ansagt
     * @param stiche - angesagte der Stiche
     */
    public static void angesagteSticheAnzeigen(Algorithm player, int stiche){
        if (players[0].equals(player)) {
            pl1_announced.setText(String.valueOf(stiche));
        } else if (players[1].equals(player)) {
            pl2_announced.setText(String.valueOf(stiche));
        } else if (players[2].equals(player)) {
            pl3_announced.setText(String.valueOf(stiche));
        } else if (players[3].equals(player)) {
            pl4_announced.setText(String.valueOf(stiche));
        }
    }

    /**
     * Jeder Spieler spielt eine Karte.
     * Dann wird die beste Karte ausgewertet, in das Log eingetragen neu ausgeteilt und wieder die Stichansage aufgerufen.
     */
    private void play(){
        for (int i = 1; cardsOnFloor.size()<4; i++){
            Card[] cArray = new Card[i];
            cardsOnFloor.values().toArray(cArray);
            cardsOnFloor.put(i,players[i].getResponseCard(Algorithm.getWinnerFromCards(cArray)));
            System.out.println(cardsOnFloor.get(i).getColor() + " " + cardsOnFloor.get(i).getValue());
        }

        //Gewinnder ermitteln
        Card[] cArray = new Card[4];
        cardsOnFloor.values().toArray(cArray);
        Card winner = Algorithm.getWinnerFromCards(cArray);
        Toast.makeText(getApplicationContext(),winner.getColor()+" "+winner.getValue(), Toast.LENGTH_SHORT).show();

        //Log eintragen



        //neu austeilen
        //austeilen();

        //Stichansage aufrufen
        //startActivity(new Intent(Playground.this, PopupStichansage.class));
    }

    public static Card getCardfromView(ImageView v){
        Card change;//Die zu tauschende Karte
        switch (v.getId()){
            case R.id.card1:
                change = Playground.getPlayer(1).getHoldingCards().get(0);
                break;
            case R.id.card2:
                change = Playground.getPlayer(1).getHoldingCards().get(1);
                break;
            case R.id.card3:
                change = Playground.getPlayer(1).getHoldingCards().get(2);
                break;
            case R.id.card4:
                change = Playground.getPlayer(1).getHoldingCards().get(3);
                break;
            case R.id.card5:
                change = Playground.getPlayer(1).getHoldingCards().get(4);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        return change;
    }
}