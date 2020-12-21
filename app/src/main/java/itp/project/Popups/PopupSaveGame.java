package itp.project.Popups;

import android.util.DisplayMetrics;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import itp.project.Mulatschak.R;

public class PopupSaveGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_save_game);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

    }
}