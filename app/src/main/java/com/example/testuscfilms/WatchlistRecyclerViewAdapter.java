package com.example.testuscfilms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRecyclerViewAdapter extends RecyclerView.Adapter<WatchlistRecyclerViewAdapter.ViewHolder> {
    List<FilmReportModel> filmReportModelList;
    LayoutInflater inflater;
    Context context;
    Toast m_currentToast;
    private WatchlistFragment watchlistFragment;
    private static final String TAG = "WatchlistRecyclerViewAd";

    public WatchlistRecyclerViewAdapter(Context context, List<FilmReportModel> filmReportModelList, WatchlistFragment watchlistFragment) {
        this.context = context;
        this.filmReportModelList = filmReportModelList;
        this.watchlistFragment = watchlistFragment;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.watchlist_grid_layout, parent, false);
        return new WatchlistRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilmReportModel filmReportModel = filmReportModelList.get(position);
        Log.d(TAG, "loading watchlist image " + filmReportModel.getPosterPath());

        Glide.with(context)
            .asBitmap()
            .load(filmReportModel.getPosterPath())
            .into(holder.imageView);

        String str = filmReportModel.getCategory();
        String cap = "";
        if (str.equals("movie"))
            cap = str.substring(0, 1).toUpperCase() + str.substring(1);
        else if (str.equals("tv"))
            cap = str.toUpperCase();

        holder.categoryText.setText(cap);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id", filmReportModel.getId());
                intent.putExtra("title", filmReportModel.getTitle());
                intent.putExtra("image_url", filmReportModel.getBackdropPath());
                intent.putExtra("poster_path", filmReportModel.getPosterPath());
                intent.putExtra("category", filmReportModel.getCategory());
                Log.d(TAG, intent.getStringExtra("poster_path"));
                context.startActivity(intent);
            }
        });

        holder.removeFromWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(filmReportModel);
                notifyDataSetChanged();
                showToast("\"" + filmReportModel.getTitle() + "\" was removed from Watchlist");
            }
        });
    }

    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("watchlist", null);
        Type type = new TypeToken<ArrayList<FilmReportModel>>() {}.getType();
        filmReportModelList = gson.fromJson(json, type);

        if (filmReportModelList == null) {
            filmReportModelList = new ArrayList<>();
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(filmReportModelList);
        editor.putString("watchlist", json);
        editor.apply();
    }

    private void deleteData(FilmReportModel filmReportModel) {
        loadData();

        filmReportModelList.removeIf(obj -> filmReportModel.getId() == obj.getId() && filmReportModel.getTitle().equals(obj.getTitle()) && filmReportModel.getCategory().equals(obj.getCategory()));
        if (filmReportModelList.size() == 0) {
            watchlistFragment.noWatchlist.setVisibility(View.VISIBLE);
            watchlistFragment.watchlist.setVisibility(View.GONE);
        }
        saveData();
    }

    @Override
    public int getItemCount() {
        return filmReportModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryText;
        ImageView imageView;
        ImageView removeFromWatchlist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryText = itemView.findViewById(R.id.categoryWatchlist);
            imageView = itemView.findViewById(R.id.imageWatchlist);
            removeFromWatchlist = itemView.findViewById(R.id.removeFromWatchlist);
        }
    }

    void showToast(String text) {
        if (m_currentToast != null) {
            m_currentToast.cancel();
        }
        m_currentToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        m_currentToast.show();
    }
}
