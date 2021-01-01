package itp.project.Popups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import itp.project.Mulatschak.Playground;
import itp.project.Mulatschak.R;

import java.util.ArrayList;
import java.util.List;

/**
 * PopUp bei welchem man die Spielernamen eingeben kann.
 */
public class PopupName extends AppCompatActivity {

    Button play;

    /**
     * Estellt ein Window.
     * Leitet bei Button Click auf die Spielfläche weiter.
     * Dabei ist es egal, ob Namen angegeben wurden.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_name);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));


        //Leitet zum Playground weiter
        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNames();
                startActivity(new Intent(PopupName.this, Playground.class));
            }
        });
    }

    /**
     * Liefert eine Array-List mit den Spielernamen zurueck.
     * @return names
     */
    public List<String> getNames() {
        List<String> names = new ArrayList();
        String tmp;
        EditText ed;

        LinearLayout linlay = findViewById(R.id.nameList);


        SharedPreferences sp = getApplicationContext().getSharedPreferences("PlayerNames",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        for(int i = 0; i < linlay.getChildCount(); i++) {
            ed = (EditText) linlay.getChildAt(i);;
            //System.out.println("ARGH" + findViewById(R.id.name+i));
            tmp = ed.getText().toString();
            names.add(tmp);
            if(!tmp.isEmpty()){
                editor.putString("PlayerName"+i, tmp);
            }
        }
        editor.apply();

        return names;
    }
}