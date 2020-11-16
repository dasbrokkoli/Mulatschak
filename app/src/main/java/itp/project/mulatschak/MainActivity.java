package itp.project.mulatschak;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Card> cards = new ArrayList<>();
    private Algorithm algorithm = new Algorithm(cards,null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCards();

        View settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
       });
    }

    private void initCards() {
        cards.add(new Card(getResources().getDrawable(R.drawable.card_heart7, null), Colors.HERZ, Values.W7));
    }

    public List<Card> getCards(){
        return cards;
    }
}