package com.example.imdb.Features.Favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imdb.Pojo.Entity.MovieEntity
import com.example.imdb.Pojo.Result
import com.example.imdb.Repository.Repository
import io.reactivex.disposables.CompositeDisposable

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application)
    private val composite = CompositeDisposable()
    private val MoviesEnityData = MutableLiveData<List<MovieEntity>>()
    private var isMovieDelete = false

    fun MovieFavoriteRequested() {
        composite.addAll(
            repository.getMovies().subscribe({
                MoviesEnityData.value = it
            }, {
                Log.d("Error", it.message)
            })
        )
    }

    fun getMovieFavoriteLiveData(): LiveData<List<MovieEntity>> = MoviesEnityData


    fun DeleteMovieFavoriteRequest(entityFavMovie: MovieEntity) {
        composite.add(
            repository.DeleteFavMovie(entityFavMovie).subscribe({
                isMovieDelete = true
            }, {
                Log.d("Error", it.message)
            })
        )
    }
}