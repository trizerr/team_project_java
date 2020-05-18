package com.example.samplerepulse;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Ball {
    public float ballX, ballY;
    public int ballXSpeed = 5; // change x position
    public int ballYSpeed = 10; // change y position
    public int ballDirectionX = 1;
    public int ballDirectionY = 1;
    public int ballSize;
    public Timer timer;
    public boolean ballMoving;
    private Drawable ballDrawable;
    private ImageView ball;
    Player playerTop, playerBottom;
    Player playerBottomBot;
    Bot playerTopBot;
    int playerMarginVertical = 10;
    public int screenWidth, screenHeight;

    TextView text;

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
                PvPGameActivity.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ballMoving) {
                            move();
                        }
                    }
                });
            }
        }, 0, 20);
    }

    public Ball(ImageView ball, Drawable ballDrawable, Bot playerTopBot, Player playerBottomBot, int screenWidth, int screenHeight){
        this.ball = ball;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.playerBottomBot = playerBottomBot;
        this.playerTopBot = playerTopBot;
        ballSize = ballDrawable.getIntrinsicWidth();
        ballMoving = true;

        ballX = (float)(screenWidth / 2);
        ballY = (float)(screenHeight / 2);
        startMove();
    }

    public void startMove(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // move the ball every 20 ms
                BotGameActivity.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ballMoving) {
                            move2();
                        }
                    }
                });
            }
        }, 0, 20);
    }

    public Ball() {
    };

    public void move(){
        hitCheck();
        checkGoal();
        ballX += ballXSpeed * ballDirectionX;
        ballY += ballYSpeed * ballDirectionY;
        System.out.println("BallX" + ballX +" ballY " + ballY + " DirectionX " + ballDirectionX);


        if (ballX <= 0){
            ballDirectionX = 1; // change x direction
        }else if(ballX >= screenWidth - ballSize){
            ballDirectionX = -1; //change x direction
        }

       // System.out.println("BallX" + ballX + " Speed " + ballXSpeed + " ScreenWidth" + screenWidth);

        ball.setX(ballX);
        ball.setY(ballY);
    }

    public void checkGoal(){
        if(ballY <= 0){
            PvPGameActivity.getInstance().playerBottomScore();
            //ballDirectionY = 1; // collision with bottom(endgame)
        }else if(ballY >= screenHeight + ballSize){
            PvPGameActivity.getInstance().playerTopScore();
            //ballDirectionY = -1; //collision with top(endgame)
        }
    }

    public void checkGoalBot(){
        if(ballY <= 0){
            BotGameActivity.getInstance().playerBottomScore();
            //ballDirectionY = 1; // collision with bottom(endgame)
        }else if(ballY >= screenHeight + ballSize){
            BotGameActivity.getInstance().botTopScore();
            //ballDirectionY = -1; //collision with top(endgame)
        }
    }

    public void move2(){
        hitCheck2();
        checkGoalBot();
        ballX += ballXSpeed * ballDirectionX;
        ballY += ballYSpeed * ballDirectionY;

        if (ballX <= 0){
            ballDirectionX = 1; // change x direction
        }else if(ballX >= screenWidth - ballSize){
            ballDirectionX = -1; //change x direction
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
        else if(ballX + ballSize >= playerTop.playerX &&
                ballX <= playerTop.playerX + playerTop.plateWidth &&
                ballY <= ballSize && ballY >= 0)
        {
            ballDirectionY = 1;
           // System.out.println(" hitCheck else");
        }
    }

    public void hitCheck2(){ // check ball collision with players
        //System.out.println("hitCheck");
        if (ballX + ballSize >= playerBottomBot.playerX &&
                ballX <= playerBottomBot.playerX + playerBottomBot.plateWidth &&
                ballY <= screenHeight + ballSize && ballY >= screenHeight)
        {
            ballDirectionY = -1;
        }
        else if(ballX + ballSize >= playerTopBot.botX &&
                ballX <= playerTopBot.botX + playerTopBot.plateWidth &&
                ballY <= ballSize && ballY >= 0)
        {
            ballDirectionY = 1;
            // System.out.println(" hitCheck else");
        }
    }
}
