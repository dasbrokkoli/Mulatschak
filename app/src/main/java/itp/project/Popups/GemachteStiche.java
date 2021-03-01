package itp.project.Popups;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import itp.project.Mulatschak.Playground;
import itp.project.Mulatschak.R;

/**
 * Es werden alle bereits gemachten Stiche angezeigt.
 * Die Stiche werden der Reihe nach angezeigt und können mit den Buttons next und back durchlaufen werden.
 */
public class GemachteStiche extends AppCompatActivity {

    TextView akt;
    int max,seite;
    Button back, next;
    ImageView card1, card2, card3,card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gemachte_stiche);

        akt = findViewById(R.id.aktuell);

        //Karten
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);

        //Buttons
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        //Anzahl wird die Anzahl der gemachten Stiche angezeigt
        max = Integer.parseInt((String) Playground.stitches[0].getText());

        //TODO:Stiche in einer Liste speichern


        //Akt Zeigt den Stich an der gezeigt wird.
        //Beginnt bei 1
        seite = 1;

        //TODO: wenn kein Stich vorhanden


        seiteAnzeiigen();

    }

    /**
     * Zeigt an welcher der bereits gemachten Stiche angezeig wird.
     */
    public void seiteAnzeiigen(){
        akt.setText(seite+"/"+max);
    }

    /**
     * Den nächsten Stich anzeigen
     */
    public void next(){
        if(seite<max) {
            seite++;
            //TODO: die neuen Karten anzeigen


            seiteAnzeiigen();
        }
    }

    /**
     * Den vorherigen Stich anzeigen
     */
    public void back(){
        if(seite>1) {
            seite--;
            //TODO: die neuen Karten anzeigen


            seiteAnzeiigen();
        }
    }
}