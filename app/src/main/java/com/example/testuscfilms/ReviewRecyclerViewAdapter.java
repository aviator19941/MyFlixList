package com.example.testuscfilms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder> {
    List<ReviewModel> reviewModels;
    LayoutInflater inflater;
    Context context;

    public ReviewRecyclerViewAdapter(Context context, List<ReviewModel> reviewModels) {
        this.context = context;
        this.reviewModels = reviewModels;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.review_layout, parent, false);
        return new ReviewRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewModel reviewModel = reviewModels.get(position);
        DateFormat df = new SimpleDateFormat("E, MMM dd yyyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

        Date date = null;
        try {
            date = inputFormat.parse(reviewModel.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = df.format(date);

        String authorDate = "by " + reviewModel.getAuthor() + " on " + convertedDate;
        String rating = reviewModel.getRating() + "/5";
        String content = reviewModel.getContent();

        holder.reviewAuthorDateTextView.setText(authorDate);
        holder.reviewRatingTextView.setText(rating);
        holder.reviewContentTextView.setText(content);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReviewActivity.class);
                intent.putExtra("author_date", authorDate);
                intent.putExtra("rating", rating);
                intent.putExtra("content", content);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviewModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewAuthorDateTextView;
        TextView reviewRatingTextView;
        TextView reviewContentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewAuthorDateTextView = itemView.findViewById(R.id.reviewAuthorDate);
            reviewRatingTextView = itemView.findViewById(R.id.reviewRating);
            reviewContentTextView = itemView.findViewById(R.id.reviewContent);
        }

    }
}
