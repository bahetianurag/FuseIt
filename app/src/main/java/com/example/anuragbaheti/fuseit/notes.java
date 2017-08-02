package com.example.anuragbaheti.fuseit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class notes extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    static Set<String> set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("NOTES");

        ListView listView = (ListView) findViewById(R.id.listView);

        SharedPreferences sp = this.getSharedPreferences("com.example.anuragbaheti.fuseit", Context.MODE_PRIVATE);
        set = sp.getStringSet("notes", null);

        notes.clear();
        if (set != null) {
            notes.addAll(set);
        }
        else {
            notes.add("EDIT THIS, ADD NEW ON RIGHT TOP");
            set = new HashSet<String>();
            set.addAll(notes);
            sp.edit().putStringSet("notes", set).apply();
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getApplicationContext(), editNote.class);
                in.putExtra("noteId", position);
                startActivity(in);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(notes.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                notes.remove(position);

                                SharedPreferences sp = notes.this.getSharedPreferences("com.example.anuragbaheti.fuseit", Context.MODE_PRIVATE);

                                if (set == null) {

                                    set = new HashSet<String>();

                                } else {

                                    set.clear();

                                }

                                set.addAll(notes);
                                sp.edit().remove("notes").apply();
                                sp.edit().putStringSet("notes", set).apply();
                                arrayAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {

            notes.add("");

            SharedPreferences sp = this.getSharedPreferences("com.example.anuragbaheti.fuseit", Context.MODE_PRIVATE);

            if (set == null) {

                set = new HashSet<String>();

            } else {

                set.clear();

            }

            set.addAll(notes);
            arrayAdapter.notifyDataSetChanged();

            sp.edit().remove("notes").apply();
            sp.edit().putStringSet("notes", set).apply();

            Intent i = new Intent(getApplicationContext(), editNote.class);
            i.putExtra("noteId", notes.size() - 1);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}