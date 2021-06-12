package co.daniel.moviegasm.network.datasources.mappers

import co.daniel.moviegasm.network.datasources.network.responses.ResultsItem
import co.daniel.moviegasm.domain.MoviesVO
import java.util.*
import javax.inject.Inject

class MoviesVOMapper @Inject constructor() {
    fun map(data: ResultsItem?,page : Int): MoviesVO? {
        if (data == null)
            return null
        return MoviesVO(
            id = data.id.toString(),
            title = data.title,
            timeStamp = Calendar.getInstance().timeInMillis,
            poster = "https://image.tmdb.org/t/p/original${data.posterPath}",
            poster2 ="https://image.tmdb.org/t/p/original${data.backdropPath}",
            overView = data.overview,
            video = data.video,
            releaseDate = data.releaseDate,
            popularity = data.popularity,
            voteAverage = data.voteAverage,
            adult = data.adult,
            voteCount = data.voteCount,
            pageNumber = page
        )
    }


}