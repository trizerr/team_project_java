package com.example.samplerepulse;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.Timer;

public class Ball {
    public float ballX, ballY;
    public int ballXSpeed = 5; // change x position
    public int ballYSpeed = 10; // change y position
    public int ballFrameSpeed = 50;
    public int ballDirectionX = 0;
    public int ballDirectionY = 0;
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
    public int hitScore = 0;
    TimerHandler timerHandler;

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

        setDirection();
    }

    public void setTimerHandler(TimerHandler timerHandler){
        this.timerHandler = timerHandler;
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
        setDirection();
//        startMoveBot();
    }

    public void setDirection(){
        this.ballDirectionX = 0;
        this.ballDirectionY = 0;

        while (ballDirectionX == 0 || ballDirectionY == 0){
            ballDirectionX = (int)( Math.random() * 3) - 1;
            ballDirectionY = (int)( Math.random() * 3) - 1;
            System.out.println("dirX " + ballDirectionX + " dirY " + ballDirectionY);
        }
        this.ballXSpeed = (int) (Math.random() * 3) + 4;
        this.ballYSpeed = (int) (Math.random() * 3) + 8;

        System.out.println("speedX " + ballXSpeed + " speedY " + ballYSpeed);
    }

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

    public void moveBot(){
        hitCheckBot();
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

        if (ballX + ballSize >= playerBottom.playerX &&
                ballX <= playerBottom.playerX + playerBottom.plateWidth &&
                ballY <= screenHeight + ballSize && ballY >= screenHeight)
        {
            ballDirectionY = -1 ;
            PvPGameActivity.getInstance().addRepulseScore();
        }
        else if(ballX + ballSize >= playerTop.playerX &&
                ballX <= playerTop.playerX + playerTop.plateWidth &&
                ballY <= ballSize && ballY >= 0)
        {
            ballDirectionY = 1;
            PvPGameActivity.getInstance().addRepulseScore();
            // System.out.println(" hitCheck else");
        }
    }

    public void hitCheckBot(){ // check ball collision with players
        //System.out.println("hitCheck");
        if (ballX + ballSize >= playerBottomBot.playerX &&
                ballX <= playerBottomBot.playerX + playerBottomBot.plateWidth &&
                ballY <= screenHeight + ballSize && ballY >= screenHeight)
        {
            ballDirectionY = -1;
            BotGameActivity.getInstance().addScore();
        }
        else if(ballX + ballSize >= playerTopBot.botX &&
                ballX <= playerTopBot.botX + playerTopBot.plateWidth &&
                ballY <= ballSize && ballY >= 0)
        {
            BotGameActivity.getInstance().addScore();
            ballDirectionY = 1;
            System.out.println("Score is " + hitScore);
            // System.out.println(" hitCheck else");
        }
    }
}
