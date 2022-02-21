package com.example.moviesdb.presentation.mapper

import com.example.moviesdb.domain.model.YearMovie
import com.example.moviesdb.presentation.model.MultiViewItem
import com.example.moviesdb.presentation.model.YearAndMoviesUiModel
import com.example.moviesdb.presentation.model.YearUiModel
import javax.inject.Inject

class YearAndMovieUiMapper @Inject constructor(
    private val movieUiMapper: MovieUiMapper
) {

    fun map(yearMovies: List<YearMovie>, watchlist: List<Long>): List<MultiViewItem> {
        val multiViewItemList = mutableListOf<MultiViewItem>()
        yearMovies.forEach { yearMovie ->
            multiViewItemList.add(YearUiModel(yearMovie.year))

            yearMovie.movies.forEach { movie ->
                multiViewItemList.add(movieUiMapper.map(movie, watchlist))
            }
        }

        return multiViewItemList
    }
}