package com.example.final_exam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        val latestMemo = MemoStorage.loadLatest(this)

        if (latestMemo != null) {
            layoutRecentMemo.visibility = View.VISIBLE

            val firstLine = if (latestMemo.content.isNotEmpty())
                latestMemo.content.lines().first() else ""
            val dateStr = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                .format(Date(latestMemo.updatedAt))

            tvRecentTitle.text = latestMemo.title.ifEmpty { getString(R.string.hint_memo_title) }
            tvRecentFirstLine.text = firstLine
            tvRecentDate.text = dateStr

            // 이전 기록 카드 클릭 → 해당 메모 편집으로 바로 이동
            layoutRecentMemo.setOnClickListener {
                val intent = Intent(this, MemoEditActivity::class.java)
                intent.putExtra("memo_id", latestMemo.id)
                startActivity(intent)
                finish()
            }
        } else {
            tvNewMemo.visibility = View.VISIBLE
        }

        // 배경(카드 외 다른 곳) 클릭 → 전체 메모 목록으로
        rootSplash.setOnClickListener {
            val intent = Intent(this, MemoListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // 뒤로가기 = 앱 종료
    override fun onBackPressed() {
        finish()
    }
}
