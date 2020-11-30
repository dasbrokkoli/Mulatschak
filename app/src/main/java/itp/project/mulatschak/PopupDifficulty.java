package itp.project.mulatschak;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.util.DisplayMetrics;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * PopUp bei welchem man die Schwierigkeit waehlen kann
 */
public class PopupDifficulty extends AppCompatActivity {

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
     * Macht bei jedem RadioButton was anderes.
     * Was gemacht werden soll muss noch definiert werden.
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.easy:
                if(checked)
                    //easy way
                    break;
            case R.id.medium:
                if(checked)
                    //medium way
                    break;
            case R.id.hard:
                if(checked)
                    //hard way
                    break;
        }
    }
}