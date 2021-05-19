package co.daniel.moviegasm.activities.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import co.daniel.moviegasm.addBackNavButton
import co.daniel.moviegasm.base.BaseActivity
import co.daniel.moviegasm.databinding.ActivityMovieDetailsBinding
import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.domain.ReturnResult
import co.daniel.moviegasm.show
import co.daniel.moviegasm.viewmodel.MovieDetailsViewModel

class MovieDetailsActivity : BaseActivity<MovieDetailsViewModel>() {

    private var movieID: String = ""
    private lateinit var binding: ActivityMovieDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    override val viewModel: MovieDetailsViewModel by contractedViewModels()

    override fun loadData() {

    }

    override fun initUI() {
        binding.toolbar.addBackNavButton(this)

        movieID = intent.getStringExtra(MOVIE_ID) ?: ""
        viewModel.getMovieDetails(movieID)

        viewModel.observeMovieDetails().observe(this, {
            if (it is ReturnResult.PositiveResult)
                setMovieDataToUI(it.data)
            else
                showSnackBar(binding.root, it)
        })
    }

    private fun setMovieDataToUI(data: MoviesVO) {
        Log.i("setMovieDataToUI","setMovieDataToUI reached showing poster and details")
        binding.moviePosterExtendedIv.show(data.poster)
    }

    companion object {
        val MOVIE_ID = "MOVIE_ID"

        fun start(context: Context, movieID: String): Intent =
            Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_ID, movieID)
            }
    }
}