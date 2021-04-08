package com.example.themoviedb.ui.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;
import com.example.themoviedb.model.GenreDTO;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class RVGenreListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context ctx;
    private List<GenreDTO> itemList;
    private RVGenreListAdapter.CustomGenreClick listener;

    public RVGenreListAdapter(List<GenreDTO> itemList, Context ctx, RVGenreListAdapter.CustomGenreClick listener) {
        this.ctx = ctx;
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GenresListViewHolder genresListViewHolder = null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        genresListViewHolder = new GenresListViewHolder(v);
        return genresListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        GenresListViewHolder holder = (GenresListViewHolder) viewHolder;
        GenreDTO item = itemList.get(position);
        if (item.getName() != null) {
            holder.tvGenreName.setText(item.getName());
            holder.cvGenre.setCardBackgroundColor(ctx.getResources().getColor(R.color.green_A5));
        }

        ((GenresListViewHolder) viewHolder).cvGenre.setOnClickListener(v -> {
            onClick(itemList.get(position));
        });

    }

    public void onClick(GenreDTO item) {
        if (listener != null) {
            listener.onGenreClick(item);
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


    public static class GenresListViewHolder extends RecyclerView.ViewHolder {
        TextView tvGenreName;
        CardView cvGenre;

        public GenresListViewHolder(View itemView) {
            super(itemView);
            tvGenreName = (TextView) itemView.findViewById(R.id.titleTextView);
            cvGenre = itemView.findViewById(R.id.cardView);
        }
    }

    public interface CustomGenreClick {
        void onGenreClick(GenreDTO item);
    }
}
