package com.example.themoviedb.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.themoviedb.R;
import com.example.themoviedb.model.DiscoverMovieDTO;
import com.example.themoviedb.model.GenreListDTO;
import com.example.themoviedb.ui.movies.MovieDetailFragment;
import com.example.themoviedb.ui.utils.FragmentConstant;

public class MovieDetailActivity extends AppCompatActivity {

    DiscoverMovieDTO  movieDTO;
    String url;
    GenreListDTO genreListDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieDTO = (DiscoverMovieDTO) getIntent().getSerializableExtra("Movie");
        url = getIntent().getStringExtra("ImageUrl");
        genreListDTO = (GenreListDTO) getIntent().getSerializableExtra("GenreListDto");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.content, new MovieDetailFragment().newInstance(movieDTO, url, genreListDTO), FragmentConstant.FRAG_MOVIE_DETAIL)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}