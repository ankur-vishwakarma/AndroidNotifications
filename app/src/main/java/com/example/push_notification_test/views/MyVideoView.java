package com.example.push_notification_test.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

import androidx.annotation.Nullable;

public class MyVideoView extends VideoView {

    //SO
    long lastPausedTime  = 0; // The time of the last pause (milliseconds)
    long totalPausedTime = 0; // The total time paused (milliseconds)

    @Override
    public void pause() {
        lastPausedTime = System.currentTimeMillis();

        super.pause();
    }

    @Override
    public void start() {
        if (lastPausedTime != 0) {
            totalPausedTime += System.currentTimeMillis() - lastPausedTime;
        }
        super.start();
    }

    public long getTotalTimeMillis() {
        return totalPausedTime;
    }


    //constuctors
    public MyVideoView(Context context) {
        super(context);

        init(null);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

     /*public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    private void init(@Nullable AttributeSet set){

    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
