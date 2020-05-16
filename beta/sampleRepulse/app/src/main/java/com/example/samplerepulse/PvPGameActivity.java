package com.example.samplerepulse;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Timer;

public class PvPGameActivity extends AppCompatActivity {
    private FrameLayout gameFrame;
    private int screenWidth, screenHeight;
    private Player playerTop, playerBottom;
    private Ball ball;
    private Drawable plate, ballDrawable;

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
        setContentView(R.layout.activity_pvp_game);

        gameFrame = findViewById(R.id.gameFrame);

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

        playerBottom.plateMove = false;
        playerTop.plateMove = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchPosX = (int) event.getX();
        int touchPosY = (int) event.getY();
        boolean playerTopMove = playerTop.plateMove;

        if (touchPosY < screenHeight / 2){
            if(!playerTopMove) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (touchPosX > screenWidth / 2) {
                        playerTop.plateDirection = 1;
                    } else {
                        playerTop.plateDirection = -1;
                    }
                    playerTop.plateMove = true;
                }
                System.out.println("Top move");
            }else{
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    playerTop.plateMove = false;
                }
            }
            System.out.println("Top touch");
        }else if (touchPosY > screenHeight / 2){
            //if(!playerBottom.plateMove) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (touchPosX > screenWidth / 2) {
                        playerBottom.plateDirection = 1;
                    } else {
                        playerBottom.plateDirection = -1;
                    }
                    playerBottom.plateMove = true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playerBottom.plateMove = false;
                }
            //}
            System.out.println("Bottom touch");
        }

        System.out.println("touch");
        return super.onTouchEvent(event);
    }

    public void startGame(){
        start_flg = true;


    }

}
