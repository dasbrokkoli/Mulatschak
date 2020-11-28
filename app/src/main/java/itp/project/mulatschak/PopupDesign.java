package itp.project.mulatschak;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class PopupDesign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_design);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * 0.8));

        // Auswählen des Packs
        TextView textView = findViewById(R.id.chosen_pack_name);
        ImageView card = findViewById(R.id.chosen_card);

        // aus DesignPackActvity mitgegebene ID
        int chosenPack = getIntent().getExtras().getInt("pack");
        // je nach Id wechselt sich der Name und das Bild
        switch (chosenPack) {
            case R.id.pack1:
                textView.setText("Mulatschak");
                card.setForeground(getDrawable(R.drawable.hintergrund));
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
    }

    public void closePopup(View view) {
        finish();
    }
}