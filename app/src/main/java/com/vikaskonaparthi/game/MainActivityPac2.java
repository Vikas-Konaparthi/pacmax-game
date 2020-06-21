package com.vikaskonaparthi.game;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivityPac2 extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView pacman;
    private ImageView straw;
    private ImageView ene;
    private ImageView ene2;
    private ImageView cherry;
    //Size
    private int frameHeight;
    private int pacmanSize;
    private  int screenWidth;
    private int screenHeight;
    //Position
    private int pacmanY;
    private int strawX;
    private int strawY;
    private int cherryX;
    private int cherryY;
    private int eneX;
    private int eneY;
    private int ene2X;
    private int ene2Y;
    public int score=0;
    //Speed
    private int pacmanSpeed;
    private int strawSpeed;
    private int cherrySpeed;
    private int eneSpeed;
    private int ene2Speed;

    private Button pauseBtn;

    //Status Check
    private boolean pause_flg=false;

    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private SoundPlayer sound;

    //Status Check
    private boolean action_flg = false;
    private  boolean start_flag= false;


    int []imageArray={R.drawable.b6,R.drawable.b7,R.drawable.b8,R.drawable.b2};

    LinearLayout l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pac2);


        sound = new SoundPlayer(this);

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        pacman = (ImageView) findViewById(R.id.pacman);
        straw = (ImageView) findViewById(R.id.straw);
        ene = (ImageView) findViewById(R.id.ene);
        ene2 = (ImageView) findViewById(R.id.ene2);
        cherry =(ImageView) findViewById(R.id.cherry);
        l = (LinearLayout)findViewById(R.id.LinearLayout1);
        //Getting screen size
        WindowManager we = getWindowManager();
        Display disp = we.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth=size.x;
        screenHeight=size.y;

        pacmanSpeed = Math.round(screenHeight/70F);
        strawSpeed =Math.round(screenWidth/70F);
        cherrySpeed= Math.round(screenWidth/46F);
        eneSpeed=Math.round(screenWidth/55F);
        ene2Speed=Math.round(screenWidth/50F);
        if(score>100)
        {
            pacmanSpeed = Math.round(screenHeight/65F);
            eneSpeed=Math.round(screenWidth/53F);
        }
        if(score>1000)
        {
            pacmanSpeed = Math.round(screenHeight/60F);
            ene2Speed=Math.round(screenWidth/40F);
        }

        Log.v("SPEED_PACMAN",pacmanSpeed+"");
        Log.v("SPEED_STRAW",strawSpeed+"");
        Log.v("SPEED_CHERRY",cherrySpeed+"");
        Log.v("SPEED_ENEMY",eneSpeed+"");



        straw.setX(-80);
        straw.setY(-80);
        cherry.setX(-80);
        cherry.setY(-80);
        ene.setX(-80);
        ene.setY(-80);
        ene2.setX(-80);
        ene2.setY(-80);
        scoreLabel.setText("Score : 0");
        MediaPlayer mp = MediaPlayer.create(this,R.raw.beginning);//To play music in onCreate
        mp.start();
        handlechange();

    }
    void handlechange() {

        Handler hand = new Handler();
        hand.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                // change image here
                change();

            }

            private void change() {
                // TODO Auto-generated method stub

                Random rand = new Random();

                int index = rand.nextInt(imageArray.length);

                l.setBackgroundResource(imageArray[index]);
                Animation fadeIn = AnimationUtils.loadAnimation(MainActivityPac2.this, R.anim.fadein);
                l.startAnimation(fadeIn);


                handlechange();
            }
        }, 30000);

    }




    public void changePos()
    {

        //Straw
        strawX-=strawSpeed;
        if(strawX < 0)
        {
            strawX=screenWidth+20;
            strawY=(int) Math.floor(Math.random()*(frameHeight - straw.getHeight()));

        }
        straw.setX(strawX);
        straw.setY(strawY);

        //Enemy
        eneX-=eneSpeed;
        if(eneX < 0)
        {
            eneX=screenWidth+10;
            eneY=(int) Math.floor(Math.random()*(frameHeight - ene.getHeight()));

        }
        ene.setX(eneX);
        ene.setY(eneY);
        if(score>500)
        {
            ene2.setVisibility(View.VISIBLE);
            //Enemy
            ene2X-=ene2Speed;
            if(ene2X < 0)
            {
                ene2X=screenWidth+100;
                ene2Y=(int) Math.floor(Math.random()*(frameHeight - ene2.getHeight()));

            }
            ene2.setX(ene2X);
            ene2.setY(ene2Y);
        }
        //Cherry
        cherryX-=cherrySpeed;
        if(cherryX < 0)
        {
            cherryX=screenWidth+5000;
            cherryY=(int) Math.floor(Math.random()*(frameHeight - cherry.getHeight()));

        }
        cherry.setX(cherryX);
        cherry.setY(cherryY);
        if(action_flg==true)
        { //Going up
            pacmanY-=pacmanSpeed;
        }
        else{
            pacmanY+=pacmanSpeed;
        }
        if(pacmanY < 0) pacmanY =0;
        if(pacmanY > frameHeight-pacmanSize) pacmanY = frameHeight-pacmanSize;
        pacman.setY(pacmanY);
        scoreLabel.setText("Score : "+score);
        hitCheck();

    }
    public void hitCheck()
    {
        //Straw
        int strawCenterX = strawX + straw.getWidth()/2;
        int strawCenterY = strawY + straw.getHeight()/2;

        //Hit

        if(0 <= strawCenterX && strawCenterX <= pacmanSize && pacmanY <= strawCenterY && strawCenterY <=pacmanY + pacmanSize){
            score+=8;
            strawX=-10;
            sound.playHitSound();
        }
        //Cherry
        int cherryCenterX = cherryX + cherry.getWidth()/2;
        int cherryCenterY = cherryY + cherry.getHeight()/2;

        //Hit

        if(0 <= cherryCenterX && cherryCenterX <= pacmanSize && pacmanY <= cherryCenterY && cherryCenterY <=pacmanY+pacmanSize){
            score+=15;
            cherryX=-10;
            sound.playHitSound();
        }
        //Enemy
        int eneCenterX = eneX + ene.getWidth()/2;
        int eneCenterY = eneY + ene.getHeight()/2;

        //Hit

        if(0 <= eneCenterX && eneCenterX <= pacmanSize && pacmanY <= eneCenterY && eneCenterY <=pacmanY+pacmanSize){
            //Stopping Timer
            timer.cancel();
            timer=null;
            sound.playOverSound();

            //Show Result
            Intent intent =new Intent(getApplicationContext(),Result2.class);
            intent.putExtra("SCORE",score);
            startActivity(intent);
            finish();
        }
        if(score>500)
        {
            //Enemy
            int ene2CenterX = ene2X + ene2.getWidth()/2;
            int ene2CenterY = ene2Y + ene2.getHeight()/2;

            //Hit

            if(0 <= ene2CenterX && ene2CenterX <= pacmanSize && pacmanY <= ene2CenterY && ene2CenterY <=pacmanY+pacmanSize){
                //Stopping Timer
                timer.cancel();
                timer=null;
                sound.playOverSound();

                //Show Result
                Intent intent =new Intent(getApplicationContext(),Result2.class);
                intent.putExtra("SCORE",score);
                startActivity(intent);
                finish();
            }
        }

    }

    public boolean onTouchEvent(MotionEvent me)
    {
        if(start_flag==false)
        {
            start_flag=true;
            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            frameHeight = frame.getHeight();
            pacmanY=(int)pacman.getY();
            pacmanSize = pacman.getHeight();
            startLabel.setVisibility(View.GONE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0,20);

        }else{
            if(me.getAction()==MotionEvent.ACTION_DOWN){
                action_flg=true;
            }else if(me.getAction()==MotionEvent.ACTION_UP)
            {
                action_flg =false;
            }
        }


        return true;
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if(event.getAction()==KeyEvent.ACTION_DOWN)
        {
            switch(event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }

        }
        return super.dispatchKeyEvent(event);

    }
    public void pausePushed(View view)
    {
        if(pause_flg==false)
        {
            pause_flg=true;
            //Stop the timer
            timer.cancel();
            timer=null;

            //Changing button text
            pauseBtn.setText("START");


        }else{
            pause_flg=false;
            pauseBtn.setText("PAUSE");

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0,1000);


        }
    }



}
