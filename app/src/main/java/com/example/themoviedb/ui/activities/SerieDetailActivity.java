package com.example.themoviedb.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.themoviedb.R;
import com.example.themoviedb.model.DiscoverMovieDTO;
import com.example.themoviedb.model.DiscoverTVProgramsDTO;
import com.example.themoviedb.model.GenreListDTO;
import com.example.themoviedb.ui.series.SerieDetailFragment;
import com.example.themoviedb.ui.utils.FragmentConstant;

public class SerieDetailActivity extends AppCompatActivity {

    DiscoverTVProgramsDTO discoverTVProgramsDTO;
    String url;
    GenreListDTO genreListDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_detail);

        discoverTVProgramsDTO = (DiscoverTVProgramsDTO) getIntent().getSerializableExtra("Serie");
        url = getIntent().getStringExtra("ImageUrl");
        genreListDTO = (GenreListDTO) getIntent().getSerializableExtra("GenreList");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.content, new SerieDetailFragment().newInstance(discoverTVProgramsDTO, url, genreListDTO), FragmentConstant.FRAG_MOVIE_DETAIL)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}