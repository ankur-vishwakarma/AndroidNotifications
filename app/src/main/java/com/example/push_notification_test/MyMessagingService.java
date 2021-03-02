package com.example.push_notification_test;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.provider.CalendarContract.CalendarCache.URI;

public class MyMessagingService extends FirebaseMessagingService {

    public static final String MYFLAG = "MYFLAG";
    private static final String M ="ANKUR" ;
    static int flag=1;
    static MediaPlayer mp;


    //gif
    Bitmap bitmap;
    private static final int NOTIF_ID = 1234;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private RemoteViews mRemoteViews;
    private Notification mNotification;

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

        //for payload
        if(remoteMessage.getData().size() > 0) {
            Log.d(M,"inside data handler of FCM");
            Log.d(M,"Data "+remoteMessage.getData().toString());

            /*//extract image
            String url = remoteMessage.getData().get("image");
            bitmap = getBitmapfromUrl(url);

            sendImageNotificationRemote(bitmap,url);*/

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

    public void sendImageNotificationRemote(Bitmap bitmap,String url){
        Log.d(M,"SEND IMAGE NOTIFICATION REMOTE VIEWS");

        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.textlayout);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.imagelayout);

        //set image
        notificationLayoutExpanded.setImageViewBitmap(13,bitmap);
        //Uri uri = Uri.parse(url);
        //notificationLayoutExpanded.setImageViewUri(123,uri);

        Notification customNotification = new NotificationCompat.Builder(this, "MyNotifications")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .build();


        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(123,customNotification);

    }

    public void showGifNotification(){
        Log.d(M,"SEND GIF NOTIFICATION");

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

    public Bitmap getBitmapfromUrl(String imageUrl) {
        Log.d(M,"getBitmapfromUrl");
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

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

    //image
    //https://cdn.pixabay.com/photo/2021/01/04/07/33/man-5886719_1280.jpg
}
