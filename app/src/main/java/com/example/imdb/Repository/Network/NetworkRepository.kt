package com.example.imdb.Repository.Network

import com.example.imdb.Pojo.ImdbPojoModel
import com.example.imdb.Pojo.Result
import com.example.imdb.Retrofit.RetrofitMainInterface
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkRepository {

    private val interceptor=HttpLoggingInterceptor()
    private val client=OkHttpClient.Builder().addInterceptor(interceptor)
        .readTimeout(30,TimeUnit.SECONDS)
        .connectTimeout(30,TimeUnit.SECONDS).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/search/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val retrofitInterface = retrofit.create(RetrofitMainInterface::class.java)

    fun getMovies(apikey: String, query: String, page: Int): Observable<ImdbPojoModel> {
        return retrofitInterface.GetMovies(apikey, query, page)
    }
}