package com.example.themoviedb.ui.movies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;
import com.example.themoviedb.model.DiscoverMovieDTO;
import com.example.themoviedb.model.ImageDTO;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class RVMoviePopularityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context ctx;
    private List<DiscoverMovieDTO> itemList;
    private ImageDTO imageDTO;
    private RVMoviePopularityListAdapter.CustomMovieClick listener;

    public RVMoviePopularityListAdapter(List<DiscoverMovieDTO> itemList, Context ctx, ImageDTO imageDTO, RVMoviePopularityListAdapter.CustomMovieClick listener) {
        this.ctx = ctx;
        this.itemList = itemList;
        this.imageDTO = imageDTO;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MoviesPopularityListViewHolder moviesPopularityListViewHolder = null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        moviesPopularityListViewHolder = new MoviesPopularityListViewHolder(v);
        return moviesPopularityListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        MoviesPopularityListViewHolder holder = (MoviesPopularityListViewHolder) viewHolder;
        DiscoverMovieDTO item = itemList.get(position);
        if (item.getTitle() != null && imageDTO != null) {
            holder.tvTitle.setText(item.getTitle());

            String baseUrl = imageDTO.getSecureBaseUrl();
            String size = imageDTO.getPosterSizes().get(3);
            String filePath = item.getPosterPath();
            String url = baseUrl + size + filePath;

            Log.d("URL", url.toString());
            holder.ivMovieImage.setTag(url);
            new DownloadImagesTask().execute(holder.ivMovieImage);
        }
        ((MoviesPopularityListViewHolder) viewHolder).cardView.setOnClickListener(v -> {
            onClick(itemList.get(position));
        });

    }

    public void onClick(DiscoverMovieDTO item) {
        if (listener != null) {
            listener.onMovieClick(item);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class MoviesPopularityListViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivMovieImage;
        CardView cardView;

        public MoviesPopularityListViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titleTextView);
            ivMovieImage = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

    public class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {
        ImageView imageView = null;

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            this.imageView = imageViews[0];
            return download_Image((String) imageView.getTag());
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

        private Bitmap download_Image(String url) {
            Bitmap bmp = null;
            try {
                URL ulrn = new URL(url);
                HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
                InputStream is = con.getInputStream();
                bmp = BitmapFactory.decodeStream(is);
                if (null != bmp) return bmp;
            } catch (Exception e) {
                Log.d("ERROR", e.getMessage());
            }
            return bmp;
        }

    }

    public interface CustomMovieClick {
        void onMovieClick(DiscoverMovieDTO item);
    }
}
