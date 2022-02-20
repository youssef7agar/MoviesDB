package com.example.moviesdb.domain.usecase

import com.example.moviesdb.common.unwrapResult
import com.example.moviesdb.common.unwrapSuspendResult
import com.example.moviesdb.domain.model.CastMember
import com.example.moviesdb.domain.model.Credits
import com.example.moviesdb.domain.model.CrewMember
import com.example.moviesdb.domain.repository.MoviesRepository
import javax.inject.Inject

class GetCreditsUseCase @Inject constructor(
    private val repo: MoviesRepository
) {

    private val actorsList = mutableListOf<CastMember>()
    private val directorsList = mutableListOf<CrewMember>()

    suspend operator fun invoke(movieId: Long): Result {
        return repo.getSimilarMovies(movieId).unwrapSuspendResult({ result ->
            result.movies.take(5).map { movie ->
                movie.id
            }.forEach { id ->
                repo.getMoviesCredits(id).unwrapResult({ credits ->
                    actorsList.addAll(credits.cast.filter { castMember -> castMember.department == "Acting" })
                    directorsList.addAll(credits.crew.filter { castMember -> castMember.department == "Directing" })
                }, { exception ->
                    Result.Error(exception)
                })
            }
            actorsList.sortedBy { castMember -> castMember.popularity }.take(5)
            directorsList.sortedBy { crewMember -> crewMember.popularity }.take(5)
            Result.Success(
                credits = Credits(
                    cast = actorsList,
                    crew = directorsList
                )
            )
        }, { exception ->
            Result.Error(exception)
        })
    }

    sealed class Result {

        data class Success(val credits: Credits) : Result()

        data class Error(val throwable: Throwable) : Result()
    }
}