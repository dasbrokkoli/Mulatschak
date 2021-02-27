package itp.project.Popups;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.Mulatschak.Listeners;
import itp.project.Mulatschak.R;

public class PopupTutorialVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_tutorial_video);

        Listeners.newListener(this);

        VideoView videoView = findViewById(R.id.videoView);
        // Video aus Pfad laden
        // videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.videoclip);
        // Video starten
        // videoView.start();
        // Controller, um Video zu steuern
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }
}