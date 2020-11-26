package itp.project.mulatschak;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Popup_atout extends AppCompatActivity {
    ImageView atout;
    Button mit, aus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_atout);

        //Popup größe
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int witdh = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(witdh*.8), (int)(height*.8));//80% der höhe und Breite des Bildschirms

        //Atout anzeigen
        atout = findViewById(R.id.at);
        atout.setImageResource(R.drawable.blatt);//Blatt wird angezeigt

        mit = findViewById(R.id.mit);
        mit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Popup schließen
                finish();
            }
        });

        aus = findViewById(R.id.aus);
        aus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Popup schließen
                finish();
            }
        });
    }
}