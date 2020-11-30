package itp.project.mulatschak;

import android.app.ActionBar;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
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
import androidx.constraintlayout.widget.ConstraintLayout;

public class Playground extends AppCompatActivity {

    //LogPopup
    Button showLogBtn;
    ImageButton closeLogView;
    PopupWindow logWindow;
    ConstraintLayout constraintLayout;

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

        constraintLayout = (ConstraintLayout) findViewById(R.id.playgroundConstraintLayout);
        showLogBtn = findViewById(R.id.logButton);
        showLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) Playground.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View logView = layoutInflater.inflate(R.layout.popup_log, null);

                closeLogView = logView.findViewById(R.id.closeLogBtn);

                logWindow = new PopupWindow(logView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                logWindow.showAtLocation(constraintLayout, Gravity.CENTER,0,0);
                closeLogView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logWindow.dismiss();
                    }
                });
            }
        });

        startActivity(new Intent(Playground.this, PopupWeli.class));
    }

    public static Colors getAdout() {
        return null;
    }

    public static Difficulty getDifficulty() {
        return null;
    }

}