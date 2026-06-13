package com.example.final_exam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.Calendar

class SplashActivity : AppCompatActivity() {

    lateinit var rootSplash: ConstraintLayout
    lateinit var layoutRecentMemo: LinearLayout
    lateinit var tvRecentTitle: TextView
    lateinit var tvRecentFirstLine: TextView
    lateinit var tvRecentDate: TextView
    lateinit var tvNewMemo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        rootSplash = findViewById(R.id.rootSplash)
        layoutRecentMemo = findViewById(R.id.layoutRecentMemo)
        tvRecentTitle = findViewById(R.id.tvRecentTitle)
        tvRecentFirstLine = findViewById(R.id.tvRecentFirstLine)
        tvRecentDate = findViewById(R.id.tvRecentDate)
        tvNewMemo = findViewById(R.id.tvNewMemo)

        val storage = MemoStorage()
        val latestMemo = storage.loadLatest(this)

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

            tvRecentDate.text = formatDate(latestMemo.updatedAt)

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

    private fun formatDate(timeMs: Long): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timeMs
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val min = cal.get(Calendar.MINUTE)
        return String.format("%d/%02d/%02d %02d:%02d", year, month, day, hour, min)
    }
}
