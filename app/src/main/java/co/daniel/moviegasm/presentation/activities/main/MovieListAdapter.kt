package co.daniel.moviegasm.presentation.activities.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import co.daniel.moviegasm.base.BaseRecyclerAdapter
import co.daniel.moviegasm.databinding.ItemMovieListBinding
import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.presentation.activities.main.MovieViewHolder

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
class MovieListAdapter(
    private val context: Context,
    private val listener: MovieListAdapterEventListener
) : BaseRecyclerAdapter<MoviesVO, MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieListBinding.inflate(LayoutInflater.from(context),parent,false)
        return MovieViewHolder(binding,listener)
    }
}

interface MovieListAdapterEventListener {
    fun onClickMovie(movieID: String)
}