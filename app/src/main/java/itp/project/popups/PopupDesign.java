package itp.project.popups;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.mulatschak.Listeners;
import itp.project.mulatschak.R;

public class PopupDesign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_design);

        Listeners.newListener(this);

        String background = "standard";
        // Auswählen des Packs
        TextView textView = findViewById(R.id.chosen_pack_name);
        ImageView card = findViewById(R.id.chosen_card);

        // aus DesignPackActvity mitgegebene ID
        int chosenPack = getIntent().getExtras().getInt("pack");
        // je nach Id wechselt sich der Name und das Bild
        switch (chosenPack) {
            case R.id.pack1:
                textView.setText("Standard");
                card.setForeground(getDrawable(R.drawable.card_standard_backside));
                background = "standard";
                break;
            case R.id.pack2:
                textView.setText("Easter-Edition");
                card.setForeground(getDrawable(R.drawable.ic_hintergrund_ostern));
                background = "easter";
                break;
            case R.id.pack3:
                textView.setText("Mulatschak");
                card.setForeground(getDrawable(R.drawable.hintergrund_mulatschak));
                background = "mulatschak";
                break;
            case R.id.pack4:
                textView.setText("Mathe");
                card.setForeground(getDrawable(R.drawable.mathe));
                background = "math";
                break;
            case R.id.pack5:
                textView.setText("Totenkopf");
                card.setForeground(getDrawable(R.drawable.totenkopf));
                background = "dead";
                break;
        }

        // Auswählen
        Button choosePack = findViewById(R.id.select_pack);
        String finalBackground = background;
        choosePack.setOnClickListener(view -> {
            SharedPreferences pref = getSharedPreferences("Design", MODE_PRIVATE);
            pref.edit().putString("background", finalBackground).apply();
            Toast.makeText(this, R.string.backgroundRestart, Toast.LENGTH_LONG).show();
            this.finishAffinity();
        });
    }

    /**
     * Schließt das Popup wieder
     *
     * @param view Button
     */
    public void closePopup(View view) {
        finish();
    }
}