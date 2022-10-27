package com.samfonsec.fuzecs.view.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.samfonsec.fuzecs.databinding.ComponentMatchViewBinding

class MatchView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding = ComponentMatchViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setFirstTeam(name: String, imageUri: String) {
        with(binding) {
            textviewTeam1Name.text = name
            imageviewTeam1.load(imageUri)
        }
    }

    fun setSecondTeam(name: String, imageUri: String) {
        with(binding) {
            textviewTeam2Name.text = name
            imageviewTeam2.load(imageUri)
        }
    }

}