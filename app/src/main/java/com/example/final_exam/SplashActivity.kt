package com.example.final_exam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SplashActivity : AppCompatActivity() {

    lateinit var btnGoToRecent: Button
    lateinit var layoutRecentMemo: LinearLayout
    lateinit var tvRecentTitle: TextView
    lateinit var tvRecentFirstLine: TextView
    lateinit var tvRecentDate: TextView
    lateinit var tvNewMemo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        btnGoToRecent = findViewById(R.id.btnGoToRecent)
        layoutRecentMemo = findViewById(R.id.layoutRecentMemo)
        tvRecentTitle = findViewById(R.id.tvRecentTitle)
        tvRecentFirstLine = findViewById(R.id.tvRecentFirstLine)
        tvRecentDate = findViewById(R.id.tvRecentDate)
        tvNewMemo = findViewById(R.id.tvNewMemo)

        val latestMemo = MemoStorage.loadLatest(this)

        if (latestMemo != null) {
            // 메모가 있을 때: 바로가기 버튼 + 최신 메모 카드 표시
            btnGoToRecent.visibility = View.VISIBLE
            layoutRecentMemo.visibility = View.VISIBLE

            val firstLine = if (latestMemo.content.isNotEmpty())
                latestMemo.content.lines().first() else ""
            val dateStr = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                .format(Date(latestMemo.updatedAt))

            tvRecentTitle.text = latestMemo.title.ifEmpty { getString(R.string.hint_memo_title) }
            tvRecentFirstLine.text = firstLine
            tvRecentDate.text = dateStr

            // 버튼 클릭: MemoListActivity → MemoEditActivity 순서로 스택 쌓고 종료
            btnGoToRecent.setOnClickListener {
                openRecentMemo(latestMemo.id)
            }
            layoutRecentMemo.setOnClickListener {
                openRecentMemo(latestMemo.id)
            }

        } else {
            // 메모가 없을 때: 새 메모 작성 텍스트 표시
            tvNewMemo.visibility = View.VISIBLE
            tvNewMemo.setOnClickListener {
                openNewMemo()
            }
        }
    }

    // 뒤로가기 = 앱 종료
    override fun onBackPressed() {
        finish()
    }

    private fun openRecentMemo(memoId: String) {
        // MemoListActivity를 먼저 스택에 쌓고, 그 위에 MemoEditActivity 오픈
        val listIntent = Intent(this, MemoListActivity::class.java)
        startActivity(listIntent)

        val editIntent = Intent(this, MemoEditActivity::class.java)
        editIntent.putExtra("memo_id", memoId)
        startActivity(editIntent)

        finish()
    }

    private fun openNewMemo() {
        val listIntent = Intent(this, MemoListActivity::class.java)
        startActivity(listIntent)

        val editIntent = Intent(this, MemoEditActivity::class.java)
        startActivity(editIntent)

        finish()
    }
}
