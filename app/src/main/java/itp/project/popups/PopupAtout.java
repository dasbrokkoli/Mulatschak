package itp.project.popups;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.enums.Colors;
import itp.project.mulatschak.Algorithm;
import itp.project.mulatschak.Listeners;
import itp.project.mulatschak.Playground;
import itp.project.mulatschak.R;

public class PopupAtout extends AppCompatActivity {
    public static boolean alreadyLeft = false;
    ImageView atout, eyeBtn;
    Button mit, aus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_atout);


        eyeBtn = findViewById(R.id.eyeBtn);

        eyeBtn.setOnClickListener(Listeners.newOnClickListener(this));
        //Atout anzeigen
        atout = findViewById(R.id.at);
        showAtout();//Anzeigen


        //Button für mitgehen
        mit = findViewById(R.id.mit);
        mit.setOnClickListener(view -> {
            alreadyLeft = false;
            //Zu Kartentausch weiterleiten
            startActivity(new Intent(PopupAtout.this, PopupKartentausch.class));
            //Popup schließen
            finish();
        });

        //Button für aussteigen - eine Neue runde wird angesagt
        aus = findViewById(R.id.aus);
        aus.setOnClickListener(view -> {
            //die Punkte des Spielers
            int userPoints = Algorithm.getPoints().get(0);

            //Unter 4 Punkten darf der Spieler nicht aussteigen
            if (userPoints < 4) {
                Toast t = Toast.makeText
                        (getApplicationContext(),
                                "Sie haben unter 3 Punkte und dürfen nicht aussteigen!",
                                Toast.LENGTH_LONG);
                t.show();
            } else {
                //Neues Spiel (fängt mit stichansage an)
                alreadyLeft = true;
                //startActivity(new Intent(PopupAtout.this, Playground.class));
                finish();
            }
        });


        //Der Benutzer kann nicht aussteigen wenn er in der Runde davor ausgestiegen ist
        if (alreadyLeft || Algorithm.getAtout() == Colors.SCHELLE) {
            aus.setClickable(false);//Button kann nicht gedrückt werden
            aus.setBackgroundColor(R.color.grey);//Button ist heller um zu zeigen, dass er nichz gedrückt werden kann
        }
    }

    /**
     * Das Atout wird in dem dafür vorgesehenen Feld angezeigt.
     * Dafür wird das gespeicherte Atout der Klasse Playground verwendet.
     */
    private void showAtout() {
        Playground.setAtoutImg(atout);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}