package itp.project.popups;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import itp.project.mulatschak.Algorithm;
import itp.project.mulatschak.Listeners;
import itp.project.mulatschak.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PopupLog extends AppCompatActivity {
    FlexboxLayout names;
    private final List<FlexboxLayout> points = new LinkedList<>();
    private final TextView[] tvNames = new TextView[4];
    private final TextView[] tvPoints = new TextView[4];
    private LinearLayout outerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_log);
        outerLayout = findViewById(R.id.linlayout);
        new PopupName();

        boolean newLine = false;

        try {
            newLine = (boolean) getIntent().getExtras().get("newLine");
        } catch (NullPointerException ignored) {
        }

        if (newLine || points.isEmpty()) {
            newLine();
        }

        Listeners.newListener(this);

        names.setJustifyContent(JustifyContent.SPACE_BETWEEN);
        points.get(points.size() - 1).setMinimumWidth(names.getWidth());
        points.get(points.size() - 1).setJustifyContent(JustifyContent.SPACE_BETWEEN);
    }

    private void newLine() {
        names = new FlexboxLayout(outerLayout.getContext());
        points.add(new FlexboxLayout(outerLayout.getContext()));
        outerLayout.addView(names);
        outerLayout.addView(points.get(points.size() - 1));
        System.out.println("Points.size: " + points.size());

        for (int i = 0; i < tvNames.length; i++) {
            tvNames[i] = new TextView(this);
            names.addView(tvNames[i]);
        }

        for (int i = 0; i < tvPoints.length; i++) {
            tvPoints[i] = new TextView(this);
            points.get(points.size() - 1).addView(tvPoints[i]);
        }

        setNames();
        setScoring();
    }

    /**
     * Setzt die  in die TextView
     */
    private void setScoring() {
        List<Integer> points = Algorithm.getPoints();
        for (int i = 0; i < points.size(); i++) {
            System.out.println("Spieler " + i + " hat " + points.get(i) + " Punkte");
            tvPoints[i].setText(String.valueOf(points.get(i)));
        }
    }

    /**
     * Setzt die Namen in die TextView
     */
    private void setNames() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("PlayerNames", Context.MODE_PRIVATE);
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            names.add(sp.getString("PlayerName" + i, "Player " + (i + 1)));
        }
        for (int i = 0; i < names.size(); i++) {
            tvNames[i].setText(names.get(i));
        }
    }

    /**
     * Schließt das Popup wieder und informiert Playground, dass das Popup geschlossen wurde
     *
     * @param view View
     */
    public void closeLog(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}