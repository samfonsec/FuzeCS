package com.samfonsec.fuzecs.view.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import coil.transform.CircleCropTransformation
import com.samfonsec.fuzecs.R
import com.samfonsec.fuzecs.databinding.ComponentMatchViewBinding
import com.samfonsec.fuzecs.utils.loadIfNotNull

class MatchView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding = ComponentMatchViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setFirstTeam(name: String, imageUri: String?) {
        with(binding) {
            textviewTeam1Name.text = name
            imageviewTeam1.loadIfNotNull(imageUri)
        }
    }

    fun setSecondTeam(name: String, imageUri: String?) {
        with(binding) {
            textviewTeam2Name.text = name
            imageviewTeam2.loadIfNotNull(imageUri)
        }
    }
}