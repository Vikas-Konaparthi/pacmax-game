package com.vikaskonaparthi.game;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {
    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX=2;
    private static SoundPool soundPool;
    public static int hitSound;
    public static  int overSound;
    public static int backSound;
    public static int beginSound;

    public SoundPlayer(Context context)
    {

        //SoundPool depricated in API level greater than 21

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            audioAttributes =new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(SOUND_POOL_MAX).build();
        }
        else{

            //SoundPool(int maxStreams,int StreamType,int srcQuality)
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC,0);

        }
        hitSound =soundPool.load(context, R.raw.fruit,1);
        overSound = soundPool.load(context, R.raw.death,2);
        beginSound = soundPool.load(context, R.raw.beginning,1);


    }
    public void playHitSound()
    {
        soundPool.play(hitSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playOverSound()
    {
        soundPool.play(overSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playBeginSound()
    {
        soundPool.play(beginSound,1.0f,1.0f,1,0,1.0f);
    }
}
