package itp.project.Popups;

import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import itp.project.Mulatschak.Playground;
import itp.project.Mulatschak.R;

public class Popup_holdingCards extends AppCompatActivity {

    ImageView card1,card2,card3,card4,card5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_holding_cards);

        //ImageViews
        card1.findViewById(R.id.card1);
        card2.findViewById(R.id.card2);
        card3.findViewById(R.id.card3);
        card4.findViewById(R.id.card4);
        card5.findViewById(R.id.card5);

        //Karten anzeigen
        card1.setImageDrawable(Playground.getPlayer(0).getHoldingCards().get(0).getPicture());
        card2.setImageDrawable(Playground.getPlayer(0).getHoldingCards().get(1).getPicture());
        card3.setImageDrawable(Playground.getPlayer(0).getHoldingCards().get(2).getPicture());
        card4.setImageDrawable(Playground.getPlayer(0).getHoldingCards().get(3).getPicture());
        card5.setImageDrawable(Playground.getPlayer(0).getHoldingCards().get(4).getPicture());
    }
}