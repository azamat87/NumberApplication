package com.app.numberapplication.main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.numberapplication.R
import com.app.numberapplication.numbers.presentation.NumbersFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, NumbersFragment())
            .commit()
    }
}