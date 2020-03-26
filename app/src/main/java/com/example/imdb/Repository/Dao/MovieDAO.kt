package com.example.imdb.Repository.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.imdb.Pojo.Entity.MovieEntity
import com.example.imdb.Pojo.Entity.ProfileEntity
import com.example.imdb.Pojo.Result
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MovieDAO {
    @Query("Select * from Movies")
    fun GetMovies(): Observable<List<MovieEntity>>

    @Query("select COUNT(*)>0 from Movies where MovieId=:Movie_ID")
    fun getMovie(Movie_ID: Int): Single<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun InsertMovie(movie: MovieEntity): Completable

    @Delete
    fun DeleteMovie(movie: MovieEntity): Completable

    @Query("select * from Profile")
    fun getProfile(): Observable<ProfileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun InsertProfile(profile: ProfileEntity): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun UpdateProfile(profile: ProfileEntity): Completable

    @Query("select count(*)>0 from Profile")
    fun checkExsistProfile(): Observable<Boolean>

}