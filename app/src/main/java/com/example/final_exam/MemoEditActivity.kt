package com.example.final_exam

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.UUID

class MemoEditActivity : AppCompatActivity() {

    lateinit var etMemoTitle: EditText
    lateinit var etMemoContent: EditText

    var memoId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_edit)

        val toolbar = findViewById<Toolbar>(R.id.toolbarEdit)
        setSupportActionBar(toolbar)

        etMemoTitle = findViewById(R.id.etMemoTitle)
        etMemoContent = findViewById(R.id.etMemoContent)

        val receivedId = intent.getStringExtra("memo_id")
        val isNew = intent.getBooleanExtra("is_new", false)

        if (receivedId != null) {
            memoId = receivedId
            if (!isNew) {
                // 기존 메모 불러오기
                val memo = MemoStorage.loadAll(this).find { it.id == memoId }
                if (memo != null) {
                    etMemoTitle.setText(memo.title)
                    etMemoContent.setText(memo.content)
                }
            }
        } else {
            memoId = UUID.randomUUID().toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_memo_edit, menu)
        return true
    }

    // 목록으로 버튼 클릭
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_go_to_list -> {
                val intent = Intent(this, MemoListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        saveMemo()
    }

    private fun saveMemo() {
        val title = etMemoTitle.text.toString().trim()
        val content = etMemoContent.text.toString().trim()

        // 이름 미수정 + 내용 비어있으면 저장하지 않음
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
