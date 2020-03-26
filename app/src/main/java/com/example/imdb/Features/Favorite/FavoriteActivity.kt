package com.example.imdb.Features.Favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Switch
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdb.Features.Favorite.Adapter.FavoriteRecyclerAdapter
import com.example.imdb.Features.Home.HomeImdb
import com.example.imdb.Pojo.Entity.MovieEntity
import com.example.imdb.R
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.movies_layout_item.view.*
import kotlinx.android.synthetic.main.navigation_drawer.*
import kotlinx.android.synthetic.main.toolbar.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteRecyclerAdapter
    lateinit var viewModel: FavoriteViewModel
    private var MoviesFavItem: List<MovieEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = ""
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        /*
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView: View = inflater.inflate(R.layout.activity_favorite, null, false)
        super.setContentView(contentView)
        */

        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)

        setUpRecycler()


        viewModel.getMovieFavoriteLiveData().observe(this, Observer {
            adapter.FavMoveis = it
            adapter.notifyDataSetChanged()
        })

        viewModel.MovieFavoriteRequested()
    }

    /******************************************************Setup Recycler View Fav Movie******************************/
    private fun setUpRecycler() {
        val linearLayout: LinearLayoutManager = LinearLayoutManager(this)
        rv_FavoriteMovies.layoutManager = linearLayout
        rv_FavoriteMovies.setHasFixedSize(true)
        /************************************ Fill Adapter And Handler Click Btn ******************************/
        adapter = FavoriteRecyclerAdapter(MoviesFavItem) { movieEnity, itemView1 ->
            viewModel.DeleteMovieFavoriteRequest(movieEnity)
        }

        rv_FavoriteMovies.adapter = adapter
    }
    /******************************************************Setup Recycler View Fav Movie******************************/


    /******************************************************Handler Click Home Toolbar******************************/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, HomeImdb::class.java)
                startActivity(intent)
            }

        }
        return true
    }
    /******************************************************Handler Click Home Toolbar******************************/
}
