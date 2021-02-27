package itp.project.Popups;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.Mulatschak.Listeners;
import itp.project.Mulatschak.R;

public class PopupSaveGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_save_game);

        Listeners.newListener(this);

    }
}