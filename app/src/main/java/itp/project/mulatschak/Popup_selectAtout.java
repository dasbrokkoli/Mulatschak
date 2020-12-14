package itp.project.mulatschak;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import itp.project.Enums.Colors;

public class Popup_selectAtout extends AppCompatActivity {

    ImageView schelle, blatt, eiche, herz, ok;
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_select_atout);

        selected = "";

        //Popup größe
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int witdh = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(witdh*.8), (int)(height*.8));//80% der höhe und Breite des Bildschirms

        //Herz
        herz = findViewById(R.id.herz);
        herz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Wird angezeigt dass gedrückt wurde
                reset();
                herz.setBackgroundColor(R.color.colorPrimary);
                selected = "herz";
                Playground.Atout = Colors.HERZ;
            }
        });

        //Blatt
        blatt = findViewById(R.id.blatt);
        blatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Wird angezeigt dass gedrückt wurde
                reset();
                blatt.setBackgroundColor(R.color.colorPrimary);
                selected = "blatt";
                Playground.Atout = Colors.BLATT;
            }
        });

        //Schelle
        schelle = findViewById(R.id.schelle);
        schelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Wird angezeigt dass gedrückt wurde
                reset();
                schelle.setBackgroundColor(R.color.colorPrimary);
                selected = "schelle";
                Playground.Atout = Colors.SCHELLE;
            }
        });

        //Eiche
        eiche = findViewById(R.id.eiche);
        eiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Wird angezeigt dass gedrückt wurde
                reset();
                eiche.setBackgroundColor(R.color.colorPrimary);
                selected = "eiche";
                Playground.Atout = Colors.EICHEL;
            }
        });

        //Eingabe ok
        ok = findViewById(R.id.best);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selected.equals("")){
                    startActivity(new Intent(Popup_selectAtout.this, Popup_kartentausch.class));
                    //Popup schließen
                    finish();
                }
            }
        });

    }

    /**
     * Setzt alle wieder zurück auf Hintergund weiß und nicht ausgwählt.
     */
    private void reset() {
        herz.setBackgroundResource(android.R.color.transparent);
        eiche.setBackgroundResource(android.R.color.transparent);
        blatt.setBackgroundResource(android.R.color.transparent);
        schelle.setBackgroundResource(android.R.color.transparent);
    }
}