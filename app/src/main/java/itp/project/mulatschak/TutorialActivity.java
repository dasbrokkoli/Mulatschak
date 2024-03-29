package itp.project.mulatschak;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import itp.project.popups.PopupTutorialVideo;
import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;
import java.util.Objects;

public class TutorialActivity extends AppCompatActivity {
    Bundle extras;
    boolean startseite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        setTitle(getResources().getString(R.string.tutorial_name));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView tutorialText = findViewById(R.id.textView);

        String tuTo;
        try {
            tuTo = new Markdown4jProcessor().process(String.valueOf(getText(R.string.tutorialText)));
        } catch (IOException e) {
            e.printStackTrace();
            tuTo = String.valueOf(getText(R.string.tutorialText));
        }
        tutorialText.setText(HtmlCompat.fromHtml(tuTo, 0));

        setBoolean();

        Button tutorial = findViewById(R.id.video_button);
        tutorial.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PopupTutorialVideo.class);
            startActivity(intent);
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Button zurueck = findViewById(android.R.id.home);


    }

    /**
     * Setzt startseite auf true, wenn man die Activity von der Startseite aus aufgerufen hat,
     * und setzt es auf false, wenn mans ueber den Playground aufgerufen hat
     */
    public void setBoolean() {
        // You can be pretty confident that the intent will not be null here.
        Intent intent = getIntent();
        System.out.println("Tutorial-ID:" + intent.getExtras().toString());

        // Get the extras (if there are any)
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("start")) {
                startseite = extras.getBoolean("start", false);
            }
        }

        // startseite = getIntent().getExtras().getBoolean("start");
        System.out.println("START:" + startseite);
    }

    /**
     * Je nachdem ob die startseite true oder false ist, wird ueber den zurueck-Button auf
     * der toolbar ein anderer Intent gestartet, welcher wieder dorthin zurueck fuehrt
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (startseite) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                closeLog();
                //Intent intent = new Intent(getApplicationContext(), Playground.class);
                //startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Schließt das Popup wieder und informiert Playground, dass das Popup geschlossen wurde
     *
     */
    public void closeLog() {
        setResult(RESULT_OK);
        finish();
    }
}