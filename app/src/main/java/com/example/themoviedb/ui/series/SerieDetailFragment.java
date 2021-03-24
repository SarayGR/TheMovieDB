package com.example.themoviedb.ui.series;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.themoviedb.R;
import com.example.themoviedb.model.DiscoverMovieDTO;
import com.example.themoviedb.model.DiscoverTVProgramsDTO;
import com.example.themoviedb.model.GenreDTO;
import com.example.themoviedb.model.GenreListDTO;
import com.example.themoviedb.ui.movies.RVMoviePopularityListAdapter;

import org.json.JSONException;

import java.util.List;

public class SerieDetailFragment extends Fragment {

    TextView tvSerieDetailTitle, tvSerieDetailDate, tvSerieDetailGenres, tvSerieDetailDescriptionValue, tvSerieDetailScoreValue;
    DiscoverTVProgramsDTO discoverTVProgramsDTO;
    String url;
    ImageView ivSerieDetailPhoto;
    View view;
    GenreListDTO genreListDTO;

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
        //
        tvSerieDetailTitle.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        try {
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

}