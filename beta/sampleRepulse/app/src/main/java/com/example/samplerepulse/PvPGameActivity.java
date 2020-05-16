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
    private int pointerTop = -1, pointerBottom = -1;

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


                    if (event.getY() < screenHeight / 2) {
                        if (pointerTop == -1){
                            pointerTop = event.getPointerId(event.getActionIndex());
                        }
                        if(!playerTop.plateMove && pointerTop == event.getPointerId(event.getActionIndex())){
                            if (touchPosX > screenWidth / 2) {
                            playerTop.plateDirection = 1;
                        } else {
                            playerTop.plateDirection = -1;
                        }
                        playerTop.plateMove = true;
                    }
                }

                //if ()
                // Player Bottom //
                else if(!playerBottom.plateMove){
                    if (event.getY() > screenHeight / 2) {
                        if (touchPosX > screenWidth / 2) {
                            playerBottom.plateDirection = 1;
                        } else {
                            playerBottom.plateDirection = -1;
                        }
                        playerBottom.plateMove = true;
                        System.out.println("Botttvmtoveitegbh");
                    }
                }

                System.out.println("X- " + touchPosX + " Y - " + event.getY() + "Direction " + playerTop.plateDirection);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                touchPosX = (int) event.getX();
                touchPosY = (int) event.getY();
                if (event.getY() < screenHeight / 2) {
                    if (event.getPointerId(event.getActionIndex()) == pointerTop) {
                        playerTop.plateMove = false;
                        pointerTop = -1;
                    }
                }else {
                    if (event.getPointerId(event.getActionIndex()) == pointerBottom)
                    playerBottom.plateMove = false;
                    pointerBottom = -1;
                }
                System.out.println("X- " + touchPosX + " Y - " + event.getY() + "Direction " + playerTop.plateDirection);
                return true;
        }

        return super.onTouchEvent(event);
    }

    public void startGame(){
        start_flg = true;


    }

}
