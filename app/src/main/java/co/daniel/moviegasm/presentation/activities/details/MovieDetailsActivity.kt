package co.daniel.moviegasm.presentation.activities.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import co.daniel.moviegasm.R
import co.daniel.moviegasm.addBackNavButton
import co.daniel.moviegasm.base.BaseActivity
import co.daniel.moviegasm.databinding.ActivityMovieDetailsBinding
import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.show
import co.daniel.moviegasm.viewmodel.MovieDetailsViewModel
import timber.log.Timber

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

        viewModel.getMovieDetails(movieID).observe(this, {
            setMovieDataToUI(it)
        })
    }

    private fun setMovieDataToUI(data: MoviesVO) {
        Timber.i("setMovieDataToUI reached showing poster and details")
        binding.moviePosterExtendedIv.show(data.poster)
        binding.contentMoviesDetailsLayout.movieTitleTv.text = data.title
        binding.contentMoviesDetailsLayout.directoryNameTv.text =
            getString(R.string.text_directed_by, data.releaseDate)
        binding.contentMoviesDetailsLayout.descriptionTv.text = data.overView
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