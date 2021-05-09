package com.example.testuscfilms;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {
    private static final String TAG = "ReviewActivity";

    private String authorDate;
    private String rating;
    private String content;
    TextView authorDateTextView;
    TextView ratingTextView;
    TextView contentTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.detailsTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Log.d(TAG, "onCreate: review started");
    
        getIncomingIntent();

        ratingTextView = findViewById(R.id.reviewActivityRating);
        ratingTextView.setText(rating);

        authorDateTextView = findViewById(R.id.reviewActivityAuthorDate);
        authorDateTextView.setText(authorDate);

        contentTextView = findViewById(R.id.reviewActivityContent);
        contentTextView.setText(content);
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntentReview: checking for incoming intents.");
        if (getIntent().hasExtra("author_date") && getIntent().hasExtra("rating") && getIntent().hasExtra("content")) {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String authorDate = getIntent().getStringExtra("author_date");
            String rating = getIntent().getStringExtra("rating");
            String content = getIntent().getStringExtra("content");
            this.authorDate = authorDate;
            this.rating = rating;
            this.content = content;

        }
    }
}
