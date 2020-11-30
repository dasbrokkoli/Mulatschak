package itp.project.mulatschak;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Card> cards = new ArrayList<>();
    private Algorithm algorithm = new Algorithm(cards,null);

    //fuer PopUp - Schwierigkeit
    Button showPopupBtn;
    ImageButton closePopupBtn;
    PopupWindow popupWindow;
    ConstraintLayout constraintLayout;
    Button next;

    //LogPopup
    Button showLogBtn;
    ImageButton closeLogView;
    PopupWindow logWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this,Playground.class));

        setContentView(R.layout.activity_main);
        initCards();

        View settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
       });

        View help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TutorialActivity.class));
            }
        });

        //PopUp
        showPopupBtn = (Button) findViewById(R.id.showPopupBtn);
        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PopupDifficulty.class));
            }
        });

        //LogPopup
        showLogBtn = findViewById(R.id.log);
        showLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View logView = layoutInflater.inflate(R.layout.popup_log, null);

                closeLogView = logView.findViewById(R.id.closeLogBtn);

                logWindow = new PopupWindow(logView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                logWindow.showAtLocation(constraintLayout,Gravity.CENTER,0,0);
                closeLogView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logWindow.dismiss();
                    }
                });
            }
        });


    }

    private void initCards() {
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_herz_sieben, null), Colors.HERZ, Values.W7));
    }

    public List<Card> getCards(){
        return cards;
    }
}