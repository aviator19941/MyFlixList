package com.example.testuscfilms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CastRecyclerViewAdapter extends RecyclerView.Adapter<CastRecyclerViewAdapter.ViewHolder> {

    List<CastModel> castList;
    LayoutInflater inflater;
    Context context;

    public CastRecyclerViewAdapter(Context context, List<CastModel> castList) {
        this.context = context;
        this.castList = castList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cast_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CastModel castModel = castList.get(position);

        Glide.with(context)
                .asBitmap()
                .load(castModel.getProfilePath())
                .circleCrop()
                .into(holder.castImage);

        holder.castName.setText(castModel.getCastName());
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView castName;
        ImageView castImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            castName = itemView.findViewById(R.id.castName);
            castImage = itemView.findViewById(R.id.castImage);

        }
    }
}
