package com.samfonsec.fuzecs.view.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.samfonsec.fuzecs.databinding.ComponentMatchViewBinding
import com.samfonsec.fuzecs.utils.loadImageOrPlaceholder

class MatchView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding = ComponentMatchViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setFirstTeam(name: String, imageUri: String?) {
        with(binding) {
            textviewTeam1Name.text = name
            imageviewTeam1.loadImageOrPlaceholder(imageUri)
        }
    }

    fun setSecondTeam(name: String, imageUri: String?) {
        with(binding) {
            textviewTeam2Name.text = name
            imageviewTeam2.loadImageOrPlaceholder(imageUri)
        }
    }
}