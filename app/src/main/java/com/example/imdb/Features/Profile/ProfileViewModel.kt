package com.example.imdb.Features.Profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imdb.Pojo.Entity.ProfileEntity
import com.example.imdb.Repository.Repository
import io.reactivex.disposables.CompositeDisposable

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)
    private val profileData = MutableLiveData<ProfileEntity>()
    private val composite = CompositeDisposable()
    private val checkexistprofile = MutableLiveData<Boolean>()

    fun getProfile() {
        composite.add(
            repository.getProfile().subscribe({
                profileData.value = it
            }, {
                Log.d("Error Profile", it.message)
            })
        )
    }


    fun checkExsistProfile() {
        composite.add(
            repository.checkExsistProfile().subscribe({
                checkexistprofile.value = it
                Log.d("Exsist Profile", it.toString())
            }, {
                Log.d("Error Exsist Profile", it.message)
            })
        )
    }

    fun insertProfile(entity: ProfileEntity) {
        Log.d("entity", entity.toString())
        composite.add(
            repository.insertProfile(entity).subscribe({
                Log.d("Suceess", "Insert Success")
            }, {
                Log.d("Error Insert Profile", it.message)
            })
        )
    }

    fun updateProfile(entity: ProfileEntity) {
        Log.d("Update entity", entity.toString())
        composite.add(
            repository.updateProfile(entity).subscribe({
                Log.d("Suceess", "Update Success")
            }, {
                Log.d("Error Update Profile", it.message)
            })
        )
    }

    fun profileDataLiveData(): LiveData<ProfileEntity> = profileData
    fun checkExsistProfileLiveData(): LiveData<Boolean> = checkexistprofile

    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}