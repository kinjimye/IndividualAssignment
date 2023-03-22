package my.edu.utar.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity {

    private ListView leaderboardListView;
    private ArrayList<Score> scoresList;
    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        //initialize UI elements
        leaderboardListView = findViewById(R.id.leaderboardListView);

        //initialize database helper
        dbHelper = new Database(this);
        Button menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back to menu
                Intent intent = new Intent(LeaderBoard.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //retrieve score data from database
        scoresList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"username", "score"};
        Cursor cursor = db.query("scores", projection, null, null, null, null, "score DESC","25");
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow("score"));
            Score newScore = new Score(username, score);
            scoresList.add(newScore);
        }
        cursor.close();
        db.close();


        //set up leaderboard listview
        LeaderboardAdapter adapter = new LeaderboardAdapter(this, scoresList);
        leaderboardListView.setAdapter(adapter);
    }


    private static class Score {
        String username;
        int score;

        public Score(String username, int score) {
            this.username = username;
            this.score = score;
        }
    }

    private static class LeaderboardAdapter extends ArrayAdapter<Score> {

        private Context context;
        private ArrayList<Score> scoresList;

        public LeaderboardAdapter(Context context, ArrayList<Score> scoresList) {
            super(context, R.layout.activity_leader_board, scoresList);
            this.context = context;
            this.scoresList = scoresList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = convertView;

            if (rowView == null) {
                rowView = inflater.inflate(R.layout.leaderboard_row, parent, false);
            }

            TextView usernameTextView = rowView.findViewById(R.id.username);
            TextView scoreTextView = rowView.findViewById(R.id.score);

            Score score = scoresList.get(position);

            usernameTextView.setText(score.username);
            scoreTextView.setText(String.valueOf(score.score));

            return rowView;
        }
    }

}