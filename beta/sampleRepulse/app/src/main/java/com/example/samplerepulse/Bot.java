package com.example.samplerepulse;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Bot {
    Ball ball;
    public float botX;
    private ImageView player;
    private Drawable plate;
    protected int plateWidth, plateHeight;
    private int screenWidth, screenHeight;

    private float ballX = 0;

    public int plateDirection = 1;
    private int plateSpeed = 10;

    public boolean plateMove = false;
    public Timer timer;

    public Bot(ImageView player, Drawable plate, int screenWidth, int screenHeight){
        this.player = player;
        plateWidth = plate.getIntrinsicWidth();
        plateHeight = plate.getIntrinsicHeight();
        plateMove = true;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        botX = (screenWidth / 2);
    }

    public void setBall(Ball ball){
        this.ball = ball;
        this.ballX = ball.ballX;
    }

    public void startMove(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //System.out.println("Bot Timer");
                if (plateMove) {
                    move(ball.ballX);
                }
            }
        }, 0, 20);
    }

    public void move(float ballX){
        botX = ballX - (int)(plateWidth / 2 - ball.ballSize / 2);
        //System.out.println("BotX " + botX + " ballX " + ballX +" plateWidth " + plateWidth + " ballSize " + ball.ballSize);
        if (botX < 0) { // check wall collision
            botX = 0;
        } else if (botX > screenWidth - plateWidth) { // check wall collision
            botX = screenWidth - plateWidth;
        }
        player.setX(botX); // update X position
//            player.setLeft((int)playerLeft);
//            player.setRight((int)pLayerRight);
        // System.out.println("X" + playerX);
    }
}