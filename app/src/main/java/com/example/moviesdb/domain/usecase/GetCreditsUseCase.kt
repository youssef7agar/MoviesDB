package com.example.moviesdb.domain.usecase

import com.example.moviesdb.common.unwrapResult
import com.example.moviesdb.common.unwrapSuspendResult
import com.example.moviesdb.domain.model.Member
import com.example.moviesdb.domain.model.Credits
import com.example.moviesdb.domain.repository.MoviesRepository
import javax.inject.Inject

class GetCreditsUseCase @Inject constructor(
    private val repo: MoviesRepository
) {

    private val actorsList = mutableListOf<Member>()
    private val directorsList = mutableListOf<Member>()

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
            Result.Success(
                credits = Credits(
                    cast = actorsList.sortedBy { castMember -> castMember.popularity }.take(5),
                    crew = directorsList.sortedBy { crewMember -> crewMember.popularity }.take(5)
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