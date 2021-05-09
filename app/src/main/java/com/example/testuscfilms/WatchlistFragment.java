package com.example.testuscfilms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WatchlistFragment extends Fragment {
    private static final String TAG = "WatchlistFragment";
    WatchlistRecyclerViewAdapter watchlistRecyclerViewAdapter;
    RecyclerView watchlist;
    TextView noWatchlist;
    List<FilmReportModel> curItems;
    Toast m_currentToast;
    private int fromPos = -1;
    private int toPos = -1;
    ItemTouchHelper itemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);
        watchlist = view.findViewById(R.id.watchlistRecyclerView);
        noWatchlist = view.findViewById(R.id.noWatchlist);

        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        supportActionBar.setShowHideAnimationEnabled(false);
        if (supportActionBar != null)
            supportActionBar.hide();

        loadData();

        if (curItems.size() > 0) {
            watchlist.setVisibility(View.VISIBLE);
            noWatchlist.setVisibility(View.INVISIBLE);

            watchlistRecyclerViewAdapter = new WatchlistRecyclerViewAdapter(getActivity(), curItems, this);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
            watchlist.setLayoutManager(gridLayoutManager);
            watchlist.setAdapter(watchlistRecyclerViewAdapter);

            watchlistRecyclerViewAdapter.notifyDataSetChanged();

        } else {
            noWatchlist.setVisibility(View.VISIBLE);
            watchlist.setVisibility(View.INVISIBLE);
        }

        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(watchlist);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            loadData();

            Collections.swap(curItems, fromPosition, toPosition);

            watchlist.getAdapter().notifyItemMoved(fromPosition,  toPosition);
            saveData();

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }


    };

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("watchlist", null);
        Type type = new TypeToken<ArrayList<FilmReportModel>>() {}.getType();
        curItems = gson.fromJson(json, type);

        if (curItems == null) {
            curItems = new ArrayList<>();
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(curItems);
        editor.putString("watchlist", json);
        editor.commit();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStop() {
        super.onStop();
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        supportActionBar.setShowHideAnimationEnabled(false);
        if (supportActionBar != null)
            supportActionBar.show();
    }

}
