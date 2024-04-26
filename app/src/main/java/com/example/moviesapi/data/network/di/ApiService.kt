package com.example.moviesapi.data.network.di


import com.example.moviesapi.data.entities.movie.Movie
import com.example.moviesapi.data.entities.movie.credits.Credits
import com.example.moviesapi.data.entities.movie.credits.cast.PersonDetail
import com.example.moviesapi.data.entities.movie.credits.cast.PersonDetailMovieCredits
import com.example.moviesapi.data.entities.movie.credits.cast.image.PersonImages
import com.example.moviesapi.data.entities.movie.searchMovie.MovieSearch
import com.example.moviesapi.data.entities.movieLists.PopularMovieList
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //@GET("character/1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21")
    //suspend fun getPopularMovies(): PopularMovieList
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): PopularMovieList

    @GET("trending/movie/{time_window}?language=en-US")
    suspend fun getTrendingMovies(
        @Path("time_window") time_window: String,
    ): PopularMovieList

    @GET("movie/{movie_id}?language=en-US")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
    ): Movie

    @GET("movie/{movie_id}/credits?language=en-US")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
    ): Credits

    @GET("person/{person_id}?language=en-US")
    suspend fun getPersonDetail(
        @Path("person_id") personId: Int,
    ): PersonDetail

    @GET("person/{person_id}/images")
    suspend fun getPersonImages(
        @Path("person_id") personId: Int,
    ): PersonImages

    @GET("person/{person_id}/movie_credits?language=en-US")
    suspend fun getPersonDetailMovieCredits(
        @Path("person_id") personId: Int,
    ): PersonDetailMovieCredits

    @GET("search/movie")
    suspend fun getMovieSearch(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieSearch
    //trending/movie/week?language=en-US"
    /*
        @GET("character/{id}")
        suspend fun getCharacterDetail(@Path("id") characterId: Int): Character
    */
    object RetrofitInstance {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("accept", "application/json")
                    .addHeader(
                        "Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMzFiZDNjNzQwN2U2YTM5NTljY2NhMTI3ZDMzYzUwNyIsInN1YiI6IjY2MDU4ZDEzZjkwYjE5MDE3Y2E4YThjYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.2__9yvEwx2niuaL7UuX1nfZ9zELbMpPSbFsdmClgiUo"
                    )
                    .build()
                chain.proceed(request)
            })
            .build()

        val api: ApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            retrofit.create(ApiService::class.java)
        }
    }
}