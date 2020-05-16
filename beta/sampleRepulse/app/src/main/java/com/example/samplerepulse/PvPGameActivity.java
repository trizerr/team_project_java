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
                moveBar(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                PointF f = new PointF();
                f.x = event.getX(pointerIndex);
                f.y = event.getY(pointerIndex);
                System.out.println("pointer");
                moveBarSecond(event, f);
                break;
            case MotionEvent.ACTION_UP:
                moveBar(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                moveBar(event);
                break;
            case MotionEvent.ACTION_CANCEL:

            case MotionEvent.ACTION_MOVE:

        }

        return super.onTouchEvent(event);
    }

    public void moveBar(MotionEvent event){
        int touchPosX = (int) event.getX();
        int touchPosY = (int) event.getY();
        System.out.println(touchPosX);
        boolean playerTopMove = playerTop.plateMove;

        if (touchPosY < screenHeight / 2){
            if(!playerTopMove) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
                    if (touchPosX > screenWidth / 2) {
                        playerTop.plateDirection = 1;
                    } else {
                        playerTop.plateDirection = -1;
                    }
                    playerTop.plateMove = true;
                }
                System.out.println("Top move");
            }else{
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
                    playerTop.plateMove = false;
                }
            }
            System.out.println("Top touch");
        }else if (touchPosY > screenHeight / 2){
            //if(!playerBottom.plateMove) {
            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
                if (touchPosX > screenWidth / 2) {
                    playerBottom.plateDirection = 1;
                } else {
                    playerBottom.plateDirection = -1;
                }
                playerBottom.plateMove = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
                playerBottom.plateMove = false;
            }
            //}
            System.out.println("Bottom touch");
        }

        System.out.println("touch");
    }

    public void moveBarSecond(MotionEvent event, PointF f){

        int touchPosX =(int) f.x;
        int touchPosY = (int) f.y;
        System.out.println(touchPosX);
        boolean playerTopMove = playerTop.plateMove;

        if (touchPosY < screenHeight / 2){
            if(!playerTopMove) {
                    if (touchPosX > screenWidth / 2) {
                        playerTop.plateDirection = 1;
                    } else {
                        playerTop.plateDirection = -1;
                    }
                    playerTop.plateMove = true;
                System.out.println("Top move");
            }else{
                if ( event.getAction() == MotionEvent.ACTION_POINTER_UP) {
                    playerTop.plateMove = false;
                }
            }
            System.out.println("Top touch");
        }else if (touchPosY > screenHeight / 2){
            //if(!playerBottom.plateMove) {

                if (touchPosX > screenWidth / 2) {
                    playerBottom.plateDirection = 1;
                } else {
                    playerBottom.plateDirection = -1;
                }
                playerBottom.plateMove = true;

            if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
                playerBottom.plateMove = false;
            }
            //}
            System.out.println("Bottom touch");
        }

        System.out.println("second touch");
    }

    public void startGame(){
        start_flg = true;


    }

}
