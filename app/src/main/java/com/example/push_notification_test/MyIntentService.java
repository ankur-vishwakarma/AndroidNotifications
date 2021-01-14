package com.example.push_notification_test;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MyIntentService extends IntentService {

    private static final String ANKUR = "ANKUR";

    public MyIntentService() {
        super("MyIntentService");
    }

    MediaPlayer mp;

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(ANKUR,"onHandleIntent");

        //when activity not running
        //startForeground(1002,sendAudioNotification());
        //String flag=null;
        if (intent != null) {
            /*if(intent.hasExtra(MyMessagingService.MYFLAG)){
                flag=intent.getStringExtra(MyMessagingService.MYFLAG);
            }
            Log.d(ANKUR,flag);*/

            if(MyMessagingService.flag==1){
                Log.d(ANKUR,"PLAY");
                MyMessagingService.flag=0;
                mp=MediaPlayer.create(this, R.raw.b);
                mp.start();
            }
            else{
                Log.d(ANKUR,"PAUSE");
                MyMessagingService.flag=1;
                mp.pause();
            }
        }

    }

    public Notification sendAudioNotification(){
        /*Intent intent = new Intent(this, commandRecieved.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
*/
        Log.d(ANKUR,"SENDAUDIONOTIFICATION");
        Intent serviceIntent = new Intent(this, MyIntentService.class);
        //serviceIntent.putExtra(MYFLAG,"play");

        PendingIntent servicePendingIntent = PendingIntent.getService(this,2,serviceIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        /*Intent serviceIntentPause = new Intent(this, MyIntentService.class);
        serviceIntentPause.putExtra(MYFLAG,"pause");

        PendingIntent servicePendingIntentPause = PendingIntent.getService(this,2,serviceIntentPause,PendingIntent.FLAG_ONE_SHOT);
*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotifications")
                .setContentTitle("music")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("my music")
                .addAction(R.drawable.play,"play",servicePendingIntent);
        //.addAction(R.drawable.play,"pause",servicePendingIntentPause);


        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        Notification notification= builder.build();
        return notification;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        stopForeground(true);
    }
}