package itp.project.Popups;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.Enums.Colors;
import itp.project.Mulatschak.Algorithm;
import itp.project.Mulatschak.R;

public class Popup_selectAtout extends AppCompatActivity {

    ImageView schelle, blatt, eiche, herz, ok, eyeBtn;
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_select_atout);
        Popup_atout.alreadyLeft = false;

        selected = "";

        //Popup größe
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int width = dm.widthPixels;
        final int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .8));//80% der höhe und Breite des Bildschirms

        eyeBtn = findViewById(R.id.eyeBtn);
        eyeBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.performClick();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        getWindow().setLayout(0, 0);
                        System.out.println("Down");
                        return true;
                    case MotionEvent.ACTION_UP:
                        getWindow().setLayout((int) (width * .8), (int) (height * .8));
                        System.out.println("Up");
                        return true;
                }
                return false;
            }
        });
        //Herz
        herz = findViewById(R.id.herz);
        herz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Wird angezeigt dass gedrückt wurde
                reset();
                herz.setBackgroundColor(R.color.colorPrimary);
                selected = "herz";
                Algorithm.setAtout(Colors.HERZ);
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
                Algorithm.setAtout(Colors.BLATT);
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
                Algorithm.setAtout(Colors.SCHELLE);
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
                Algorithm.setAtout(Colors.EICHEL);
            }
        });

        //Eingabe ok
        ok = findViewById(R.id.best);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selected.equals("")) {
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