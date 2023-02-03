package com.example.picturematch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView[] img = new ImageView[12];
    ImageView[] cover = new ImageView[12];
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    int click = 1;
    int pos;
    int check1, check2;
    public static final int[] IMAGES = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e,
                                        R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        for (int i = 0; i < 12; i++) {
            int id = getResources().getIdentifier("img" + i, "id", getPackageName());
            img[i] = findViewById(id);
        }

        for (int i = 0; i < 12; i++) {
            int id = getResources().getIdentifier("cover" + i, "id", getPackageName());
            cover[i] = findViewById(id);
            cover[i].setVisibility(View.INVISIBLE);
            cover[i].setOnClickListener(this);
        }

        Dialog dialog = new Dialog(GameActivity.this);
        dialog.setContentView(R.layout.ntl_info_dialog);
        dialog.setCancelable(false);

        Button ntl_go = dialog.findViewById(R.id.ntl_go);
        ntl_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                int l = 1;
                while (true) {
                    int i = new Random().nextInt(IMAGES.length);
                    int j = new Random().nextInt(12);
                    int k = new Random().nextInt(12);
                    if (j != k && !list1.contains(j) && !list1.contains(k) && !list2.contains(i)) {
                        list2.add(i);

                        img[j].setBackground(getResources().getDrawable(R.drawable.flipedimg_bg));
                        img[j].setImageResource(IMAGES[i]);
                        cover[j].setTag(l);
                        list1.add(j);

                        img[k].setBackground(getResources().getDrawable(R.drawable.flipedimg_bg));
                        img[k].setImageResource(IMAGES[i]);
                        cover[k].setTag(l);
                        list1.add(k);

                        l++;

                        if (list2.size() >= 6) {
                            break;
                        }
                    }
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 12; i++) {
                            cover[i].setVisibility(View.VISIBLE);
                        }
                    }
                }, 5000);
            }
        });
        dialog.show();

    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < 12; i++) {
            checkWin();
            if (view.getId() == cover[i].getId()){
                cover[i].setVisibility(View.INVISIBLE);
                if (click == 1){
                    click = 2;
                    pos = i;
                    check1 = Integer.parseInt(cover[i].getTag().toString());
                } else {
                    click = 1;
                    check2 = Integer.parseInt(cover[i].getTag().toString());
                    if (check1 == check2){
                        cover[i].setClickable(false);
                        cover[pos].setClickable(false);
                    } else if (check1 != check2){
                        int finalI = i;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cover[finalI].setVisibility(View.VISIBLE);
                                cover[pos].setVisibility(View.VISIBLE);
                            }
                        }, 500);
                    }
                }
                checkWin();
            }
        }
    }

    public void checkWin(){
        int check = 0;
        for (int i = 0; i < 12; i++) {
            if (!cover[i].isClickable()){
                check ++;
            }
        }

        if (check == 12){
            Dialog dialog = new Dialog(GameActivity.this);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.win_dialog);

            Button win_okBtn = dialog.findViewById(R.id.win_okBtn);

            win_okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    finish();
                }
            });

            dialog.show();
        }
    }
}