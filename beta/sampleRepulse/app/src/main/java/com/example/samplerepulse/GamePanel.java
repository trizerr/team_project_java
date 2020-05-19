package com.example.samplerepulse;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Timer;

public class GamePanel extends SurfaceView {

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

    public GamePanel(Context context) {
        super(context);
    }
}
