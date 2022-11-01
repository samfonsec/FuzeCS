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
import com.google.android.material.snackbar.Snackbar
import com.samfonsec.fuzecs.data.api.Status
import com.samfonsec.fuzecs.databinding.FragmentHomeBinding
import com.samfonsec.fuzecs.model.Match
import com.samfonsec.fuzecs.utils.Constants.FIRST_PAGE
import com.samfonsec.fuzecs.utils.PaginationScrollListener
import com.samfonsec.fuzecs.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private val matches: ArrayList<Match> = arrayListOf()
    private var lastLoadedPage = FIRST_PAGE
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private val paginationScrollListener by lazy {
        object : PaginationScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                binding.recyclerviewMatches.removeOnScrollListener(this)
                lastLoadedPage = page
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
        viewModel.getRunningMatches()
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
                is Status.Error -> onError(status.message)
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

    private fun onError(message: String) {
        hideProgress()
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE).show()
    }

    private fun showProgress() {
        with(binding) {
            progressbar.isVisible = true
            recyclerviewMatches.isVisible = false
        }
    }

    private fun hideProgress() {
        with(binding) {
            progressbar.isVisible = false
            recyclerviewMatches.isVisible = true
        }
    }

    private fun loadMoreMatches() {
        viewModel.loadMoreUpcomingMatches(lastLoadedPage)
    }

    private fun onItemClicked(match: Match) {
        Snackbar.make(binding.root, match.begin_at.toString(), Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}