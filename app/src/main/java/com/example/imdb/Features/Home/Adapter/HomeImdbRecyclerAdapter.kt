package com.example.imdb.Features.Home.Adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.imdb.Features.Favorite.FavoriteViewModel
import com.example.imdb.Features.Home.HomeImdbViewModel
import com.example.imdb.Pojo.Entity.MovieEntity
import com.example.imdb.Pojo.ImdbPojoModel
import com.example.imdb.Pojo.Result
import com.example.imdb.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movies_layout_item.view.*

lateinit var viewModel: HomeImdbViewModel

class HomeImdbRecyclerAdapter(
    var Moveis: List<Result>,
    var context: Context,
    private val clickListener: (Result, View) -> Unit
) :
    RecyclerView.Adapter<HomeImdbRecyclerAdapter.MoviesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movies_layout_item, parent, false)
        return MoviesViewHolder(view, context, clickListener)
    }

    override fun getItemCount(): Int {
        return Moveis.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(Moveis[position])
    }


    class MoviesViewHolder(val view: View, private val context: Context, val clickListener: (Result, View) -> Unit) :
        RecyclerView.ViewHolder(view) {
        fun bind(imdbpojomodel: Result) {
            viewModel = ViewModelProviders.of(context as FragmentActivity).get(HomeImdbViewModel::class.java)

            /*viewModel.checkMovieIsFavLiveData().observe(context, Observer {
                when (it) {
                    true -> {
                        view.btnLike.setImageResource(R.drawable.like_fill)
                        Log.d("check", it.toString())
                    }
                    false -> {
                        view.btnLike.setImageResource(R.drawable.like)
                    }
                }
                Log.d("check data flag", imdbpojomodel.id.toString() + " IT -- > " + it.toString())
            })
            viewModel.checkMovieIsFav(imdbpojomodel.id)*/
            view.txtTitle.text = imdbpojomodel.original_title
            view.txtDesc.text = imdbpojomodel.overview
            view.txtRelease.text = imdbpojomodel.release_date
            view.txtRate.text = imdbpojomodel.vote_average.toString()
            Picasso.get()

                .load("https://image.tmdb.org/t/p/w500/" + imdbpojomodel.backdrop_path)
                .placeholder(R.drawable.place_holder).into(view.imgImdb)

            view.btnLike.setOnClickListener {
                clickListener(imdbpojomodel, it)
            }
        }
    }
}