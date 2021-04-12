package itp.project.popups;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.enums.Difficulty;
import itp.project.mulatschak.Algorithm;
import itp.project.mulatschak.Listeners;
import itp.project.mulatschak.R;

/**
 * PopUp bei welchem man die Spiel-Schwierigkeit waehlen kann.
 */
public class PopupDifficulty extends AppCompatActivity {

    /**
     * Erstellt ein Window. Leitet bei Button Click auf "next" zum PopUpName weiter. Leitet aber nur weiter, wenn ein
     * RadioButton ausgewaeht wurde, ansosnten passiert nichts.
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_difficulty);

        Listeners.newListener(this);

        //Weiterleitung zu PopUpName
        Button showNamePopup = findViewById(R.id.showNamePopup);
        //Klick auf den "next"-Button
        showNamePopup.setOnClickListener(view -> {
            //Leitet nur weiter, wenn was ausgewaehlt wurde
            RadioGroup group = findViewById(R.id.radioGroup2);
            if (group.getCheckedRadioButtonId() != -1) {
                Algorithm.setDifficulty(getDifficulty());
                startActivity(new Intent(PopupDifficulty.this, PopupName.class));

            }
        });

    }


    /**
     * Liefert die Schwierigkeit zurueck, die ausgewaehlt wurde.
     *
     * @return Difficulty
     */
    private Difficulty getDifficulty() {
        Difficulty d = null;
        switch (((RadioGroup) findViewById(R.id.radioGroup2)).getCheckedRadioButtonId()) {
            case R.id.easy:
                d = Difficulty.EASY;
                break;
            case R.id.medium:
                d = Difficulty.MEDIUM;
                break;
            case R.id.hard:
                d = Difficulty.HARD;
                break;
            case R.id.unbeatable:
                d = Difficulty.UNBEATABLE;
                break;
        }

        return d;
    }
}