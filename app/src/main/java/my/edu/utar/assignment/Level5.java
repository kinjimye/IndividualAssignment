package my.edu.utar.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Level5 extends AppCompatActivity {

    private View[] views;
    private int highlightedIndex;
    private int successfulTouches;
    private TextView touchedTextView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level5);
        //initialize views
        views = new View[] {
                findViewById(R.id.view1),
                findViewById(R.id.view2),
                findViewById(R.id.view3),
                findViewById(R.id.view4),
                findViewById(R.id.view5),
                findViewById(R.id.view6),
                findViewById(R.id.view7),
                findViewById(R.id.view8),
                findViewById(R.id.view9),
                findViewById(R.id.view10),
                findViewById(R.id.view11),
                findViewById(R.id.view12),
                findViewById(R.id.view13),
                findViewById(R.id.view14),
                findViewById(R.id.view15),
                findViewById(R.id.view16),
                findViewById(R.id.view17),
                findViewById(R.id.view18),
                findViewById(R.id.view19),
                findViewById(R.id.view20),
                findViewById(R.id.view21),
                findViewById(R.id.view22),
                findViewById(R.id.view23),
                findViewById(R.id.view24),
                findViewById(R.id.view25),
                findViewById(R.id.view26),
                findViewById(R.id.view27),
                findViewById(R.id.view28),
                findViewById(R.id.view29),
                findViewById(R.id.view30),
                findViewById(R.id.view31),
                findViewById(R.id.view32),
                findViewById(R.id.view33),
                findViewById(R.id.view34),
                findViewById(R.id.view35),
                findViewById(R.id.view36)
        };
        highlightedIndex = -1;
        touchedTextView = findViewById(R.id.successfulTouchesTextView);
        successfulTouches = getIntent().getIntExtra("score_level4",1);

        //random highlight a view
        highlightRandomView();

        //set up touch listeners for views
        for (View view : views) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v == views[highlightedIndex]) {
                        // Player touched the highlighted view
                        successfulTouches++;
                        touchedTextView.setText("Successful touches: " + successfulTouches);
                        highlightRandomView();
                    }
                    return true;
                }
            });
        }

        Button menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back to Menu
                Intent intent = new Intent(Level5.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //set up countdown timer
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long timeUntilFinished) {}

            @Override
            public void onFinish() {
                // Proceed to insert player name
                Intent intent = new Intent(Level5.this, playerName.class);
                intent.putExtra("Level", 5);
                intent.putExtra("score_level5", successfulTouches);
                startActivity(intent);
                finish();
            }
        };
        countDownTimer.start();
    }

    private void highlightRandomView() {
        //remove highlight from previous view
        if (highlightedIndex != -1) {
            views[highlightedIndex].setBackgroundColor(Color.WHITE);
        }
        //randomly highlight a new view that is different from the previous view
        int randomIndex = highlightedIndex;
        while (randomIndex == highlightedIndex) {
            randomIndex = (int) (Math.random() * views.length);
        }
        highlightedIndex = randomIndex;
        views[highlightedIndex].setBackgroundColor(Color.RED);
    }
}