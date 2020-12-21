package itp.project.Mulatschak;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import itp.project.Popups.PopupTutorialVideo;
import itp.project.Mulatschak.R;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        setTitle(getResources().getString(R.string.tutorial_name));

        TextView tutorialText = findViewById(R.id.textView);
        tutorialText.setText(getText(R.string.tutorialText));

        Button tutorial = findViewById(R.id.video_button);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupTutorialVideo.class);
                startActivity(intent);
            }
        });
    }
}