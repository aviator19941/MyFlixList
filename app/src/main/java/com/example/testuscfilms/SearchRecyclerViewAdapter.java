package com.example.testuscfilms;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {
    List<FilmReportModel> searchList;
    LayoutInflater inflater;
    Context context;
    private static final String TAG = "SearchRecyclerViewAdapt";

    public SearchRecyclerViewAdapter(Context context, List<FilmReportModel> searchList) {
        this.searchList = searchList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_layout, parent, false);
        return new SearchRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilmReportModel filmReportModel = searchList.get(position);

        String categoryYear = filmReportModel.getCategory().toUpperCase() + " (" + filmReportModel.getReleaseDate() + ")";
        String title = filmReportModel.getTitle().toUpperCase();
        String rating = "" + filmReportModel.getRating();

        Glide.with(context)
                .asBitmap()
                .load(filmReportModel.getBackdropPath())
                .into(holder.imageView);

        holder.categoryYear.setText(categoryYear);
        holder.rating.setText(rating);
        holder.title.setText(title);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id", filmReportModel.getId());
                intent.putExtra("title", filmReportModel.getTitle());
                intent.putExtra("image_url", filmReportModel.getPosterPath());
                intent.putExtra("category", filmReportModel.getCategory());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView categoryYear;
        TextView rating;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.searchImage);
            categoryYear = itemView.findViewById(R.id.searchCategoryYear);
            rating = itemView.findViewById(R.id.searchRating);
            title = itemView.findViewById(R.id.searchTitle);
        }
    }
}
