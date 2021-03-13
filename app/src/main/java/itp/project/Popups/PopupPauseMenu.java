package itp.project.Popups;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.Mulatschak.Listeners;
import itp.project.Mulatschak.MainActivity;
import itp.project.Mulatschak.R;

public class PopupPauseMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_pause_menu);
        Listeners.newListener(this);

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> finish());

        Button quitButton = findViewById(R.id.quit_button);
        quitButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));

    }
}