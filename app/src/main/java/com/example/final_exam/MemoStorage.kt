package com.example.final_exam

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object MemoStorage {

    private const val PREF_NAME = "memo_prefs"
    private const val KEY_MEMOS = "memos"

    fun saveAll(context: Context, memos: ArrayList<Memo>) {
        val array = JSONArray()
        for (memo in memos) {
            val obj = JSONObject()
            obj.put("id", memo.id)
            obj.put("title", memo.title)
            obj.put("content", memo.content)
            obj.put("updatedAt", memo.updatedAt)
            array.put(obj)
        }
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit().putString(KEY_MEMOS, array.toString()).apply()
    }

    fun loadAll(context: Context): ArrayList<Memo> {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = pref.getString(KEY_MEMOS, "[]") ?: "[]"
        val array = JSONArray(json)
        val list = ArrayList<Memo>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            list.add(
                Memo(
                    id = obj.getString("id"),
                    title = obj.getString("title"),
                    content = obj.getString("content"),
                    updatedAt = obj.getLong("updatedAt")
                )
            )
        }
        return list
    }

    fun delete(context: Context, id: String) {
        val list = loadAll(context)
        list.removeAll { it.id == id }
        saveAll(context, list)
    }

    // 날짜 기준 내림차순 정렬
    fun loadSorted(context: Context): ArrayList<Memo> {
        val list = loadAll(context)
        list.sortByDescending { it.updatedAt }
        return list
    }

    // 가장 최근 메모 1개 반환 (없으면 null)
    fun loadLatest(context: Context): Memo? {
        return loadSorted(context).firstOrNull()
    }
}
