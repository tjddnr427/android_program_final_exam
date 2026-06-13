package com.example.myappl

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPageAdapter(fm: FragmentManager, lc: Lifecycle) : FragmentStateAdapter(fm,lc)
{
    var fragmentList: ArrayList<Fragment> = ArrayList()
    fun addFragmentToList(fragment: Fragment) {
        fragmentList.add(fragment)
    }
    override fun createFragment(p0: Int): Fragment {
        return fragmentList.get(p0)
    }

    override fun getItemCount(): Int {
       return fragmentList.size
    }

}