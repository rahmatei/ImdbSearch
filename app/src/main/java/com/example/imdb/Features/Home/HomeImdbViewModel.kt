package com.example.imdb.Features.Home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.imdb.Pojo.Entity.MovieEntity
import com.example.imdb.Pojo.ImdbPojoModel
import com.example.imdb.Pojo.Result
import com.example.imdb.Repository.Local.LocalRepository
import com.example.imdb.Repository.Repository
import com.example.imdb.Util.DataBase
import io.reactivex.disposables.CompositeDisposable
import java.sql.Types.NULL
import java.util.concurrent.TimeUnit

class HomeImdbViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = Repository(application)
    private val MoviesData = MutableLiveData<List<Result>>()
    private var isMovieSaved = false

    private val CheckMoviesExsist = MutableLiveData<MovieEntity>()
    private val isMovieExsist = MutableLiveData<Boolean>()
    //private var isMovieExsist = false


    private val composite = CompositeDisposable()


    fun MovieRequested(apikey: String, query: String, page: Int) {
        composite.add(
            repository.getMovies(apikey, query, page).subscribe({
                Log.d("data", it.results[0].title)
                checkMovieIsFav(it.results[0].id)
                MoviesData.value = it.results
            }, {
                Log.d("Errorrr", it.message)
            })
        )
    }

    fun getMoviesLiveData(): LiveData<List<Result>> = MoviesData


    fun InsertMovie(entityMovie: MovieEntity) {
        composite.add(repository.insertMovie(entityMovie).subscribe({
            isMovieSaved = true
            Log.d("Data Insert", "Success")
        }, {
            Log.d("Error", it.message)
        }))
    }

    fun checkMovieIsFav(MovieID: Int) {
        composite.add(
            repository.checkMovieInDB(MovieID).subscribe({
                     isMovieExsist.value=it
            }, {
                Log.d("Error", it.message)
                //checkMovieIsFav(MovieID)
            })
        )
    }

    fun checkMovieIsFavLiveData(): LiveData<Boolean> = isMovieExsist


    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}