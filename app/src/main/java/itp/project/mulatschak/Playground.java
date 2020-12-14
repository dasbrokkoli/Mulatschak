package itp.project.mulatschak;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import itp.project.Enums.Colors;
import itp.project.Enums.Difficulty;

import java.util.List;

public class Playground extends AppCompatActivity {
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
    ImageView card1, card2, card3,card4,card5;
    //Liste für die Karten

    //Algorithmen für Spieler
    Algorithm player1;
    Algorithm player2;
    Algorithm player3;
    Algorithm player4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);
        alreadyLeft = false;
        //Spieler
        player1 = new Algorithm(MainActivity.getCards(), 1);

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
        card1.setImageDrawable(player1.getHoldingCards().get(0).getPicture());
//        card1.setImageResource(cards.get(0).getPicture()); //Karte über Liste anzeigen
        //Karte ausspielen
//        card1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                playCard(cards.get(0));
//            }
//        });
        card2 = findViewById(R.id.card);
        card2.setImageDrawable(player1.getHoldingCards().get(1).getPicture());
        card3 = findViewById(R.id.card2);
        card3.setImageDrawable(player1.getHoldingCards().get(2).getPicture());
        card4 = findViewById(R.id.card3);
        card4.setImageDrawable(player1.getHoldingCards().get(3).getPicture());
        card5 = findViewById(R.id.card4);
        card5.setImageDrawable(player1.getHoldingCards().get(4).getPicture());
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
        //Das Atout wird angezeigt
        showAtout();
    }

    /**
     * Gibt das Gespeicherte Atout als Color Object zurück;
     * @return - Atout
     */
    public static Colors getAdout() {
        return Atout;
    }

    public static Difficulty getDifficulty() {
        return null;
    }

    /**
     * Eine Karte wird ausgespielt. Diese wird im Parameter übergeben.
     */
    public void playCard(Card card){
        //Methode im Algotithmus aufrufen

    }

    /**
     * Die Karten des Spielers die angezeigt werden sollen werden alls Liste übergeben
     * @param c -Karten
     */
//    public static void setCards(List<Card> c){
//        cards = c;
//    }

}