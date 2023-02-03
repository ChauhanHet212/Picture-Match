package com.example.picturematch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button noTimeModeBtn, normalModeBtn, hardModeBtn;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Select mode");

        noTimeModeBtn = findViewById(R.id.noTimeModeBtn);
        normalModeBtn = findViewById(R.id.normalModeBtn);
        hardModeBtn = findViewById(R.id.hardModeBtn);

        noTimeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                intent.putExtra("key", 1);
                startActivity(intent);
            }
        });
        normalModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                intent.putExtra("key", 2);
                startActivity(intent);
            }
        });
        hardModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                intent.putExtra("key", 3);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_sound){
            if (i == 1){
                item.setIcon(R.drawable.ic_volume_off);
                i = 2;
            } else {
                item.setIcon(R.drawable.ic_volume);
                i = 1;
            }
        }

        return true;
    }
}