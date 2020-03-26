package com.example.imdb.Features.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdb.Features.Favorite.FavoriteActivity
import com.example.imdb.Features.Home.Adapter.HomeImdbRecyclerAdapter
import com.example.imdb.Features.Profile.ProfileActivity
import com.example.imdb.Pojo.Entity.MovieEntity
import com.example.imdb.Pojo.Result
import com.example.imdb.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.about_layout.*
import kotlinx.android.synthetic.main.about_layout.view.*
import kotlinx.android.synthetic.main.activity_home_imdb.*
import kotlinx.android.synthetic.main.movies_layout_item.view.*
import kotlinx.android.synthetic.main.navigation_drawer.*
import kotlinx.android.synthetic.main.toolbar.*


class HomeImdb : AppCompatActivity() {
    private lateinit var adapter: HomeImdbRecyclerAdapter
    lateinit var viewModel: HomeImdbViewModel
    private var MoviesItem: List<Result> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = ""


        viewModel = ViewModelProviders.of(this).get(HomeImdbViewModel::class.java)


        setUpRecycler()


        viewModel.getMoviesLiveData().observe(this, Observer {
            progressBar.visibility = ProgressBar.GONE
            adapter.Moveis = it
            adapter.notifyDataSetChanged()
        })

        /************************************************Btn Search IME Editor******************************************************/
        edtSearchIMDB.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> SearchMovieImdb()
            }
            false
        }
        /************************************************Btn Btn Search IME Editor******************************************************/


        /************************************************Btn SearchIMDB ***************************************************************/
        btnSearchImdb.setOnClickListener {
            SearchMovieImdb()
        }
        /************************************************Btn SearchIMDB ***************************************************************/


        /************************************************************NavigationDrawer********************************************/
        ViewCompat.setLayoutDirection(NavigationView, ViewCompat.LAYOUT_DIRECTION_RTL)
        NavigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_Like -> {
                    val intent = Intent()
                    intent.setClass(this, FavoriteActivity::class.java)
                    startActivity(intent)
                }
                R.id.item_About -> {
//                    Toast.makeText(this, "About", Toast.LENGTH_LONG).show()
                    val inflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val popup: View = inflater.inflate(R.layout.about_layout, null)
                    val popupWindow = PopupWindow(
                        popup,
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                    popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0)

                    popup.btnAgree.setOnClickListener {
                        popupWindow.dismiss()
                    }
                }
                R.id.item_User->{
                    val intent = Intent()
                    intent.setClass(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawer(Gravity.RIGHT)
            true
        }
        val actionbar = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.ProfileName, R.string.ProfileEmail)
        drawerLayout.setDrawerListener(actionbar)
        actionbar.isDrawerIndicatorEnabled = false
        actionbar.syncState()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawer(Gravity.RIGHT)
        } else {
            super.onBackPressed()
        }
    }
    /************************************************************End NavigationDrawer********************************************/


    /************************************************************Start Setup Recycler ****************************************/
    private fun setUpRecycler() {

        adapter = HomeImdbRecyclerAdapter(MoviesItem, this) { Result, itemView ->

            /************************************************Fab Btn Like Click ******************************************************/
            val movie: MovieEntity = MovieEntity()
            movie.MovieId = Result.id
            movie.AverageVote = Result.vote_average
            movie.Description = Result.overview
            movie.ImagePAth = Result.backdrop_path
            movie.ReleaseDate = Result.release_date
            movie.Title = Result.title

            val snakebar = Snackbar.make(itemView, "اطلاعات فیلم در علاقمندی ها ذخیره گردد؟", Snackbar.LENGTH_LONG)

            if (itemView.btnLike.tag == "false") {
                val view = snakebar.view
                snakebar.setActionTextColor(
                    resources.getColor(
                        R.color.Success
                    )
                )
                snakebar.setAction("تایید", View.OnClickListener {
                    itemView.btnLike.tag = "true"
                    itemView.btnLike.setImageResource(R.drawable.like_fill)
                    viewModel.InsertMovie(movie)
                    snakebar.dismiss()
                })
            } else {
                snakebar.setText("اطلاعات فیلم از علاقمندی ها حذف گردد؟")
                snakebar.setAction("تایید", View.OnClickListener {
                    itemView.btnLike.tag = "false"
                    itemView.btnLike.setImageResource(R.drawable.like)
                    snakebar.dismiss()
                })
            }

            ViewCompat.setLayoutDirection(snakebar.view, ViewCompat.LAYOUT_DIRECTION_RTL)
            snakebar.show()
            /************************************************Fab Btn Like Click ******************************************************/
        }

        val linearLayout: LinearLayoutManager = LinearLayoutManager(this)
        rv_Movies.layoutManager = linearLayout
        rv_Movies.setHasFixedSize(true)
        rv_Movies.adapter = adapter
    }

    /************************************************************End Data Recycler ******************************************/


    /************************************************************Menu********************************************************/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_right -> {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT)
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT)
                }
            }
        }
        return true
    }
    /************************************************************Menu********************************************************/

    /******************************************************Start Fun Search Imdb*********************************************/
    fun SearchMovieImdb() {
        progressBar.visibility = ProgressBar.VISIBLE
        viewModel.MovieRequested("e788ddc6a6d7f043650a7fcc41eca5b8", edtSearchIMDB.text.toString(), 1)
    }
    /************************************************************End Fun Search Imdb******************************************/

}
