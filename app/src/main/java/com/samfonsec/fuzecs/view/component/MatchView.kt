package com.samfonsec.fuzecs.view.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.samfonsec.fuzecs.databinding.ComponentMatchViewBinding

class MatchView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding = ComponentMatchViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setFirstTeam(name: String, imageUri: String) {

    }

    fun setSecondTeam(name: String, imageUri: String) {

    }

}