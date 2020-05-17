package com.example.samplerepulse;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class PvPGameActivity extends AppCompatActivity {
    private static PvPGameActivity instance;

    private FrameLayout gameFrame;
    private LinearLayout resultBoard;

    private int screenWidth, screenHeight;
    private int pointerTop = -1, pointerBottom = -1;
    private int speed = 1;
    private int playerTopScore, playerBottomScore;
    private int maxScore = 3;

    private Player playerTop, playerBottom;
    private Ball ball;
    private Drawable plate, ballDrawable;

    private TextView playerTopScoreText, playerBottomScoreText;

    private ImageView ballImg;
    private ImageView playerBottomImg,playerTopImg;

    private Button mainMenuButton, restartButton;

    private boolean plateMove = false;
    private Timer timer, plateTimer;
    private Handler handler;
    private Runnable runnable;

    private boolean start_flg = false;
    private boolean action_flg = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvp_game);

        instance = this;

        playerTopScoreText = findViewById(R.id.playerTopScore);
        playerBottomScoreText = findViewById(R.id.playerBottomScore);

        gameFrame = findViewById(R.id.PvPGameFrame);
        resultBoard = findViewById(R.id.resultBoard);

        mainMenuButton = findViewById(R.id.mainMenuButton);
        restartButton = findViewById(R.id.restartButton);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainMenu();
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });

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
        plateMove = false;

        playerTop = new Player(playerTopImg, plate, screenWidth, screenHeight);
        playerBottom = new Player(playerBottomImg, plate, screenWidth, screenHeight);
        ball = new Ball(ballImg, ballDrawable,playerTop, playerBottom, screenWidth, screenHeight);

    }

    public static PvPGameActivity getInstance(){
        return instance;
    }
    /*    public static PointF[] touchScreenStartPtArr = new PointF[10];
        public static PointF[] touchScreenStopPtArr = new PointF[10];
        public static PointF[] touchScreenCurrPtArr = new PointF[10];*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();
        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
                PointF f = new PointF();
                f.x = event.getX(pointerIndex);
                f.y = event.getY(pointerIndex);
                System.out.println("pointer");
                moveBar(event, f);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                PointF second = new PointF();
                second.x = event.getX(pointerIndex);
                second.y = event.getY(pointerIndex);
                System.out.println("pointer");
                moveBar(event, second);
                break;
            case MotionEvent.ACTION_UP:
                Up(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Up(event);
                break;
            case MotionEvent.ACTION_CANCEL:

            case MotionEvent.ACTION_MOVE:
                int count = event.getPointerCount();
                for (int i = 0; i < count; i++) {
                    PointF up = new PointF();
                    up.x = event.getX(i);
                    up.y = event.getY(i);
                    System.out.println("pointer");
                    moveBar(event, up);
                }

                break;

        }

        return super.onTouchEvent(event);
    }

    public void Up(MotionEvent event){
        int touchPosX = (int) event.getX();
        int touchPosY = (int) event.getY();

        if (touchPosY < screenHeight / 2){
            playerTop.plateMove = false;
        }else{
            playerBottom.plateMove = false;
        }
    }

    public void moveBar(MotionEvent event, PointF f){

        int touchPosX =(int) f.x;
        int touchPosY = (int) f.y;
        System.out.println(touchPosX);
        boolean playerTopMove = playerTop.plateMove;

        if (touchPosY < screenHeight / 2){
            if(touchPosX < playerTop.playerLeft || touchPosX > playerTop.pLayerRight){
                if (touchPosX > playerTop.playerX) {
                    playerTop.plateDirection = speed;
                } else {
                    playerTop.plateDirection = -speed;
                }
                playerTop.plateMove = true;
            } else{
                playerTop.plateMove = false;
            }
        }else if (touchPosY > screenHeight / 2){
            if(touchPosX < playerBottom.playerLeft || touchPosX > playerBottom.pLayerRight){
                if (touchPosX > playerBottom.playerX) {
                    playerBottom.plateDirection = speed;
                } else {
                    playerBottom.plateDirection = -speed;
                }
                playerBottom.plateMove = true;
            } else{
                playerBottom.plateMove = false;
            }
        }
    }

    public void restartGame(){
        Intent restart = new Intent(this, PvPGameActivity.class); //change it to your main class
        //the following 2 tags are for clearing the backStack and start fresh
        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        restart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(restart);
    }

    public void playerTopScore(){
        playerTopScore++;
        playerTopScoreText.setText(Integer.toString(playerTopScore));
        checkScore();
        scoreRestart();
    }

    public void playerBottomScore(){
        playerBottomScore++;
        playerBottomScoreText.setText(Integer.toString(playerBottomScore));
        checkScore();
        scoreRestart();
    }

    public void checkScore(){
        if (playerTopScore >= maxScore){
            playerBottom.timer.cancel();
            playerTop.timer.cancel();
            ball.timer.cancel();
            resultBoard.setVisibility(View.VISIBLE);
        }else if(playerBottomScore >= maxScore){
            playerBottom.timer.cancel();
            playerTop.timer.cancel();
            ball.timer.cancel();
            resultBoard.setVisibility(View.VISIBLE);
        }
    }

    public void toMainMenu(){
        Intent mainMenu = new Intent(this, MainActivity.class);
        //the following 2 tags are for clearing the backStack and start fresh
        mainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainMenu);
        finish();
    }

    public void scoreRestart(){
        ball.ballMoving = false;
        playerTop.plateMove = false;
        playerBottom.plateMove = false;
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                // Do the task...
                System.out.println("qwertyuikol;");
                setScene();
            }
        };
        handler.postDelayed(runnable, 2000);

// Stop a repeating task like this.
        //handler.removeCallbacks(runnable);
    }

    public void setScene(){
        System.out.println("setScene");
        ball.ballX = screenWidth/2 - ball.ballSize / 2;
        ball.ballY = screenHeight/2 - ball.ballSize / 2;
        playerTop.playerX = screenWidth/2 - playerTop.plateWidth / 2;
        playerBottom.playerX = screenWidth/2 - playerBottom.plateWidth / 2;
        ball.ballMoving = true;


//        ball.timer.cancel();
//        playerTop.timer.cancel();
//        playerBottom.timer.cancel();
//        playerTop = new Player(playerTopImg, plate, screenWidth, screenHeight);
//        playerBottom = new Player(playerBottomImg, plate, screenWidth, screenHeight);
//        ball = new Ball(ballImg, ballDrawable,playerTop, playerBottom, screenWidth, screenHeight);
       // handler.removeCallbacks(runnable);
    }

    public void startGame(){
        start_flg = true;


    }

}

