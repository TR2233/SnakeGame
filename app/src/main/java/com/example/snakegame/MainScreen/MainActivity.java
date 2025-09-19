package com.example.snakegame.MainScreen;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snakegame.Game.Play;
import com.example.snakegame.R;


public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button settingsButton;
    TextView mTextView;
    SharedPreferences sharedPreferences;
    private IntentFilter filter = new IntentFilter(HighScoreReceiver.NEW_HIGHSCORE_ACTION);
    private HighScoreReceiver highscoreReceiver = new HighScoreReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the start button and set the on-click listener
        startButton = findViewById(R.id.startB);
        settingsButton = findViewById(R.id.main_settings);

        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Play.class);
            startActivity(intent);
        });

        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        });

        //create the shared preferences for score and highscore if they don't already exist
        Context context = getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sharedPreferences.contains("Score")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Score", 0);
            editor.putInt("HighScore", 0);
            editor.apply();
        }

        //assign the score value to the textview
//        mTextView = findViewById(R.id.score);
//        mTextView.setText("HighScore: " + sharedPreferences.getInt("HighScore",0));

        //create the notification channel

        createNotificationChannel();

    }

    //register the receiver at the beginning of the application
    @Override
    public void onStart() {
        super.onStart();
        registerReceiver(highscoreReceiver, filter);

        //takes care of new highscore when returned to this activity

    }

    //make sure the receiver is unregistered when the app is closing
    @Override
    public void onStop() {
        unregisterReceiver(highscoreReceiver);
        super.onStop();
    }


    public void resetHighScore(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("HighScore", 0);
        editor.apply();
        Toast.makeText(this, "Score reset!", Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("01", name, importance);
            channel.setDescription(description);
            //Register the channel with the system, you cant change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}