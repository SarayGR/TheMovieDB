package com.example.themoviedb.api;

import com.example.themoviedb.model.ConfigurationApiDTO;
import com.example.themoviedb.model.CreateSessionDTO;
import com.example.themoviedb.model.CreateSessionResponseDTO;
import com.example.themoviedb.model.DiscoverMovieResponseDTO;
import com.example.themoviedb.model.DiscoverTVResponseDTO;
import com.example.themoviedb.model.GenreListDTO;
import com.example.themoviedb.model.LoginSessionDTO;
import com.example.themoviedb.model.MovieVideoDTO;
import com.example.themoviedb.model.MyBaseModelDTO;
import com.example.themoviedb.model.TokenDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("3/authentication/token/new")
    Call<TokenDTO> getRequestToken(@Query("api_key") String apiKey);

    @POST("3/authentication/token/validate_with_login")
    Call<TokenDTO> validateWithLogin(@Body LoginSessionDTO loginSessionDTO, @Query("api_key") String apiKey);

    @POST("3/authentication/session/new")
    Call<CreateSessionResponseDTO> createSessionId(@Body CreateSessionDTO createSessionDTO, @Query("api_key") String apiKey);

    @GET("3/genre/movie/list?")
    Call<GenreListDTO> getGenders(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("3/discover/movie?")
    Call<DiscoverMovieResponseDTO> discoverMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("region") String region, @Query("sort_by") String sortBy, @Query("include_adult") String includeAdult, @Query("include_video") String includeVideo, @Query("page") String page);

    @GET("3/configuration?")
    Call<ConfigurationApiDTO> getConfigurationApi(@Query("api_key") String apiKey);

    @GET("3/discover/movie?")
    Call<DiscoverMovieResponseDTO> discoverMoviesByGenre(@Query("api_key") String apiKey, @Query("language") String language, @Query("region") String region, @Query("sort_by") String sortBy, @Query("include_adult") String includeAdult, @Query("include_video") String includeVideo, @Query("page") String page, @Query("with_genres") String genresIds);

    @GET("3/genre/tv/list?api_key=c8593b39c5f32dce2528c89e8ced7302&language=es-ES")
    Call<GenreListDTO> getTvGenders(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("3/discover/tv?")
    Call<DiscoverTVResponseDTO> discoverTVPrograms(@Query("api_key") String apiKey, @Query("language") String language, @Query("sort_by") String sortBy, @Query("page") String page, @Query("timezone") String timezone, @Query("include_null_first_air_dates") String includeNullFirsrAirDates);

    @GET("3/discover/tv?")
    Call<DiscoverTVResponseDTO> discoverSeriesByGenre(@Query("api_key") String apiKey, @Query("language") String language, @Query("region") String region, @Query("sort_by") String sortBy, @Query("include_adult") String includeAdult, @Query("include_video") String includeVideo, @Query("page") String page, @Query("with_genres") String genresIds);

    @GET("/3/movie/{movieId}/videos?")
    Call<MovieVideoDTO> getMovieTrailers(@Path("movieId") String movieId, @Query("api_key") String apiKey);
}
