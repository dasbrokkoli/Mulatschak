package itp.project.mulatschak;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Card> cards = new ArrayList<>();
    private Algorithm algorithm = new Algorithm(cards,null);

    //fuer PopUp - Schwierigkeit
    Button showPopupBtn;
    ImageButton closePopupBtn;
    PopupWindow popupWindow;
    ConstraintLayout constraintLayout;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCards();

        View settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
       });

        View help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TutorialActivity.class));
            }
        });

        View log = findViewById(R.id.log);
        log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LogActivity.class));
            }
        });

        //PopUp - Schwierigkeit
        showPopupBtn = (Button) findViewById(R.id.showPopupBtn);
        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PopupDifficulty.class));
            }
        });




        /*showPopupBtn = (Button) findViewById(R.id.showPopupBtn);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View customView = layoutInflater.inflate(R.layout.popup_difficulty,null);
                closePopupBtn = (ImageButton) customView.findViewById(R.id.closePopupBtn);

                popupWindow = new PopupWindow(customView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.showAtLocation(constraintLayout, Gravity.CENTER,0,0);

                //Next
                next = (Button) customView.findViewById(R.id.showNamePopup);
                next.setOnClickListener(new View.OnClickListener() {
                    //PopUp - Names
                    @Override
                    public void onClick(View view) {
                        showPopupBtn = (Button) customView.findViewById(R.id.showNamePopup);
                        //System.out.println(showPopupBtn.getText());
                        constraintLayout = (ConstraintLayout) customView.findViewById(R.id.cLayout);

                        System.out.println("Beginn");
                        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View customView = layoutInflater.inflate(R.layout.popup_name,null);

                        closePopupBtn = (ImageButton) customView.findViewById(R.id.closePopupBtn2);

                        popupWindow = new PopupWindow(customView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER,0,0);
                        closePopupBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                System.out.println("Closing 2");
                                popupWindow.dismiss();
                            }
                        });
                        System.out.println("ende");

                    }
                });

                //Close
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                        System.out.println("Closing 1");
                    }
                });
            }
        });*/

        //PopUp - Namen
        /*showPopupBtn = (Button) constraintLayout.findViewById(R.id.showNamePopup);
        System.out.println(showPopupBtn.getText());
        constraintLayout = (ConstraintLayout) findViewById(R.id.cLayout);
        System.out.println("vor onClick");
        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Beginn");
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup_name,null);

                closePopupBtn = (ImageButton) customView.findViewById(R.id.closePopupBtn2);

                popupWindow = new PopupWindow(customView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.showAtLocation(constraintLayout, Gravity.CENTER,0,0);
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                System.out.println("ende");
            }
        });
        System.out.println("Nach onClick");
*/



    }

    private void initCards() {
        cards.add(new Card(getResources().getDrawable(R.drawable.card_heart7, null), Colors.HERZ, Values.W7));
    }

    public List<Card> getCards(){
        return cards;
    }
}