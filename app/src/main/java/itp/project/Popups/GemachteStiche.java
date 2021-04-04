package itp.project.Popups;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.Mulatschak.MainActivity;
import itp.project.Mulatschak.Playground;
import itp.project.Mulatschak.R;

/**
 * Es werden alle bereits gemachten Stiche angezeigt.
 * Die Stiche werden der Reihe nach angezeigt und können mit den Buttons next und back durchlaufen werden.
 */
public class GemachteStiche extends AppCompatActivity {

    TextView akt;
    int max, seite;
    Button back, next;
    ImageView card1, card2, card3, card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gemachte_stiche);
        setTitle("Gemachte Stiche");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button zurueck = findViewById(android.R.id.home);

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

        //Wenn kein Stich vorhanden
        if(Playground.gewonnene.isEmpty()||max ==0){
            Toast.makeText(this, "Noch keine gemachten Stiche", Toast.LENGTH_SHORT).show();

            //Wenn bereits Stiche vorhanden sind
        }else {

            //Die Karten sind im Playground in einer Liste gespeichert die erste Seite

            card1.setImageDrawable(Playground.gewonnene.get(0).getPicture());
            card2.setImageDrawable(Playground.gewonnene.get(1).getPicture());
            card3.setImageDrawable(Playground.gewonnene.get(2).getPicture());
            card4.setImageDrawable(Playground.gewonnene.get(3).getPicture());

            //Akt Zeigt den Stich an der gezeigt wird.
            //Beginnt bei 1
            seite = 1;


            seiteAnzeiigen();

        }
    }

    /**
     * Den nächsten Stich anzeigen
     */
    public void next() {
        if (seite < max) {
            seite++;
            //Die neuen Karten anzeigen
            int beginn = (seite-1)*4;
            card1.setImageDrawable(Playground.gewonnene.get(beginn).getPicture());
            card2.setImageDrawable(Playground.gewonnene.get(beginn+1).getPicture());
            card3.setImageDrawable(Playground.gewonnene.get(beginn+2).getPicture());
            card4.setImageDrawable(Playground.gewonnene.get(beginn+3).getPicture());
            seiteAnzeiigen();
        }
    }

    /**
     * Den vorherigen Stich anzeigen
     */
    public void back() {
        if (seite > 1) {
            seite--;
            //Die neuen Karten anzeigen
            int beginn = (seite-1)*4;
            card1.setImageDrawable(Playground.gewonnene.get(beginn).getPicture());
            card2.setImageDrawable(Playground.gewonnene.get(beginn+1).getPicture());
            card3.setImageDrawable(Playground.gewonnene.get(beginn+2).getPicture());
            card4.setImageDrawable(Playground.gewonnene.get(beginn+3).getPicture());
            seiteAnzeiigen();
        }
    }

    /**
     * Zeigt an welcher der bereits gemachten Stiche angezeig wird.
     */
    public void seiteAnzeiigen() {
        akt.setText(seite + "/" + max);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}