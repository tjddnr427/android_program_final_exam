package com.example.final_exam

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        tvTitle.text = memo.title.ifEmpty { context.getString(R.string.hint_memo_title) }

        val firstLine = if (memo.content.isNotEmpty())
            memo.content.lines().first() else ""
        tvFirstLine.text = firstLine

        tvDate.text = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
            .format(Date(memo.updatedAt))

        return view
    }
}
