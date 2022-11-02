package com.samfonsec.fuzecs.view

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.samfonsec.fuzecs.R
import com.samfonsec.fuzecs.databinding.ActivityMainBinding
import com.samfonsec.fuzecs.utils.Constants.MAIN_BACK_STACK
import com.samfonsec.fuzecs.view.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showHomeFragment()
        setupBackPressed()
    }

    private fun showHomeFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.framelayout_container, HomeFragment())
            .addToBackStack(MAIN_BACK_STACK)
            .commit()
    }

    private fun setupBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isFirstFragment())
                    finish()
            }
        })
    }

    private fun isFirstFragment() = supportFragmentManager.fragments.size == 1
}
