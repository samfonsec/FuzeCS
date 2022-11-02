package com.samfonsec.fuzecs.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.samfonsec.fuzecs.R
import com.samfonsec.fuzecs.data.api.Status
import com.samfonsec.fuzecs.databinding.FragmentDetailsBinding
import com.samfonsec.fuzecs.model.Match
import com.samfonsec.fuzecs.model.OpponentsResponse
import com.samfonsec.fuzecs.utils.parcelable
import com.samfonsec.fuzecs.view.adapter.PlayerAdapter
import com.samfonsec.fuzecs.viewModel.details.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels()

    private val match: Match? by lazy { arguments?.parcelable(ARGS_MATCH) }
    private val playerAdapter = PlayerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackPressed()
        setupList()
        buildUi()
        setupObservables()
        match?.let { viewModel.getOpponents(it.id) }
    }

    private fun setupBackPressed() {
        activity?.run {
            onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackButtonPressed()
                }
            })
        }
    }

    private fun onBackButtonPressed() {
        activity?.run {
            supportFragmentManager.beginTransaction().remove(this@DetailsFragment).commit()
        }
    }

    private fun setupList() {
        with(binding.recyclerviewPlayers) {
            layoutManager = LinearLayoutManager(context)
            adapter = playerAdapter
        }
    }

    private fun buildUi() {
        match?.run {
            with(binding) {
                toolbar.title = getLeagueAndSerie(LEAGUE_SERIE_SEPARATOR)
                getFirstTeam()?.let {
                    matchviewDetails.setFirstTeam(it.name, it.image_url)
                }
                getSecondTeam()?.let {
                    matchviewDetails.setSecondTeam(it.name, it.image_url)
                }
                textviewMatchTime.text = if (isLive())
                    getString(R.string.live)
                else
                    getFormattedTime(getString(R.string.today))
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

    private fun onSuccess(opponents: OpponentsResponse?) {
        hideProgress()
        playerAdapter.submitList(opponents?.getPlayers())
    }

    private fun onError(message: String) {
        hideProgress()
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showProgress() {
        with(binding) {
            progressbar.isVisible = true
            groupDetails.isVisible = false
        }
    }

    private fun hideProgress() {
        with(binding) {
            progressbar.isVisible = false
            groupDetails.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARGS_MATCH = "args_match"
        private const val LEAGUE_SERIE_SEPARATOR = " | "

        fun newInstance(match: Match) = DetailsFragment().apply {
            arguments = bundleOf(ARGS_MATCH to match)
        }
    }
}