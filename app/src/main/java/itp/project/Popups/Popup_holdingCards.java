package itp.project.Popups;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import itp.project.Mulatschak.Playground;
import itp.project.Mulatschak.R;

public class Popup_holdingCards extends AppCompatActivity {

    ImageView card1,card2,card3,card4,card5;
    ImageButton close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_holding_cards);

        //Popup größe
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int width = dm.widthPixels;
        final int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .8));//80% der höhe und Breite des Bildschirms


        //ImageViews
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);

        //Karten anzeigen
        card1.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(0).getPicture());
        card2.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(1).getPicture());
        card3.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(2).getPicture());
        card4.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(3).getPicture());
        card5.setImageDrawable(Playground.getPlayer(1).getHoldingCards().get(4).getPicture());

        //Close Button
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Activity schließen
                finish();
            }
        });
    }
}