package co.daniel.moviegasm.datasources.network.responses

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
	@SerializedName("page")
	val page: Int = 0,

	@SerializedName("total_pages")
	val totalPages: Int = 0,

	@SerializedName("results")
	val results: List<ResultsItem?>? = null,

	@SerializedName("total_results")
	val totalResults: Int = 0
)

data class ResultsItem(

	@SerializedName("overview")
	val overview: String = "",

	@SerializedName("original_language")
	val originalLanguage: String = "",

	@SerializedName("original_title")
	val originalTitle: String = "",

	@SerializedName("video")
	val video: Boolean = false,

	@SerializedName("title")
	val title: String = "",

	@SerializedName("genre_ids")
	val genreIds: List<Int> = arrayListOf(),

	@SerializedName("poster_path")
	val posterPath: String = "",

	@SerializedName("backdrop_path")
	val backdropPath: String = "",

	@SerializedName("release_date")
	val releaseDate: String = "",

	@SerializedName("popularity")
	val popularity: Double = 0.0,

	@SerializedName("vote_average")
	val voteAverage: Double = 0.0,

	@SerializedName("id")
	val id: Int = 0,

	@SerializedName("adult")
	val adult: Boolean = false,

	@SerializedName("vote_count")
	val voteCount: Int = 0
)
