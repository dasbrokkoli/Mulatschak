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

        //PopUp
        Button b = findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(Playground.this, PopupStichansage.class));
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