package com.example.themoviedb.ui.series;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;
import com.example.themoviedb.api.ApiAdapter;
import com.example.themoviedb.model.ConfigurationApiDTO;
import com.example.themoviedb.model.DiscoverTVProgramsDTO;
import com.example.themoviedb.model.DiscoverTVResponseDTO;
import com.example.themoviedb.model.GenreDTO;
import com.example.themoviedb.model.GenreListDTO;
import com.example.themoviedb.model.ImageDTO;
import com.example.themoviedb.ui.activities.InicioActivity;
import com.example.themoviedb.ui.movies.RVGenreListAdapter;
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

public class
SeriesFragment extends Fragment implements RVGenreListAdapter.CustomGenreClick, RVSeriesListAdapter.CustomSerieClick {

    View root;
    RecyclerView rvSeriesGenres;
    public RVGenreListAdapter.CustomGenreClick listenerGenre;
    public boolean comeFromGenre = false;
    private TextView tvSerieGenre;
    private List<GenreDTO> genreListData = new ArrayList<>();
    private RVGenreListAdapter adapterGenres;
    private GenreListDTO genreListDTO;
    private List<DiscoverTVProgramsDTO> seriesPopularityList;
    private ImageDTO imageDto;
    private RVSeriesListAdapter adapterSeries;
    private RVSeriesListAdapter.CustomSerieClick listener;
    private RecyclerView rvSeriesOrderByPopularity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_series, container, false);
        //Init genres recycler
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvSeriesGenres = root.findViewById(R.id.rvSeriesGenres);
        rvSeriesGenres.setLayoutManager(layoutManager);
        listenerGenre = this::onGenreClick;
        //
        tvSerieGenre = root.findViewById(R.id.tvSeriesGenre);
        //
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvSeriesOrderByPopularity = root.findViewById(R.id.rvSeriesOrderByPopularity);
        rvSeriesOrderByPopularity.setLayoutManager(layoutManager1);
        listener = this::onSerieClick;

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callConfigurationApi();
        callGenre();
    }

    public void callGenre() {
        Call<GenreListDTO> call = ApiAdapter.getApiService().getTvGenders(getString(R.string.api_key), es_ES);
        call.enqueue(new Callback<GenreListDTO>() {
            @Override
            public void onResponse(Call<GenreListDTO> call, Response<GenreListDTO> response) {
                if (response != null && response.body() != null) {
                    genreListData = response.body().getGenres();
                    genreListDTO = response.body();
                    adapterGenres = new RVGenreListAdapter(genreListData, getContext(), listenerGenre);
                    rvSeriesGenres.setAdapter(adapterGenres);
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
    public void onGenreClick(GenreDTO item) {
        comeFromGenre = true;
        callConfigurationApi();
        tvSerieGenre.setVisibility(View.VISIBLE);
        tvSerieGenre.setText(getString(R.string.movies_recycler_genre,item.getName()));
        callGetSeriesByGenre(item);
    }

    public void callGetSeriesByGenre(GenreDTO selectedItem) {
        String genreId = String.valueOf(selectedItem.getId());
        Call<DiscoverTVResponseDTO> call = ApiAdapter.getApiService().discoverSeriesByGenre(getString(R.string.api_key), es_ES, ES, "popularity.desc", FALSE, TRUE, STRING_ONE, genreId);
        call.enqueue(new Callback<DiscoverTVResponseDTO>() {
            @Override
            public void onResponse(Call<DiscoverTVResponseDTO> call, Response<DiscoverTVResponseDTO> response) {
                if (response != null && response.body()!= null) {
                    seriesPopularityList = response.body().getTvProgramsDTOList();
                    initSeriesRecycler();
                }
            }

            @Override
            public void onFailure(Call<DiscoverTVResponseDTO> call, Throwable t) {
                Dialogs dialogs = new Dialogs();
                dialogs.showAlertDialog(t.getMessage(), getContext());
            }
        });
    }

    public void callSeriesOrderByPopularity() {
        String timezone = "España/Madrid".replace("/", "%2F");
        Call<DiscoverTVResponseDTO> call = ApiAdapter.getApiService().discoverTVPrograms(getString(R.string.api_key), es_ES, "popularity.desc", STRING_ONE, timezone, FALSE );
        call.enqueue(new Callback<DiscoverTVResponseDTO>() {
            @Override
            public void onResponse(Call<DiscoverTVResponseDTO> call, Response<DiscoverTVResponseDTO> response) {
                if (response != null && response.body() != null) {
                    seriesPopularityList = response.body().getTvProgramsDTOList();
                    initSeriesRecycler();
                }
            }

            @Override
            public void onFailure(Call<DiscoverTVResponseDTO> call, Throwable t) {
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
                    if (!comeFromGenre) callSeriesOrderByPopularity();
                }
            }

            @Override
            public void onFailure(Call<ConfigurationApiDTO> call, Throwable t) {
                Dialogs dialogs = new Dialogs();
                dialogs.showAlertDialog(t.getMessage(), getContext());
            }
        });
    }

    public void initSeriesRecycler() {
        adapterSeries = new RVSeriesListAdapter(seriesPopularityList, getContext(), imageDto, listener);
        rvSeriesOrderByPopularity.setAdapter(adapterSeries);
    }

    @Override
    public void onSerieClick(DiscoverTVProgramsDTO item, String url) {
        Log.d("HOLA", "HE HECHO ONCLICK");
        ((InicioActivity) getActivity()).goToSerieDetail(item, url, genreListDTO);

    }

}