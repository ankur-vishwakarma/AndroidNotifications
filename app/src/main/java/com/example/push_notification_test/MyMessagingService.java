package com.example.push_notification_test;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String url="";
        if (remoteMessage.getData().size() > 0) {
            //Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            url = remoteMessage.getData().get("url");
        }

        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    public void showCustomNotification(String title, String message,VideoView videoView){
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.textlayout);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.videolayout);

        //saurabhs lemnisk sdk
        ///notificationLayoutExpanded.addView(R.id.video_view,);

        // Apply the layouts to the notification
        //notificationLayoutExpanded.set

        Notification customNotification = new NotificationCompat.Builder(this, "MyNotifications")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .build();

    }

    public void showNotification(String title, String message){
        //setting activity on touching not
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //showing nots
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotifications")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(123, builder.build());
    }



}
