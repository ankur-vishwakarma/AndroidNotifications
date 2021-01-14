package com.example.push_notification_test;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.w3c.dom.DOMImplementationList;

public class MyMessagingService extends FirebaseMessagingService {


    public static final String MYFLAG = "MYFLAG";
    private static final String M ="ANKUR" ;
    static int flag=1;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        /*String url="";
        if (remoteMessage.getData().size() > 0) {
            //Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            url = remoteMessage.getData().get("url");
        }

        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    */
        Log.d(M,"onMessageReceived");

        if(remoteMessage.getData().size() > 0) {
            Log.d(M,"inside data handler of FCM");
            Log.d(M,"Data "+remoteMessage.getData().toString());
            sendAudioNotification();
        }

        //for normal nots
        //sendAudioNotification();


    }

    public void sendAudioNotification(){
        /*Intent intent = new Intent(this, commandRecieved.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
*/
        Log.d(M,"SENDAUDIONOTIFICATION");
        Intent serviceIntent = new Intent(this, MyIntentService.class);
        //serviceIntent.putExtra("MYFLAG","play");

        PendingIntent servicePendingIntent = PendingIntent.getService(this,2,serviceIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        /*Intent serviceIntentPause = new Intent(this, MyIntentService.class);
        serviceIntentPause.putExtra(MYFLAG,"pause");

        PendingIntent servicePendingIntentPause = PendingIntent.getService(this,2,serviceIntentPause,PendingIntent.FLAG_ONE_SHOT);
*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotifications")
                .setContentTitle("music")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("my music")
                .addAction(R.drawable.play,"play",servicePendingIntent)
                .addAction(R.drawable.play,"pause",servicePendingIntent);


        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(123, builder.build());
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

    @Override
    public void onNewToken(String token) {
        Log.d(M,token);
    }

   /*
   pixel2 api24 -
   dp0AZzHSQLe8d6ayvmmYfd:APA91bH5-9Hzri9ttLnqIzHWox5Dq8ce7txQC-m4pbE9BHI6srJQ5dbc3EwRJ7ZdF4FFDigEGeWuSZqENrEipHs0Z3C2BhU5sIZrHL2saxGjOvfzQxBJw7MyLws50SQbE7UoTSsZUC_n
   *
   * */
}
