package itp.project.Popups;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.Mulatschak.Algorithm;
import android.os.Bundle;
import itp.project.Mulatschak.Listeners;
import itp.project.Mulatschak.Playground;
import itp.project.Mulatschak.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PopupStichansage extends AppCompatActivity {

    Button skip, muli;
    TextView one, two, three, four, highest;//Stichanzahl
    int countStitches, said;
    ImageView eyeBtn;
    List<Algorithm> players;
    int dealer;
    int highestStitches;
    int currentStiche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_stichansage);

        Playground.austeilen();
        dealer = Algorithm.getDealer();
        rundenStichansage();
    }

    /**
     * Es wird geprüft ob die angegeben Stiche höher sind als die bereits angesagten. Je nachdem kann man dem Popup
     * Atout oder Atout wählen kommen.
     */
    private void nextPopup() {
        if (countStitches > said) {
            //man hat die höchsten Stiche angesagt und kann jetzt das Atout wählen
            startActivity(new Intent(PopupStichansage.this, Popup_selectAtout.class));
        } else {
            startActivity(new Intent(PopupStichansage.this, Popup_atout.class));
        }
        finish();
    }

    public void rundenStichansage() {
        // Reihenfolge
        this.players = order(dealer);

        getSpieler1(dealer);
        int player1 = players.get(0).getStiche(); // dealer
        int player2 = players.get(1).getStiche();
        int player3 = players.get(2).getStiche();
        int player4 = players.get(3).getStiche();
        currentStiche = 0;
        while (!dreiSpielerAusgestiegen(players)) {
            if (!players.get(0).isKi()) {
                popup();
            } else {
                System.out.println("player1: " + player1);
                if (player1 + currentStiche > currentStiche) {
                    currentStiche++;
                    player1--;
                } else {
                    players.get(0).setAusgestiegen(true);
                }
            }
            if (!players.get(1).isKi()) {
                popup();
            } else {
                System.out.println("player2: " + player2);
                if (player2 + currentStiche > currentStiche) {
                    currentStiche++;
                    player2--;
                } else {
                    players.get(1).setAusgestiegen(true);
                }
            }
            if (!players.get(2).isKi()) {
                popup();
            } else {
                System.out.println("player3: " + player3);
                if (player3 + currentStiche > currentStiche) {
                    currentStiche++;
                    player3--;
                } else {
                    players.get(2).setAusgestiegen(true);
                }
            }
            if (!players.get(3).isKi()) {
                popup();
            } else {
                System.out.println("player4: " + player4);
                if (player4 + currentStiche > currentStiche) {
                    currentStiche++;
                    player4--;
                } else {
                    players.get(3).setAusgestiegen(true);
                }
            }
        }
        highestStitches = currentStiche;
    }

    public void popup() {
        eyeBtn = findViewById(R.id.eyeBtn);
        eyeBtn.setOnTouchListener(Listeners.newListener(this));


        //Button Muli
        muli = findViewById(R.id.muli);
        muli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countStitches = 5;
                //Atout wählen
                startActivity(new Intent(PopupStichansage.this, Popup_selectAtout.class));
                finish();
            }
        });

        //Bereits angesagte Stiche
        highest = findViewById(R.id.highest);
        highest.setText(String.valueOf(currentStiche));
        try {
            said = Integer.parseInt(highest.getText().toString());
        } catch (NumberFormatException e) {
            said = 0;
        }


        //Skip Button
        skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(PopupStichansage.this, Popup_atout.class));
                players.get(getSpieler1(dealer)).setAusgestiegen(true);
                finish();
            }
        });

        //Ein Stich
        one = findViewById(R.id.stich1);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countStitches = 1;
                nextPopup();
            }
        });

        //Zwei Stiche
        two = findViewById(R.id.stich2);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countStitches = 2;
                nextPopup();
            }
        });

        //Drei Stiche
        three = findViewById(R.id.stich3);
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countStitches = 3;
                nextPopup();
            }
        });

        //Vier Stiche
        four = findViewById(R.id.stich4);
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countStitches = 4;
                nextPopup();
            }
        });
    }

    /**
     * Das ArrayList wird so rotiert, dass der Dealer beginnt.
     *
     * @param dealer die Nummer des Dealers
     * @return die Reihenfolge mit allen Spielern
     */
    private List<Algorithm> order(int dealer) {
        List<Algorithm> reihenfolge = new ArrayList<>();
        reihenfolge.add(Playground.getPlayer(1));
        reihenfolge.add(Playground.getPlayer(2));
        reihenfolge.add(Playground.getPlayer(3));
        reihenfolge.add(Playground.getPlayer(4));
        switch (dealer) {
            case 1:
                Collections.rotate(reihenfolge, 0);
            case 2:
                Collections.rotate(reihenfolge, 3);
            case 3:
                Collections.rotate(reihenfolge, 2);
            case 4:
                Collections.rotate(reihenfolge, 1);
        }
        return reihenfolge;
    }

    /**
     * Gibt an, ob drei Spieler ausgestiegen sind
     * @param players alle Spieler
     * @return ob drei Spieler ausgestiegen sind
     */
    private boolean dreiSpielerAusgestiegen(List<Algorithm> players) {
        int ausgestiegen = 0;
        for (Algorithm player : players) {
            if (player.istAusgestiegen()) ausgestiegen++;
        }
        return ausgestiegen == 3;
    }

    /**
     * Gibt den Spieler, der keine KI ist zurück und setzt das jeweilige Attribut auf true oder false
     * @param dealer der Dealer
     * @return Nicht-KI Spieler
     */
    private int getSpieler1(int dealer) {
        switch (dealer) {
            case 1:
                players.get(0).setKi(false);
                return 1;
            case 2:
                players.get(3).setKi(false);
                return 4;
            case 3:
                players.get(2).setKi(false);
                return 3;
            case 4:
                players.get(1).setKi(false);
                return 2;
        }
        return 0;
    }

}