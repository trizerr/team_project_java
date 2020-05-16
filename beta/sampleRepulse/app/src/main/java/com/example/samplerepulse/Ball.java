package com.example.samplerepulse;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Ball {
    public float ballX, ballY;
    public int ballXSpeed = 5; // change x position
    public int ballYSpeed = 10; // change y position
    public int ballDirectionX = 1;
    public int ballDirectionY = 1;
    private int ballSize;
    private Timer timer;
    public boolean ballMoving;
    private Drawable ballDrawable;
    private ImageView ball;
    Player playerTop, playerBottom;
    int playerMarginVertical = 10;
    public int screenWidth, screenHeight;

    public Ball(ImageView ball, Drawable ballDrawable,Player playerTop, Player playerBottom, int screenWidth, int screenHeight){
        this.ball = ball;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.playerBottom = playerBottom;
        this.playerTop = playerTop;
        ballSize = ballDrawable.getIntrinsicWidth();
        ballMoving = true;

        ballX = (float)(screenWidth / 2);
        ballY = (float)(screenHeight / 2);
        System.out.println(ballX +" " + ballY);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // move the ball every 20 ms
                if (ballMoving) {
                    move();
                }
            }
        }, 0, 20);
    }


    public void move(){
        hitCheck();
        ballX += ballXSpeed * ballDirectionX;
        ballY += ballYSpeed * ballDirectionY;

        if (ballX <= 0){
            ballDirectionX = 1; // change x direction
        }else if(ballX >= screenWidth - ballSize){
            ballDirectionX = -1; //change x direction
        }else if(ballY <= 0){
            ballDirectionY = 1; // collision with bottom(endgame)
        }else if(ballY >= screenHeight + ballSize){
            ballDirectionY = -1; //collision with top(endgame)
        }

       // System.out.println("BallX" + ballX + " Speed " + ballXSpeed + " ScreenWidth" + screenWidth);

        ball.setX(ballX);
        ball.setY(ballY);
    }

    public void hitCheck(){ // check ball collision with players
        //System.out.println("hitCheck");
        if (ballX + ballSize >= playerBottom.playerX &&
                ballX <= playerBottom.playerX + playerBottom.plateWidth &&
                ballY <= screenHeight + ballSize && ballY >= screenHeight)
        {
            ballDirectionY = -1;
        }
        else if(ballX + ballSize>= playerTop.playerX &&
                ballX <= playerTop.playerX + playerTop.plateWidth &&
                ballY <= ballSize && ballY >= 0)
        {
            ballDirectionY = 1;
           // System.out.println(" hitCheck else");
        }
    }
}
