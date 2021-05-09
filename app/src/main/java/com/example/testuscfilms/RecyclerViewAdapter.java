package com.example.testuscfilms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private final List<SliderData> mSliderItems;
    List<FilmReportModel> savedData;
    private Context mContext;
    private Toast m_currentToast;

    public RecyclerViewAdapter(Context mContext, List<SliderData> mSliderItems) {
        this.mSliderItems = mSliderItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_layout, parent, false);
        loadData();

        return new ViewHolder(view);
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

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.menu);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                Menu popupMenu = popup.getMenu();
                loadData();
                for (FilmReportModel f : savedData) {
                    if (f.getId() == sliderItem.getId() && f.getTitle().equals(sliderItem.getTitle()) && f.getCategory().equals(sliderItem.getCategory())) {
                        popupMenu.getItem(3).setTitle("Remove from Watchlist");
                    }
                }
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.open_tmdb:
                                //handle menu1 click
                                Intent intent1 = new Intent(android.content.Intent.ACTION_VIEW);
                                intent1.setData(Uri.parse("https://www.themoviedb.org/movie/" + sliderItem.getId()));
                                mContext.startActivity(intent1);
                                return true;
                            case R.id.share_fb:
                                //handle menu2 click
                                String facebookUrl = "https://www.facebook.com/sharer/sharer.php?u=https://www.themoviedb.org/movie/" + sliderItem.getId();
                                Intent intent2 = new Intent(Intent.ACTION_VIEW);
                                intent2.setData(Uri.parse(facebookUrl));
                                mContext.startActivity(intent2);
                                return true;
                            case R.id.share_twitter:
                                //handle menu3 click
                                String twitterUrl = "http://www.twitter.com/intent/tweet?text=Check this out!%0Ahttps://www.themoviedb.org/movie/"  + sliderItem.getId();
                                Intent intent3 = new Intent(Intent.ACTION_VIEW);
                                intent3.setData(Uri.parse(twitterUrl));
                                mContext.startActivity(intent3);
                                return true;
                            case R.id.add_watchlist:
                                if (item.getTitle().equals("Add to Watchlist")) {
                                    showToast(sliderItem.getTitle() + " was added to Watchlist");
                                    FilmReportModel filmReportModel = new FilmReportModel();

                                    filmReportModel.setId(sliderItem.getId());
                                    filmReportModel.setTitle(sliderItem.getTitle());
                                    Log.d(TAG, "posterPath of recyclerview popup menu ADD is: " + sliderItem.getImgUrl());
                                    filmReportModel.setPosterPath(sliderItem.getImgUrl());
                                    filmReportModel.setCategory(sliderItem.getCategory());
                                    savedData.add(filmReportModel);
                                    saveData();
                                } else if (item.getTitle().equals("Remove from Watchlist")) {
                                    showToast(sliderItem.getTitle() + " was removed from Watchlist");
                                    FilmReportModel filmReportModel = new FilmReportModel();

                                    filmReportModel.setId(sliderItem.getId());
                                    filmReportModel.setTitle(sliderItem.getTitle());
                                    Log.d(TAG, "posterPath of recyclerview popup menu REMOVE is: " + sliderItem.getImgUrl());
                                    filmReportModel.setPosterPath(sliderItem.getImgUrl());
                                    filmReportModel.setCategory(sliderItem.getCategory());

                                    deleteData(filmReportModel);
                                }

                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(savedData);
        editor.putString("watchlist", json);
        editor.apply();
    }

    private void deleteData(FilmReportModel filmReportModel) {
        loadData();
        Log.d(TAG, filmReportModel.getId() + ", " + filmReportModel.getTitle() + ", " + filmReportModel.getPosterPath() + ", " + filmReportModel.getCategory());
        savedData.removeIf(obj -> filmReportModel.getId() == obj.getId() && filmReportModel.getTitle().equals(obj.getTitle()) && filmReportModel.getCategory().equals(obj.getCategory()));
        saveData();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("watchlist", null);
        Type type = new TypeToken<ArrayList<FilmReportModel>>() {}.getType();
        savedData = gson.fromJson(json, type);

        if (savedData == null) {
            savedData = new ArrayList<>();
        }
    }

    void showToast(String text) {
        if (m_currentToast != null) {
            m_currentToast.cancel();
        }
        m_currentToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
        m_currentToast.show();
    }

    @Override
    public int getItemCount() {
        return mSliderItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView menu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            menu = itemView.findViewById(R.id.menu_options);
        }
    }
}
