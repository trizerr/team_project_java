package com.example.samplerepulse;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button botGame = findViewById(R.id.BotButton);
        botGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button PvPGame = findViewById(R.id.PvPButton);
        PvPGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PvPGameStart();
            }
        });
        PvPGameStart();

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int touchPosX = (int) event.getX();
//        if(!plateMove) {
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                if (touchPosX > screenWidth / 2) {
//                    playerBottom.plateDirection = 1;
//                } else {
//                    playerBottom.plateDirection = -1;
//                }
//                playerBottom.plateMove = true;
//            } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                playerBottom.plateMove = false;
//            }
//        }
//        System.out.println("touch");
//        return super.onTouchEvent(event);
//    }


    public void PvPGameStart(){
        Intent PvP = new Intent(this, PvPGameActivity.class);
        startActivity(PvP);
    }


    public void startGame(){

    }


}


