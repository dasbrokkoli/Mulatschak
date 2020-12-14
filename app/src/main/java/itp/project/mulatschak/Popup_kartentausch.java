package itp.project.mulatschak;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Popup_kartentausch extends AppCompatActivity {

    ImageView card1, card2, card3, card4, card5;
    ImageButton change, delete;
    List<Integer> cards;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_kartentausch);

        //Popup größe
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int witdh = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(witdh*.8), (int)(height*.8));//80% der höhe und Breite des Bildschirms

        //Karten des SPielers in liste
        cards = new ArrayList<>();
        cards.add(R.drawable.card_standard_schelle_acht);
        cards.add(R.drawable.card_standard_schelle_acht);
        cards.add(R.drawable.card_standard_schelle_acht);
        cards.add(R.drawable.card_standard_schelle_acht);
        cards.add(R.drawable.card_standard_schelle_acht);

        //Karten des SPielers anzeigen
        card1 = findViewById(R.id.card1);
        card1.setImageResource(cards.get(0));

        card2 = findViewById(R.id.card2);
        card2.setImageResource(cards.get(1));

        card3 = findViewById(R.id.card3);
        card3.setImageResource(cards.get(2));

        card4 = findViewById(R.id.card4);
        card4.setImageResource(cards.get(3));

        card5 = findViewById(R.id.card5);
        card5.setImageResource(cards.get(4));

        //Ok Button
        change = findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(Popup_kartentausch.this, Playground.class));
                finish();
            }
        });

        //Löschen Button
        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}