package com.example.samplerepulse;

import java.util.TimerTask;
import java.util.Timer;

public class TimerHandler {

    public Timer timer;
    public int frameSpeed = 50;
    public Ball ball;
    public Player playerBottom;
    public Player playerTop;
    public Bot playerTopBot;

    public TimerHandler(final Ball ball, final Player playerBottom, final Bot playerTopBot){
        this.ball = ball;
        this.playerTopBot = playerTopBot;
        this.playerBottom = playerBottom;
    }
    public TimerHandler(final Ball ball, final Player playerBottom, final Player playerTop){
        this.ball = ball;
        this.playerTop = playerTop;
        this.playerBottom = playerBottom;
    }

    public void startTimerBot(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // move the ball every 20 ms
                BotGameActivity.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Ball Timer");
                        ball.moveBot();
                        if (playerBottom.plateMove) {
                            playerBottom.move();
                        }
                        playerTopBot.move(ball.ballX);
                    }
                });
            }
        }, 0, 1000 / frameSpeed);
    }

    public void startTimerPlayer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // move the ball every 20 ms
                PvPGameActivity.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Ball Timer");
                        ball.move();
                        if (playerBottom.plateMove) {
                            playerBottom.move();
                        }
                        if (playerTop.plateMove){
                            playerTop.move();
                        }
                    }
                });
            }
        }, 0, 1000 / frameSpeed);
    }

    public void speedUp(){
        this.frameSpeed += 20;
    }

    public void timerCancel(){
        this.timer.cancel();
        this.timer = null;
    }
}
