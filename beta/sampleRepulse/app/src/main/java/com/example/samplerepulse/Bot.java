package com.example.samplerepulse;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Bot {
    Ball ballPos = new Ball(); //Влад: в Ball створено пустий конструктор щоб я міг отримувати змінні м'яча
    public float botX; // player position X
    private ImageView player; // player image main
    private Drawable plate; // player plate(for plate values)
    protected int plateWidth, plateHeight;
    private int screenWidth, screenHeight;

    private float ballX = ballPos.ballX;//Влад

    public int plateDirection = 1; // plate Direction 1 - right, (-1) - left

    private int plateSpeed = 10;

    public boolean plateMove = false; // check whether plate need moving
    private Timer timer;

    public Bot(ImageView player, Drawable plate, int screenWidth, int screenHeight){
        this.player = player;
        plateWidth = plate.getIntrinsicWidth();
        plateHeight = plate.getIntrinsicHeight();

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        botX = (float)(screenWidth / 2); // set player start position

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
        botX += ballX;
        if (botX < 0) { // check wall collision
            botX = 0;
        } else if(botX > screenWidth - plateWidth){ // check wall collision
            botX = screenWidth - plateWidth;
        } else{
            player.setX(botX); // update X position
            System.out.println("X" + botX);
        }
    }
}
