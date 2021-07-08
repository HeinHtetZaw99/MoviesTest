package co.daniel.moviegasm.presentation.activities.main

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import co.daniel.moviegasm.base.BaseActivity
import co.daniel.moviegasm.databinding.ActivityMainBinding
import co.daniel.moviegasm.presentation.activities.details.MovieDetailsActivity
import co.daniel.moviegasm.presentation.activities.home.MovieListAdapter
import co.daniel.moviegasm.presentation.activities.home.MovieListAdapterEventListener
import co.daniel.moviegasm.presentation.utils.addCustomScrollListener
import co.daniel.moviegasm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeScreenActivity : BaseActivity<HomeViewModel>(), MovieListAdapterEventListener {

    private val TAG = "HOMESCREEN_ACTIVITY"
    private lateinit var binding: ActivityMainBinding
    override val viewModel: HomeViewModel by viewModels()
    private val movieAdapter = MovieListAdapter(this@HomeScreenActivity, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("OnCreate Reached")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        fetchMovies(false)
    }

    override fun loadData() {

    }

    override fun initUI() {
        with(binding.moviesRv)
        {
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                binding.moviesRv.layoutManager = GridLayoutManager(this@HomeScreenActivity, 2)
            } else {
                binding.moviesRv.layoutManager = GridLayoutManager(this@HomeScreenActivity, 4)
            }
            adapter = movieAdapter

        }


        binding.moviesRv.addCustomScrollListener(2) {
            if (viewModel.hasMoreToLoadMessage) {
                if (!viewModel.isMessageListAlreadyLoading) {
                    fetchMovies(false)
                }
            }
        }


        binding.swipeRefreshLayout.setOnRefreshListener {
            fetchMovies(true)
        }

        viewModel.getMoviesListFromCache().observe(this, {
            binding.swipeRefreshLayout.isRefreshing = false
            if (it.isNotEmpty()) {
                Log.v(TAG, "fetched List = ${it.size}")
                movieAdapter.appendNewData(it)
            }
        })

    }

    private fun fetchMovies(fetchFromStart: Boolean) {
        viewModel.getMovieListFromNetwork(fetchFromStart)
    }

    override fun onClickMovie(movieID: String) {
        startActivity(MovieDetailsActivity.start(this, movieID))
    }

    // life cycle test

}
