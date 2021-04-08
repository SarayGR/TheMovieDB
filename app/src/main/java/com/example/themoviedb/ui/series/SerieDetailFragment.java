package com.example.themoviedb.ui.series;

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
import com.example.themoviedb.model.DiscoverTVProgramsDTO;
import com.example.themoviedb.model.GenreDTO;
import com.example.themoviedb.model.GenreListDTO;
import com.example.themoviedb.model.ResultVideosDTO;
import com.example.themoviedb.model.VideoDTO;
import com.example.themoviedb.ui.movies.RVMoviePopularityListAdapter;
import com.example.themoviedb.ui.utils.Dialogs;

import org.json.JSONException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SerieDetailFragment extends Fragment {

    TextView tvSerieDetailTitle, tvSerieDetailDate, tvSerieDetailGenres, tvSerieDetailDescriptionValue, tvSerieDetailScoreValue;
    DiscoverTVProgramsDTO discoverTVProgramsDTO;
    String url;
    ImageView ivSerieDetailPhoto;
    View view;
    GenreListDTO genreListDTO;
    Button btnPlayTrailer;
    ResultVideosDTO trailer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            discoverTVProgramsDTO = (DiscoverTVProgramsDTO) getArguments().getSerializable("Serie");
            url = getArguments().getString("ImageUrl");
            genreListDTO = (GenreListDTO) getArguments().getSerializable("GenreListDto");
        }
    }

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_serie_detail, container, false);
        //
        tvSerieDetailTitle = view.findViewById(R.id.tvSerieDetailTitle);
        tvSerieDetailDate = view.findViewById(R.id.tvSerieDetailDate);
        tvSerieDetailGenres = view.findViewById(R.id.tvSerieDetailGenres);
        tvSerieDetailDescriptionValue = view.findViewById(R.id.tvSerieDetailDescriptionValue);
        tvSerieDetailScoreValue = view.findViewById(R.id.tvSerieDetailScoreValue);
        ivSerieDetailPhoto = view.findViewById(R.id.ivSerieDetailPhoto);

        btnPlayTrailer = view.findViewById(R.id.btnPlayTrailer);
        //
        tvSerieDetailTitle.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        try {
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        callTvTrailer();

        btnPlayTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTrailer();
            }
        });

        return view;
    }

    public SerieDetailFragment newInstance(DiscoverTVProgramsDTO discoverTVProgramsDTO, String url, GenreListDTO genreListDTO) {
        SerieDetailFragment f = new SerieDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("Serie", discoverTVProgramsDTO);
        args.putString("ImageUrl", url);
        args.putSerializable("GenreListDto", genreListDTO);
        f.setArguments(args);
        return f;
    }

    public void setData() throws JSONException {
        tvSerieDetailTitle.setText(discoverTVProgramsDTO.getName());
        //Set release date
        String[] dateSplit = discoverTVProgramsDTO.getFirstAirDate().split("-");
        String month = dateSplit[1];
        String year = dateSplit[0];
        String day = dateSplit[2];
        tvSerieDetailDate.setText(day + " / " + month + " / " + year);
        //
        List<GenreDTO> genres = genreListDTO.getGenres();
        List<Integer> genreIds = discoverTVProgramsDTO.getGenreIds();
        String genresNames = "";
        for (int i = 0; i < genres.size(); i++){
            for (int j = 0; j < genreIds.size(); j++) {
                if (genreIds.get(j).intValue() == genres.get(i).getId()) {
                    genresNames += genres.get(i).getName() +", ";
                }
            }
        }
        String cadena = getString(R.string.movie_detail_genres, genresNames);
        tvSerieDetailGenres.setText(cadena.substring(0, cadena.length()-2));
        tvSerieDetailDescriptionValue.setText(discoverTVProgramsDTO.getOverview());
        tvSerieDetailScoreValue.setText(discoverTVProgramsDTO.getVoteAverage().toString());
        setImage();
    }

    public void setImage() {
        ivSerieDetailPhoto.setTag(url);
        new RVMoviePopularityListAdapter.DownloadImagesTask().execute(ivSerieDetailPhoto);
    }

    public void callTvTrailer() {
        Call<VideoDTO> call = ApiAdapter.getApiService().getTvTrailers(discoverTVProgramsDTO.getId().toString(), getString(R.string.api_key));
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

}