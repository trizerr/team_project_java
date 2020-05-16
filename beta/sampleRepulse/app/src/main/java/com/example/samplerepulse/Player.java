package com.example.samplerepulse;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class Player {
    public float playerX; // player position X
    public float playerLeft, pLayerRight,playerCenter;
    private ImageView player; // player image main
    private Drawable plate; // player plate(for plate values)
    protected int plateWidth, plateHeight;
    private int screenWidth, screenHeight;

    public int plateDirection = 1; // plate Direction 1 - right, (-1) - left
    private int plateSpeed = 10;
    public boolean plateMove = false; // check whether plate need moving
    private Timer timer;

    public Player(ImageView player, Drawable plate, int screenWidth, int screenHeight){
        this.player = player;
        plateWidth = plate.getIntrinsicWidth();
        plateHeight = plate.getIntrinsicHeight();

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        playerX = (float)(screenWidth / 2); // set player start position
        this.playerLeft =  playerX-100;
        this.pLayerRight = playerX+100;
        this.playerCenter = playerX+100;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // call move() every 20ms
                if (plateMove) {
                    move();
                }
            }
        }, 0, 20);

    }
    public void move(){ // move the player
        playerX += plateSpeed * plateDirection;
        playerLeft =  playerX-100;
        pLayerRight = playerX+100;
        if (playerX < 0) { // check wall collision
//            System.out.println("Collision");
//            System.out.println("X" + playerX + " Framewidth " + screenWidth + "plateWidth " + plateWidth);
            playerX = 0;
        } else if( playerX > screenWidth - plateWidth){ // check wall collision
            playerX = screenWidth - plateWidth;
        } else{
            player.setX(playerX); // update X position
            player.setLeft((int)playerLeft);
            player.setRight((int)pLayerRight);
           // System.out.println("X" + playerX);
        }
    }

}
