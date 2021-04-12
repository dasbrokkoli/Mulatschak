package itp.project.popups;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.mulatschak.*;

public class PopupDesign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_design);

        Listeners.newListener(this);

        Drawable background = getDrawable(R.drawable.card_standard_back);
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
                background = getDrawable(R.drawable.card_standard_backside);
                break;
            case R.id.pack2:
                textView.setText("Easter-Edition");
                card.setForeground(getDrawable(R.drawable.ic_hintergrund_ostern));
                background = getDrawable(R.drawable.ic_hintergrund_ostern);
                break;
            case R.id.pack3:
                textView.setText("Mulatschak");
                card.setForeground(getDrawable(R.drawable.hintergrund_mulatschak));
                background = getDrawable(R.drawable.hintergrund_mulatschak);
                break;
        }

        // Auswählen
        Button choosePack = findViewById(R.id.select_pack);
        Drawable finalBackground = background;
        choosePack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.background = finalBackground;
                finish();
            }
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