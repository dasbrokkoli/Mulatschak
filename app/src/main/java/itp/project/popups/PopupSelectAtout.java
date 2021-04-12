package itp.project.popups;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.enums.Colors;
import itp.project.mulatschak.Algorithm;
import itp.project.mulatschak.Listeners;
import itp.project.mulatschak.R;

public class PopupSelectAtout extends AppCompatActivity {

    ImageView schelle, blatt, eiche, herz, ok, eyeBtn;
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_select_atout);
        PopupAtout.alreadyLeft = false;

        selected = "";


        eyeBtn = findViewById(R.id.eyeBtn);
        eyeBtn.setOnClickListener(Listeners.newOnClickListener(this));
        //Herz
        herz = findViewById(R.id.herz);
        herz.setOnClickListener(view -> {
            //Wird angezeigt dass gedrückt wurde
            reset();
            herz.setBackgroundColor(R.color.colorPrimary);
            selected = "herz";
            Algorithm.setAtout(Colors.HERZ);
        });

        //Blatt
        blatt = findViewById(R.id.blatt);
        blatt.setOnClickListener(view -> {
            //Wird angezeigt dass gedrückt wurde
            reset();
            blatt.setBackgroundColor(R.color.colorPrimary);
            selected = "blatt";
            Algorithm.setAtout(Colors.BLATT);
        });

        //Schelle
        schelle = findViewById(R.id.schelle);
        schelle.setOnClickListener(view -> {
            //Wird angezeigt dass gedrückt wurde
            reset();
            schelle.setBackgroundColor(R.color.colorPrimary);
            selected = "schelle";
            Algorithm.setAtout(Colors.SCHELLE);
        });

        //Eiche
        eiche = findViewById(R.id.eiche);
        eiche.setOnClickListener(view -> {
            //Wird angezeigt dass gedrückt wurde
            reset();
            eiche.setBackgroundColor(R.color.colorPrimary);
            selected = "eiche";
            Algorithm.setAtout(Colors.EICHEL);
        });

        //Eingabe ok
        ok = findViewById(R.id.best);
        ok.setOnClickListener(view -> {
            if (!selected.equals("")) {
                startActivity(new Intent(PopupSelectAtout.this, PopupKartentausch.class));
                //Popup schließen
                finish();
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}