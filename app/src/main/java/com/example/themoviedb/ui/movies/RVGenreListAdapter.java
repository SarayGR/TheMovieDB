package com.example.themoviedb.ui.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;
import com.example.themoviedb.model.GenreDTO;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class RVGenreListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context ctx;
    private List<GenreDTO> itemList;
    private Boolean anyPending;

    public RVGenreListAdapter(List<GenreDTO> itemList, Context ctx) {
        this.ctx = ctx;
        this.itemList = itemList;
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


    public static class GenresListViewHolder extends RecyclerView.ViewHolder {
        TextView tvGenreName;

        public GenresListViewHolder(View itemView) {
            super(itemView);
            tvGenreName = (TextView) itemView.findViewById(R.id.titleTextView);
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
