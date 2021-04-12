package itp.project.popups;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.mulatschak.Listeners;
import itp.project.mulatschak.R;

public class PopupDesign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_design);

        Listeners.newListener(this);

        // Auswählen des Packs
        TextView textView = findViewById(R.id.chosen_pack_name);
        ImageView card = findViewById(R.id.chosen_card);

        // aus DesignPackActvity mitgegebene ID
        int chosenPack = getIntent().getExtras().getInt("pack");
        // je nach Id wechselt sich der Name und das Bild
        switch (chosenPack) {
            case R.id.pack1:
                textView.setText("Mulatschak");
                card.setForeground(getDrawable(R.drawable.card_standard_backside));
                break;
            case R.id.pack2:
                textView.setText("Red");
                card.setForeground(getDrawable(R.drawable.hintergrund_test1));
                break;
            case R.id.pack3:
                textView.setText("Blue");
                card.setForeground(getDrawable(R.drawable.hintergrund_test2));
                break;
        }

        // Auswählen
        /*Button choosePack = findViewById(R.id.select_pack);
        choosePack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DesignPackActivity.i1.set(20,20,20,20);
                DesignPackActivity.i1.setBackgroundColor(Color.GREEN);
                finish();
            }
        });*/
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