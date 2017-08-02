package com.example.anuragbaheti.fuseit;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class wakeup extends AppCompatActivity {
    EditText ans;int a;SoundPool soundPool;int soundID;int stid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakeup);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("WAKE UP");

        Random r =new Random(System.currentTimeMillis());
        int m = r.nextInt(20);
        int n= r.nextInt(15);
        int l = r.nextInt(25);
        a=m*n-l;
        EditText ans = (EditText) findViewById(R.id.answbox);
        this.ans= ans;
        TextView tv = (TextView) findViewById(R.id.questv);
        tv.setText(m+"x"+n+"-"+l);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        final float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                stid =  soundPool.play(soundID, maxVolume, maxVolume, 1, -1, 1f);
            }
        });
        soundID = soundPool.load(this, R.raw.sound1, 1);
        Button sub =(Button) findViewById(R.id.subansw);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkansw();
            }
        });


    }

    void checkansw()
    {
        int an = Integer.parseInt(ans.getText().toString());
        if(an==a)
        {
            Toast.makeText(this,"Correct answer",Toast.LENGTH_LONG).show();
            soundPool.stop(stid);
            this.finish();
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
