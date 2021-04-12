package itp.project.popups;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.mulatschak.Listeners;
import itp.project.mulatschak.R;

public class PopupWeli extends AppCompatActivity {

    ImageView eyeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_weli);

        eyeBtn = findViewById(R.id.eyeBtn);
        eyeBtn.setOnTouchListener(Listeners.newListener(this));
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}