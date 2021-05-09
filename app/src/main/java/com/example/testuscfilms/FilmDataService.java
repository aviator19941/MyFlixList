package com.example.testuscfilms;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.slider.Slider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FilmDataService {
    public static final String NOW_PLAYING_URL = "https://usc-films-android.uc.r.appspot.com/api/nowPlaying";
    public static final String TOP_RATED_MOVIES_URL = "https://usc-films-android.uc.r.appspot.com/api/topRatedMovies";
    public static final String POPULAR_MOVIES_URL = "https://usc-films-android.uc.r.appspot.com/api/popularMovies";
    public static final String TRENDING_TV_URL = "https://usc-films-android.uc.r.appspot.com/api/trendingTv";
    public static final String TOP_RATED_TV_URL = "https://usc-films-android.uc.r.appspot.com/api/topRatedTV";
    public static final String POPULAR_TV_URL = "https://usc-films-android.uc.r.appspot.com/api/popularTV";
    public static final String DETAILS_URL = "https://usc-films-android.uc.r.appspot.com/api/details";
    public static final String MULTI_SEARCH_URL = "https://usc-films-android.uc.r.appspot.com/api/multiSearch";
    private static final String TAG = "FilmDataService";

    Context context;

    public FilmDataService(Context context) {
        this.context = context;
    }

    public interface FilmResponseListener {
        void onError(String message);

        void onResponse(List<FilmReportModel> filmReportModels);
    }

    public void getNowPlaying(FilmResponseListener filmResponseListener) {
        List<FilmReportModel> filmReportModels = new ArrayList<>();

        String url = NOW_PLAYING_URL;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray nowPlayingMovies = response.getJSONArray("results");

                    for (int i = 0; i < nowPlayingMovies.length(); i++) {
                        FilmReportModel firstElement = new FilmReportModel();
                        StringBuilder builder = new StringBuilder();

                        JSONObject firstElementFromAPI = (JSONObject) nowPlayingMovies.get(i);

                        firstElement.setId(firstElementFromAPI.getInt("id"));
                        firstElement.setTitle(firstElementFromAPI.getString("title"));
                        builder.append("https://image.tmdb.org/t/p/w500");
                        builder.append(firstElementFromAPI.getString("poster_path"));
                        firstElement.setPosterPath(builder.toString());

                        filmReportModels.add(firstElement);
                    }

                    filmResponseListener.onResponse(filmReportModels);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error fetching now playing movies data occurred.", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getTopRatedMovies(FilmResponseListener filmResponseListener) {
        List<FilmReportModel> filmReportModels = new ArrayList<>();

        String url = TOP_RATED_MOVIES_URL;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray topRatedMovies = response.getJSONArray("topRatedMovies");

                    for (int i = 0; i < topRatedMovies.length(); i++) {
                        FilmReportModel firstElement = new FilmReportModel();

                        JSONObject firstElementFromAPI = (JSONObject) topRatedMovies.get(i);

                        firstElement.setId(firstElementFromAPI.getInt("id"));
                        firstElement.setTitle(firstElementFromAPI.getString("title"));
                        firstElement.setPosterPath(firstElementFromAPI.getString("poster_path"));

                        filmReportModels.add(firstElement);
                    }

                    filmResponseListener.onResponse(filmReportModels);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error fetching top rated movies data occurred.", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getPopularMovies(FilmResponseListener filmResponseListener) {
        List<FilmReportModel> filmReportModels = new ArrayList<>();

        String url = POPULAR_MOVIES_URL;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray popularMovies = response.getJSONArray("popularMovies");

                    for (int i = 0; i < popularMovies.length(); i++) {
                        FilmReportModel firstElement = new FilmReportModel();

                        JSONObject firstElementFromAPI = (JSONObject) popularMovies.get(i);

                        firstElement.setId(firstElementFromAPI.getInt("id"));
                        firstElement.setTitle(firstElementFromAPI.getString("title"));
                        firstElement.setPosterPath(firstElementFromAPI.getString("poster_path"));

                        filmReportModels.add(firstElement);
                    }

                    filmResponseListener.onResponse(filmReportModels);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error fetching popular movies data occurred.", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    // TRENDING TV SHOWS
    public void getTrendingTVShows(FilmResponseListener filmResponseListener) {
        List<FilmReportModel> filmReportModels = new ArrayList<>();

        String url = TRENDING_TV_URL;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray trendingTV = response.getJSONArray("trendingTV");

                    for (int i = 0; i < trendingTV.length(); i++) {
                        FilmReportModel firstElement = new FilmReportModel();

                        JSONObject firstElementFromAPI = (JSONObject) trendingTV.get(i);

                        firstElement.setId(firstElementFromAPI.getInt("id"));
                        firstElement.setTitle(firstElementFromAPI.getString("title"));
                        firstElement.setPosterPath(firstElementFromAPI.getString("poster_path"));

                        filmReportModels.add(firstElement);
                    }

                    filmResponseListener.onResponse(filmReportModels);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error fetching trending TV data occurred.", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    // TOP RATED TV SHOWS
    public void getTopRatedTVShows(FilmResponseListener filmResponseListener) {
        List<FilmReportModel> filmReportModels = new ArrayList<>();

        String url = TOP_RATED_TV_URL;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray topRatedTV = response.getJSONArray("topRatedTV");

                    for (int i = 0; i < topRatedTV.length(); i++) {
                        FilmReportModel firstElement = new FilmReportModel();

                        JSONObject firstElementFromAPI = (JSONObject) topRatedTV.get(i);

                        firstElement.setId(firstElementFromAPI.getInt("id"));
                        firstElement.setTitle(firstElementFromAPI.getString("title"));
                        firstElement.setPosterPath(firstElementFromAPI.getString("poster_path"));

                        filmReportModels.add(firstElement);
                    }

                    filmResponseListener.onResponse(filmReportModels);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error fetching top rated TV data occurred.", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    // POPULAR TV SHOWS
    public void getPopularTVShows(FilmResponseListener filmResponseListener) {
        List<FilmReportModel> filmReportModels = new ArrayList<>();

        String url = POPULAR_TV_URL;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray popularTV = response.getJSONArray("popularTV");

                    for (int i = 0; i < popularTV.length(); i++) {
                        FilmReportModel firstElement = new FilmReportModel();

                        JSONObject firstElementFromAPI = (JSONObject) popularTV.get(i);

                        firstElement.setId(firstElementFromAPI.getInt("id"));
                        firstElement.setTitle(firstElementFromAPI.getString("title"));
                        firstElement.setPosterPath(firstElementFromAPI.getString("poster_path"));

                        filmReportModels.add(firstElement);
                    }

                    filmResponseListener.onResponse(filmReportModels);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error fetching popular TV data occurred.", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    // DETAILS MOVIE OR TV SHOW
    public void getDetails(int id, String category, FilmResponseListener filmResponseListener) {
        List<FilmReportModel> filmReportModels = new ArrayList<>();
        List<CastModel> castModels = new ArrayList<>();
        List<ReviewModel> reviewModels = new ArrayList<>();
        List<SliderData> recommendedPicks = new ArrayList<>();

        String url = DETAILS_URL + "?category=" + category + "&id=" + id;
        Log.d(TAG, url);

        if (category.equals("movie")) {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject detailsData = response.getJSONObject("detailsData");
                        String overview = detailsData.getString("overview");
                        String backdropPath = detailsData.getString("backdrop_path");
                        String poster = detailsData.getString("poster_path");

                        JSONObject video = response.getJSONObject("video");
                        String key = video.getString("key");
                        JSONArray genres = detailsData.getJSONArray("genres");
                        String releaseDate = detailsData.getString("release_date");
                        StringBuilder builder = new StringBuilder();
                        JSONArray cast = response.getJSONArray("cast");
                        JSONArray reviews = response.getJSONArray("reviews");
                        JSONArray recommended = response.getJSONArray("recommended");

                        for (int i = 0; i < genres.length(); i++) {
                            JSONObject curGenre = genres.getJSONObject(i);
                            String curGenreString = curGenre.getString("name");
                            if (i == genres.length() - 1) {
                                builder.append(curGenreString);
                            } else {
                                builder.append(curGenreString + ", ");
                            }
                        }

                        for (int i = 0; i < cast.length(); i++) {
                            JSONObject curCast = cast.getJSONObject(i);
                            CastModel firstCast = new CastModel();
                            String curCastName = curCast.getString("name");
                            String curCastProfilePath = curCast.getString("profile_path");

                            firstCast.setCastName(curCastName);
                            firstCast.setProfilePath(curCastProfilePath);
                            castModels.add(firstCast);
                        }

                        for (int i = 0; i < reviews.length(); i++) {
                            JSONObject curReview = reviews.getJSONObject(i);
                            ReviewModel firstReview = new ReviewModel();
                            String author = curReview.getString("author");
                            float rating = (float) curReview.getDouble("rating") / 2;
                            String content = curReview.getString("content");
                            String createdAt = curReview.getString("created_at");

                            firstReview.setAuthor(author);
                            firstReview.setRating(rating);
                            firstReview.setContent(content);
                            firstReview.setCreatedAt(createdAt);
                            reviewModels.add(firstReview);
                        }

                        for (int i = 0; i < recommended.length(); i++) {
                            JSONObject curRec = recommended.getJSONObject(i);
                            SliderData firstRec = new SliderData();
                            int id = curRec.getInt("id");
                            String title = curRec.getString("title");
                            String posterPath = curRec.getString("poster_path");
                            String category = curRec.getString("category");

                            firstRec.setId(id);
                            firstRec.setTitle(title);
                            firstRec.setImgUrl(posterPath);
                            firstRec.setCategory(category);
                            recommendedPicks.add(firstRec);
                        }

                        FilmReportModel firstElement = new FilmReportModel();

                        firstElement.setVideoKey(key);
                        firstElement.setOverview(overview);
                        firstElement.setGenres(builder.toString());
                        firstElement.setReleaseDate(releaseDate);
                        firstElement.setBackdropPath(backdropPath);
                        firstElement.setPosterPath(poster);
                        firstElement.setCastModels(castModels);
                        firstElement.setReviewModels(reviewModels);
                        firstElement.setRecommendedItems(recommendedPicks);

                        filmReportModels.add(firstElement);

                        filmResponseListener.onResponse(filmReportModels);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error fetching details data for id " + id + " and category " + category + " occurred.", Toast.LENGTH_SHORT).show();
                }
            });

            MySingleton.getInstance(context).addToRequestQueue(request);
        } else if (category.equals("tv")) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject detailsData = response.getJSONObject("detailsData");
                        String overview = detailsData.getString("overview");
                        String backdropPath = detailsData.getString("backdrop_path");
                        String poster = detailsData.getString("poster_path");

                        JSONObject video = response.getJSONObject("video");
                        String key = video.getString("key");
                        JSONArray genres = detailsData.getJSONArray("genres");
                        String releaseDate = detailsData.getString("first_air_date");
                        StringBuilder builder = new StringBuilder();
                        JSONArray cast = response.getJSONArray("cast");
                        JSONArray reviews = response.getJSONArray("reviews");
                        JSONArray recommended = response.getJSONArray("recommended");

                        for (int i = 0; i < genres.length(); i++) {
                            JSONObject curGenre = genres.getJSONObject(i);
                            String curGenreString = curGenre.getString("name");
                            if (i == genres.length() - 1) {
                                builder.append(curGenreString);
                            } else {
                                builder.append(curGenreString + ", ");
                            }
                        }

                        for (int i = 0; i < cast.length(); i++) {
                            JSONObject curCast = cast.getJSONObject(i);
                            CastModel firstCast = new CastModel();
                            String curCastName = curCast.getString("name");
                            String curCastProfilePath = curCast.getString("profile_path");

                            firstCast.setCastName(curCastName);
                            firstCast.setProfilePath(curCastProfilePath);
                            castModels.add(firstCast);
                        }

                        for (int i = 0; i < reviews.length(); i++) {
                            JSONObject curReview = reviews.getJSONObject(i);
                            ReviewModel firstReview = new ReviewModel();
                            String author = curReview.getString("author");
                            float rating = (float) curReview.getDouble("rating") / 2;
                            String content = curReview.getString("content");
                            String createdAt = curReview.getString("created_at");

                            firstReview.setAuthor(author);
                            firstReview.setRating(rating);
                            firstReview.setContent(content);
                            firstReview.setCreatedAt(createdAt);
                            reviewModels.add(firstReview);
                        }

                        for (int i = 0; i < recommended.length(); i++) {
                            JSONObject curRec = recommended.getJSONObject(i);
                            SliderData firstRec = new SliderData();
                            int id = curRec.getInt("id");
                            String title = curRec.getString("title");
                            String posterPath = curRec.getString("poster_path");
                            String category = curRec.getString("category");

                            firstRec.setId(id);
                            firstRec.setTitle(title);
                            firstRec.setImgUrl(posterPath);
                            firstRec.setCategory(category);
                            recommendedPicks.add(firstRec);
                        }

                        FilmReportModel firstElement = new FilmReportModel();

                        firstElement.setVideoKey(key);
                        firstElement.setOverview(overview);
                        firstElement.setGenres(builder.toString());
                        firstElement.setReleaseDate(releaseDate);
                        firstElement.setBackdropPath(backdropPath);
                        firstElement.setPosterPath(poster);
                        firstElement.setCastModels(castModels);
                        firstElement.setReviewModels(reviewModels);
                        firstElement.setRecommendedItems(recommendedPicks);

                        filmReportModels.add(firstElement);

                        filmResponseListener.onResponse(filmReportModels);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error fetching details data for id " + id + " and category " + category + " occurred.", Toast.LENGTH_SHORT).show();
                }
            });

            MySingleton.getInstance(context).addToRequestQueue(request);
        }
    }

    // MULTI SEARCH
    public void getMultiSearchResults(String query, FilmResponseListener filmResponseListener) {
        List<FilmReportModel> searchList = new ArrayList<>();

        String encodedQuery = query.replaceAll(" ", "%20");
        String url = MULTI_SEARCH_URL + "?query=" + encodedQuery;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray searchResults = response.getJSONArray("results");

                    for (int i = 0; i < searchResults.length(); i++) {
                        JSONObject curSearchResult = searchResults.getJSONObject(i);
                        FilmReportModel filmReportModel = new FilmReportModel();
                        int id = curSearchResult.getInt("id");
                        String title = curSearchResult.getString("name");
                        String category = curSearchResult.getString("media_type");
                        String backdropPath = curSearchResult.getString("backdrop_path");
                        String posterPath = curSearchResult.getString("poster_path");
                        float rating = (float) curSearchResult.getDouble("vote_average")/2;
                        String year = curSearchResult.getString("release_date");

                        filmReportModel.setId(id);
                        filmReportModel.setTitle(title);
                        filmReportModel.setCategory(category);
                        filmReportModel.setBackdropPath(backdropPath);
                        filmReportModel.setPosterPath(posterPath);
                        filmReportModel.setRating(rating);
                        filmReportModel.setReleaseDate(year);

                        searchList.add(filmReportModel);
                    }

                    filmResponseListener.onResponse(searchList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Error fetching search data for query " + encodedQuery + " occurred.", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
