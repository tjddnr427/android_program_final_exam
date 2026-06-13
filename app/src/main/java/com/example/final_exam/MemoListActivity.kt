package com.example.final_exam

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MemoListActivity : AppCompatActivity() {

    lateinit var listViewMemo: ListView
    lateinit var adapter: MemoListAdapter
    var memoList = ArrayList<Memo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_list)

        title = getString(R.string.title_all_memo)

        listViewMemo = findViewById(R.id.listViewMemo)
        adapter = MemoListAdapter(this, memoList)
        listViewMemo.adapter = adapter

        // 메모 클릭: MemoEditActivity로 이동
        listViewMemo.setOnItemClickListener { _, _, position, _ ->
            val memo = memoList[position]
            val intent = Intent(this, MemoEditActivity::class.java)
            intent.putExtra("memo_id", memo.id)
            startActivity(intent)
        }

        // 메모 롱클릭: 즉시 삭제
        listViewMemo.setOnItemLongClickListener { _, _, position, _ ->
            val memo = memoList[position]
            MemoStorage.delete(this, memo.id)
            memoList.removeAt(position)
            adapter.notifyDataSetChanged()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        // 화면 돌아올 때마다 목록 갱신
        memoList.clear()
        memoList.addAll(MemoStorage.loadSorted(this))
        adapter.notifyDataSetChanged()
    }

    // 상단 액션바 메뉴 (새 메모 추가)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_memo_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_new_memo -> {
                val intent = Intent(this, MemoEditActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return false
    }
}
