package com.example.myappl2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var baseLayout : LinearLayout
    lateinit var imageView1: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        baseLayout = findViewById<LinearLayout>(R.id.main)
        //툴바를 액션바로 설정
        setSupportActionBar(toolbar)
        title = "동물사진바꾸기"
        imageView1 = findViewById<ImageView>(R.id.imageView1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        var mInflater = menuInflater
        mInflater.inflate(R.menu.mymenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dog-> {
                imageView1.setImageResource(R.drawable.dog)
                return true
            }
            R.id.cat -> {
                imageView1.setImageResource(R.drawable.cat)
                return true
            }
            R.id.rabbit -> {
                imageView1.setImageResource(R.drawable.rabbit)
                return true
            }
        }
        return false
    }
}