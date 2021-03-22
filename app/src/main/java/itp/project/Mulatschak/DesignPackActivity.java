package itp.project.Mulatschak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.Popups.PopupDesign;

public class DesignPackActivity extends AppCompatActivity {

    ImageView i1;
    ImageView i2;
    ImageView i3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_pack);
        setTitle(getResources().getString(R.string.design));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final String choosePackId = "pack";
        i1 = findViewById(R.id.pack1);
        i2 = findViewById(R.id.pack2);
        i3 = findViewById(R.id.pack3);

        // für das gewählte Popup wird die Id mitgegeben
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupDesign.class);
                intent.putExtra(choosePackId, i1.getId());
                startActivity(intent);
            }
        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupDesign.class);
                intent.putExtra(choosePackId, i2.getId());
                startActivity(intent);
            }
        });

        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupDesign.class);
                intent.putExtra(choosePackId, i3.getId());
                startActivity(intent);
            }
        });
    }
}