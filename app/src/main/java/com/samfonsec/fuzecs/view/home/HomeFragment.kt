package com.samfonsec.fuzecs.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samfonsec.fuzecs.R
import com.samfonsec.fuzecs.data.api.Status
import com.samfonsec.fuzecs.databinding.FragmentHomeBinding
import com.samfonsec.fuzecs.model.Match
import com.samfonsec.fuzecs.utils.Constants.FIRST_PAGE
import com.samfonsec.fuzecs.utils.Constants.MAIN_BACK_STACK
import com.samfonsec.fuzecs.utils.PaginationScrollListener
import com.samfonsec.fuzecs.utils.hide
import com.samfonsec.fuzecs.utils.show
import com.samfonsec.fuzecs.view.adapter.MatchAdapter
import com.samfonsec.fuzecs.view.details.DetailsFragment
import com.samfonsec.fuzecs.viewModel.home.HomeViewModel
import com.samfonsec.fuzecs.viewModel.home.HomeViewModel.Companion.RUNNING_MATCHES_ERROR
import com.samfonsec.fuzecs.viewModel.home.HomeViewModel.Companion.UPCOMING_MATCHES_ERROR
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private val matches: ArrayList<Match> = arrayListOf()
    private var lastLoadedPage = FIRST_PAGE
    private var isFirstLoading = true
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private val paginationScrollListener by lazy {
        object : PaginationScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                binding.recyclerviewMatches.removeOnScrollListener(this)
                lastLoadedPage = page
                isFirstLoading = false
                loadMoreMatches()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupObservables()
        loadRunningMatches()
    }

    private fun setupList() {
        with(binding.recyclerviewMatches) {
            layoutManager = linearLayoutManager
            adapter = MatchAdapter { onItemClicked(it) }.apply {
                submitList(matches)
            }
        }
    }

    private fun setupObservables() {
        viewModel.onResult.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.Success -> onSuccess(status.data)
                is Status.Error -> onError(status.errorType)
                is Status.Loading -> showProgress()
            }
        }
    }

    private fun onSuccess(matchList: List<Match>?) {
        hideProgress()
        val previousContentSize = matches.size
        matchList?.let { matches += it }
        with(binding.recyclerviewMatches) {
            adapter?.notifyItemRangeInserted(previousContentSize, matches.size)
            addOnScrollListener(paginationScrollListener)
        }
    }

    private fun onError(errorType: Int) {
        hideProgress(withError = true)
        with(binding.errorview) {
            root.show()
            buttonTryAgain.setOnClickListener {
                root.hide()
                onTryAgainClicked(errorType)
            }
        }
    }

    private fun onTryAgainClicked(errorType: Int) {
        when (errorType) {
            RUNNING_MATCHES_ERROR -> {
                loadRunningMatches()
            }
            UPCOMING_MATCHES_ERROR -> {
                showProgress()
                loadMoreMatches()
            }
        }
    }

    private fun showProgress() {
        with(binding) {
            if (isFirstLoading) {
                progressbar.show()
                recyclerviewMatches.hide()
            } else
                progressbarMoreItems.show()
        }
    }

    private fun hideProgress(withError: Boolean = false) {
        with(binding) {
            progressbar.hide()
            progressbarMoreItems.hide()
            recyclerviewMatches.isVisible = !withError
        }
    }

    private fun loadRunningMatches() = viewModel.getRunningMatches()

    private fun loadMoreMatches() = viewModel.loadMoreUpcomingMatches(lastLoadedPage)

    private fun onItemClicked(match: Match) {
        activity?.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.framelayout_container, DetailsFragment.newInstance(match))
                .addToBackStack(MAIN_BACK_STACK)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}