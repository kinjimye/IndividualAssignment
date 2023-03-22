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

public class Level1 extends AppCompatActivity {

    private View[] views;
    private int highlightedIndex;
    private int successfulTouches;
    private TextView touchedTextView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        //initialize views
        views = new View[] {
                findViewById(R.id.view1),
                findViewById(R.id.view2),
                findViewById(R.id.view3),
                findViewById(R.id.view4)
        };
        highlightedIndex = -1;
        successfulTouches = 0;
        touchedTextView = findViewById(R.id.successfulTouchesTextView);

        //random highlight a view
        highlightRandomView();

        // Set up touch listeners for views
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
                // Back to Menu
                Intent intent = new Intent(Level1.this, MainActivity.class);
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
                // Proceed to Level 2
                Intent intent = new Intent(Level1.this, Level2.class);
                intent.putExtra("Level", 1);
                intent.putExtra("score_level1", successfulTouches);
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
        //random highlight a new view that is different from the previous view
        int randomIndex = highlightedIndex;
        while (randomIndex == highlightedIndex) {
            randomIndex = (int) (Math.random() * views.length);
        }
        highlightedIndex = randomIndex;
        views[highlightedIndex].setBackgroundColor(Color.RED);
    }

}