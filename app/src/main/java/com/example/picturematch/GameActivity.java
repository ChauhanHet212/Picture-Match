package com.example.picturematch;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picturematch.Adapters.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class GameActivity extends AppCompatActivity {

    RecyclerView recycler;
    RecyclerAdapter recyclerAdapter;
    SeekBar seekBar;
    TextView time_txtv;
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    List<Integer> imglist = new ArrayList<>();
    Handler handler;
    Runnable runnable;
    public static boolean win = false;
    int i;
    int check1, check2;
    public static final int[] IMAGES = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e,
            R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        time_txtv = findViewById(R.id.time_txtv);
        recycler = findViewById(R.id.recycler);

        imglist.add(1);
        imglist.add(1);
        imglist.add(1);
        imglist.add(1);
        imglist.add(1);
        imglist.add(1);
        imglist.add(1);
        imglist.add(1);
        imglist.add(1);
        imglist.add(1);
        imglist.add(1);
        imglist.add(1);

        i = getIntent().getIntExtra("level_type", 0);

        seekBar = findViewById(R.id.seekBar);
        if (i == 1) {
            getSupportActionBar().setTitle("NO TIME LIMIT");
            seekBar.setVisibility(View.GONE);
        } else if (i == 2) {
            getSupportActionBar().setTitle("NORMAL");
            seekBar.setVisibility(View.VISIBLE);
        } else if (i == 3) {
            getSupportActionBar().setTitle("HARD");
            seekBar.setVisibility(View.VISIBLE);
        }

        Dialog dialog = new Dialog(GameActivity.this);
        dialog.setContentView(R.layout.ntl_info_dialog);
        dialog.setCancelable(false);

        Button ntl_go = dialog.findViewById(R.id.ntl_go);
        TextView info_title = dialog.findViewById(R.id.info_tital);

        if (i == 1) {
            info_title.setText("TIME: NO TIME LIMIT");
            time_txtv.setText("No Limit");
        } else if (i == 2) {
            info_title.setText("TIME: 30 s");
            time_txtv.setText("0/30");
        } else if (i == 3) {
            info_title.setText("TIME: 5 s");
            time_txtv.setText("0/5");
        }
        ntl_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                while (true) {
                    int i = new Random().nextInt(IMAGES.length);
                    int j = new Random().nextInt(12);
                    int k = new Random().nextInt(12);
                    if (j != k && !list1.contains(j) && !list1.contains(k) && !list2.contains(i)) {
                        list2.add(i);

                        imglist.set(j ,IMAGES[i]);
                        list1.add(j);
                        imglist.set(k ,IMAGES[i]);
                        list1.add(k);

                        if (list2.size() >= 6) {
                            break;
                        }
                    }
                }

                recyclerAdapter = new RecyclerAdapter(GameActivity.this, imglist);
                recycler.setLayoutManager(new GridLayoutManager(GameActivity.this, 3));
                recycler.setAdapter(recyclerAdapter);

                if (i == 2) {
                    seekBar.setMax(30);
                } else if (i == 3) {
                    seekBar.setMax(5);
                }
                handler = new Handler();
                runnable = new Runnable() {
                    int j = 5;
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
                            if (i == 2) {
                                if (k <= 30) {
                                    time_txtv.setText(k + "/30");
                                }
                            } else if (i == 3) {
                                if (k <= 5) {
                                    time_txtv.setText(k + "/5");
                                }
                            }
                        }
                        if (i == 2) {
                            if (k == 30) {
                                Dialog dialog1 = new Dialog(GameActivity.this);
                                dialog1.setCancelable(false);
                                dialog1.setContentView(R.layout.time_out_dialog);

                                ((Button) dialog1.findViewById(R.id.over_cancel)).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog1.dismiss();
                                        finish();
                                    }
                                });

                                ((Button) dialog1.findViewById(R.id.over_ok)).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog1.dismiss();
                                        recreate();
                                    }
                                });
                                if (!isFinishing() && !win) {
                                    dialog1.show();
                                }
                            }
                        } else if (i == 3) {
                            if (k == 5) {
                                Dialog dialog1 = new Dialog(GameActivity.this);
                                dialog1.setCancelable(false);
                                dialog1.setContentView(R.layout.time_out_dialog);

                                ((Button) dialog1.findViewById(R.id.over_cancel)).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog1.dismiss();
                                        finish();
                                    }
                                });

                                ((Button) dialog1.findViewById(R.id.over_ok)).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog1.dismiss();
                                        recreate();
                                    }
                                });
                                if (!isFinishing() && !win) {
                                    dialog1.show();
                                }
                            }
                        }
                        handler.postDelayed(this::run, 1000);
                    }
                };
                handler.post(runnable);
            }
        });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return true;
    }
}