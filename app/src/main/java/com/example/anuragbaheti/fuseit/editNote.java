package com.example.anuragbaheti.fuseit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashSet;

public class editNote extends AppCompatActivity implements TextWatcher{

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("EDIT YOUR NOTE");

        EditText editText = (EditText) findViewById(R.id.editText);

        Intent i = getIntent();
        noteId = i.getIntExtra("noteId", -1);

        if (noteId != -1) {
            editText.setText(notes.notes.get(noteId));
        }

        editText.addTextChangedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editnote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        notes.notes.set(noteId, String.valueOf(s));
        notes.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sp = getSharedPreferences("com.example.anuragbaheti.fuseit", Context.MODE_PRIVATE);

        if (notes.set == null) {
            notes.set = new HashSet<String>();
        } else {
            notes.set.clear();
        }
        notes.set.addAll(notes.notes);
        sp.edit().remove("notes").apply();
        sp.edit().putStringSet("notes", notes.set).apply();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
