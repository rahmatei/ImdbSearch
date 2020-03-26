package com.example.imdb.Features.Favorite.Adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.imdb.Features.Favorite.FavoriteViewModel
import com.example.imdb.Pojo.Entity.MovieEntity
import com.example.imdb.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movies_layout_item.view.*



class FavoriteRecyclerAdapter(
    var FavMoveis: List<MovieEntity>,
    private val clickListener: (MovieEntity, View) -> Unit
) :
    RecyclerView.Adapter<FavoriteRecyclerAdapter.FavoriteMoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movies_layout_item, parent, false)
        return FavoriteMoviesViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int {
        return FavMoveis.size
    }

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        holder.bind(FavMoveis[position])
    }


    class FavoriteMoviesViewHolder(
        private val view: View,
        val clickListener: (MovieEntity, View) -> Unit
    ) :
        RecyclerView.ViewHolder(view) {
        fun bind(Enity: MovieEntity) {
            view.btnLike.setImageResource(R.drawable.like_fill)
            view.txtTitle.text = Enity.Title
            view.txtDesc.text = Enity.Description
            view.txtRelease.text = Enity.ReleaseDate
            view.txtRate.text = Enity.AverageVote.toString()
            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500/" + Enity.ImagePAth)
                .placeholder(R.drawable.place_holder).into(view.imgImdb)

            view.btnLike.setOnClickListener {
                clickListener(Enity, it)
            }
        }
    }
}