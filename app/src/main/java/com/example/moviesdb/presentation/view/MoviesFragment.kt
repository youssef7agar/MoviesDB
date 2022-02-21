package com.example.moviesdb.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AbsListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.moviesdb.MyApplication
import com.example.moviesdb.common.hideKeyboard
import com.example.moviesdb.databinding.FragmentMoviesBinding
import com.example.moviesdb.di.ViewModelProviderFactory
import com.example.moviesdb.presentation.adapter.YearMoviesAdapter
import com.example.moviesdb.presentation.viewmodel.MoviesViewModel
import com.example.moviesdb.presentation.viewstate.MoviesViewEvent
import com.example.moviesdb.presentation.viewstate.MoviesViewState
import javax.inject.Inject

class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: YearMoviesAdapter
    private var isScrolling = false

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private val viewModel: MoviesViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).provideAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tryAgainButton.setOnClickListener {
            if (binding.searchEditText.text.isEmpty())
                viewModel.getPopularMovies()
            else
                viewModel.search(binding.searchEditText.text.toString())
        }

        setUpRecyclerView()
        handleViewState()
        handleViewEvent()

        binding.searchEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                requireActivity().hideKeyboard()
                viewModel.search(binding.searchEditText.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        binding.searchImage.setOnClickListener {
            requireActivity().hideKeyboard()
            viewModel.search(binding.searchEditText.text.toString())
        }

        (binding.yearMoviesRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false
    }

    private fun handleViewState() {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                MoviesViewState.Error -> {
                    binding.yearMoviesRecyclerView.visibility = View.GONE
                    binding.tryAgainButton.visibility = View.VISIBLE
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.loadMoreProgressBar.visibility = View.GONE
                }
                MoviesViewState.Loading -> {
                    binding.yearMoviesRecyclerView.visibility = View.GONE
                    binding.tryAgainButton.visibility = View.GONE
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    binding.loadMoreProgressBar.visibility = View.GONE
                }
                MoviesViewState.NoInternet -> {
                    binding.yearMoviesRecyclerView.visibility = View.GONE
                    binding.tryAgainButton.visibility = View.VISIBLE
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.loadMoreProgressBar.visibility = View.GONE
                }
                is MoviesViewState.Success -> {
                    binding.tryAgainButton.visibility = View.GONE
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.yearMoviesRecyclerView.visibility = View.VISIBLE
                    adapter.submitList(viewState.movies)
                    binding.loadMoreProgressBar.isVisible = viewState.loadingMore
                }
            }
        }
    }

    private fun handleViewEvent() {
        viewModel.viewEvent.observe(viewLifecycleOwner) { event ->
            event.peekContent().let { viewEvent ->
                when (viewEvent) {
                    MoviesViewEvent.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Something went wrong. please try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    MoviesViewEvent.NoInternet -> {
                        Toast.makeText(
                            requireContext(),
                            "It seems like you lost your internet connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = YearMoviesAdapter { movieId ->
            val action =
                MoviesFragmentDirections.toMovieDetailsAction(movieId)
            binding.root.findNavController().navigate(action)
        }
        binding.apply {
            yearMoviesRecyclerView.adapter = adapter
            yearMoviesRecyclerView.addOnScrollListener(this@MoviesFragment.scrollListener)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val shouldPaginate = isAtLastItem && isNotAtBeginning && isScrolling

            if (shouldPaginate) {
                if (binding.searchEditText.text.toString().isEmpty())
                    viewModel.nextPage()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }
}