package com.samfonsec.fuzecs.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samfonsec.fuzecs.R
import com.samfonsec.fuzecs.R.drawable.*
import com.samfonsec.fuzecs.databinding.ItemPlayerBinding
import com.samfonsec.fuzecs.model.Player
import com.samfonsec.fuzecs.utils.hide
import com.samfonsec.fuzecs.utils.invisible
import com.samfonsec.fuzecs.utils.loadImageOrPlaceholder

typealias Players = Pair<Player?, Player?>

class PlayerAdapter : ListAdapter<Players, PlayerAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemPlayerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(players: Players) {
            setupFirstPlayer(players.first)
            setupSecondPlayer(players.second)
        }

        private fun setupFirstPlayer(player: Player?) {
            with(binding) {
                player?.let {
                    textviewPlayer1Nickname.text = it.name
                    textviewPlayer1Name.text = it.first_name
                    imageviewPlayer1.loadImageOrPlaceholder(it.image_url, placeholder_rounded)
                } ?: hideFirstPlayer()
            }
        }

        private fun hideFirstPlayer() {
            with(binding) {
                textviewPlayer1Nickname.hide()
                textviewPlayer1Name.hide()
                imageviewPlayer1.hide()
                imageviewBgPlayer1.invisible()
            }
        }

        private fun setupSecondPlayer(player: Player?) {
            with(binding) {
                player?.let {
                    textviewPlayer2Nickname.text = it.name
                    textviewPlayer2Name.text = it.first_name
                    imageviewPlayer2.loadImageOrPlaceholder(it.image_url, placeholder_rounded)
                } ?: hideSecondPlayer()
            }
        }

        private fun hideSecondPlayer() {
            with(binding) {
                textviewPlayer2Nickname.hide()
                textviewPlayer2Name.hide()
                imageviewPlayer2.hide()
                imageviewBgPlayer2.invisible()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Players> =
            object : DiffUtil.ItemCallback<Players>() {
                override fun areItemsTheSame(oldItem: Players, newItem: Players): Boolean {
                    return oldItem.first?.id == newItem.first?.id && oldItem.second?.id == newItem.second?.id
                }

                override fun areContentsTheSame(oldItem: Players, newItem: Players): Boolean {
                    return oldItem.first == newItem.first && oldItem.second == newItem.second
                }
            }
    }
}