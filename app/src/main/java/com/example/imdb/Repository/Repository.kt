package com.example.imdb.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Database
import com.example.imdb.Pojo.Entity.MovieEntity
import com.example.imdb.Pojo.Entity.ProfileEntity
import com.example.imdb.Pojo.ImdbPojoModel
import com.example.imdb.Pojo.Result
import com.example.imdb.Repository.Dao.MovieDAO
import com.example.imdb.Repository.Local.LocalRepository
import com.example.imdb.Repository.Network.NetworkRepository
import com.example.imdb.Util.DataBase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class Repository(application: Application) {

    private val network = NetworkRepository()
    private var localDB: MovieDAO

    init {
        val database = DataBase.getDatabase(
            application.applicationContext as Application
        )!!
        localDB = database.getMovieDAO()
    }

    fun getMovies(apikey: String, query: String, page: Int): Observable<ImdbPojoModel> {
        return network.getMovies(apikey, query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMovies(): Observable<List<MovieEntity>> {
        return localDB.GetMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun checkMovieInDB(MovieID: Int): Single<Boolean> {
        return localDB.getMovie(MovieID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertMovie(entityMovie: MovieEntity): Completable {
        return localDB.InsertMovie(entityMovie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun DeleteFavMovie(entityMovie: MovieEntity): Completable {
        return localDB.DeleteMovie(entityMovie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**************************************************************************************************/

    fun insertProfile(profile: ProfileEntity): Completable {
        return localDB.InsertProfile(profile)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateProfile(profile: ProfileEntity): Completable {
        return localDB.UpdateProfile(profile)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getProfile(): Observable<ProfileEntity> {
        return localDB.getProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun checkExsistProfile(): Observable<Boolean> {
        return localDB.checkExsistProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}