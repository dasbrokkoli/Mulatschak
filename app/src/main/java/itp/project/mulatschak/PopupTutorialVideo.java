package itp.project.mulatschak;

import android.util.DisplayMetrics;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class PopupTutorialVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_tutorial_video);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * 0.8));

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