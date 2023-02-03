package com.example.picturematch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.picturematch.Adapters.LevelAdapter;

public class LevelActivity extends AppCompatActivity {

    ImageView infoBtn;
    RecyclerView levelRecycler;
    LevelAdapter levelAdapter;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select level");

        int i = getIntent().getIntExtra("key", 0);
        if (i == 1){
            getSupportActionBar().setSubtitle("NO TIME LIMIT");
        } else if (i == 2){
            getSupportActionBar().setSubtitle("NORMAL");
        } else if (i == 3){
            getSupportActionBar().setSubtitle("HARD");
        }

        infoBtn = findViewById(R.id.infoBtn);
        levelRecycler = findViewById(R.id.levelRecycler);

        levelRecycler.setLayoutManager(new LinearLayoutManager(LevelActivity.this));
        levelAdapter = new LevelAdapter(LevelActivity.this);
        levelRecycler.setAdapter(levelAdapter);

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(LevelActivity.this);
                dialog.setContentView(R.layout.info_dialog);

                Button info_okBtn = dialog.findViewById(R.id.info_okBtn);

                info_okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sub_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            this.finish();
        } else if (item.getItemId() == R.id.menu_sound){
            if (i == 1){
                item.setTitle("SOUND ON");
                i = 2;
            } else {
                item.setTitle("SOUND OFF");
                i = 1;
            }
        }

        return true;
    }
}