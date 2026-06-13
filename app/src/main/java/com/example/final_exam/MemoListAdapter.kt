package com.example.final_exam

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.Calendar

class MemoListAdapter(var context: Context, var memoList: ArrayList<Memo>) : BaseAdapter() {

    override fun getCount(): Int = memoList.size

    override fun getItem(position: Int): Any = memoList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = View.inflate(context, R.layout.item_memo, null)

        val tvTitle = view.findViewById<TextView>(R.id.tvItemTitle)
        val tvFirstLine = view.findViewById<TextView>(R.id.tvItemFirstLine)
        val tvDate = view.findViewById<TextView>(R.id.tvItemDate)

        val memo = memoList[position]

        if (memo.title == "") {
            tvTitle.text = context.getString(R.string.hint_memo_title)
        } else {
            tvTitle.text = memo.title
        }

        val newlineIdx = memo.content.indexOf('\n')
        if (newlineIdx >= 0) {
            tvFirstLine.text = memo.content.substring(0, newlineIdx)
        } else {
            tvFirstLine.text = memo.content
        }

        tvDate.text = formatDate(memo.updatedAt)

        return view
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
