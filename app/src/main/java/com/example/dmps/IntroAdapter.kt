package com.example.dmps

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter

class IntroAdapter(fragmentManager: FragmentManager) :FragmentPagerAdapter(fragmentManager) {

//    @NonNull
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FirstScreen.newInstance()
            1 -> SecondScreen.newInstance()
            2 -> ThirdScreen.newInstance()
            else -> {
                Fragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }
}