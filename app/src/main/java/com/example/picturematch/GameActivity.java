package com.example.picturematch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Dialog dialog = new Dialog(GameActivity.this);
        dialog.setContentView(R.layout.ntl_info_dialog);
        dialog.setCancelable(false);

        Button ntl_go = dialog.findViewById(R.id.ntl_go);
        ntl_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}