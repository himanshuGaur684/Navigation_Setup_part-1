package gaur.himanshu.august.moviedetails.ui.details

import gaur.himanshu.august.moviedetails.data.moviedetails.MovieDetails
import gaur.himanshu.august.moviedetails.remote.MovieInterface
import gaur.himanshu.august.moviedetails.utils.Constants
import gaur.himanshu.august.moviedetails.utils.Result
import gaur.himanshu.august.moviedetails.utils.Status

class MovieDetailsRepository(private val movieInterface: MovieInterface) {


    suspend fun getMovieDetails(imdbId: String): Result<MovieDetails> {


        return try {

            val response = movieInterface.getMovieDetails(imdbId, Constants.API_KEY)
            if (response.isSuccessful) {

                Result(Status.SUCCESS, response.body(), null)

            } else {
                Result(Status.ERROR, null, null)
            }


        } catch (e: Exception) {
            Result(Status.ERROR, null, null)
        }


    }


}