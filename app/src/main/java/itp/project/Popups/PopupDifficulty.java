package itp.project.Popups;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import itp.project.Enums.Difficulty;
import itp.project.Mulatschak.R;

/**
 * PopUp bei welchem man die Spiel-Schwierigkeit waehlen kann.
 */
public class PopupDifficulty extends AppCompatActivity {

    /**
     * Ersellt ein Window.
     * Leitet bei Button Click auf "next" zum PopUpName weiter.
     * Leitet aber nur weiter, wenn ein RadiButton ausgewaeht wurde, ansosnten passiert nichts.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_difficulty);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        //Weiterleitung zu PopUpName
        Button showNamePopup = (Button) findViewById(R.id.showNamePopup);
        showNamePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            //Klick auf den "next"-Button
            public void onClick(View view) {
                //Leitet nur weiter, wenn was ausgewaehlt wurde
                RadioGroup group = findViewById(R.id.radioGroup2);
                if (group.getCheckedRadioButtonId() != -1) {
                    startActivity(new Intent(PopupDifficulty.this, PopupName.class));

                }
            }
        });

    }


    /**
     * Liefert die Schwierigkeit zurueck, die ausgewaehlt wurde.
     * @param view
     * @return Difficulty
     */
    public Difficulty getDifficulty(View view){
        boolean checked = ((RadioButton) view).isChecked();
        Difficulty d = null;
        switch(view.getId()) {
            case R.id.easy:
                if(checked)
                    d = Difficulty.EASY;
                break;
            case R.id.medium:
                if(checked)
                    d = Difficulty.MEDIUM;
                break;
            case R.id.hard:
                if(checked)
                    d = Difficulty.HARD;
                break;
            case R.id.unbeatable:
                if(checked)
                    d = Difficulty.UNBEATABLE;
                break;
        }

        return d;
    }
}