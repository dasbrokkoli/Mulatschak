package itp.project.mulatschak;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Card> cards = new ArrayList<>();
    private Algorithm algorithm = new Algorithm(cards);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCards();
    }

    private void initCards() {
        cards.add(new Card(getResources().getDrawable(R.drawable.card_heart7, null), Colors.HERZ, Values.W7));
    }

    public List<Card> getCards(){
        return cards;
    }
}