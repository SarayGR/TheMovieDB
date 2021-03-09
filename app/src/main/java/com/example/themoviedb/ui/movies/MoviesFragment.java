package com.example.themoviedb.ui.movies;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.themoviedb.ui.activities.InicioActivity;
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

public class MoviesFragment extends Fragment implements RVMoviePopularityListAdapter.CustomMovieClick, RVGenreListAdapter.CustomGenreClick {

    private View root;
    private RecyclerView rvGenres;
    private RecyclerView rvMoviesOrderByPopularity;
    private RVGenreListAdapter adapterGenres;
    private RVMoviePopularityListAdapter adapterMovies;
    private List<GenreDTO> genreListData = new ArrayList<>();
    private List<DiscoverMovieDTO> moviePopularityList = new ArrayList<>();
    private ImageDTO imageDto;
    public RVMoviePopularityListAdapter.CustomMovieClick listener;
    public RVGenreListAdapter.CustomGenreClick listenerGenre;
    public boolean comeFromGenre = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_movies, container, false);
        //Init genres recycler
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvGenres = root.findViewById(R.id.rvMoviesGenres);
        rvGenres.setLayoutManager(layoutManager);
        listenerGenre = this::onGenreClick;
        //Init movies order by popularity recycler
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMoviesOrderByPopularity = root.findViewById(R.id.rvMoviesOrderByPopularity);
        rvMoviesOrderByPopularity.setLayoutManager(layoutManager1);
        listener = this::onMovieClick;
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callConfigurationApi();
        callGenre();
    }

    public void callMoviesOrderByPopularity() {
        Call<DiscoverMovieResponseDTO> call = ApiAdapter.getApiService().discoverMovies(getString(R.string.api_key), es_ES, ES, "popularity.desc", FALSE, TRUE, STRING_ONE);
        call.enqueue(new Callback<DiscoverMovieResponseDTO>() {
            @Override
            public void onResponse(Call<DiscoverMovieResponseDTO> call, Response<DiscoverMovieResponseDTO> response) {
                if (response != null && response.body() != null) {
                    moviePopularityList = response.body().getResults();
                    initMovieRecycler();
                }
            }

            @Override
            public void onFailure(Call<DiscoverMovieResponseDTO> call, Throwable t) {
                Dialogs dialogs = new Dialogs();
                dialogs.showAlertDialog(t.getMessage(), getContext());
            }
        });
    }

    public void callConfigurationApi() {
        Call<ConfigurationApiDTO> call = ApiAdapter.getApiService().getConfigurationApi(getString(R.string.api_key));
        call.enqueue(new Callback<ConfigurationApiDTO>() {
            @Override
            public void onResponse(Call<ConfigurationApiDTO> call, Response<ConfigurationApiDTO> response) {
                if (response != null & response.body() != null) {
                    imageDto = response.body().getImages();
                    if (!comeFromGenre) callMoviesOrderByPopularity();
                }
            }

            @Override
            public void onFailure(Call<ConfigurationApiDTO> call, Throwable t) {
                Dialogs dialogs = new Dialogs();
                dialogs.showAlertDialog(t.getMessage(), getContext());
            }
        });
    }

    public void callGenre() {
        Call<GenreListDTO> call = ApiAdapter.getApiService().getGenders(getString(R.string.api_key), es_ES);
        call.enqueue(new Callback<GenreListDTO>() {
            @Override
            public void onResponse(Call<GenreListDTO> call, Response<GenreListDTO> response) {
                if (response != null && response.body() != null) {
                    genreListData = response.body().getGenres();
                    adapterGenres = new RVGenreListAdapter(genreListData, getContext(), listenerGenre);
                    rvGenres.setAdapter(adapterGenres);
                }
            }

            @Override
            public void onFailure(Call<GenreListDTO> call, Throwable t) {
                Dialogs dialogs = new Dialogs();
                dialogs.showAlertDialog(t.getMessage(), getContext());
            }
        });
    }

    @Override
    public void onMovieClick(DiscoverMovieDTO item) {
        //TODO IMPLEMENT GO TO MOVIE DETAIL.
        Log.d("HOLA", "HE HECHO ONCLICK");
        ((InicioActivity) getActivity()).goToMovieDetail(item);

    }

    @Override
    public void onGenreClick(GenreDTO item) {
        comeFromGenre = true;
        callConfigurationApi();
        callGetMoviesByGenre(item);
    }

    public void callGetMoviesByGenre(GenreDTO selectedItem) {
        String genreId = String.valueOf(selectedItem.getId());
        Call<DiscoverMovieResponseDTO> call = ApiAdapter.getApiService().discoverMoviesByGenre(getString(R.string.api_key), es_ES, ES, "popularity.desc", FALSE, TRUE, STRING_ONE, genreId);
        call.enqueue(new Callback<DiscoverMovieResponseDTO>() {
            @Override
            public void onResponse(Call<DiscoverMovieResponseDTO> call, Response<DiscoverMovieResponseDTO> response) {
                if (response != null && response.body()!= null) {
                    moviePopularityList = response.body().getResults();
                    initMovieRecycler();
                }
            }

            @Override
            public void onFailure(Call<DiscoverMovieResponseDTO> call, Throwable t) {
                Dialogs dialogs = new Dialogs();
                dialogs.showAlertDialog(t.getMessage(), getContext());
            }
        });
    }

    public void initMovieRecycler() {
        adapterMovies = new RVMoviePopularityListAdapter(moviePopularityList, getContext(), imageDto, listener);
        rvMoviesOrderByPopularity.setAdapter(adapterMovies);
    }

}