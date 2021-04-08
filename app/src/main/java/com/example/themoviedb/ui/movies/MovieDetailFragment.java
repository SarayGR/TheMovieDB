package com.example.themoviedb.ui.movies;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.themoviedb.R;
import com.example.themoviedb.api.ApiAdapter;
import com.example.themoviedb.model.DiscoverMovieDTO;
import com.example.themoviedb.model.GenreDTO;
import com.example.themoviedb.model.GenreListDTO;
import com.example.themoviedb.model.VideoDTO;
import com.example.themoviedb.model.ResultVideosDTO;
import com.example.themoviedb.ui.utils.Dialogs;

import org.json.JSONException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailFragment extends Fragment {

    TextView tvMovieDetailTitle, tvMovieDetailDate, tvMovieDetailGenres, tvMovieDetailDescriptionValue, tvMovieDetailScoreValue;
    DiscoverMovieDTO movieDTO;
    String url;
    ImageView ivMovieDetailPhoto;
    View view;
    GenreListDTO genreListDTO;
    Button btnPlayTrailer;
    ResultVideosDTO trailer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieDTO = (DiscoverMovieDTO) getArguments().getSerializable("Movie");
            url = getArguments().getString("ImageUrl");
            genreListDTO = (GenreListDTO) getArguments().getSerializable("GenreListDto");
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

        btnPlayTrailer = view.findViewById(R.id.btnPlayTrailer);
        //
        tvMovieDetailTitle.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        try {
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        callMovieTrailers();

        btnPlayTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTrailer();
            }
        });

        return view;
    }

    public MovieDetailFragment newInstance(DiscoverMovieDTO movieDTO, String url, GenreListDTO genreListDTO) {
        MovieDetailFragment f = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("Movie", movieDTO);
        args.putString("ImageUrl", url);
        args.putSerializable("GenreListDto", genreListDTO);
        f.setArguments(args);
        return f;
    }

    public void setData() throws JSONException {
        tvMovieDetailTitle.setText(movieDTO.getTitle());
        //Set release date
        String[] dateSplit = movieDTO.getReleaseDate().split("-");
        String month = dateSplit[1];
        String year = dateSplit[0];
        String day = dateSplit[2];
        tvMovieDetailDate.setText(day + " / " + month + " / " + year);
        //
        List<GenreDTO> genres = genreListDTO.getGenres();
        List<Integer> genreIds = movieDTO.getGenreIds();
        String genresNames = "";
        for (int i = 0; i < genres.size(); i++){
            for (int j = 0; j < genreIds.size(); j++) {
                if (genreIds.get(j).intValue() == genres.get(i).getId()) {
                    genresNames += genres.get(i).getName() +", ";
                }
            }
        }
        String cadena = getString(R.string.movie_detail_genres, genresNames);
        tvMovieDetailGenres.setText(cadena.substring(0, cadena.length()-2));
        tvMovieDetailDescriptionValue.setText(movieDTO.getOverview());
        tvMovieDetailScoreValue.setText(movieDTO.getVoteAverage().toString());
        setImage();
    }

    public void setImage() {
        ivMovieDetailPhoto.setTag(url);
        new RVMoviePopularityListAdapter.DownloadImagesTask().execute(ivMovieDetailPhoto);
    }

    public void playTrailer() {
        String youtubeVideoId = trailer.getKey(); //Id video.
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeVideoId));
        try {
            startActivity(appIntent);  //Abre con aplicación.
        } catch (ActivityNotFoundException ex) {
            //En caso de no existir la aplicación instalada se abre mediante el navegador.
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + youtubeVideoId));
            startActivity(webIntent);
        }
    }

    public void callMovieTrailers() {
        Call<VideoDTO> call = ApiAdapter.getApiService().getMovieTrailers(movieDTO.getId().toString(), getString(R.string.api_key));
        call.enqueue(new Callback<VideoDTO>() {
            @Override
            public void onResponse(Call<VideoDTO> call, Response<VideoDTO> response) {
                if (response != null && response.body() != null) {
                    btnPlayTrailer.setVisibility(View.VISIBLE);
                    List<ResultVideosDTO> results = response.body().getResults();
                    for (ResultVideosDTO r : results) {
                        if (r.getType().equalsIgnoreCase("Trailer")) {
                            trailer = r;
                        }
                        break;
                    }
                } else {
                    btnPlayTrailer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<VideoDTO> call, Throwable t) {
                Dialogs dialogs = new Dialogs();
                dialogs.showAlertDialog(t.getMessage(), getContext());
            }
        });
    }

}