package com.example.moviesdb.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesdb.MyApplication
import com.example.moviesdb.R
import com.example.moviesdb.databinding.FragmentMovieDetailsBinding
import com.example.moviesdb.di.ViewModelProviderFactory
import com.example.moviesdb.presentation.adapter.MemberAdapter
import com.example.moviesdb.presentation.adapter.MoviesAdapter
import com.example.moviesdb.presentation.viewmodel.MovieDetailsViewModel
import com.example.moviesdb.presentation.viewstate.CreditsViewState
import com.example.moviesdb.presentation.viewstate.DetailsViewState
import com.example.moviesdb.presentation.viewstate.SimilarMoviesViewState
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<MovieDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private val viewModel: MovieDetailsViewModel by viewModels { viewModelFactory }

    private lateinit var similarMoviesAdapter: MoviesAdapter
    private lateinit var actorsAdapter: MemberAdapter
    private lateinit var directorsAdapter: MemberAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).provideAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        similarMoviesAdapter = MoviesAdapter {}
        binding.similarMoviesRecyclerView.adapter = similarMoviesAdapter

        actorsAdapter = MemberAdapter()
        binding.actorsRecyclerView.adapter = actorsAdapter

        directorsAdapter = MemberAdapter()
        binding.directorsRecyclerView.adapter = directorsAdapter

        viewModel.getMovieDetails(movieId = args.movieId)
        viewModel.getSimilarMovies(movieId = args.movieId)
        viewModel.getCredits(movieId = args.movieId)

        handleDetailsViewState()
        handleSimilarMoviesViewState()
        handleCreditsViewState()
    }

    private fun handleDetailsViewState() {
        viewModel.detailsViewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                DetailsViewState.Error -> {
                    binding.detailsLoadingProgressBar.visibility = View.GONE
                    binding.detailsTryAgainButton.visibility = View.VISIBLE
                    binding.detailsGroup.visibility = View.GONE
                }
                DetailsViewState.Loading -> {
                    binding.detailsLoadingProgressBar.visibility = View.VISIBLE
                    binding.detailsTryAgainButton.visibility = View.GONE
                    binding.detailsGroup.visibility = View.GONE
                }
                DetailsViewState.NoInternet -> {
                }
                is DetailsViewState.Success -> {
                    binding.detailsLoadingProgressBar.visibility = View.GONE
                    binding.detailsTryAgainButton.visibility = View.GONE
                    binding.detailsGroup.visibility = View.VISIBLE

                    Glide.with(requireContext())
                        .load(viewState.movieDetails.backImage)
                        .into(binding.backdropImageView)

                    binding.movieTitleTextView.text = String.format(
                        getString(R.string.title_and_tagline),
                        viewState.movieDetails.title,
                        viewState.movieDetails.tagline
                    )
                    binding.movieOverviewTextView.text = viewState.movieDetails.overview
                    binding.releaseDateTextView.text = String.format(
                        getString(R.string.release_date),
                        viewState.movieDetails.releaseDate
                    )
                    binding.statusTextView.text = String.format(
                        getString(R.string.status),
                        viewState.movieDetails.status
                    )
                    binding.revenueTextView.text = String.format(
                        getString(R.string.revenue),
                        viewState.movieDetails.revenue
                    )
                }
            }
        }
    }

    private fun handleSimilarMoviesViewState() {
        viewModel.similarMoviesViewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                SimilarMoviesViewState.Error -> {
                    binding.similarTryAgainButton.visibility = View.VISIBLE
                    binding.similarLoadingProgressBar.visibility = View.GONE
                    binding.similarMoviesRecyclerView.visibility = View.GONE
                }
                SimilarMoviesViewState.Loading -> {
                    binding.similarTryAgainButton.visibility = View.GONE
                    binding.similarLoadingProgressBar.visibility = View.VISIBLE
                    binding.similarMoviesRecyclerView.visibility = View.GONE
                }
                SimilarMoviesViewState.NoInternet -> {
                }
                is SimilarMoviesViewState.Success -> {
                    binding.similarTryAgainButton.visibility = View.GONE
                    binding.similarLoadingProgressBar.visibility = View.GONE
                    binding.similarMoviesRecyclerView.visibility = View.VISIBLE

                    similarMoviesAdapter.submitList(viewState.movies)
                }
            }
        }
    }

    private fun handleCreditsViewState() {
        viewModel.creditsViewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                CreditsViewState.Error -> {
                    binding.creditsTryAgainButton.visibility = View.VISIBLE
                    binding.creditsLoadingProgressBar.visibility = View.GONE
                    binding.creditsGroup.visibility = View.GONE
                }
                CreditsViewState.Loading -> {
                    binding.creditsTryAgainButton.visibility = View.GONE
                    binding.creditsLoadingProgressBar.visibility = View.VISIBLE
                    binding.creditsGroup.visibility = View.GONE
                }
                CreditsViewState.NoInternet -> {
                }
                is CreditsViewState.Success -> {
                    binding.creditsTryAgainButton.visibility = View.GONE
                    binding.creditsLoadingProgressBar.visibility = View.GONE
                    binding.creditsGroup.visibility = View.VISIBLE

                    actorsAdapter.submitList(viewState.actors)
                    directorsAdapter.submitList(viewState.directors)
                }
            }
        }
    }
}