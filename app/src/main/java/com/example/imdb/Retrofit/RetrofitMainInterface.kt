package com.example.imdb.Retrofit

import com.example.imdb.Pojo.ImdbPojoModel
import com.example.imdb.Pojo.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitMainInterface {

    @GET("movie")
    fun GetMovies(
        @Query("api_key") apikey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Observable<ImdbPojoModel>
}