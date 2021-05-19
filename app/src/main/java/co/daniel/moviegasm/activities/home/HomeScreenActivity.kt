package co.daniel.moviegasm.activities.home

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.daniel.moviegasm.activities.details.MovieDetailsActivity
import co.daniel.moviegasm.base.BaseActivity
import co.daniel.moviegasm.databinding.ActivityMainBinding
import co.daniel.moviegasm.viewmodel.HomeViewModel

class HomeScreenActivity : BaseActivity<HomeViewModel>(), MovieListAdapterEventListener {

    private val TAG = "HOMESCREEN_ACTIVITY"
    private lateinit var binding: ActivityMainBinding
    override val viewModel: HomeViewModel by contractedViewModels()
    private val movieAdapter = MovieListAdapter(this@HomeScreenActivity, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


        binding.moviesRv.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = binding.moviesRv.layoutManager as? LinearLayoutManager
                    layoutManager?.let {
                        val lastVisible: Int = layoutManager.findLastVisibleItemPosition()
                        if (lastVisible >= movieAdapter.itemCount - 2) {
                            if (viewModel.hasMoreToLoadMessage) {
                                if (!viewModel.isMessageListAlreadyLoading) {
                                    fetchMovies(false)
                                }
                            }
                        }
                    }
                }
            })


        binding.swipeRefreshLayout.setOnRefreshListener {
            fetchMovies(true)
        }
    }

    private fun fetchMovies(fetchFromStart: Boolean) {
        viewModel.getMoviesListFromCache(fetchFromStart).observe(this, {
            binding.swipeRefreshLayout.isRefreshing = false
            if (it.isNotEmpty()) {
                Log.v("Paging", "fetched List = ${it.size}")
                movieAdapter.appendNewData(it)
            } else if (viewModel.hasMoreToLoadMessage) {
                Log.v("Paging", "fetching from network ...")
                viewModel.getMovieListFromNetwork()
            }

        })
    }

    override fun onClickMovie(movieID: String) {
        startActivity(MovieDetailsActivity.start(this, movieID))
    }
}