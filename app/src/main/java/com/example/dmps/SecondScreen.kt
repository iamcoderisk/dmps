package com.example.dmps

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager

class SecondScreen : Fragment() {
    private  var nextSecond: Button? = null
    private lateinit var viewPager: ViewPager
    private lateinit var skipSecond: TextView
    private lateinit var backFirst:TextView
    private lateinit var secondfirstUnticked: ImageView
    private lateinit var secondTicked: ImageView
    private lateinit var secondThirdUnticked: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_second_screen, container, false)
        nextSecond = view.findViewById(R.id.nextSecond)
        skipSecond = view.findViewById(R.id.skipSecond)
        backFirst = view.findViewById(R.id.backToFirst)
        secondfirstUnticked = view.findViewById(R.id.secondFirstUnticked)
        secondTicked = view.findViewById(R.id.secondTicked)
        secondThirdUnticked = view.findViewById(R.id.secondThirdUnticked)
        viewPager = activity!!.findViewById(R.id.viewPager)
        skipSecond.setOnClickListener {
            val openLogin = Intent(activity,LoginActivity::class.java)
            startActivity(openLogin)
            activity!!.finish()
            activity!!.overridePendingTransition(R.anim.to_middle,R.anim.from_middle)
        }
        secondTicked.setOnClickListener {
            viewPager.setCurrentItem(1)
        }
        backFirst.setOnClickListener {
            viewPager.setCurrentItem(0)
        }
        secondfirstUnticked.setOnClickListener {
            viewPager.setCurrentItem(0)
        }
        secondThirdUnticked.setOnClickListener {
            viewPager.setCurrentItem(2)
        }
        nextSecond!!.setOnClickListener {
            viewPager.setCurrentItem(0)
        }

        return view
    }

    companion object {
        fun newInstance(): SecondScreen {
            val fragmentTransfer = SecondScreen()
            val args = Bundle()
            fragmentTransfer.arguments = args
            return fragmentTransfer
        }

    }
}
