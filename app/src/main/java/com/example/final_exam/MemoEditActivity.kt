package com.example.final_exam

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.UUID

class MemoEditActivity : AppCompatActivity() {

    lateinit var etMemoTitle: EditText
    lateinit var etMemoContent: EditText

    var memoId: String = ""
    var isNewMemo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_edit)

        etMemoTitle = findViewById(R.id.etMemoTitle)
        etMemoContent = findViewById(R.id.etMemoContent)

        val receivedId = intent.getStringExtra("memo_id")

        if (receivedId != null) {
            // 기존 메모 열기
            memoId = receivedId
            isNewMemo = false
            val memos = MemoStorage.loadAll(this)
            val memo = memos.find { it.id == memoId }
            if (memo != null) {
                etMemoTitle.setText(memo.title)
                etMemoContent.setText(memo.content)
            }
        } else {
            // 새 메모: UUID 생성
            memoId = UUID.randomUUID().toString()
            isNewMemo = true
        }
    }

    override fun onPause() {
        super.onPause()
        saveMemo()
    }

    private fun saveMemo() {
        val title = etMemoTitle.text.toString().trim()
        val content = etMemoContent.text.toString().trim()

        // 이름이 hint 그대로이고 내용도 비어있으면 저장하지 않음
        val isEmptyTitle = title.isEmpty() || title == getString(R.string.hint_memo_title)
        if (isEmptyTitle && content.isEmpty()) {
            return
        }

        val memos = MemoStorage.loadAll(this)
        val existing = memos.find { it.id == memoId }

        if (existing != null) {
            existing.title = title.ifEmpty { getString(R.string.hint_memo_title) }
            existing.content = content
            existing.updatedAt = System.currentTimeMillis()
        } else {
            memos.add(
                Memo(
                    id = memoId,
                    title = title.ifEmpty { getString(R.string.hint_memo_title) },
                    content = content,
                    updatedAt = System.currentTimeMillis()
                )
            )
        }

        MemoStorage.saveAll(this, memos)
    }
}
