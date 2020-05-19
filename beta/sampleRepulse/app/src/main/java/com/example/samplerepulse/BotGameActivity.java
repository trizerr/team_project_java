package com.example.samplerepulse;

import androidx.annotation.ContentView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;

public class BotGameActivity extends AppCompatActivity {
    private FrameLayout gameFrame;

    private int screenWidth, screenHeight;
    private int pointerTop = -1, pointerDown = -1;
    private int score = 0;

    private boolean start_flg = false;
    private boolean action_flg = false;
    private boolean plateMove = false;
    private boolean pause_flg = false;

    private Player playerBottom;

    private Bot playerTopBot;

    private Ball ball;

    private Drawable plate, ballDrawable;

    private TextView scoreBoard;

    private static BotGameActivity instance;

    private ImageView ballImg;
    private ImageView playerBottomImg,playerTopImg;

    private Button resumeButton, exitButton;
    private Button restartButton, gameFinishExitButton;
    private Button pauseButton;

    private LinearLayout pauseBoard, gameFinishBoard;

    private Timer timer, plateTimer;
    private Handler handler;

//    Ball ballPos = new Ball();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_game);

        instance = this;

        gameFrame = findViewById(R.id.BotGameFrame);

        gameFinishBoard = findViewById(R.id.gameFinishBoard);
        pauseBoard = findViewById(R.id.pauseBoard);
        scoreBoard = findViewById(R.id.scoreBoard);

        pauseButton =  findViewById(R.id.pauseButton);
        resumeButton = findViewById(R.id.resumeButton);
        exitButton = findViewById(R.id.exitButton);
        gameFinishExitButton = findViewById(R.id.gameFinishExitButton);
        restartButton = findViewById(R.id.restartButton);

        playerTopImg = findViewById(R.id.playerTop);
        playerBottomImg = findViewById(R.id.playerBottom);
        ballImg = findViewById(R.id.ball);

        plate = getDrawable(R.drawable.plate);
        ballDrawable = getDrawable(R.drawable.ball);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        action_flg = true;
        plateMove = true;

        playerTopBot = new Bot(playerTopImg, plate, screenWidth, screenHeight);
        playerBottom = new Player(playerBottomImg, plate, screenWidth, screenHeight);
        ball = new Ball(ballImg, ballDrawable,playerTopBot, playerBottom, screenWidth, screenHeight);

        playerTopBot.setBall(ball);
        playerTopBot.startMove();

        playerBottom.plateMove = false;
        playerTopBot.plateMove = true;

        gameFinishBoard.setVisibility(View.GONE);

        addListeners();
    }

    public void addListeners(){

        gameFinishExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitGame();
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameRestart();
            }
        });

        pauseButton.setClickable(true);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pause_flg) {
                    System.out.println("Click");
                    pauseGame();
                }
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pause_flg) {
                    resumeGame();
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pause_flg) {
                    exitGame();
                }
            }
        });
    }

    public static BotGameActivity getInstance(){
        return instance;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                int touchPosX = (int) event.getX();
                int touchPosY = (int) event.getY();

                // Player Bottom //
                if(!playerBottom.plateMove){
                    if (event.getY() > screenHeight / 2) {
                        if (touchPosX > screenWidth / 2) {
                            playerBottom.plateDirection = 1;
                        } else {
                            playerBottom.plateDirection = -1;
                        }
                        playerBottom.plateMove = true;
//                        System.out.println("Botttvmtoveitegbh");
                    }
                }

//                System.out.println("X- " + touchPosX + " Y - " + event.getY() + "Direction " + playerTopBot.plateDirection);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                touchPosX = (int) event.getX();
                touchPosY = (int) event.getY();
                if (event.getY() < screenHeight / 2) {
                    if (event.getPointerId(event.getActionIndex()) == pointerDown)
                        playerBottom.plateMove = false;
                }else {
                    playerBottom.plateMove = false;
                }
//                System.out.println("X- " + touchPosX + " Y - " + event.getY() + "Direction " + playerTopBot.plateDirection);
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void  playerBottomScore(){
        gameFinish();
    }

    public void  botTopScore(){
        gameFinish();
    }

    public void addScore(){
        score++;
        scoreBoard.setText("Score: " + Integer.toString(score));
        System.out.println(score);
    }

    public void gameFinish(){
        gameFinishBoard.setVisibility(View.VISIBLE);

        ball.timer.cancel();
        ball.timer = null;

        playerTopBot.timer.cancel();
        playerTopBot.startMove();

        playerBottom.timer.cancel();
        playerBottom.timer = null;
    }

    public void gameRestart(){
        Intent restart = new Intent(this, BotGameActivity.class);
        //the following 2 tags are for clearing the backStack and start fresh
        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        restart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(restart);
    }


    public void pauseGame(){
//        ball.ballMoving = false;
//        playerBottom.plateMove = false;
//        playerTopBot.plateMove = false;

        ball.timer.cancel();
        ball.timer = null;

        playerTopBot.timer.cancel();
        playerTopBot.timer = null;

        playerBottom.timer.cancel();
        playerBottom.timer = null;
//
        pauseBoard.setVisibility(View.VISIBLE);
//        pauseButton.setVisibility(View.GONE);

        pause_flg = true;
    }

    public void resumeGame(){
        ball.startMove();
        playerTopBot.startMove();
        playerBottom.startMove();

        pauseButton.setPressed(false);
        pause_flg = false;
        pauseBoard.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);
    }

    public void exitGame(){
        Intent mainMenu = new Intent(this, MainActivity.class);
        //the following 2 tags are for clearing the backStack and start fresh
        mainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainMenu);
        finish();
    }

    public void startGame(){
        start_flg = true;
    }
}