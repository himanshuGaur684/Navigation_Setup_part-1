package gaur.himanshu.august.moviedetails.data

data class MovieResponse(
    val Response: String,
    val Search: List<Movie>,
    val totalResults: String
)