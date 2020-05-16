package com.example.samplerepulse;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
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
    private int pointerTop = -1, pointerBottom = -1;

    private ImageView ballImg;
    private ImageView playerBottomImg,playerTopImg;
    private int speed = 1;

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

        gameFrame = findViewById(R.id.PvPGameFrame);

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

    public void startGame(){
        start_flg = true;


    }

}
