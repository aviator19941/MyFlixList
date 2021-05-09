package com.example.testuscfilms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity";
    private int id;
    private String title;
    private String category;
    private String posterPath;
    private String posterPathWatchlist;
    List<FilmReportModel> savedData;
    ImageView facebookButton;
    ImageView twitterButton;
    ImageView addWatchlist;
    ImageView removeWatchlist;
    RecyclerView castList;
    RecyclerView reviewList;
    CastRecyclerViewAdapter castRecyclerViewAdapter;
    ReviewRecyclerViewAdapter reviewRecyclerViewAdapter;
    Toast m_currentToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.detailsTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Log.d(TAG, "onCreate: details started");
        FilmDataService filmDataService = new FilmDataService(DetailsActivity.this);
        castList = findViewById(R.id.castList);
        reviewList = findViewById(R.id.reviewList);
        addWatchlist = findViewById(R.id.addWatchlistButton);
        removeWatchlist = findViewById(R.id.removeWatchlistButton);

        getIncomingIntent();
        shareToFacebook();
        shareToTwitter();
        addToWatchlist();
        removeFromWatchlist();

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        filmDataService.getDetails(id, category, new FilmDataService.FilmResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(DetailsActivity.this, "something wrong with details", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<FilmReportModel> filmReportModels) {
                findViewById(R.id.loadingDetails).setVisibility(View.GONE);
                findViewById(R.id.loadingDetailsText).setVisibility(View.GONE);
                findViewById(R.id.details_image_description).setVisibility(View.VISIBLE);
                findViewById(R.id.overviewTitle).setVisibility(View.VISIBLE);
                findViewById(R.id.overviewText).setVisibility(View.VISIBLE);
                findViewById(R.id.genresTitle).setVisibility(View.VISIBLE);
                findViewById(R.id.genresText).setVisibility(View.VISIBLE);
                findViewById(R.id.yearTitle).setVisibility(View.VISIBLE);
                findViewById(R.id.yearText).setVisibility(View.VISIBLE);
                findViewById(R.id.sharingDetails).setVisibility(View.VISIBLE);
                findViewById(R.id.castTitle).setVisibility(View.VISIBLE);
                findViewById(R.id.castRecyclerView).setVisibility(View.VISIBLE);
                findViewById(R.id.reviewTitle).setVisibility(View.VISIBLE);
                findViewById(R.id.reviews).setVisibility(View.VISIBLE);
                findViewById(R.id.recommended).setVisibility(View.VISIBLE);

                FilmReportModel firstElement = filmReportModels.get(0);

                String backdropPath = firstElement.getBackdropPath();
                setImage(id, title, backdropPath);
                loadData();

                for (FilmReportModel f : savedData) {
                    if (f.getId() == id && f.getTitle().equals(title) && f.getCategory().equals(category)) {
                        addWatchlist.setVisibility(View.INVISIBLE);
                        removeWatchlist.setVisibility(View.VISIBLE);
                    }
                }

                String videoId = firstElement.getVideoKey();
                setYoutubeVideo(videoId, youTubePlayerView);

                String overview = firstElement.getOverview();
                setOverview(overview);

                String genres = firstElement.getGenres();
                setGenres(genres);

                String releaseDate = firstElement.getReleaseDate();
                setReleaseDate(releaseDate);

                List<CastModel> castModels = firstElement.getCastModels();
                validateAndCreateGridLayout(castModels);

                List<ReviewModel> reviewModels = firstElement.getReviewModels();
                validateAndCreateReviews(reviewModels);

                List<SliderData> recommendedPicks = firstElement.getRecommendedItems();
                validateAndCreateRecommended(recommendedPicks);

            }
        });

    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("image_url")
            && getIntent().hasExtra("category")) {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            int id = getIntent().getIntExtra("id", 0);
            String posterPath = getIntent().getStringExtra("image_url");
            String posterPathWatchlist = null;
            if (getIntent().hasExtra("poster_path")) {
                posterPathWatchlist = getIntent().getStringExtra("poster_path");
            }
            String title = getIntent().getStringExtra("title");
            String category = getIntent().getStringExtra("category");
            this.id = id;
            this.title = title;
            this.category = category;
            this.posterPath = posterPath;
            this.posterPathWatchlist = posterPathWatchlist;

        }
    }

    private void setImage(int id, String title, String backdropPath) {
        Log.d(TAG, "setImage: " + backdropPath);

        TextView name = findViewById(R.id.details_image_description);
        if (title.length() > 31) {
            name.setGravity(Gravity.CENTER);
        }
        name.setText(title);

        ImageView image = findViewById(R.id.details_image);
        if (!backdropPath.contains("null")) {
            Glide.with(this)
                    .asBitmap()
                    .load(backdropPath)
                    .into(image);
        }
    }

    private void setYoutubeVideo(String videoId, YouTubePlayerView youTubePlayerView) {
        if (!videoId.equals("")) {
            findViewById(R.id.details_image).setVisibility(View.GONE);
            findViewById(R.id.youtube_player_view).setVisibility(View.VISIBLE);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(videoId, 0);
                }
            });
        } else {
            findViewById(R.id.details_image).setVisibility(View.VISIBLE);
        }
    }

    private void setOverview(String overview) {
        if (overview != null) {
            if (!overview.equals("")) {
                ReadMoreTextView overviewText = findViewById(R.id.overviewText);
                overviewText.setText(overview);
            } else {
                findViewById(R.id.overviewTitle).setVisibility(View.GONE);
                findViewById(R.id.overviewText).setVisibility(View.GONE);
            }
        } else {
            findViewById(R.id.overviewTitle).setVisibility(View.GONE);
            findViewById(R.id.overviewText).setVisibility(View.GONE);
        }
    }

    private void setGenres(String genres) {
        if (genres != null) {
            if (!genres.equals("")) {
                TextView genresText = findViewById(R.id.genresText);
                genresText.setText(genres);
            } else {
                findViewById(R.id.genresTitle).setVisibility(View.GONE);
                findViewById(R.id.genresText).setVisibility(View.GONE);
            }
        } else {
            findViewById(R.id.genresTitle).setVisibility(View.GONE);
            findViewById(R.id.genresText).setVisibility(View.GONE);
        }
    }

    private void setReleaseDate(String releaseDate) {
        if (releaseDate != null) {
            if (!releaseDate.equals("")) {
                TextView genresText = findViewById(R.id.yearText);
                genresText.setText(releaseDate);
            } else {
                findViewById(R.id.yearTitle).setVisibility(View.GONE);
                findViewById(R.id.yearText).setVisibility(View.GONE);
            }
        } else {
            findViewById(R.id.yearTitle).setVisibility(View.GONE);
            findViewById(R.id.yearText).setVisibility(View.GONE);
        }
    }

    private void shareToFacebook() {
        facebookButton = findViewById(R.id.facebookButton);

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facebookUrl = "https://www.facebook.com/sharer/sharer.php?u=https://www.themoviedb.org/" + category + "/" + id;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(facebookUrl));
                DetailsActivity.this.startActivity(intent);
            }
        });

    }

    private void shareToTwitter() {
        twitterButton = findViewById(R.id.twitterButton);

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String twitterUrl = "http://www.twitter.com/intent/tweet?text=Check this out!%0Ahttps://www.themoviedb.org/" + category + "/" + id;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(twitterUrl));
                DetailsActivity.this.startActivity(intent);
            }
        });
    }

    private void validateAndCreateGridLayout(List<CastModel> castModels) {
        if (castModels == null || castModels.size() == 0) {
            // remove castImage and castText
            findViewById(R.id.castTitle).setVisibility(View.GONE);
            findViewById(R.id.castList).setVisibility(View.GONE);
        } else {
            castRecyclerViewAdapter = new CastRecyclerViewAdapter(DetailsActivity.this, castModels);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(DetailsActivity.this, 3, GridLayoutManager.VERTICAL, false);
            castList.setLayoutManager(gridLayoutManager);
            castList.setAdapter(castRecyclerViewAdapter);
        }
    }

    private void validateAndCreateReviews(List<ReviewModel> reviewModels) {
        if (reviewModels == null || reviewModels.size() == 0) {
            // remove castImage and castText
            findViewById(R.id.reviewTitle).setVisibility(View.GONE);
            findViewById(R.id.reviewList).setVisibility(View.GONE);
        } else {
            reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(DetailsActivity.this, reviewModels);

            LinearLayoutManager layoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.VERTICAL, false);
            reviewList.setLayoutManager(layoutManager);
            reviewList.setAdapter(reviewRecyclerViewAdapter);
        }
    }

    private void validateAndCreateRecommended(List<SliderData> recommendedPicks) {
        if (recommendedPicks == null || recommendedPicks.size() == 0) {
            // remove recommended title and recommendedPicks
            findViewById(R.id.recommendedTitle).setVisibility(View.GONE);
            findViewById(R.id.recommendedPicks).setVisibility(View.GONE);
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerView = findViewById(R.id.recommendedPicks);
            recyclerView.setLayoutManager(layoutManager);
            RecommendedRecyclerViewAdapter recommendedRecyclerViewAdapter = new RecommendedRecyclerViewAdapter(DetailsActivity.this, recommendedPicks);
            recyclerView.setAdapter(recommendedRecyclerViewAdapter);
        }
    }

    private void addToWatchlist() {
        Log.d(TAG, "Called add to watchlist for " + title);

        addWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get data from details and add to shared prefs
                addWatchlist.setVisibility(View.INVISIBLE);
                removeWatchlist.setVisibility(View.VISIBLE);
                showToast(title + " was added to Watchlist");

                FilmReportModel filmReportModel = new FilmReportModel();
                filmReportModel.setId(id);
                filmReportModel.setTitle(title);
                Log.d(TAG, "posterPath is: " + posterPath + " watchlist is: " + posterPathWatchlist);
                if (posterPath != null)
                    filmReportModel.setPosterPath(posterPath);
                else if (posterPathWatchlist != null)
                    filmReportModel.setPosterPath(posterPathWatchlist);
                filmReportModel.setCategory(category);
                savedData.add(filmReportModel);
                saveData();
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
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
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("watchlist", null);
        Type type = new TypeToken<ArrayList<FilmReportModel>>() {}.getType();
        savedData = gson.fromJson(json, type);

        if (savedData == null) {
            savedData = new ArrayList<>();
        }
    }

    private void removeFromWatchlist() {
        Log.d(TAG, "Called remove from for " + title);

        removeWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get data from details and add to shared prefs
                removeWatchlist.setVisibility(View.INVISIBLE);
                addWatchlist.setVisibility(View.VISIBLE);
                showToast(title + " was removed from Watchlist");

                FilmReportModel filmReportModel = new FilmReportModel();
                filmReportModel.setId(id);
                filmReportModel.setTitle(title);
                Log.d(TAG, "posterPath is: " + posterPath + " watchlist is: " + posterPathWatchlist);
                if (posterPath != null)
                    filmReportModel.setPosterPath(posterPath);
                else if (posterPathWatchlist != null)
                    filmReportModel.setPosterPath(posterPathWatchlist);
                filmReportModel.setCategory(category);

                deleteData(filmReportModel);
            }
        });
    }

    void showToast(String text) {
        if (m_currentToast != null) {
            m_currentToast.cancel();
        }
        m_currentToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        m_currentToast.show();
    }
}
