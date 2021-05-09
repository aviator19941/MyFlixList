package com.example.testuscfilms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecommendedRecyclerViewAdapter extends RecyclerView.Adapter<RecommendedRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecommendedRecyclerView";

    private final List<SliderData> mSliderItems;
    private Context mContext;

    public RecommendedRecyclerViewAdapter(Context mContext, List<SliderData> mSliderItems) {
        this.mSliderItems = mSliderItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_layout, parent, false);
        return new RecommendedRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SliderData sliderItem = mSliderItems.get(position);

        Glide.with(mContext)
                .asBitmap()
                .load(sliderItem.getImgUrl())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("id", sliderItem.getId());
                intent.putExtra("title", sliderItem.getTitle());
                intent.putExtra("image_url", sliderItem.getImgUrl());
                intent.putExtra("category", sliderItem.getCategory());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSliderItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recommendedImage);
        }
    }
}
