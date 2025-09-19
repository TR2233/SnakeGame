package com.example.snakegame.MainScreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.snakegame.R;

public class HighScoreReceiver extends BroadcastReceiver {
    public static final String NEW_HIGHSCORE_ACTION = "com.example.snake.action.NEW_HIGHSCORE_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "01");

        Handler mhandler = new Handler();

        mhandler.post(new Runnable() {
            @Override
            public void run() {
                builder.setSmallIcon(R.drawable.snake2)
                        .setContentTitle("Level up!")
                        .setContentText("New High Score!!!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                notificationManager.notify(1, builder.build());
            }
        });
    }
}