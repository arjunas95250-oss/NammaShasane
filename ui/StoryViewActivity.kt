package com.mindmatrix.nammashasane.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mindmatrix.nammashasane.databinding.ActivityStoryViewBinding

class StoryViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener { finish() }
    }
}
