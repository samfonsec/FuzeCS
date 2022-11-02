package com.samfonsec.fuzecs.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samfonsec.fuzecs.R
import com.samfonsec.fuzecs.databinding.ItemMatchBinding
import com.samfonsec.fuzecs.model.Match
import com.samfonsec.fuzecs.utils.loadImageOrPlaceholder

class MatchAdapter(
    private val onItemClicked: (Match) -> Unit
) : ListAdapter<Match, MatchAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { match ->
            holder.bind(match)
            holder.itemView.setOnClickListener { onItemClicked(match) }
        }
    }

    class ViewHolder(private val binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match) {
            with(binding) {
                match.getFirstTeam()?.let {
                    matchview.setFirstTeam(it.name, it.image_url)
                }
                match.getSecondTeam()?.let {
                    matchview.setSecondTeam(it.name, it.image_url)
                }
                imageviewLeague.loadImageOrPlaceholder(match.league.image_url)
                textviewLeague.text = match.getLeagueAndSerie()
            }
            setMatchTime(match)
        }

        private fun setMatchTime(match: Match) {
            with(binding) {
                root.context.run {
                    if (match.isLive()) {
                        imageviewDateBackground.setBackgroundColor(getColor(R.color.live_match_color))
                        textviewTime.text = getString(R.string.live)
                    } else {
                        textviewTime.text = match.getFormattedTime(getString(R.string.today))
                        imageviewDateBackground.isVisible != textviewTime.text.isNullOrEmpty()
                        imageviewDateBackground.setBackgroundColor(getColor(R.color.tag_color))
                    }
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Match> =
            object : DiffUtil.ItemCallback<Match>() {
                override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
                    return oldItem == newItem
                }
            }
    }
}