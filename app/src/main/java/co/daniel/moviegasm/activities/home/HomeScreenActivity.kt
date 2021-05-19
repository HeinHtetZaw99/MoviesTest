package co.daniel.moviegasm.activities.home

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import co.daniel.moviegasm.activities.details.MovieDetailsActivity
import co.daniel.moviegasm.base.BaseActivity
import co.daniel.moviegasm.databinding.ActivityMainBinding
import co.daniel.moviegasm.domain.ReturnResult
import co.daniel.moviegasm.showShortToast
import co.daniel.moviegasm.viewmodel.HomeViewModel

class HomeScreenActivity : BaseActivity<HomeViewModel>(), MovieListAdapterEventListener {

    private lateinit var binding: ActivityMainBinding
    override val viewModel: HomeViewModel by contractedViewModels()
    private val movieAdapter = MovieListAdapter(this@HomeScreenActivity, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    override fun loadData() {

    }

    override fun initUI() {
        with(binding.moviesRv)
        {
            layoutManager = GridLayoutManager(this@HomeScreenActivity, 2)
            adapter = movieAdapter

        }

        viewModel.observeMovieList().observe(this, {
            binding.swipeRefreshLayout.isRefreshing = false

            if (it is ReturnResult.PositiveResult)
                movieAdapter.appendNewData(it.data)
            else {
                showShortToast(it.getContent())
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getMovieList(1)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMovieList(1)
    }

    override fun onClickMovie(movieID: String) {
        startActivity(MovieDetailsActivity.start(this,movieID))
    }
}