package gaur.himanshu.august.moviedetails.ui.movie

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import gaur.himanshu.august.moviedetails.data.Movie
import gaur.himanshu.august.moviedetails.remote.MovieInterface
import gaur.himanshu.august.moviedetails.utils.Constants

class MoviePaging(val s: String, val movieInterface: MovieInterface) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {

            val data = movieInterface.getAllMovies(s, page, Constants.API_KEY)
            Log.d("TAG", "load: ${data.body()}")
            LoadResult.Page(
                data = data.body()?.Search!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.body()?.Search?.isEmpty()!!) null else page + 1
            )


        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }


    }
}