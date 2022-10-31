package com.samfonsec.fuzecs.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.samfonsec.fuzecs.data.api.Status
import com.samfonsec.fuzecs.databinding.FragmentHomeBinding
import com.samfonsec.fuzecs.model.Match
import com.samfonsec.fuzecs.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        viewModel.getMatches(1)
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

    private fun onSuccess(matches: List<Match>?) {
        hideProgress()
        binding.recyclerviewMatches.adapter = MatchAdapter {
            onItemClicked(it)
        }.apply {
            submitList(matches)
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

    private fun onItemClicked(match: Match) {
        Snackbar.make(binding.root, match.begin_at.toString(), Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}