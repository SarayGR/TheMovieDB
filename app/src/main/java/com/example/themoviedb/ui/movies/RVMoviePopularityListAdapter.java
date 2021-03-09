package com.example.themoviedb.ui.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;
import com.example.themoviedb.model.DiscoverMovieDTO;
import com.example.themoviedb.model.GenreDTO;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class RVMoviePopularityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context ctx;
    private List<DiscoverMovieDTO> itemList;

    public RVMoviePopularityListAdapter(List<DiscoverMovieDTO> itemList, Context ctx) {
        this.ctx = ctx;
        this.itemList = itemList;
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
        if (item.getTitle() != null) {
            holder.tvTitle.setText(item.getTitle());
        }

    }

    /*@Override
    public int getItemViewType(int position) {
        if (position == (INT_ZERO)) return INT_ZERO;
        else return INT_ONE;
    }*/

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

        public MoviesPopularityListViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titleTextView);
            ivMovieImage = itemView.findViewById(R.id.imageView);

        }
    }

    /*public static class PayViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llInvoicePaymentInfo;
        ConstraintLayout clInvoiceListPayment;
        ConstraintLayout clInvoiceListFirstItemParent;

        public PayViewHolder(View itemView) {
            super(itemView);
            llInvoicePaymentInfo = (LinearLayout) itemView.findViewById(R.id.llInvoicePaymentInfo);
            clInvoiceListPayment = (ConstraintLayout) itemView.findViewById(R.id.clInvoiceListPayment);
            clInvoiceListFirstItemParent = (ConstraintLayout) itemView.findViewById(R.id.clInvoiceListFirstItemParent);
        }
    }*/
}
