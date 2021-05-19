package co.daniel.moviegasm.activities.home

import co.daniel.moviegasm.base.BaseViewHolder
import co.daniel.moviegasm.databinding.ItemMovieListBinding
import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.show

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
class MovieViewHolder(
    private val binding: ItemMovieListBinding,
    private val listener: MovieListAdapterEventListener
) : BaseViewHolder<MoviesVO>(binding.root) {
    override fun setData(mData: MoviesVO) {
        binding.moviePosterIv.show(mData.poster)
        binding.movieTitleTv.text = mData.title
        binding.root.setOnClickListener { listener.onClickMovie(mData.id) }
//        binding.movieRatedTv.text = mData.popularity.toString()
    }
}