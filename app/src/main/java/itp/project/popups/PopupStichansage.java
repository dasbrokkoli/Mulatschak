package itp.project.popups;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import itp.project.mulatschak.Algorithm;
import itp.project.mulatschak.Listeners;
import itp.project.mulatschak.Playground;
import itp.project.mulatschak.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PopupStichansage extends AppCompatActivity {

    Button skip, muli, uebernehmen;
    TextView one, two, three, four, highest;//Stichanzahl
    int countStitches, said;
    ImageView eyeBtn;
    List<Algorithm> players;
    int dealer;
    int highestStitches;
    int currentStiche;
    int indexOfHighestStich;
    int howMuch;
    boolean isDealer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_stichansage);

        dealer = Algorithm.getDealer();
        isDealer = false;
        rundenStichansage();
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
                if (player1 + currentStiche > currentStiche) {
                    currentStiche++;
                    indexOfHighestStich = 0;
                    player1--;
                } else {
                    players.get(0).setAusgestiegen(true);
                }
            }
            if (!players.get(1).isKi()) {
                popup();
            } else {
                if (player2 + currentStiche > currentStiche) {
                    currentStiche++;
                    indexOfHighestStich = 1;
                    player2--;
                } else {
                    players.get(1).setAusgestiegen(true);
                }
            }
            if (!players.get(2).isKi()) {
                popup();
            } else {
                if (player3 + currentStiche > currentStiche) {
                    currentStiche++;
                    indexOfHighestStich = 2;
                    player3--;
                } else {
                    players.get(2).setAusgestiegen(true);
                }
            }
            if (!players.get(3).isKi()) {
                popup();
            } else {
                if (player4 + currentStiche > currentStiche) {
                    currentStiche++;
                    indexOfHighestStich = 3;
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
//        eyeBtn.setOnTouchListener(Listeners.newListener(this));
        eyeBtn.setOnClickListener(Listeners.newOnClickListener(this));
        //Button Muli
        muli = findViewById(R.id.muli);
        muli.setOnClickListener(view -> {
            countStitches = 5;
            Collections.rotate(players, howMuch);
            Playground.angesagteSticheAnzeigen(players.get(0), countStitches);
            //Atout wählen
            startActivity(new Intent(PopupStichansage.this, PopupSelectAtout.class));
            finish();
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
        skip.setOnClickListener(view -> {
            countStitches = 0;
            nextPopup();
        });

        //Ein Stich
        one = findViewById(R.id.stich1);
        one.setOnClickListener(view -> {
            countStitches = 1;
            nextPopup();
        });

        //Zwei Stiche
        two = findViewById(R.id.stich2);
        two.setOnClickListener(view -> {
            countStitches = 2;
            nextPopup();
        });

        //Drei Stiche
        three = findViewById(R.id.stich3);
        three.setOnClickListener(view -> {
            countStitches = 3;
            nextPopup();
        });

        //Vier Stiche
        four = findViewById(R.id.stich4);
        four.setOnClickListener(view -> {
            countStitches = 4;
            nextPopup();
        });

        // Wenn Spieler dealer ist, kann er Stiche uebernehmen
        System.out.println("Dealer:" + dealer);
        if(dealer == 1) {
            isDealer = true;
            System.out.println("MMM: if");
            //Wenn man die Stiche uebernimmt
            uebernehmen = findViewById(R.id.uebernehmen);
            uebernehmen.setOnClickListener(view -> {
                countStitches = Integer.parseInt(highest.getText().toString());
                nextPopup();
            });
        }else{
            System.out.println("MMM: else");
            uebernehmen = findViewById(R.id.uebernehmen);
            uebernehmen.setVisibility(View.GONE);
        }
    }

    /**
     * Es wird geprüft ob die angegeben Stiche höher sind als die bereits angesagten. Je nachdem kann man dem Popup
     * Atout oder Atout wählen kommen.
     */
    private void nextPopup() {
        //die Punkte des Spielers
        int userPoints = Algorithm.getPoints().get(0);


        if (userPoints > 5) {
            if ((countStitches > said) || isDealer) {
                Collections.rotate(players, howMuch);
                Playground.angesagteSticheAnzeigen(players.get(0), countStitches);
                //man hat die höchsten Stiche angesagt und kann jetzt das Atout wählen
                startActivity(new Intent(PopupStichansage.this, PopupSelectAtout.class));
            }else {
                //Unter 6 Punkten, darf der Spieler keine Stiche ansagen
                Playground.angesagteSticheAnzeigen(players.get(indexOfHighestStich), highestStitches);
                Algorithm.setAtout(players.get(indexOfHighestStich).getAtoutFromPlayers());
                startActivity(new Intent(PopupStichansage.this, PopupAtout.class));
            }
            //Wenn der Spieler weniger od gleich als 5 Punkte hat, kann er keine Stiche mehr ansagen
        } else {
            Playground.angesagteSticheAnzeigen(players.get(indexOfHighestStich), highestStitches);
            Algorithm.setAtout(players.get(indexOfHighestStich).getAtoutFromPlayers());
            startActivity(new Intent(PopupStichansage.this, PopupAtout.class));
        }
        finish();
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
                howMuch = 0;
                break;
            case 2:
                Collections.rotate(reihenfolge, 3);
                howMuch = -3;
                break;
            case 3:
                Collections.rotate(reihenfolge, 2);
                howMuch = -2;
                break;
            case 4:
                Collections.rotate(reihenfolge, 1);
                howMuch = -1;
                break;
        }
        return reihenfolge;
    }

    /**
     * Gibt an, ob drei Spieler ausgestiegen sind
     *
     * @param players alle Spieler
     * @return ob drei Spieler ausgestiegen sind
     */
    private boolean dreiSpielerAusgestiegen(List<Algorithm> players) {
        int ausgestiegen = 0;
        for (Algorithm player : players) {
            if (player.istAusgestiegen()) ausgestiegen++;
        }
        return ausgestiegen >= 3;
    }

    /**
     * Gibt den Spieler, der keine KI ist zurück und setzt das jeweilige Attribut auf true oder false
     *
     * @param dealer der Dealer
     */
    private void getSpieler1(int dealer) {
        switch (dealer) {
            case 1:
                players.get(0).setKi(false);
                return;
            case 2:
                players.get(3).setKi(false);
                return;
            case 3:
                players.get(2).setKi(false);
                return;
            case 4:
                players.get(1).setKi(false);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}