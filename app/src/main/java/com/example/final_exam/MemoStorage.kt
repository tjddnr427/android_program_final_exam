package com.example.final_exam

import android.content.Context

class MemoStorage {

    fun saveAll(context: Context, memos: ArrayList<Memo>) {
        val pref = context.getSharedPreferences("memo_prefs", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.putInt("count", memos.size)
        for (i in 0 until memos.size) {
            editor.putString("id_$i", memos[i].id)
            editor.putString("title_$i", memos[i].title)
            editor.putString("content_$i", memos[i].content)
            editor.putLong("date_$i", memos[i].updatedAt)
        }
        editor.apply()
    }

    fun loadAll(context: Context): ArrayList<Memo> {
        val pref = context.getSharedPreferences("memo_prefs", Context.MODE_PRIVATE)
        val count = pref.getInt("count", 0)
        val list = ArrayList<Memo>()
        for (i in 0 until count) {
            val id = pref.getString("id_$i", "") ?: ""
            val title = pref.getString("title_$i", "") ?: ""
            val content = pref.getString("content_$i", "") ?: ""
            val date = pref.getLong("date_$i", 0L)
            if (id != "") {
                list.add(Memo(id, title, content, date))
            }
        }
        return list
    }

    fun delete(context: Context, id: String) {
        val list = loadAll(context)
        var indexToRemove = -1
        for (i in 0 until list.size) {
            if (list[i].id == id) {
                indexToRemove = i
                break
            }
        }
        if (indexToRemove >= 0) {
            list.removeAt(indexToRemove)
        }
        saveAll(context, list)
    }

    fun loadSorted(context: Context): ArrayList<Memo> {
        val list = loadAll(context)
        for (i in 0 until list.size - 1) {
            for (j in 0 until list.size - 1 - i) {
                if (list[j].updatedAt < list[j + 1].updatedAt) {
                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
        }
        return list
    }

    fun loadLatest(context: Context): Memo? {
        val list = loadSorted(context)
        if (list.size > 0) {
            return list[0]
        }
        return null
    }
}
