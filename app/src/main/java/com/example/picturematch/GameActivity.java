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
    public static boolean plus = false;
    int i, l, max;
    public static final int[] IMAGES = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e,
            R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        time_txtv = findViewById(R.id.time_txtv);
        recycler = findViewById(R.id.recycler);

        l = getIntent().getIntExtra("pos", 0);
        i = getIntent().getIntExtra("level_type", 0);

        if (l <= 3) {
            for (int j = 0; j < 12; j++) {
                imglist.add(j);
            }
        } else if (l <= 6) {
            for (int j = 0; j < 16; j++) {
                imglist.add(j);
            }
        } else {
            for (int j = 0; j < 20; j++) {
                imglist.add(j);
            }
        }

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
                    if (l <= 3) {
                        int i = new Random().nextInt(IMAGES.length);
                        int j = new Random().nextInt(6);
                        int k = new Random().nextInt(12 - 6) + 6;
                        if (!list1.contains(j) && !list1.contains(k) && !list2.contains(i)) {
                            list2.add(i);

                            imglist.set(j ,IMAGES[i]);
                            list1.add(j);
                            imglist.set(k ,IMAGES[i]);
                            list1.add(k);

                            if (list2.size() >= 6) {
                                break;
                            }
                        }
                    } else if (l <= 6){
                        int i = new Random().nextInt(IMAGES.length);
                        int j = new Random().nextInt(8);
                        int k = new Random().nextInt(16 - 8) + 8;
                        if (!list1.contains(j) && !list1.contains(k) && !list2.contains(i)) {
                            list2.add(i);

                            imglist.set(j ,IMAGES[i]);
                            list1.add(j);
                            imglist.set(k ,IMAGES[i]);
                            list1.add(k);

                            if (list2.size() >= 8) {
                                break;
                            }
                        }
                    } else {
                        int i = new Random().nextInt(IMAGES.length);
                        int j = new Random().nextInt(10);
                        int k = new Random().nextInt(20 - 10) + 10;
                        if (!list1.contains(j) && !list1.contains(k) && !list2.contains(i)) {
                            list2.add(i);

                            imglist.set(j ,IMAGES[i]);
                            list1.add(j);
                            imglist.set(k ,IMAGES[i]);
                            list1.add(k);

                            if (list2.size() >= 10) {
                                break;
                            }
                        }
                    }
                }

                if (l <= 3) {
                    recyclerAdapter = new RecyclerAdapter(GameActivity.this, imglist);
                    recycler.setLayoutManager(new GridLayoutManager(GameActivity.this, 3));
                    recycler.setAdapter(recyclerAdapter);
                } else if (l <= 6){
                    recyclerAdapter = new RecyclerAdapter(GameActivity.this, imglist);
                    recycler.setLayoutManager(new GridLayoutManager(GameActivity.this, 4));
                    recycler.setAdapter(recyclerAdapter);
                } else {
                    recyclerAdapter = new RecyclerAdapter(GameActivity.this, imglist);
                    recycler.setLayoutManager(new GridLayoutManager(GameActivity.this, 4));
                    recycler.setAdapter(recyclerAdapter);
                }

                if (i == 2) {
                    max = 30;
                    seekBar.setMax(max);
                } else if (i == 3) {
                    max = 5;
                    seekBar.setMax(max);
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
                        if (j == 0 && !win) {
                            k++;
                            if (i == 2) {
                                if (k <= max) {
                                    if (plus){
                                        plus = false;
                                        max = max + 5;
                                        seekBar.setMax(max);
                                    }
                                    time_txtv.setText(k + "/" + max);
                                }
                            } else if (i == 3) {
                                if (k <= max) {
                                    if (plus){
                                        plus = false;
                                        max = max + 5;
                                        seekBar.setMax(max);
                                    }
                                    time_txtv.setText(k + "/" + max);
                                }
                            }
                            seekBar.setProgress(k);
                        }
                        if (i == 2) {
                            if (k == max) {
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
                            if (k == max) {
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
