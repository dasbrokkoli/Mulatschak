package itp.project.mulatschak;

import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Card> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initCards();
    }

    private void initCards() {
        cards.add(new Card(getResources().getDrawable(R.drawable.card_heart7, null)));
    }
}