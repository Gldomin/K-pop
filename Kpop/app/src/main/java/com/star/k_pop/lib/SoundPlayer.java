package com.star.k_pop.lib;

import android.content.Context;
import android.media.AudioManager;
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
        soundPool = new SoundPool(5,  AudioManager.STREAM_MUSIC, 0);
    }
    public void playRawResource(int soundID) { //простой метод для воспроизведения одного аудио-ресурса
        sound=MediaPlayer.create(context, soundID);
        sound.start();
    }

    public int load(int soundID) { //возвращает id загруженного потока
       int id = soundPool.load(context, soundID,1);//реализация через SoundPool
        Log.i("Sound", "["+soundID+"] loaded, StreamId="+id);
        return  id;
    }
    public void playSoundStream(int soundStreamId) { //воспроизводит по id загруженный поток
        soundPool.play(soundStreamId,1,1,1,0,1);
        Log.i("SoundStream", "["+soundStreamId+"] played");
    }
}
