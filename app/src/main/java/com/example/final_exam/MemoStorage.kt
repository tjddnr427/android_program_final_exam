package com.example.final_exam

object MemoStorage {
    val memos = ArrayList<Memo>()

    fun delete(id: String) {
        var indexToRemove = -1
        for (i in 0 until memos.size) {
            if (memos[i].id == id) {
                indexToRemove = i
                break
            }
        }
        if (indexToRemove >= 0) {
            memos.removeAt(indexToRemove)
        }
    }

    fun getLatest(): Memo? {
        if (memos.size > 0) {
            return memos[0]
        }
        return null
    }
}
