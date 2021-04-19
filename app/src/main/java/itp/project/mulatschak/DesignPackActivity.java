package itp.project.mulatschak;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.popups.PopupDesign;

public class DesignPackActivity extends AppCompatActivity {

    ImageView i1;
    ImageView i2;
    ImageView i3;
    ImageView i4;
    ImageView i5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_pack);
        setTitle(getResources().getString(R.string.design));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MainActivity.background = getDrawable(R.drawable.card_standard_backside);
        final String choosePackId = "pack";
        i1 = findViewById(R.id.pack1);
        i2 = findViewById(R.id.pack2);
        i3 = findViewById(R.id.pack3);
        i4 = findViewById(R.id.pack4);
        i5 = findViewById(R.id.pack5);

        // für das gewählte Popup wird die Id mitgegeben
        i1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PopupDesign.class);
            intent.putExtra(choosePackId, i1.getId());
            startActivity(intent);
        });

        i2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PopupDesign.class);
            intent.putExtra(choosePackId, i2.getId());
            startActivity(intent);
        });

        i3.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PopupDesign.class);
            intent.putExtra(choosePackId, i3.getId());
            startActivity(intent);
        });

        i4.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PopupDesign.class);
            intent.putExtra(choosePackId, i4.getId());
            startActivity(intent);
        });

        i5.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PopupDesign.class);
            intent.putExtra(choosePackId, i5.getId());
            startActivity(intent);
        });
    }
}