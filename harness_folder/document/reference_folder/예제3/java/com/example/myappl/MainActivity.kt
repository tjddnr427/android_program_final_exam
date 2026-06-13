package com.example.myappl

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.myappl.R.id
import com.example.myappl.R.id.viewpager2

class MainActivity : AppCompatActivity() {

    lateinit var viewPager2: ViewPager2
    lateinit var myAdapter: MyPageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        // 1. 뷰페이져 초기화
        viewPager2 = findViewById(R.id.viewpager2)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //2. Add Fragments to the List in the adapter class
        myAdapter = MyPageAdapter(supportFragmentManager, lifecycle)
        myAdapter.addFragmentToList(OneFragment())
        myAdapter.addFragmentToList(TwoFragment())

        // 3.   뷰페이져와 어댑터 연결 Connecting the adapter with ViewPager2
        viewPager2.adapter = myAdapter
    }
}