package com.example.final_exam

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

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
                val memos = MemoStorage().loadAll(this)
                var found: Memo? = null
                for (i in 0 until memos.size) {
                    if (memos[i].id == memoId) {
                        found = memos[i]
                        break
                    }
                }
                if (found != null) {
                    etMemoTitle.setText(found.title)
                    etMemoContent.setText(found.content)
                }
            }
        } else {
            memoId = System.currentTimeMillis().toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_memo_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_go_to_list -> {
                finish()
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

        if (title == "" && content == "") {
            return
        }

        val memos = MemoStorage().loadAll(this)

        var existing: Memo? = null
        for (i in 0 until memos.size) {
            if (memos[i].id == memoId) {
                existing = memos[i]
                break
            }
        }

        if (existing != null) {
            if (title == "") {
                existing.title = getString(R.string.hint_memo_title)
            } else {
                existing.title = title
            }
            existing.content = content
            existing.updatedAt = System.currentTimeMillis()
        } else {
            val newTitle: String
            if (title == "") {
                newTitle = getString(R.string.hint_memo_title)
            } else {
                newTitle = title
            }
            memos.add(Memo(memoId, newTitle, content, System.currentTimeMillis()))
        }

        MemoStorage().saveAll(this, memos)
    }
}
