package itp.project.Mulatschak;

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
import itp.project.Enums.Colors;
import itp.project.Enums.Values;
import itp.project.Popups.PopupDifficulty;
import itp.project.Mulatschak.R;
import itp.project.Popups.PopupLog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final List<Card> cards = new ArrayList<>();

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
        showLogBtn = (Button) findViewById(R.id.log);
        showLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PopupLog.class));
            }
        });

        /*constraintLayout = (ConstraintLayout) findViewById(R.id.mainConstraintLayout);
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
        });*/

        //HoldingCards
        HoldingCards.setWeliStatus(false);
    }

    private void initCards() {
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_herz_sieben, null), Colors.HERZ, Values.W7));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_herz_acht, null), Colors.HERZ, Values.W8));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_herz_neun, null), Colors.HERZ, Values.W9));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_herz_zehn, null), Colors.HERZ, Values.W10));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_herz_unter, null), Colors.HERZ, Values.UNTER));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_herz_ober, null), Colors.HERZ, Values.OBER));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_herz_koenig, null), Colors.HERZ, Values.KOENIG));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_herz_ass, null), Colors.HERZ, Values.DAUS));

        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_blatt_sieben, null), Colors.BLATT, Values.W7));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_blatt_acht, null), Colors.BLATT, Values.W8));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_blatt_neun, null), Colors.BLATT, Values.W9));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_blatt_zehn, null), Colors.BLATT, Values.W10));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_blatt_unter, null), Colors.BLATT, Values.UNTER));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_blatt_ober, null), Colors.BLATT, Values.OBER));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_blatt_koenig, null), Colors.BLATT, Values.KOENIG));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_blatt_ass, null), Colors.BLATT, Values.DAUS));

        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_eiche_sieben, null), Colors.EICHEL, Values.W7));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_eiche_acht, null), Colors.EICHEL, Values.W8));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_eiche_neun, null), Colors.EICHEL, Values.W9));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_eiche_zehn, null), Colors.EICHEL, Values.W10));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_eiche_unter, null), Colors.EICHEL, Values.UNTER));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_eiche_ober, null), Colors.EICHEL, Values.OBER));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_eiche_koenig, null), Colors.EICHEL, Values.KOENIG));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_eiche_ass, null), Colors.EICHEL, Values.DAUS));

        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_schelle_sieben, null), Colors.SCHELLE, Values.W7));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_schelle_acht, null), Colors.SCHELLE, Values.W8));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_schelle_neun, null), Colors.SCHELLE, Values.W9));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_schelle_zehn, null), Colors.SCHELLE, Values.W10));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_schelle_unter, null), Colors.SCHELLE, Values.UNTER));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_schelle_ober, null), Colors.SCHELLE, Values.OBER));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_schelle_koenig, null), Colors.SCHELLE, Values.KOENIG));
        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_schelle_ass, null), Colors.SCHELLE, Values.DAUS));

        cards.add(new Card(getResources().getDrawable(R.drawable.card_standard_weli,null),Colors.WELI,Values.WELI));
    }

    public static List<Card> getCards(){
        return cards;
    }
}