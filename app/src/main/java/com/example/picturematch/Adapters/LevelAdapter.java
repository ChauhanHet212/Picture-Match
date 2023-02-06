package com.example.picturematch.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picturematch.GameActivity;
import com.example.picturematch.R;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    Context context;
    String[] number = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    int i;

    public LevelAdapter(Context context, int i) {
        this.context = context;
        this.i = i;
    }

    @NonNull
    @Override
    public LevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.level_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapter.ViewHolder holder, int position) {
        holder.level_btn.setText("LEVEL " + number[position]);

        holder.level_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GameActivity.class);
                intent.putExtra("level_type", i);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return number.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button level_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            level_btn = itemView.findViewById(R.id.level_btn);
        }
    }
}
