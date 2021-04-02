package itp.project.Mulatschak;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import itp.project.Exceptions.WinException;
import itp.project.Popups.*;

import java.io.Serializable;
import java.util.*;

public class Playground extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener, Serializable {
//    public static boolean alreadyLeft;

    //Gemachte Stiche
    //public static TextView stitches_pl1, stitches_pl2, stitches_pl3, stitches_pl4;
    public static final TextView[] stitches = new TextView[4];
    //Algorithmen für Spieler
    static final Algorithm[] players = new Algorithm[4];
    private static final BiMap<Integer, Card> cardsOnFloor = HashBiMap.create();
    public static Thread playThread;
    public static List<Card> gewonnene;
    static ImageView move;
    static ImageView atout;
    private static TextView pl1_announced, pl2_announced, pl3_announced, pl4_announced;
    private static int beginner;
    private static int playerCardNumber;
    final long ANIMATION_DURATION = 3000;
    //Liste für die Karten
    //LogPopup
    Button showLogBtn;
    ImageButton closeLogView;
    //View diffView = findViewById(R.layout.popup_difficulty);
    PopupWindow logWindow;
    ConstraintLayout constraintLayout;
    /**
     * Jeder Spieler spielt eine Karte.
     * Dann wird die beste Karte ausgewertet, in das Log eingetragen neu ausgeteilt und wieder die Stichansage aufgerufen.
     */
    public long TIME_TO_WAIT_AFTER_CARD = 1000;
    ImageView anim2, anim3, anim4;
    //Gemachte Stiche Popup
    Button gemachteStiche;
    //View anim2, anim3, anim4;
    //Cards
    ImageView card4, card1, card2, card3, card5, destination, card_pl2, card_pl3, card_pl4, pl2, pl3, pl4;
    long animationOffset = 0;

    /**
     * Spieler zurückgeben
     *
     * @return - Player
     */
    public static Algorithm getPlayer(int playernumber) {
        return players[playernumber - 1];
    }

    /**
     * Die gemachten Stiche sollen im Playground angezeigt werden.
     * Dazu wird bei dem Spieler der gestochen hat die neue Stichanzahl angezeigt.
     *
     * @param player - Spieler der den Zug gewonnen hat
     * @param count  - neue Stichanzahl des Spielers
     */
    public synchronized static void stitchesMade(int player, int count) {
        new Thread(() -> {
            synchronized ((Integer) player) {
                synchronized (stitches) {
                    switch (player) {
                        case 1:
                            stitches[0].setText(String.valueOf(count));
                            System.out.println("Player 1 won: " + count);
                            break;
                        case 2:
                            stitches[1].setText(String.valueOf(count));
                            System.out.println("Player 2 won: " + count);
                            break;
                        case 3:
                            stitches[2].setText(String.valueOf(count));
                            System.out.println("Player 3 won: " + count);
                            break;
                        case 4:
                            stitches[3].setText(String.valueOf(count));
                            System.out.println("Player 4 won: " + count);
                            break;
                    }
                }
            }
        }).start();
    }

    /**
     * Die angesagten Stiche werden angezeigt
     *
     * @param player - Spieler der Siche ansagt
     * @param stiche - angesagte der Stiche
     */
    public synchronized static void angesagteSticheAnzeigen(Algorithm player, int stiche) {
        new Thread(() -> {
            synchronized (players) {
                synchronized (player) {
                    if (players[0].equals(player)) {
                        beginner = 0;
                        pl1_announced.setText(String.valueOf(stiche));
                    } else if (players[1].equals(player)) {
                        beginner = 1;
                        pl2_announced.setText(String.valueOf(stiche));
                    } else if (players[2].equals(player)) {
                        beginner = 2;
                        pl3_announced.setText(String.valueOf(stiche));
                    } else if (players[3].equals(player)) {
                        beginner = 3;
                        pl4_announced.setText(String.valueOf(stiche));
                    }
                }
            }
        }).start();
    }

    public synchronized static Card getCardfromView(ImageView v) {
        Card change;//Die zu tauschende Karte
        switch (v.getId()) {
            case R.id.card1:
                change = Playground.getPlayer(1).getHoldingCards().get(0);
                break;
            case R.id.card2:
                change = Playground.getPlayer(1).getHoldingCards().get(1);
                break;
            case R.id.card3:
                change = Playground.getPlayer(1).getHoldingCards().get(2);
                break;
            case R.id.card4:
                change = Playground.getPlayer(1).getHoldingCards().get(3);
                break;
            case R.id.card5:
                change = Playground.getPlayer(1).getHoldingCards().get(4);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        return change;
    }

    /**
     * Im dafür vorgesehenen Feld wird das geählte Atout angezeigt.
     * Das Atout ist in der Konstante gespeichert welches angezeigt werden soll.
     */
    public synchronized static void showAtout() {
        //Wenn noch kein Atout gespeichert ist wird ein leeres Feld angezeigt
        if (Algorithm.getAtout() == null) {
            atout.setImageResource(R.drawable.empty);
        } else {
            //Das gespeicherte Atout wird angezeigt
            setAtoutImg(atout);
        }
    }

    public static void setAtoutImg(ImageView atout) {
        switch (Algorithm.getAtout()) {
            case HERZ:
                atout.setImageResource(R.drawable.herz);
                break;
            case BLATT:
                atout.setImageResource(R.drawable.blatt);
                break;
            case EICHEL:
                atout.setImageResource(R.drawable.eiche);
                break;
            case SCHELLE:
                atout.setImageResource(R.drawable.schelle);
                break;
            default:
                atout.setImageResource(R.drawable.empty);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        alreadyLeft = false;
        //Spieler
        synchronized (this) {
            new Thread(() -> {
                austeilen();
                Algorithm.rundenbeginn();
            }).start();
        }

        startActivity(new Intent(Playground.this, PopupStichansage.class).putExtra("Playground", this));

        // open Pause Menu
        View menu = findViewById(R.id.settings);
        menu.setOnClickListener(v -> startActivity(new Intent(Playground.this, PopupPauseMenu.class)));

        //Tutorial Button
        View help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String start = "start";
                Intent intent = new Intent(Playground.this, TutorialActivity.class);
                intent.putExtra(start, false);
                startActivityForResult(intent, 0);
                //startActivity(new Intent(Playground.this, TutorialActivity.class));
                //startActivityForResult(new Intent(Playground.this, PopupLog.class), 0); // zeigt PopupLog an, wartet auf Result (schließen)
                return;
            }
        });

        //Fuer die Schwierigkeit
        //diffView = findViewById(R.layout.popup_difficulty);

        //ImageView für Atout anzeigen
        atout = findViewById(R.id.atout);

        constraintLayout = findViewById(R.id.playgroundConstraintLayout);
        //LogPopup
        showLogBtn = findViewById(R.id.logButton);
        showLogBtn.setOnClickListener(view -> startActivity(new Intent(Playground.this, PopupLog.class)));

        //Gemachte Stiche ansehen
        gemachteStiche = findViewById(R.id.gemachteStiche);
        gemachteStiche.setOnClickListener(view -> startActivity(new Intent(Playground.this, GemachteStiche.class)));

        //Karten aus dem Algorithmus


        //Cards
        card1 = findViewById(R.id.card1);
        card1.setOnTouchListener(this);
        card2 = findViewById(R.id.card2);
        card2.setOnTouchListener(this);
        card3 = findViewById(R.id.card3);
        card3.setOnTouchListener(this);
        card4 = findViewById(R.id.card4);
        card4.setOnTouchListener(this);
        card5 = findViewById(R.id.card5);
        card5.setOnTouchListener(this);

        //Destination
        destination = findViewById(R.id.card_pl1);
        destination.setOnDragListener(this);
        card_pl2 = findViewById(R.id.card_pl2);
        card_pl3 = findViewById(R.id.card_pl3);
        card_pl4 = findViewById(R.id.card_pl4);

        //Animation Views
        anim2 = findViewById(R.id.animation_p2);
        anim3 = findViewById(R.id.animation_p3);
        anim4 = findViewById(R.id.animation_p4);

        //Stiche gemacht
        pl1_announced = findViewById(R.id.player_announced);
        pl2_announced = findViewById(R.id.pl1_announced);
        pl3_announced = findViewById(R.id.pl2_announced);
        pl4_announced = findViewById(R.id.pl3_announced);
        //stitches_pl1 = findViewById(R.id.player_stitches);
        //stitches_pl2 = findViewById(R.id.pl1_stitches);
        //stitches_pl3 = findViewById(R.id.pl2_stitches);
        //stitches_pl4 = findViewById(R.id.pl3_stitches);

        stitches[0] = findViewById(R.id.player_stitches);
        stitches[1] = findViewById(R.id.pl1_stitches);
        stitches[2] = findViewById(R.id.pl2_stitches);
        stitches[3] = findViewById(R.id.pl3_stitches);

        gewonnene = new ArrayList<>();

        //Spielernamen anzeigen wenn auf die Katren gedrückt wird
        pl2 = findViewById(R.id.player4);
        pl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlayersName(2);

            }
        });
        pl3 = findViewById(R.id.player2);
        pl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlayersName(3);
            }
        });
        pl4 = findViewById(R.id.player3);
        pl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlayersName(4);
            }
        });
    }

    /**
     * der Name des spielers wird ausgegebn.
     *
     * @param pl - Spielerid
     */
    public synchronized void showPlayersName(int pl) {
        //Wenn kein Spielername gepeichert ist
        if (PopupName.namen.get(pl - 1).equals("")) {
            Toast.makeText(this, "Player" + pl, Toast.LENGTH_SHORT).show();
            //Wenn ein Spielername gespeichert ist
        } else {
            Toast.makeText(this, PopupName.namen.get(pl - 1), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Neue runde die Karten werden neu ausgeteilt.
     * Jeder Spieler bekommt einen neuen Algorithmus
     */
    public synchronized static void austeilen() {
        new Thread(() -> {
            synchronized (MainActivity.getCards()) {
                HoldingCards.setAllCards(MainActivity.getCards());
                players[0] = new Algorithm(MainActivity.getCards(), 1);
                playerCardNumber = 5;
                players[1] = new Algorithm(MainActivity.getCards(), 2);
                players[2] = new Algorithm(MainActivity.getCards(), 3);
                players[3] = new Algorithm(MainActivity.getCards(), 4);
            }
        }).start();
    }

    @Override
    public synchronized boolean onTouch(View v, MotionEvent event) {
        if (beginner != 0) {
            Toast.makeText(this, R.string.playerNotDran, Toast.LENGTH_SHORT).show();
            return false;
        }
        move = (ImageView) v;
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        ClipData data = ClipData.newPlainText("", "");
        v.startDrag(data, shadowBuilder, v, 0);
        return true;
    }

    @Override
    public synchronized boolean onDrag(View v, DragEvent event) {
        new Thread(() -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //Karte in das Feld gezogen
                    if (event.getResult()) {
                        runOnUiThread(() -> destination.setImageDrawable(move.getDrawable()));
                        move.setVisibility(View.INVISIBLE);
                        cardsOnFloor.put(beginner, getCardfromView(move));
                        playerCardNumber--;
                        System.out.println(getCardfromView(move).getColor() + "" + getCardfromView(move).getValue());
                        rotateBeginner();
                        play();
                    }
                default:
                    break;
            }
        }).start();
        return true;
    }

    public synchronized void whichCardWon() {
        Card[] cards = cardsOnFloor.values().toArray(new Card[0]);
        Card winner = Algorithm.getWinnerFromCards(cards);
        int winnerIndex = cardsOnFloor.inverse().get(winner);
        //Wenn Spieler 0 gewonnen hat werden die Karten gespeichert um sie später ansehen zu können
        if (winnerIndex == 0) {
            gewonnene.addAll(Arrays.asList(cards));
        }
        beginner = winnerIndex;
        players[winnerIndex].wonThisCard();
        assert winner != null;
        System.out.println("Winning Card: " + winner.getColor() + winner.getValue() + " von Spieler " + (winnerIndex + 1));
    }

    public synchronized void play() {
        System.out.println("Play started");
        while (cardsOnFloor.size() < 4) {
            System.out.println("While: " + cardsOnFloor.size());
            if (beginner == 0) {
                System.out.println("Spieler ist dran");
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), R.string.playerDran, Toast.LENGTH_SHORT).show());
                return;
            }
            Card[] cArray = new Card[cardsOnFloor.size()];
            cardsOnFloor.values().toArray(cArray);
            cardsOnFloor.put(beginner, players[beginner].getResponseCard(Algorithm.getWinnerFromCards(cArray)));

            /* hier kommt die Animation hin */

            kartenAnzeigen(beginner, cardsOnFloor.get(beginner).getPicture());
            //System.out.println("Ich habe Karte: "+ cardsOnFloor.get(beginner).getPicture().toString() );
            System.out.println(("Ich bin " + players[beginner].getName() + " und spiele " + cardsOnFloor.get(beginner).getColor() + cardsOnFloor.get(beginner).getValue() + ". Ich habe folgende Karten: " + players[beginner].getHoldingCardsString()));
            rotateBeginner();
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Gewinner ermitteln
        //Stich eintragen
        this.whichCardWon();

        //Spielfeldkarten löschen

        /* Hier wird die Animation zurück gesetzt*/
        //System.out.println("Karten löschen xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        cardsOnFloor.clear();
        kartenAnzeigen(0, null);
        kartenAnzeigen(1, null);
        kartenAnzeigen(2, null);
        kartenAnzeigen(3, null);
        //System.out.println("Karten löschen Ende xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        animationOffset = 0;

        //Noch Karten vorhanden?
        System.out.println("Playerkarten: " + playerCardNumber);
        if (playerCardNumber == 0) {
            System.out.println("Runde fertig");
            //Gemachten Stiche löschen
            gewonnene.clear();
            try {
                Algorithm.scoring(players);
            } catch (WinException e) {
                win(Integer.parseInt(e.getMessage()));
            }
            // Popup bei Gewinner anzeigen und Punkte anzeigen
            startActivityForResult(new Intent(Playground.this, PopupLog.class), 0); // zeigt PopupLog an, wartet auf Result (schließen)
            return;
        }
        play();
    }

    public synchronized void animation(int spieler, Drawable card) {
        // Card card4, card1, card2, card3, card5
        // Destination destination, card_pl2, card_pl3, card_pl4, pl2, pl3, pl4;

        TranslateAnimation animation;
        //runOnUiThread();
        //TranslateAnimation animation = null;
        //Mit Switch Case
        switch (spieler) {
            case 0:
                System.out.println("No Animation needed");
                break;

            case 1:
                anim2.setImageDrawable(card);
                animation = new TranslateAnimation(0, (card_pl2.getX() - anim2.getX()) + 7, 0, card_pl2.getY() - anim2.getY());
                animation.setRepeatMode(0);
                animation.setDuration(ANIMATION_DURATION);
                animation.setStartOffset(animationOffset);
                animationOffset += ANIMATION_DURATION;
                animation.setFillAfter(true);
                anim2.startAnimation(animation);
                break;

            case 2:
                anim3.setImageDrawable(card);
                animation = new TranslateAnimation(0, (card_pl3.getX() - anim3.getX()) + 7, 0, card_pl3.getY() - anim3.getY());
                animation.setRepeatMode(0);
                animation.setDuration(ANIMATION_DURATION);
                animation.setStartOffset(animationOffset);
                animationOffset += ANIMATION_DURATION;
                animation.setFillAfter(true);
                anim3.startAnimation(animation);
                break;

            case 3:
                anim4.setImageDrawable(card);
                animation = new TranslateAnimation(0, (card_pl4.getX() - anim4.getX()) + 7, 0, card_pl4.getY() - anim4.getY());
                animation.setRepeatMode(0);
                animation.setDuration(ANIMATION_DURATION);
                animation.setStartOffset(animationOffset);
                animationOffset += ANIMATION_DURATION;
                animation.setFillAfter(true);
                anim4.startAnimation(animation);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // teilt die Karten aus, nachdem das PopupLog geschlossen wurde
        if (resultCode == RESULT_CANCELED) {
            neuAusteilen();
        } else if (resultCode == RESULT_OK) {
            System.out.println("RESULT_OK bei Tutorial");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Neu austeilen wenn ausgestiegen
        if (Popup_atout.alreadyLeft) {
            neuAusteilen();
        }

        //Das Atout wird angezeigt -> wird im Popup_kartentausch schon angezeigt
        //showAtout();
        anzeigen();

        playThread = new Thread(() -> {
            while (true) {
                try {
                    synchronized (playThread) {
                        playThread.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " is calling play()");
                play();
            }
        });
        playThread.start();
    }

    public void neuAusteilen() {
        reset();
        austeilen();
        anzeigen();
        startActivity(new Intent(Playground.this, PopupStichansage.class));
    }

    /**
     * Die Karten des Spielers anzeigen.
     */
    public synchronized void anzeigen() {
        runOnUiThread(() -> {
            card1.setImageDrawable(players[0].getHoldingCards().get(0).getPicture());
            card2.setImageDrawable(players[0].getHoldingCards().get(1).getPicture());
            card3.setImageDrawable(players[0].getHoldingCards().get(2).getPicture());
            card4.setImageDrawable(players[0].getHoldingCards().get(3).getPicture());
            card5.setImageDrawable(players[0].getHoldingCards().get(4).getPicture());
        });
    }

    /**
     * Setzt alle Anzeigen zurück
     */
    private synchronized void reset() {
        runOnUiThread(() -> {
            //Angesagte Stiche zurücksetzen
            pl1_announced.setText("/");
            pl2_announced.setText("/");
            pl3_announced.setText("/");
            pl4_announced.setText("/");

            //Karten wieder Visible setzen
            card1.setVisibility(View.VISIBLE);
            card2.setVisibility(View.VISIBLE);
            card3.setVisibility(View.VISIBLE);
            card4.setVisibility(View.VISIBLE);
            card5.setVisibility(View.VISIBLE);

            //gemachte Stiche zurücksetzen
            stitches[0].setText("0");
            stitches[1].setText("0");
            stitches[2].setText("0");
            stitches[3].setText("0");

            for (Algorithm player : players) {
                player.setTrick(0);
            }

            //Atout zurücksetzen
            atout.setImageResource(R.drawable.empty);

            animationOffset = 0;
        });
    }

    private synchronized void rotateBeginner() {
        if (beginner < 3) {
            beginner++;
        } else {
            beginner = 0;
        }
    }

    public synchronized void kartenAnzeigen(int spieler, Drawable card) {
        System.out.println("Ahhhhhhhhhhhhhhhhhhhhhh");
        System.out.println("Card: " + card);
        System.out.println("Spieler: " + spieler);
        if (spieler == 0) {
            runOnUiThread(() -> destination.setImageDrawable(null));
        } else {
            runOnUiThread (new Thread(new Runnable() {
                public void run() {
                    if (card != null) {
                        animation(spieler, card);
                        //runOnUiThread(() -> animation(spieler, card));
                    } else {
                        switch (spieler) {
                            case 1:
                                //cardsOnFloor.clear();
                                card_pl2.setImageDrawable(null);
                                anim2.setImageDrawable(null);
                                break;
                            case 2:
                                //cardsOnFloor.clear();
                                card_pl3.setImageDrawable(null);
                                anim3.setImageDrawable(null);
                                break;
                            case 3:
                                //cardsOnFloor.clear();
                                card_pl4.setImageDrawable(null);
                                anim4.setImageDrawable(null);
                                break;
                        }
                    }
                }
            }));

        }
    }

    public void win(int playerNumber) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PlayerNames", Context.MODE_PRIVATE);
        Toast.makeText(this, sharedPreferences.getString("PlayerName" + playerNumber, "Player " + (playerNumber + 1)) + " " + getString(R.string.winMessage), Toast.LENGTH_LONG).show();
    }

    public synchronized static Map<Integer, Integer> getHighestStich() {
        Map<Integer, Integer> tempMap = new HashMap<>();
        synchronized (pl1_announced) {
            synchronized (pl2_announced) {
                synchronized (pl3_announced) {
                    synchronized (pl4_announced) {
                        try {
                            int tempInt = Integer.parseInt(pl1_announced.getText().toString());
                            tempMap.put(0, tempInt);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        try {
                            int tempInt = Integer.parseInt(pl2_announced.getText().toString());
                            if (!tempMap.isEmpty()) {
                                if (tempInt > Collections.max(tempMap.values())) {
                                    if (!tempMap.isEmpty()) tempMap.clear();
                                    tempMap.put(1, tempInt);
                                }
                            } else {
                                tempMap.put(1, tempInt);
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        try {
                            int tempInt = Integer.parseInt(pl3_announced.getText().toString());
                            if (!tempMap.isEmpty()) {
                                if (tempInt > Collections.max(tempMap.values())) {
                                    if (!tempMap.isEmpty()) tempMap.clear();
                                    tempMap.put(2, tempInt);
                                }
                            } else {
                                tempMap.put(2, tempInt);
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        try {
                            int tempInt = Integer.parseInt(pl4_announced.getText().toString());
                            if (!tempMap.isEmpty()) {
                                if (tempInt > Collections.max(tempMap.values())) {
                                    if (!tempMap.isEmpty()) tempMap.clear();
                                    tempMap.put(3, tempInt);
                                }
                            } else {
                                tempMap.put(3, tempInt);
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return tempMap;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
