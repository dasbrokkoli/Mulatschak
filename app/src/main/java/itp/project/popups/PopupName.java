package itp.project.popups;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.mulatschak.Listeners;
import itp.project.mulatschak.Playground;
import itp.project.mulatschak.R;

import java.util.ArrayList;
import java.util.List;

/**
 * PopUp bei welchem man die Spielernamen eingeben kann.
 */
public class PopupName extends AppCompatActivity {

    Button play;
    public static List<String> namen;

    /**
     * Estellt ein Window. Leitet bei Button Click auf die Spielfläche weiter. Dabei ist es egal, ob Namen angegeben
     * wurden.
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_name);

        Listeners.newListener(this);

        //Leitet zum Playground weiter
        play = findViewById(R.id.play);
        play.setOnClickListener(view -> {
            new Thread(() -> namen = getNames()).start();
            startActivity(new Intent(PopupName.this, Playground.class));
        });
    }

    /**
     * Liefert eine Array-List mit den Spielernamen zurueck.
     *
     * @return names
     */
    public synchronized List<String> getNames() {
        List<String> names = new ArrayList<>();
        String tmp;
        EditText ed;

        LinearLayout linlay = findViewById(R.id.nameList);


        SharedPreferences sp = getApplicationContext().getSharedPreferences("PlayerNames", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        for (int i = 0; i < linlay.getChildCount(); i++) {
            ed = (EditText) linlay.getChildAt(i);
            //System.out.println("ARGH" + findViewById(R.id.name+i));
            tmp = ed.getText().toString();
            names.add(tmp);
            if (!tmp.isEmpty()) {
                editor.putString("PlayerName" + i, tmp);
            }
        }
        editor.apply();

        return names;
    }
}