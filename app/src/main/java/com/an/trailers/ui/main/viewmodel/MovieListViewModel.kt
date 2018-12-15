package com.an.trailers.ui.main.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.an.trailers.data.Resource
import com.an.trailers.data.local.dao.MovieDao
import com.an.trailers.data.local.entity.MovieEntity
import com.an.trailers.data.remote.api.MovieApiService
import com.an.trailers.data.repository.MovieRepository
import com.an.trailers.ui.base.BaseViewModel
import javax.inject.Inject

class MovieListViewModel@Inject constructor(
    movieDao: MovieDao,
    movieApiService: MovieApiService) : BaseViewModel() {

    private val movieRepository: MovieRepository = MovieRepository(movieDao, movieApiService)
    private val moviesLiveData = MutableLiveData<Resource<List<MovieEntity>>>()


    fun fetchMovies(type: String) {
        movieRepository.loadMoviesByType(type)
            .doOnSubscribe { addToDisposable(it) }
            .subscribe { resource -> moviesLiveData.postValue(resource) }
    }

    fun getMoviesLiveData() = moviesLiveData
}
