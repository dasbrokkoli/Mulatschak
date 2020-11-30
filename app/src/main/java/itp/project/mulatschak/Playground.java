package itp.project.mulatschak;

import android.app.ActionBar;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Playground extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);

        ImageView atout = findViewById(R.id.atout);

        //Settings Button
        View settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Playground.this, SettingsActivity.class));
            }
        });

        //Tutorial Button
        View help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Playground.this, TutorialActivity.class));
            }
        });

        //PopUp Stichansage aufrufen
//        Button b = findViewById(R.id.button2);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity( new Intent(Playground.this, PopupStichansage.class));
//            }
//        });
//
//        //PopUp Atout aufrufen
//        Button a = findViewById(R.id.button3);
//        a.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity( new Intent(Playground.this, Popup_atout.class));
//            }
//        });
//
//        //PopUp Select Atout aufrufen
//        Button sa = findViewById(R.id.button4);
//        sa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity( new Intent(Playground.this, Popup_selectAtout.class));
//            }
//        });

        //PopUp Stichansage aufrufen
        Button b = findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(Playground.this, Popup_kartentausch.class));
            }
        });

    }

    public static Colors getAdout() {
        return null;
    }

    public static Difficulty getDifficulty() {
        return null;
    }

}