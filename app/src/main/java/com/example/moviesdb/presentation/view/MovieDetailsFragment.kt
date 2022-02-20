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
import com.example.moviesdb.presentation.viewmodel.MovieDetailsViewModel
import com.example.moviesdb.presentation.viewstate.DetailsViewState
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<MovieDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private val viewModel: MovieDetailsViewModel by viewModels { viewModelFactory }

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

        viewModel.getMovieDetails(movieId = args.movieId)
        handleDetailsViewState()
    }

    private fun handleDetailsViewState() {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                DetailsViewState.Error -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.tryAgainButton.visibility = View.VISIBLE
                    binding.detailsGroup.visibility = View.GONE
                }
                DetailsViewState.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    binding.tryAgainButton.visibility = View.GONE
                    binding.detailsGroup.visibility = View.GONE
                }
                DetailsViewState.NoInternet -> {
                }
                is DetailsViewState.Success -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.tryAgainButton.visibility = View.GONE
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
}