package com.star.k_pop.lib;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import com.star.k_pop.R;

public class SoundPlayer {
    SoundPool soundPool;
    MediaPlayer sound;
    Context context;
    public SoundPlayer(Context context){
        this.context = context;

    }
    public void play(int soundID) {
        sound=MediaPlayer.create(context, soundID);
        sound.start();
    }

    public int load(int soundID) { //возвращает id загруженного потока
       int id = soundPool.load(context,soundID,1);//реализация через SoundPool
        Log.i("Sound", "["+soundID+"] loaded, StreamId="+id);
        return  id;
    }
    public void playSoundStream(int soundStreamId) {
        soundPool.play(soundStreamId,1,1,1,0,1);
        //Log.i("Sound", "["+sou+"] loaded");
    }
}
