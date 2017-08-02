package com.example.anuragbaheti.fuseit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

        ImageButton ala,todo,note,rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ala=(ImageButton)findViewById(R.id.alarm);
        todo=(ImageButton)findViewById(R.id.note);
        note=(ImageButton)findViewById(R.id.todo);
        rec=(ImageButton)findViewById(R.id.voicerecorder);

        ala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),alarm.class);
                startActivity(in);
            }
        });
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),notes.class);
                startActivity(in);
            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),to_do.class);
                startActivity(in);
            }
        });
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),recorder.class);
                startActivity(in);
            }
        });
    }
}


