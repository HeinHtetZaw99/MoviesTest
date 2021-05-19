package co.daniel.moviegasm.domain

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
@Entity(tableName = "movies")
data class MoviesVO(
     @PrimaryKey
     @NonNull
     val id: String = "",
     val timeStamp : Long = 0,
     val title: String = "",
     val poster: String = "",
     val poster2: String = "",
     val overView: String = "",
     val video: Boolean = false,
     val releaseDate: String = "",
     val popularity: Double = 0.0,
     val voteAverage: Double = 0.0,
     val adult: Boolean = false,
     val voteCount: Int = 0
) {
}