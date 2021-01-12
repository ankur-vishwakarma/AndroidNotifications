package com.example.push_notification_test;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.push_notification_test.views.CustomView;
import com.example.push_notification_test.views.MyVideoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    //private  boolean isTouched;
    MyVideoView v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //showCustomNotification("hh","dd");
        //showNotification("aa","baban");

        //normal videoview
        //setContentView(R.layout.videolayout);  //videono
        //VideoView v =   findViewById(R.id.video_view);


        //sO video error - CUSTOM VIEW
        /*setContentView(R.layout.ytplayeractivity);
        v = new MyVideoView(this);

        v.findViewById(R.id.fullscreen_content);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.myvideo;

        Uri uri = Uri.parse(videoPath);
        v.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        v.setMediaController(mediaController);
        mediaController.setAnchorView(v);
        v.start();
        */

        //v.setOnCompletionListener(videoListener);



        //showCustomNotification("mytitle","this is custom");
        //showVideoNotificationCustomView();
        //YCustomview
        //CustomView customView = new CustomView(this);



        //NOTIFICATION PART - commmit 1
        //since for oreo and above we need to create channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("MyNotifications","MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            //lets tell notif manager that this is my channel
            //we are using notif_mangr not notif_mangr_compat as we know tht compat one was needed for older version while this code if for oreo+ only

            //notif manager is exlusive in OS and only one is found so we will need to get service
            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel);
        }

        //playVideo();

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "success";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }
                        //Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });



    }

    public void playAudio(){
        MediaPlayer mp=MediaPlayer.create(this, R.raw.b);
        mp.start();
    }

    public void playVideo(){
        VideoView videoView =   findViewById(R.id.video_view);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.myvideo;

        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
    }

    public void playVideoCustomView(){
        VideoView videoView =   findViewById(R.id.custom_view_vid);
        //showCustomNotification("a","b",videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.myvideo;

        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
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

    public void showCustomNotification(String title, String message){
        //stack
        /*RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        VideoView video = new VideoView(this);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.myvideo;

        Uri uri = Uri.parse(videoPath);
        video.setVideoURI(uri);
        video.setLayoutParams(new FrameLayout.LayoutParams(550, 550));
        layout.addView(video);*/

        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.main_view);
        RemoteViews listEntryLayout = new RemoteViews(getPackageName(), R.layout.videolayout);

        VideoView videoView =   findViewById(R.id.video_view);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.myvideo;


        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        //videoView.setVideoPath(videoPath);
        listEntryLayout.setCharSequence(R.id.video_view, "setVideoPath", "videoPath");
       // listEntryLayout.

        //default set for text
        //listEntryLayout.setTextViewText(R.id.stock_text, "Ankurs text");



        //Add the new remote view to the parent/containing Layout object
        rv.addView(R.id.main_layout, listEntryLayout);

        Notification customNotification = new NotificationCompat.Builder(this, "MyNotifications")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(rv)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(123, customNotification);



        //DEVELOPER ANDROID
        //RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.textlayout);
        /*RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.videolayout);

        //saurabhs lemnisk sdk

        notificationLayoutExpanded.addView(R.id.video_view,notificationLayout);

        // Apply the layouts to the notification
        //notificationLayoutExpanded.set

        Notification customNotification = new NotificationCompat.Builder(this, "MyNotifications")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .build();*/

    }

    //SO videoview

    private static final String TAG = "MEDIA";
    private TextView tv;


    MediaPlayer.OnCompletionListener videoListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.d("onCompletion", "Total time paused: " + v.getTotalTimeMillis());
        }
    };

    public void PlayPause(View view) {
        //Do something in response to button press
        if(v.isPlaying()){
            v.pause();
        } else {
            v.start();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vide, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.videolayout, container, false);
            return rootView;
        }
    }

    //SO listener
    /*private void initActivity() {
        MyVideoView vPlayer = findViewById(R.id.fullscreen_content);
        vPlayer.setOnTouchListener(new View.OnTouchListener() {
                                       @Override
                                       public boolean onTouch(View view, MotionEvent motionEvent) {
                                           if (isTouched) return false;
                                           switch (motionEvent.getAction()) {
                                               case MotionEvent.ACTION_UP:
                                                   MyVideoView vPlayer = (MyVideoView) view;
                                                   vPlayer.seekTo(vPlayer.getDuration());
                                                   isTouched = true;
                                                   break;
                                               default:
                                                   break;
                                           }
                                           view.performClick();
                                           return true;
                                       }
        });
    }*/


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/




}

//oncreate defaultcodr
/*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/