package com.example.samplerepulse;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Ball {
    public float ballX, ballY;
    public int ballXSpeed = 0;
    public int ballYSpeed = 10;
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
            public void run() {
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
            ballDirectionX = 1;
//            ballX += ballXSpeed * ballDirectionX;
        }else if(ballX >= screenWidth - ballSize){
            ballDirectionX = -1;
//            ballX += ballXSpeed * ballDirectionX;
        }else if(ballY <= 0){
            ballDirectionY = 1;
//            ballY += ballYSpeed * ballDirectionY;
            //ballMoving = false;
        }else if(ballY >= screenHeight + ballSize){
            ballDirectionY = -1;
//            ballY += ballYSpeed * ballDirectionY;
        }

        System.out.println("BallX" + ballX + " Speed " + ballXSpeed + " ScreenWidth" + screenWidth);

        ball.setX(ballX);
        ball.setY(ballY);
    }

    public void hitCheck(){
//        int playerTopX = playerTop.pl
        System.out.println("hitCheck");
        if (ballX >= playerBottom.playerX &&
                ballX <= playerBottom.playerX + playerBottom.plateWidth &&
                ballY <= screenHeight + ballSize && ballY >= screenHeight)
        {
            ballDirectionY = -1;
//            ballY += ballYSpeed * ballDirectionY;
        }
        else if(ballX <= playerTop.playerX &&
                ballX >= playerTop.playerX - playerTop.plateWidth &&
                ballY <= ballSize && ballY >= 0)
        {
            ballDirectionY = 1;
//            ballY += ballYSpeed * ballDirectionY;
            System.out.println("else");
        }
    }
}
