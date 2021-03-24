package com.example.themoviedb.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.themoviedb.R;
import com.example.themoviedb.model.DiscoverMovieDTO;
import com.example.themoviedb.model.DiscoverTVProgramsDTO;
import com.example.themoviedb.model.GenreDTO;
import com.example.themoviedb.model.GenreListDTO;
import com.example.themoviedb.model.ImageDTO;
import com.example.themoviedb.ui.movies.MovieDetailFragment;
import com.example.themoviedb.ui.utils.FragmentConstant;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void goToMovieDetail(DiscoverMovieDTO item, String url, GenreListDTO genreListDTO) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("Movie", item);
        intent.putExtra("ImageUrl", url);
        intent.putExtra("GenreListDto", genreListDTO);
        startActivity(intent);
    }

    public void   goToSerieDetail(DiscoverTVProgramsDTO item, String url, GenreListDTO genreListDTO) {
        Intent intent = new Intent(this, SerieDetailActivity.class);
        intent.putExtra("Serie", item);
        intent.putExtra("ImageUrl", url);
        intent.putExtra("GenreList", genreListDTO);
        startActivity(intent);
    }
}