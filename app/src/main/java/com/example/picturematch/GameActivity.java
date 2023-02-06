package com.example.picturematch;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    SeekBar seekBar;
    ImageView[] img = new ImageView[12];
    ImageView[] cover = new ImageView[12];
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    int click = 1;
    int i;
    int pos;
    int check1, check2;
    public static final int[] IMAGES = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e,
            R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        i = getIntent().getIntExtra("level_type", 0);

        seekBar = findViewById(R.id.seekBar);
        if (i == 1) {
            getSupportActionBar().setTitle("NO TIME LIMIT");
            getSupportActionBar().setSubtitle("Time :- no limit");
            seekBar.setVisibility(View.GONE);
        } else if (i == 2){
            getSupportActionBar().setTitle("NORMAL");
            getSupportActionBar().setSubtitle("Time :- 30s");
            seekBar.setVisibility(View.VISIBLE);
        } else if (i == 3){
            getSupportActionBar().setTitle("HARD");
            getSupportActionBar().setSubtitle("Time :- 5s");
            seekBar.setVisibility(View.VISIBLE);
        }


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

                if (i == 2) {
                    seekBar.setMax(30);
                } else if (i == 3) {
                    seekBar.setMax(5);
                }
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    int j = 6;
                    int k = -1;

                    @Override
                    public void run() {
                        if (j > 0) {
                            j--;
                            seekBar.setProgress(j);
                        }
                        if (j == 0) {
                            k++;
                            seekBar.setProgress(k);
                        }
                        if (i == 2) {
                            if (k == 30) {
                                Dialog dialog1 = new Dialog(GameActivity.this);
                                dialog1.setCancelable(false);
                                dialog1.setContentView(R.layout.time_out_dialog);

                                Button cancel, ok;

                                cancel = dialog1.findViewById(R.id.over_cancel);
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog1.dismiss();
                                        finish();
                                    }
                                });

                                ok = dialog1.findViewById(R.id.over_ok);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog1.dismiss();
                                        recreate();
                                    }
                                });
                                dialog1.show();
                            }
                        } else if (i == 3) {
                            if (k == 5) {
                                Dialog dialog1 = new Dialog(GameActivity.this);
                                dialog1.setCancelable(false);
                                dialog1.setContentView(R.layout.time_out_dialog);

                                Button cancel, ok;

                                cancel = dialog1.findViewById(R.id.over_cancel);
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog1.dismiss();
                                        finish();
                                    }
                                });

                                ok = dialog1.findViewById(R.id.over_ok);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog1.dismiss();
                                        recreate();
                                    }
                                });
                                dialog1.show();
                            }
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                });
            }
        });
        dialog.show();

    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < 12; i++) {
            checkWin();
            if (view.getId() == cover[i].getId()) {
                if (click == 1) {
                    cover[i].setVisibility(View.INVISIBLE);
                    click = 3;
                    pos = i;
                    check1 = Integer.parseInt(cover[i].getTag().toString());

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            click = 2;
                        }
                    }, 100);
                } else if (click == 2) {
                    cover[i].setVisibility(View.INVISIBLE);
                    click = 3;
                    check2 = Integer.parseInt(cover[i].getTag().toString());
                    if (check1 == check2) {
                        cover[i].setClickable(false);
                        cover[pos].setClickable(false);
                        click = 1;
                    } else {
                        int finalI = i;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cover[finalI].setVisibility(View.VISIBLE);
                                cover[pos].setVisibility(View.VISIBLE);
                                click = 1;
                            }
                        }, 500);
                    }
                }
                checkWin();
            }
        }

    }

    public void checkWin() {
        int check = 0;
        for (int i = 0; i < 12; i++) {
            if (!cover[i].isClickable()) {
                check++;
            }
        }

        if (check == 12) {
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return true;
    }
}