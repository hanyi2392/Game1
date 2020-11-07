package com.example.hackaton;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

public class Game extends AppCompatActivity {
    ImageButton btn1, btn2;
    ImageButton btnSound;

    public static int score=0;    // 정답을 맞히면 오르는 점수

    TextView txt_score;

    SoundPool soundPool;    // 앱내에서 sound를 재생해야 할 때 쓰이는 Class
    SoundManager soundManager;
    boolean play;
    int playSoundId;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);


        txt_score = (TextView) findViewById(R.id.txt_score);
        txt_score.setText("점수 : "+score);

        btnSound = (ImageButton) findViewById(R.id.btn_sound);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);

        soundManager = new SoundManager(this,soundPool);
        soundManager.addSound(0,R.raw.fire);

        btnSound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 단어 소리 재생
                if(!play){
                    playSoundId=soundManager.playSound(0);
                    play = true;
                }else{
                    soundManager.pauseSound(0);
                    play = false;
                }

            }
        });


        btn1 = (ImageButton) findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 1번째 그림 선택, 정답
                score+=10;
                txt_score.setText("점수 : "+score);

            }
        });


        btn2 = (ImageButton) findViewById(R.id.btn2);

        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 2번째 그림 선택, 오답
            }
        });
    }

    class SoundManager {
        private SoundPool mSoundPool;
        private HashMap<Integer,Integer> mSoundPoolMap;
        private AudioManager mAudioManager;
        private Context mContext;
        public SoundManager(Context mContext, SoundPool mSoundPool){
            this.mContext = mContext;
            this.mSoundPool = mSoundPool;
            mSoundPoolMap = new HashMap<Integer, Integer>();
            mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
        }
        public void addSound(int index, int soundId){ //효과음 추가
            mSoundPoolMap.put(index,mSoundPool.load(mContext,soundId,1));
        }
        public int playSound(int index){ //효과음 재생
            int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            return mSoundPool.play(mSoundPoolMap.get(index),streamVolume,streamVolume,1,0,1f);
        }
        public void stopSound(int streamId){
            mSoundPool.stop(streamId);
        } //효과음 정지
        public void pauseSound(int streamId){
            mSoundPool.pause(streamId);
        } //효과음 일시정지
        public void resumeSound(int streamId){
            mSoundPool.resume(streamId);
        } //효과음 재시작

        //    출처: https://everyshare.tistory.com/11 [에브리셰어]
    }
}