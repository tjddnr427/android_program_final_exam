package com.example.final_exam

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MemoListActivity : AppCompatActivity() {

    lateinit var listViewMemo: ListView
    lateinit var adapter: MemoListAdapter
    var memoList = ArrayList<Memo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbarList)
        setSupportActionBar(toolbar)

        listViewMemo = findViewById(R.id.listViewMemo)
        adapter = MemoListAdapter(this, memoList)
        listViewMemo.adapter = adapter

        listViewMemo.setOnItemClickListener { _, _, position, _ ->
            val memo = memoList[position]
            val intent = Intent(this, MemoEditActivity::class.java)
            intent.putExtra("memo_id", memo.id)
            startActivity(intent)
        }

        listViewMemo.setOnItemLongClickListener { _, _, position, _ ->
            val memo = memoList[position]
            AlertDialog.Builder(this)
                .setMessage("삭제하시겠습니까?")
                .setPositiveButton("삭제") { _, _ ->
                    MemoStorage.delete(memo.id)
                    memoList.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("취소", null)
                .show()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        memoList.clear()
        memoList.addAll(MemoStorage.memos)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_memo_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_new_memo -> {
                val newId = System.currentTimeMillis().toString()
                val intent = Intent(this, MemoEditActivity::class.java)
                intent.putExtra("memo_id", newId)
                intent.putExtra("is_new", true)
                startActivity(intent)
                return true
            }
        }
        return false
    }
}
