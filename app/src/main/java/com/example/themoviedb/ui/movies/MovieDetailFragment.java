package com.example.themoviedb.ui.movies;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;
import com.example.themoviedb.api.ApiAdapter;
import com.example.themoviedb.model.ConfigurationApiDTO;
import com.example.themoviedb.model.DiscoverMovieDTO;
import com.example.themoviedb.model.DiscoverMovieResponseDTO;
import com.example.themoviedb.model.GenreDTO;
import com.example.themoviedb.model.GenreListDTO;
import com.example.themoviedb.model.ImageDTO;
import com.example.themoviedb.ui.utils.Dialogs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.themoviedb.ui.utils.StringConstants.ES;
import static com.example.themoviedb.ui.utils.StringConstants.FALSE;
import static com.example.themoviedb.ui.utils.StringConstants.STRING_ONE;
import static com.example.themoviedb.ui.utils.StringConstants.TRUE;
import static com.example.themoviedb.ui.utils.StringConstants.es_ES;

public class MovieDetailFragment extends Fragment {

    TextView tvMovieDetailTitle, tvMovieDetailDate, tvMovieDetailGenres, tvMovieDetailDescriptionValue, tvMovieDetailScoreValue;
    DiscoverMovieDTO movieDTO;
    ImageView ivMovieDetailPhoto;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieDTO = (DiscoverMovieDTO) getArguments().getSerializable("Movie");
        }
    }

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        //
        tvMovieDetailTitle = view.findViewById(R.id.tvMovieDetailTitle);
        tvMovieDetailDate = view.findViewById(R.id.tvMovieDetailDate);
        tvMovieDetailGenres = view.findViewById(R.id.tvMovieDetailGenres);
        tvMovieDetailDescriptionValue = view.findViewById(R.id.tvMovieDetailDescriptionValue);
        tvMovieDetailScoreValue = view.findViewById(R.id.tvMovieDetailScoreValue);
        ivMovieDetailPhoto = view.findViewById(R.id.ivMovieDetailPhoto);
        //
        tvMovieDetailTitle.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        setData();
        return view;
    }

    public MovieDetailFragment newInstance(DiscoverMovieDTO movieDTO) {
        MovieDetailFragment f = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("Movie", movieDTO);
        f.setArguments(args);
        return f;
    }

    public void setData() {
        tvMovieDetailTitle.setText(movieDTO.getTitle());
        tvMovieDetailDate.setText(movieDTO.getReleaseDate());
        tvMovieDetailDescriptionValue.setText(movieDTO.getOverview());
        tvMovieDetailScoreValue.setText(movieDTO.getVoteAverage().toString());
    }

}