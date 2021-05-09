package com.example.testuscfilms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchFragment extends Fragment {
    SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    RecyclerView searchList;
    private static final String TAG = "SearchFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchList = view.findViewById(R.id.searchResults);
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setFocusable(true);
        searchView.onActionViewExpanded();
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });

        View closeBtn = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery(null, false);
                searchView.setQueryHint("Search movies and TV");
                view.findViewById(R.id.searchResults).setVisibility(View.GONE);
                view.findViewById(R.id.noResults).setVisibility(View.GONE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FilmDataService filmDataService = new FilmDataService(getContext());

                filmDataService.getMultiSearchResults(newText, new FilmDataService.FilmResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(), "something wrong with search fragment receiving data", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<FilmReportModel> filmReportModels) {
                        if (filmReportModels == null || filmReportModels.size() == 0) {
                            view.findViewById(R.id.searchResults).setVisibility(View.GONE);
                            view.findViewById(R.id.noResults).setVisibility(View.VISIBLE);
                        } else {
                            view.findViewById(R.id.searchResults).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.noResults).setVisibility(View.GONE);
                            searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(getContext(), filmReportModels);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            searchList.setLayoutManager(layoutManager);
                            searchList.setAdapter(searchRecyclerViewAdapter);
                        }
                    }
                });

                return false;
            }
        });

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
