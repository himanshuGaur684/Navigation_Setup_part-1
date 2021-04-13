package gaur.himanshu.august.moviedetails

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import gaur.himanshu.august.moviedetails.data.moviedetails.MovieDetails
import gaur.himanshu.august.moviedetails.remote.MovieInterface
import gaur.himanshu.august.moviedetails.ui.details.MovieDetailsRepository
import gaur.himanshu.august.moviedetails.ui.movie.MoviePaging
import gaur.himanshu.august.moviedetails.utils.Events
import gaur.himanshu.august.moviedetails.utils.Result
import gaur.himanshu.august.moviedetails.utils.Status
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieInterface: MovieInterface,
    private val repository: MovieDetailsRepository
) : ViewModel() {


    private val query = MutableLiveData<String>()


    val list = query.switchMap { query ->
        Pager(PagingConfig(pageSize = 10)) {
            MoviePaging(query, movieInterface)
        }.liveData.cachedIn(viewModelScope)
    }

    fun setQuery(s: String) {
        query.postValue(s)
    }

    private val _movieDetails = MutableLiveData<Events<Result<MovieDetails>>>()
    val movieDetails: LiveData<Events<Result<MovieDetails>>> = _movieDetails


    fun getMovieDetails(imdbId: String) = viewModelScope.launch {
        _movieDetails.postValue(Events(Result(Status.LOADING, null, null)))
        _movieDetails.postValue(Events(repository.getMovieDetails(imdbId)))

    }


}