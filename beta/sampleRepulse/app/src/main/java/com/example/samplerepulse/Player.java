package com.example.samplerepulse;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class Player {
    public float playerX;
    private ImageView player;
    private Drawable plate;
    protected int plateWidth, plateHeight;
    private int screenWidth, screenHeight;

    public int plateDirection = 1;
    private int plateSpeed = 10;
    public boolean plateMove = false;
    private Timer timer;

    public Player(ImageView player, Drawable plate, int screenWidth, int screenHeight){
        this.player = player;
        plateWidth = plate.getIntrinsicWidth();
        plateHeight = plate.getIntrinsicHeight();

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        playerX = (float)(screenWidth / 2);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (plateMove) {
                    move();
                }
            }
        }, 0, 20);

    }
    public void move(){
        playerX += plateSpeed * plateDirection;
        if (playerX < 0) {
            System.out.println("Collision");
            System.out.println("X" + playerX + " Framewidth " + screenWidth + "plateWidth " + plateWidth);
            playerX = 0;
        } else if( playerX > screenWidth - plateWidth){
            playerX = screenWidth - plateWidth;
        } else{
            player.setX(playerX);
            System.out.println("X" + playerX);
        }
    }


}
