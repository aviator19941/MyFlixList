package com.example.testuscfilms;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ProgressBar progressBar;
    TextView loadingText;
    TextView topRatedTxt;
    TextView popularTxt;
    TextView footer;
    TextView moviesText;
    TextView tvText;
    String myResponse;
    FilmDataService filmDataService;
    ArrayList<SliderData> sliderDataArrayList;
    SliderView sliderView;
    SliderAdapter adapter;
    View view;

    private static final String TAG = "HomeFragment";

    public HomeFragment(FilmDataService filmDataService) {
        this.filmDataService = filmDataService;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        progressBar = view.findViewById(R.id.progressBar2);
        loadingText = view.findViewById(R.id.loadingText);
        topRatedTxt = view.findViewById(R.id.topRatedMoviesTxt);
        popularTxt = view.findViewById(R.id.popularMoviesTxt);

        // NOW PLAYING MOVIES
        filmDataService.getNowPlaying(new FilmDataService.FilmResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), "something wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<FilmReportModel> filmReportModels) {

                sliderDataArrayList = new ArrayList<>();

                sliderView = view.findViewById(R.id.slider);
                sliderView.setVisibility(View.VISIBLE);

                for (int i = 0; i < filmReportModels.size(); i++) {
                    FilmReportModel filmReportModel = filmReportModels.get(i);
                    sliderDataArrayList.add(new SliderData(filmReportModel.getId(), filmReportModel.getPosterPath(), filmReportModel.getTitle(), "movie"));
                }

                adapter = new SliderAdapter(getActivity(), sliderDataArrayList);

                sliderView.setSliderAdapter(adapter);
                sliderView.setScrollTimeInSec(3);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();
            }
        });

        // TOP RATED MOVIES
        filmDataService.getTopRatedMovies(new FilmDataService.FilmResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity().getApplicationContext(), "something wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<FilmReportModel> filmReportModels) {
                sliderDataArrayList = new ArrayList<>();

                for (int i = 0; i < filmReportModels.size(); i++) {
                    FilmReportModel filmReportModel = filmReportModels.get(i);
                    sliderDataArrayList.add(new SliderData(filmReportModel.getId(), filmReportModel.getPosterPath(), filmReportModel.getTitle(), "movie"));
                }

                initTopRatedMovies();
            }
        });

        // GET POPULAR MOVIES
        filmDataService.getPopularMovies(new FilmDataService.FilmResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity().getApplicationContext(), "something wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<FilmReportModel> filmReportModels) {
                sliderDataArrayList = new ArrayList<>();

                for (int i = 0; i < filmReportModels.size(); i++) {
                    FilmReportModel filmReportModel = filmReportModels.get(i);
                    sliderDataArrayList.add(new SliderData(filmReportModel.getId(), filmReportModel.getPosterPath(), filmReportModel.getTitle(), "movie"));
                }

                initPopularMovies();
                footer = view.findViewById(R.id.footer);

                footer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.themoviedb.org/"));
                        startActivity(intent);
                    }
                });
            }
        });

        filmDataService.getTrendingTVShows(new FilmDataService.FilmResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), "something wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<FilmReportModel> filmReportModels) {

                sliderDataArrayList = new ArrayList<>();

                sliderView = view.findViewById(R.id.sliderTv);
                sliderView.setVisibility(View.VISIBLE);

                for (int i = 0; i < filmReportModels.size(); i++) {
                    FilmReportModel filmReportModel = filmReportModels.get(i);
                    sliderDataArrayList.add(new SliderData(filmReportModel.getId(), filmReportModel.getPosterPath(), filmReportModel.getTitle(), "tv"));
                }

                adapter = new SliderAdapter(getActivity(), sliderDataArrayList);

                sliderView.setSliderAdapter(adapter);
                sliderView.setScrollTimeInSec(3);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();
            }
        });

        filmDataService.getTopRatedTVShows(new FilmDataService.FilmResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity().getApplicationContext(), "something wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<FilmReportModel> filmReportModels) {
                sliderDataArrayList = new ArrayList<>();

                for (int i = 0; i < filmReportModels.size(); i++) {
                    FilmReportModel filmReportModel = filmReportModels.get(i);
                    sliderDataArrayList.add(new SliderData(filmReportModel.getId(), filmReportModel.getPosterPath(), filmReportModel.getTitle(), "tv"));
                }

                initTopRatedTVShows();
                footer = view.findViewById(R.id.footerTv);

                footer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.themoviedb.org/"));
                        startActivity(intent);
                    }
                });
            }
        });

        filmDataService.getPopularTVShows(new FilmDataService.FilmResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity().getApplicationContext(), "something wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<FilmReportModel> filmReportModels) {
                sliderDataArrayList = new ArrayList<>();

                for (int i = 0; i < filmReportModels.size(); i++) {
                    FilmReportModel filmReportModel = filmReportModels.get(i);
                    sliderDataArrayList.add(new SliderData(filmReportModel.getId(), filmReportModel.getPosterPath(), filmReportModel.getTitle(), "tv"));
                }

                initPopularTVShows();
                progressBar.setVisibility(View.GONE);
                loadingText.setVisibility(View.GONE);
                view.findViewById(R.id.moviesView).setVisibility(View.VISIBLE);
                view.findViewById(R.id.tvView).setVisibility(View.INVISIBLE);
                topRatedTxt.setVisibility(View.VISIBLE);
                popularTxt.setVisibility(View.VISIBLE);

                ((AppCompatActivity) getActivity()).getSupportActionBar().show();

                moviesText = ((AppCompatActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.moviesTextView);
                tvText = ((AppCompatActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.tvTextView);
                moviesText.setTextColor(getResources().getColor(R.color.white, null));
                tvText.setTextColor(getResources().getColor(R.color.inactiveBottomNav, null));
                moviesText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        topRatedTxt = view.findViewById(R.id.topRatedMoviesTxt);
                        popularTxt = view.findViewById(R.id.popularMoviesTxt);
                        moviesText.setTextColor(getResources().getColor(R.color.white, null));
                        tvText.setTextColor(getResources().getColor(R.color.inactiveBottomNav, null));
                        view.findViewById(R.id.moviesView).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.tvView).setVisibility(View.INVISIBLE);
                        topRatedTxt.setVisibility(View.VISIBLE);
                        popularTxt.setVisibility(View.VISIBLE);
                    }
                });

                tvText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        topRatedTxt = view.findViewById(R.id.topRatedTvTxt);
                        popularTxt = view.findViewById(R.id.popularTvTxt);
                        tvText.setTextColor(getResources().getColor(R.color.white, null));
                        moviesText.setTextColor(getResources().getColor(R.color.inactiveBottomNav, null));
                        view.findViewById(R.id.tvView).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.moviesView).setVisibility(View.INVISIBLE);
                        topRatedTxt.setVisibility(View.VISIBLE);
                        popularTxt.setVisibility(View.VISIBLE);

                    }
                });
            }
        });

        return view;
    }

    private void initTopRatedMovies() {
        Log.d(TAG, "initRecyclerView: init top rated movies");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.topRatedMovies);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), sliderDataArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initPopularMovies() {
        Log.d(TAG, "initRecyclerView: init popular movies");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.popularMovies);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), sliderDataArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initTopRatedTVShows() {
        Log.d(TAG, "initRecyclerView: init top rated TV shows");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.topRatedTv);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), sliderDataArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initPopularTVShows() {
        Log.d(TAG, "initRecyclerView: init popular TV shows");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.popularTv);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), sliderDataArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

}
