package itp.project.mulatschak;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class PopupStichansage extends AppCompatActivity {

    Button skip, muli;
    TextView one,two, three,four, highest;//Stichanzahl

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_stichansage);

        //Popup größe
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int witdh = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(witdh*.8), (int)(height*.8));//80% der höhe und Breite des Bildschirms

        //Button Muli
        muli = findViewById(R.id.muli);
        muli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Popup schließen
                finish();
            }
        });

        //Skip Button
        skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Popup schließen
                finish();
            }
        });

        //Ein Stich
        one = findViewById(R.id.stich1);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Popup schließen
                finish();
            }
        });

        //Zwei Stiche
        two = findViewById(R.id.stich2);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Popup schließen
                finish();
            }
        });

        //Drei Stiche
        three = findViewById(R.id.stich3);
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Popup schließen
                finish();
            }
        });

        //Vier Stichee
        four = findViewById(R.id.stich4);
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Popup schließen
                finish();
            }
        });

        //Bereits angesagte Stiche
        highest = findViewById(R.id.highest);

    }
}