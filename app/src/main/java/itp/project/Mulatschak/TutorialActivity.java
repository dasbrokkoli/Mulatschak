package itp.project.Mulatschak;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.Popups.PopupTutorialVideo;

public class TutorialActivity extends AppCompatActivity {
    Bundle extras;
    boolean startseite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        setTitle(getResources().getString(R.string.tutorial_name));


        TextView tutorialText = findViewById(R.id.textView);
        tutorialText.setText(getText(R.string.tutorialText));

        setBoolean();

        Button tutorial = findViewById(R.id.video_button);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupTutorialVideo.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                if (startseite) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    closeLog();
                    //Intent intent = new Intent(getApplicationContext(), Playground.class);
                    //startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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