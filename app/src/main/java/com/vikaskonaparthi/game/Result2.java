package com.vikaskonaparthi.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class Result2 extends AppCompatActivity implements RewardedVideoAdListener {
    AdView mAdview;
    private RewardedVideoAd mAd;

    public int scoreA;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);
        MobileAds.initialize (this, getString (R.string.admob_app_id));
        MobileAds.initialize(this,"ca-app-pub-6862851420275633/4787970558");
        mAdview = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        mAd=MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
        mAd.loadAd("ca-app-pub-6862851420275633/4787970558",new AdRequest.Builder().build());

        TextView scoreLabel = (TextView)findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView)findViewById(R.id.highScoreLabel);

        int score = getIntent().getIntExtra("SCORE",0);
        scoreLabel.setText(score+"");
        scoreA=score;
        setScore(scoreA);


        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore=settings.getInt("HIGH_SCORE",0);

        if(score > highScore)
        {
            highScoreLabel.setText("High Score  : "+ score);

            //Saving SCORE
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE",score);
            editor.commit();
        }else{
            highScoreLabel.setText("High Score  : "+ highScore);
        }



    }
    private void loadRewardedVideoAd()
    {
        if(!mAd.isLoaded())
        {
            mAd.loadAd("ca-app-pub-6862851420275633/4787970558",new AdRequest.Builder().build());
        }
        else{
            mAd.loadAd("ca-app-pub-6862851420275633/4787970558",new AdRequest.Builder().build());
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public int getScore()
    {
        return this.scoreA;
    }
    public void setScore(int scoreA)
    {
        Log.d("SCORE1", String.valueOf(scoreA));
        this.scoreA=scoreA;
        Log.d("SCORE2", String.valueOf(scoreA));
    }


    public void tryAgain(View view)
    {
        MediaPlayer mp = MediaPlayer.create(this,R.raw.click);//To play music in onCreate
        mp.start();
        startActivity(new Intent(getApplicationContext(),Start.class));
    }
    public void watchAd(View view)
    {
        if(mAd.isLoaded())
        {
            mAd.show();
        }
        else{
            mAd.loadAd("ca-app-pub-6862851420275633/4787970558",new AdRequest.Builder().build());
            loadRewardedVideoAd();

            Toast.makeText(this, "Ad failed to load,press the button again", Toast.LENGTH_SHORT).show();
        }
        MediaPlayer mp = MediaPlayer.create(this,R.raw.click);//To play music in onCreate
        mp.start();
    }
    public void checkConnection(){
        if(isOnline()){
            Intent intent =new Intent(getApplicationContext(),MainActivity2Pac2.class);
            intent.putExtra("SCOREAD",scoreA);
            startActivity(intent);
            Log.d("SCOREAd", String.valueOf(scoreA));
        }else{
            Toast.makeText(Result2.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }
    //Removing the Return Button
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


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        checkConnection();

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
    @Override
    public void onResume() {
        mAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mAd.destroy(this);
        super.onDestroy();
    }
}
