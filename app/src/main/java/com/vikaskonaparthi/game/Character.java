package com.vikaskonaparthi.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Character extends AppCompatActivity {
    AdView mAdview;
    private int high;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        high=settings.getInt("HIGH_SCORE",0);
        Log.d("HighScore", String.valueOf(high));
        MobileAds.initialize (this, getString (R.string.admob_app_id));
        mAdview = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);



    }
    public void pacman1(View view)
    {
        MediaPlayer mp = MediaPlayer.create(this,R.raw.click);//To play music in onCreate
        mp.start();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
    public void pacman2(View view)
    {
        MediaPlayer mp = MediaPlayer.create(this,R.raw.click);//To play music in onCreate
        mp.start();
        if(high>1000) {
            startActivity(new Intent(getApplicationContext(), MainActivityPac2.class));
            finish();
        }
        else{
            Toast.makeText(this, "Score greater than 1000 to unlock the Oswald PacMan", Toast.LENGTH_SHORT).show();
        }
    }
}
