package com.example.samplerepulse;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.pm.ActivityInfo;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botGame = findViewById(R.id.BotButton);
        botGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BotGameStart();
            }
        });

        Button PvPGame = findViewById(R.id.PvPButton);
        PvPGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PvPGameStart();
            }
        });
    }

    public void PvPGameStart(){
        Intent PvP = new Intent(this, PvPGameActivity.class);
        startActivity(PvP);
    }

    public void BotGameStart(){
        Intent Bot = new Intent(this, BotGameActivity.class);
        startActivity(Bot);
    }
}


