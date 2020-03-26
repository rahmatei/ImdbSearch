package com.example.imdb.Features.Profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.imdb.Features.Favorite.FavoriteActivity
import com.example.imdb.Features.Home.HomeImdbViewModel
import com.example.imdb.Pojo.Entity.ProfileEntity
import com.example.imdb.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    lateinit var viewModel: ProfileViewModel
    var profile = ProfileEntity()
    var check: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)


        init()

        btnSave.setOnClickListener {
            val profileEntity = ProfileEntity()
            profileEntity.Name = edtName.text.toString()
            profileEntity.Family = edtFamily.text.toString()
            profileEntity.Email = edtEmail.text.toString()

            if (check) {
                if (edtName.toString().trim() != "" && edtFamily.toString().trim() != "" && edtEmail.toString().trim() != "") {
                    viewModel.updateProfile(profileEntity)
                    val snakebar = Snackbar.make(ProfileLayout, "اطلاعات با موفقیت ذخیره گردید.", Snackbar.LENGTH_LONG)
                    ViewCompat.setLayoutDirection(snakebar.view, ViewCompat.LAYOUT_DIRECTION_RTL)
                    snakebar.show()
                } else {
                    Toast.makeText(this, "لطفا اطلاعات را کامل تکمیل نمایید", Toast.LENGTH_LONG).show()
                }
            } else {
                if (edtName.toString().trim() != "" && edtFamily.toString().trim() != "" && edtEmail.toString().trim() != "") {
                    viewModel.insertProfile(profileEntity)
                } else {
                    Toast.makeText(this, "لطفا اطلاعات را کامل تکمیل نمایید", Toast.LENGTH_LONG).show()
                }
            }
        }
        btnCancel.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, HomeImdbViewModel::class.java)
            startActivity(intent)
        }
    }

    fun init() {

        viewModel.profileDataLiveData().observe(this, Observer {
            edtName.setText(it.Name)
            edtFamily.setText(it.Family)
            edtEmail.setText(it.Email)
        })
        viewModel.checkExsistProfileLiveData().observe(this, Observer {
            check = it
        })

        viewModel.getProfile()
        viewModel.checkExsistProfile()
    }
}
