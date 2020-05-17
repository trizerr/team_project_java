package com.example.samplerepulse;

import androidx.annotation.ContentView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Timer;

public class BotGameActivity extends AppCompatActivity {
    private FrameLayout gameFrame;
    private int screenWidth, screenHeight;
    private Player playerBottomBot;
    private Bot playerTopBot;
    private Ball ball;
    private Drawable plate, ballDrawable;
    private int pointerTop = -1, PointerDown = -1;

    private ImageView ballImg;
    private ImageView playerBottomImg,playerTopImg;


    private boolean plateMove = false;
    private Timer timer, plateTimer;
    private Handler handler;

    private boolean start_flg = false;
    private boolean action_flg = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_game);

        gameFrame = findViewById(R.id.BotGameFrame);

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


        playerTopBot = new Bot(playerTopImg, plate, screenWidth, screenHeight);
        playerBottomBot = new Player(playerBottomImg, plate, screenWidth, screenHeight);
        ball = new Ball(ballImg, ballDrawable,playerTopBot, playerBottomBot, screenWidth, screenHeight);

        playerBottomBot.plateMove = false;
        playerTopBot.plateMove = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                int touchPosX = (int) event.getX();
                int touchPosY = (int) event.getY();
//                for (int i = 0; i < event.getPointerCount(); i++) {
//                    System.out.println("Pointer index " + event.getPointerId(i) + " pointerX " + event.getX(i) + " pointer Y " + event.getY(i));
//                }
                // Player Top//
                if (pointerTop != event.getPointerId(event.getActionIndex())){
                    if(!playerTopBot.plateMove){
                        if (event.getY() < screenHeight / 2) {
                            if (touchPosX > screenWidth / 2) {
                                playerTopBot.plateDirection = 1;
                            } else {
                                playerTopBot.plateDirection = -1;
                            }
                            playerTopBot.plateMove = true;
                            pointerTop = event.getPointerId(event.getActionIndex());
                        }
                    }
                    // Player Bottom //
                }else if(!playerBottomBot.plateMove){
                    if (event.getY() > screenHeight / 2) {
                        if (touchPosX > screenWidth / 2) {
                            playerBottomBot.plateDirection = 1;
                        } else {
                            playerBottomBot.plateDirection = -1;
                        }
                        playerBottomBot.plateMove = true;
                        System.out.println("Botttvmtoveitegbh");
                    }
                }

                System.out.println("X- " + touchPosX + " Y - " + event.getY() + "Direction " + playerTopBot.plateDirection);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                touchPosX = (int) event.getX();
                touchPosY = (int) event.getY();
                if (event.getY() < screenHeight / 2) {
                    if (event.getPointerId(event.getActionIndex()) == pointerTop)
                        playerTopBot.plateMove = false;
                }else {
                    playerBottomBot.plateMove = false;
                }
                System.out.println("X- " + touchPosX + " Y - " + event.getY() + "Direction " + playerTopBot.plateDirection);
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void startGame(){
        start_flg = true;
    }
}