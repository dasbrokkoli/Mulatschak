package itp.project.mulatschak;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.muddzdev.styleabletoast.StyleableToast;
import itp.project.exceptions.WinException;
import itp.project.popups.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Playground extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener, Serializable {
    //Gemachte Stiche
    public static final TextView[] stitches = new TextView[4];
    //Algorithmen für Spieler
    static final Algorithm[] players = new Algorithm[4];
    public static final BiMap<Integer, Card> cardsOnFloor = HashBiMap.create();
    public static Thread playThread;
    public static Thread animationThread;
    public static List<Card> gewonnene;
    static ImageView move;
    static ImageView atout;
    private static TextView pl1_announced, pl2_announced, pl3_announced, pl4_announced;
    public static int beginner;
    private static int playerCardNumber;
    final long ANIMATION_DURATION = 500;
    private static Context context;

    //Liste für die Karten
    //LogPopup
    Button showLogBtn;
    ImageButton closeLogView;
    PopupWindow logWindow;
    ConstraintLayout constraintLayout;

    //fuer play
    final Lock lock = new ReentrantLock();
    final Condition spielerSpielt = lock.newCondition();
    public long TIME_TO_WAIT_AFTER_ROUND = 3000;
    ImageView anim2, anim3, anim4;
    //Gemachte Stiche Popup
    Button gemachteStiche;
    //Cards
    ImageView card4, card1, card2, card3, card5, destination, card_pl2, card_pl3, card_pl4, pl2, pl3, pl4;

    private static ArrayList<ImageView> player2cards, player3cards, player4cards;
    public static int firstPlayerIndex;
    private List<Card> playerPlayedCards;

    /**
     * Spieler zurückgeben
     *
     * @return - Player
     */
    public static Algorithm getPlayer(int playernumber) {
        return players[playernumber - 1];
    }

    /**
     * Die gemachten Stiche sollen im Playground angezeigt werden. Dazu wird bei dem Spieler der gestochen hat die neue
     * Stichanzahl angezeigt.
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
                            new Handler(Looper.getMainLooper()).post(() -> StyleableToast.makeText(context, context.getString(R.string.p1_winner_text), R.style.player1).show());
                            System.out.println("Player 1 won: " + count);
                            break;
                        case 2:
                            stitches[1].setText(String.valueOf(count));
                            new Handler(Looper.getMainLooper()).post(() -> StyleableToast.makeText(context, context.getString(R.string.p2_winner_text), R.style.player2).show());
                            System.out.println("Player 2 won: " + count);
                            break;
                        case 3:
                            stitches[2].setText(String.valueOf(count));
                            new Handler(Looper.getMainLooper()).post(() -> StyleableToast.makeText(context, context.getString(R.string.p3_winner_text), R.style.player3).show());
                            System.out.println("Player 3 won: " + count);
                            break;
                        case 4:
                            stitches[3].setText(String.valueOf(count));
                            new Handler(Looper.getMainLooper()).post(() -> StyleableToast.makeText(context, context.getString(R.string.p4_winner_text), R.style.player4).show());
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

    public synchronized static Card getCardFromView(ImageView v) {
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
     * Im dafür vorgesehenen Feld wird das geählte Atout angezeigt. Das Atout ist in der Konstante gespeichert welches
     * angezeigt werden soll.
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

    /**
     * Neue runde die Karten werden neu ausgeteilt. Jeder Spieler bekommt einen neuen Algorithmus
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

    /**
     * der Name des spielers wird ausgegeben.
     *
     * @param pl spielerID
     */
    public synchronized void showPlayersName(int pl) {
        //Wenn kein Spielername gespeichert ist
        if (PopupName.namen.get(pl - 1).equals("")) {
            Toast.makeText(this, "Player" + pl, Toast.LENGTH_SHORT).show();
            //Wenn ein Spielername gespeichert ist
        } else {
            Toast.makeText(this, PopupName.namen.get(pl - 1), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);
        Playground.context = getApplicationContext();
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
        help.setOnClickListener(v -> {
            String start = "start";
            Intent intent = new Intent(Playground.this, TutorialActivity.class);
            intent.putExtra(start, false);
            startActivityForResult(intent, 0);
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

        playerPlayedCards = new ArrayList<>();


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

        stitches[0] = findViewById(R.id.player_stitches);
        stitches[1] = findViewById(R.id.pl1_stitches);
        stitches[2] = findViewById(R.id.pl2_stitches);
        stitches[3] = findViewById(R.id.pl3_stitches);

        setPlayerCards();

        gewonnene = new ArrayList<>();

        //Spielernamen anzeigen wenn auf die Katren gedrückt wird
        pl2 = findViewById(R.id.player4);
        pl2.setOnClickListener(view -> showPlayersName(2));
        pl3 = findViewById(R.id.player2);
        pl3.setOnClickListener(view -> showPlayersName(3));
        pl4 = findViewById(R.id.player3);
        pl4.setOnClickListener(view -> showPlayersName(4));
    }

    private void setPlayerCards() {
        ImageView player2card1 = findViewById(R.id.player2_card1);
        ImageView player2card2 = findViewById(R.id.player2_card2);
        ImageView player2card3 = findViewById(R.id.player2_card3);
        ImageView player2card4 = findViewById(R.id.player2_card4);
        ImageView player2card5 = findViewById(R.id.player2_card5);
        ImageView player3card1 = findViewById(R.id.player3_card1);
        ImageView player3card2 = findViewById(R.id.player3_card2);
        ImageView player3card3 = findViewById(R.id.player3_card3);
        ImageView player3card4 = findViewById(R.id.player3_card4);
        ImageView player3card5 = findViewById(R.id.player3_card5);
        ImageView player4card1 = findViewById(R.id.player4_card1);
        ImageView player4card2 = findViewById(R.id.player4_card2);
        ImageView player4card3 = findViewById(R.id.player4_card3);
        ImageView player4card4 = findViewById(R.id.player4_card4);
        ImageView player4card5 = findViewById(R.id.player4_card5);

        player2cards = new ArrayList<>();
        player2cards.add(player2card1);
        player2cards.add(player2card2);
        player2cards.add(player2card3);
        player2cards.add(player2card4);
        player2cards.add(player2card5);

        player3cards = new ArrayList<>();
        player3cards.add(player3card1);
        player3cards.add(player3card2);
        player3cards.add(player3card3);
        player3cards.add(player3card4);
        player3cards.add(player3card5);

        player4cards = new ArrayList<>();
        player4cards.add(player4card1);
        player4cards.add(player4card2);
        player4cards.add(player4card3);
        player4cards.add(player4card4);
        player4cards.add(player4card5);
        setCardBackground();
    }

    @Override
    public synchronized boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        if (beginner != 0) {
            runOnUiThread(() -> Toast.makeText(this, R.string.playerNotDran, Toast.LENGTH_SHORT).show());
            return false;
        }
        move = (ImageView) v;
        try {
            if (getCardFromView(move).getColor() != cardsOnFloor.get(firstPlayerIndex).getColor()) {
                // && getCardFromView(move).getColor() != Algorithm.getAtout() && getCardFromView(move).getColor() != Colors.WELI
                for (Card card : getPlayer(1).getHoldingCards()) {
                    if (playerPlayedCards.contains(card)) continue;
                    if (card.getColor() == cardsOnFloor.get(firstPlayerIndex).getColor()) {
                        Toast.makeText(this, "Farbzwang", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
        } catch (NullPointerException ignored) {
            System.out.println("Player is first.");
            System.out.println("Beginner: " + firstPlayerIndex);
        }
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        ClipData data = ClipData.newPlainText("", "");
        v.startDragAndDrop(data, shadowBuilder, v, 0);
        playerPlayedCards.add(getCardFromView(move));
        return true;
    }

    @Override
    public synchronized boolean onDrag(View v, DragEvent event) {
        new Thread(() -> {
            if (event.getAction() == DragEvent.ACTION_DRAG_ENDED) {//Karte in das Feld gezogen
                if (event.getResult()) {
                    runOnUiThread(() -> destination.setImageDrawable(move.getDrawable()));
                    move.setVisibility(View.INVISIBLE);
                    cardsOnFloor.put(beginner, getCardFromView(move));
                    playerCardNumber--;
                    System.out.println(getCardFromView(move).getColor() + "" + getCardFromView(move).getValue());
                    rotateBeginner();
                    play();
                }
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
        System.out.println(Thread.currentThread().getName() + " handled play()");
        new Thread(() -> {
            System.out.println("Play started");
            while (cardsOnFloor.size() < 4) {
                boolean first = cardsOnFloor.isEmpty();
                System.out.println("While: " + cardsOnFloor.size());
                if (beginner == 0) {
                    lock.lock();
                    System.out.println("Spieler ist dran");
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), R.string.playerDran, Toast.LENGTH_SHORT).show());
                    try {
                        System.out.println(Thread.currentThread().getName() + " is sleeping");
                        spielerSpielt.await();
                        System.out.println(Thread.currentThread().getName() + " is awake");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.unlock();
                    continue;
                }
                Card[] cArray = new Card[cardsOnFloor.size()];
                cardsOnFloor.values().toArray(cArray);
                Card temp = null;
                while (temp == null) {
                    temp = players[beginner].getResponseCard(Algorithm.getWinnerFromCards(cArray));
                    try {
                        if (temp.getColor() != cardsOnFloor.get(firstPlayerIndex).getColor()) {
                            // && getCardFromView(move).getColor() != Algorithm.getAtout() && getCardFromView(move).getColor() != Colors.WELI
                            for (Card card : getPlayer(beginner + 1).getHoldingCards()) {
                                if (card.getColor() == cardsOnFloor.get(firstPlayerIndex).getColor()) {
                                    temp = null;
                                    break;
                                }
                            }
                        }
                    } catch (NullPointerException ignored) {
                        System.out.println(beginner + " is first.");
                        System.out.println("Beginner: " + firstPlayerIndex);
                    }
                }
                cardsOnFloor.put(beginner, temp);
                if (first) {
                    firstPlayerIndex = beginner;
                }

                /* hier kommt die Animation hin */

                Drawable pic = null;
                try {
                    pic = cardsOnFloor.get(beginner).getPicture();
                } catch (NullPointerException ignored) {
                }
                kartenAnzeigen(beginner, pic);

                System.out.println(("Ich bin " + players[beginner].getName() + " und spiele " + cardsOnFloor.get(beginner).getColor() + cardsOnFloor.get(beginner).getValue() + ". Ich habe folgende Karten: " + players[beginner].getHoldingCardsString()));
                rotateBeginner();
            }
            try {
                Thread.sleep(TIME_TO_WAIT_AFTER_ROUND);
            } catch (InterruptedException | NullPointerException e) {
                e.printStackTrace();
            }

            //Gewinner ermitteln
            //Stich eintragen
            this.whichCardWon();

            //Spielfeldkarten löschen

            /* Hier wird die Animation zurück gesetzt*/

            cardsOnFloor.clear();
            kartenAnzeigen(0, null);
            kartenAnzeigen(1, null);
            kartenAnzeigen(2, null);
            kartenAnzeigen(3, null);


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
        }).start();
    }

    public synchronized void animate(int spieler, Drawable card, boolean waitForIt) {
        animation(spieler, card);
        if (waitForIt) {
            try {
                Thread.sleep(ANIMATION_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void animation(int spieler, Drawable card) {
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
                runOnUiThread(() -> anim2.setImageDrawable(card));
                animation = new TranslateAnimation(0, (card_pl2.getX() - anim2.getX()) + 7, 0, card_pl2.getY() - anim2.getY());
                animation.setRepeatMode(0);
                animation.setDuration(ANIMATION_DURATION);
                animation.setFillAfter(true);
                anim2.startAnimation(animation);
                hideCards(1, player2cards);
                break;

            case 2:
                runOnUiThread(() -> anim3.setImageDrawable(card));
                animation = new TranslateAnimation(0, (card_pl3.getX() - anim3.getX()) + 7, 0, card_pl3.getY() - anim3.getY());
                animation.setRepeatMode(0);
                animation.setDuration(ANIMATION_DURATION);
                animation.setFillAfter(true);
                anim3.startAnimation(animation);
                hideCards(2, player3cards);
                break;

            case 3:
                runOnUiThread(() -> anim4.setImageDrawable(card));
                animation = new TranslateAnimation(0, (card_pl4.getX() - anim4.getX()) + 7, 0, card_pl4.getY() - anim4.getY());
                animation.setRepeatMode(0);
                animation.setDuration(ANIMATION_DURATION);
                animation.setFillAfter(true);
                anim4.startAnimation(animation);
                hideCards(3, player4cards);
                break;
        }
    }

    /**
     * Die Anzahl der angezeigten Handkarten entspricht der tatsächlichen Anzahl der Handkarten des jeweiligen Spielers
     *
     * @param player der Spieler
     * @param cards  die Karten des Spielers als ImageView
     */
    private void hideCards(int player, ArrayList<ImageView> cards) {
        switch (players[player].getHoldingCards().size()) {
            case 4:
                cards.get(0).setVisibility(View.INVISIBLE);
                break;
            case 3:
                cards.get(1).setVisibility(View.INVISIBLE);
                break;
            case 2:
                cards.get(2).setVisibility(View.INVISIBLE);
                break;
            case 1:
                cards.get(3).setVisibility(View.INVISIBLE);
                break;
            case 0:
                cards.get(4).setVisibility(View.INVISIBLE);
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
        if (PopupAtout.alreadyLeft) {
            neuAusteilen();
        }

        //Das Atout wird angezeigt -> wird im PopupKartentausch schon angezeigt
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
        stitches[Algorithm.getDealer() - 1].setBackgroundResource(R.drawable.empty_magenta);
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
            for (ImageView card : player2cards) {
                card.setVisibility(View.VISIBLE);
            }
            for (ImageView card : player3cards) {
                card.setVisibility(View.VISIBLE);
            }
            for (ImageView card : player4cards) {
                card.setVisibility(View.VISIBLE);
            }
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

            for (TextView view : stitches) {
                view.setBackgroundResource(R.drawable.empty);
            }

            playerPlayedCards.clear();
        });

        //Damit sich der dealer jede Runde aendert
        int dealer = Algorithm.getDealer();
        if (dealer < 4) {
            int i = dealer + 1;
            Algorithm.setDealer(i);
        } else {
            Algorithm.setDealer(1);
        }
        System.out.println("Runde: reset, dealer: " + Algorithm.getDealer());
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
            runOnUiThread(new Thread(() -> {
                if (card != null) {
                    animate(spieler, card, true);
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

    /**
     * Die Karten der anderen Spieler werden angezeigt
     */
    public static void setCardBackground(){

        for(int i = 0; i <5; i++){
            player2cards.get(i).setImageDrawable(MainActivity.background);
            //Karten um 90 Grad drehen
            player3cards.get(i).setRotation(-90);
            player4cards.get(i).setRotation(90);
            player3cards.get(i).setImageDrawable(MainActivity.background);
            player4cards.get(i).setImageDrawable(MainActivity.background);
        }
    }

}
