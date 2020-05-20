package com.example.samplerepulse;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Player {
    public float playerX; // player position X
    public float playerLeft, pLayerRight,playerCenter;
    public ImageView player; // player image main
    private Drawable plate; // player plate(for plate values)
    protected int plateWidth, plateHeight;
    private int screenWidth, screenHeight;

    public int plateDirection = 1; // plate Direction 1 - right, (-1) - left
    private int plateSpeed = 10;
    public int plateSpeedFrame = 50;
    public boolean plateMove = false; // check whether plate need moving
    public TimerHandler timerHandler;


    public Player(ImageView player, Drawable plate, int screenWidth, int screenHeight){
        this.player = player;
        plateWidth = plate.getIntrinsicWidth();
        plateHeight = plate.getIntrinsicHeight();

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        playerX = (float)(screenWidth / 2) - plateWidth / 2; // set player start position
        this.playerLeft =  playerX - plateWidth/2;
        this.pLayerRight = playerX + plateWidth / 2;
        this.playerCenter = playerX+100;
    }

    public void setTimerHandler(TimerHandler timerHandler){
        this.timerHandler = timerHandler;
    }


    public void move(){ // move the player
        playerX += plateSpeed * plateDirection;
        playerLeft =  playerX - plateWidth/2;
        pLayerRight = playerX + plateWidth/2;
        if (playerX < 0) { // check wall collision
            playerX = 0;
        } else if( playerX > screenWidth - plateWidth){ // check wall collision
            playerX = screenWidth - plateWidth;
        } else{
            player.setX(playerX); // update X position
        }
    }
}
