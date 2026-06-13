package com.example.final_exam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class SplashActivity : AppCompatActivity() {

    lateinit var rootSplash: ConstraintLayout
    lateinit var layoutRecentMemo: LinearLayout
    lateinit var tvRecentTitle: TextView
    lateinit var tvRecentFirstLine: TextView
    lateinit var tvNewMemo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        rootSplash = findViewById(R.id.rootSplash)
        layoutRecentMemo = findViewById(R.id.layoutRecentMemo)
        tvRecentTitle = findViewById(R.id.tvRecentTitle)
        tvRecentFirstLine = findViewById(R.id.tvRecentFirstLine)
        tvNewMemo = findViewById(R.id.tvNewMemo)

        val latestMemo = MemoStorage.getLatest()

        if (latestMemo != null) {
            layoutRecentMemo.visibility = View.VISIBLE

            if (latestMemo.title == "") {
                tvRecentTitle.text = getString(R.string.hint_memo_title)
            } else {
                tvRecentTitle.text = latestMemo.title
            }

            val newlineIdx = latestMemo.content.indexOf('\n')
            if (newlineIdx >= 0) {
                tvRecentFirstLine.text = latestMemo.content.substring(0, newlineIdx)
            } else {
                tvRecentFirstLine.text = latestMemo.content
            }

            layoutRecentMemo.setOnClickListener {
                val intent = Intent(this, MemoEditActivity::class.java)
                intent.putExtra("memo_id", latestMemo.id)
                startActivity(intent)
                finish()
            }
        } else {
            tvNewMemo.visibility = View.VISIBLE
        }

        rootSplash.setOnClickListener {
            val intent = Intent(this, MemoListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
