package itp.project.Popups;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import itp.project.Mulatschak.Algorithm;
import itp.project.Mulatschak.Listeners;
import itp.project.Mulatschak.R;

import java.util.ArrayList;
import java.util.List;

public class PopupLog extends AppCompatActivity {
    private LinearLayout outerLayout;
    private FlexboxLayout nameLayout;
    private FlexboxLayout pointLayout;
    private final TextView[] tvNames = new TextView[4];
    private final TextView[] tvPoints = new TextView[4];
    private PopupName popName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_log);
        outerLayout = findViewById(R.id.linlayout);
        nameLayout = new FlexboxLayout(outerLayout.getContext());
        pointLayout = new FlexboxLayout(outerLayout.getContext());
        outerLayout.addView(nameLayout);
        outerLayout.addView(pointLayout);
        popName = new PopupName();

        for (int i = 0; i < tvNames.length; i++) {
            tvNames[i] = new TextView(this);
            nameLayout.addView(tvNames[i]);
        }

        for (int i = 0; i < tvPoints.length; i++) {
            tvPoints[i] = new TextView(this);
            pointLayout.addView(tvPoints[i]);
        }

        Listeners.newListener(this);

        //Methodenaufruf
        setNames();
        setScoring();

        nameLayout.setJustifyContent(JustifyContent.SPACE_BETWEEN);
        pointLayout.setMinimumWidth(nameLayout.getWidth());
        pointLayout.setJustifyContent(JustifyContent.SPACE_BETWEEN);
    }

    /**
     * Setzt die  in die TextView
     */
    public void setScoring() {
        List<Integer> points = Algorithm.getPoints();
        for (int i = 0; i < points.size(); i++) {
            System.out.println("Spieler " + i + " hat " + points.get(i) + " Punkte");
            tvPoints[i].setText(String.valueOf(points.get(i)));
        }
    }

    /**
     * Setzt die Namen in die TextView
     */
    public void setNames() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("PlayerNames", Context.MODE_PRIVATE);
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            names.add(sp.getString("PlayerName" + i, "Player " + (i + 1)));
        }
        for (int i = 0; i < names.size(); i++) {
//            if(names.get(i) == null) { //Wenn keine Namen eingegeben wurden, dann wird eine ID gesetzt
//                System.out.println("No Value");
//                names.set(i,String.valueOf(i));
//            }
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