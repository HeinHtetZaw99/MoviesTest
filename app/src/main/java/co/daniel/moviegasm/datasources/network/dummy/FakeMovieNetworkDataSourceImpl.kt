package co.daniel.moviegasm.datasources.network.dummy

import co.daniel.moviegasm.datasources.network.MovieNetworkDataSource
import co.daniel.moviegasm.domain.MoviesVO
import java.util.*
import javax.inject.Inject

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
class FakeMovieNetworkDataSourceImpl @Inject constructor() : MovieNetworkDataSource {
    override fun getMoviesList(pageNumber : Int): List<MoviesVO> {
        return Collections.nCopies(10, MoviesVO("jl2020", "Justice League Synder's Cut" , "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjKiwgVJ_ce_o31yNCrAIp7hMGnpm5XkPMXUxvjv6eNOr9PEOrjB_Bz7xOPx5kpFBO5mg&usqp=CAU","Heroes saved the day"))
    }

    override fun getTempToken(): String {
       return "your-fake-token"
    }

    override fun getMovieByID(movieID: String): MoviesVO {
        return MoviesVO("jl2020", "Justice League Synder's Cut" , "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjKiwgVJ_ce_o31yNCrAIp7hMGnpm5XkPMXUxvjv6eNOr9PEOrjB_Bz7xOPx5kpFBO5mg&usqp=CAU","Heroes saved the day")
    }
}