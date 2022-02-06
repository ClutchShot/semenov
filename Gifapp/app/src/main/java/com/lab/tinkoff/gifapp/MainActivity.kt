package com.lab.tinkoff.gifapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.lab.tinkoff.gifapp.databinding.ActivityMainBinding
import com.lab.tinkoff.gifapp.ui.adapter.PageAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewPager = binding.viewpager
        viewPager.adapter = PageAdapter(supportFragmentManager)

        val tabs = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}