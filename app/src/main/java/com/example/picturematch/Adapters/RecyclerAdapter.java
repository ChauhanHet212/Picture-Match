package com.example.picturematch.Adapters;

import static com.example.picturematch.GameActivity.plus;
import static com.example.picturematch.GameActivity.win;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picturematch.GameActivity;
import com.example.picturematch.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Activity activity;
    List<Integer> img;
    int click = 1;
    View view1;
    int pos1, pos2;
    int check;

    public RecyclerAdapter(Activity activity, List<Integer> img) {
        this.activity = activity;
        this.img = img;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.img.setImageResource(img.get(position));
        holder.cover.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.cover.setVisibility(View.VISIBLE);
            }
        }, 4000);

        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click == 1) {
                    holder.cover.setVisibility(View.INVISIBLE);
                    click = 3;
                    view1 = holder.cover;
                    pos1 = holder.getAdapterPosition();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            click = 2;
                        }
                    }, 100);
                } else if (click == 2) {
                    holder.cover.setVisibility(View.INVISIBLE);
                    click = 3;
                    pos2 = holder.getAdapterPosition();
                    if (img.get(pos1).equals(img.get(pos2))) {
                        holder.cover.setClickable(false);
                        view1.setClickable(false);
                        click = 1;
                        check ++;
                        plus = true;
                        if (check == (img.size()/2)){
                            win = true;
                            Dialog dialog1 = new Dialog(activity);
                            dialog1.setCancelable(false);
                            dialog1.setContentView(R.layout.win_dialog);

                            Button win_okBtn = dialog1.findViewById(R.id.win_okBtn);

                            win_okBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog1.dismiss();
                                    activity.finish();
                                }
                            });

                            dialog1.show();
                        }
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.cover.setVisibility(View.VISIBLE);
                                view1.setVisibility(View.VISIBLE);
                                click = 1;
                            }
                        }, 500);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return img.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img, cover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            cover = itemView.findViewById(R.id.cover);
        }
    }
}