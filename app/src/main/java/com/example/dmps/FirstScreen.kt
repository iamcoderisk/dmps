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
import kotlinx.android.synthetic.main.fragment_first_screen.*
import kotlinx.android.synthetic.main.fragment_third_screen.*


class FirstScreen : Fragment() {
    private  var nextFirst: Button? = null
    private lateinit var viewPager: ViewPager
    private lateinit var skipFirst:TextView

    private lateinit var firstTicked:ImageView
    private lateinit var firstSecondUnticked:ImageView
    private lateinit var firstThirdUnticked:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_first_screen, container, false)
        nextFirst = view.findViewById(R.id.nextFirst)
        skipFirst = view.findViewById(R.id.skipFirst)
        firstTicked = view.findViewById(R.id.firstTicked)
        firstSecondUnticked = view.findViewById(R.id.firstSecondUnticked)
        firstThirdUnticked = view.findViewById(R.id.firstThirdUnticked)
        viewPager = activity!!.findViewById(R.id.viewPager)
        skipFirst.setOnClickListener {
            val openLogin = Intent(activity,LoginActivity::class.java)
            startActivity(openLogin)
            activity!!.finish()
            activity!!.overridePendingTransition(R.anim.to_middle,R.anim.from_middle)
        }
        firstTicked.setOnClickListener {
            viewPager.setCurrentItem(0)
        }
        firstSecondUnticked.setOnClickListener {
            viewPager.setCurrentItem(1)
        }
        firstThirdUnticked.setOnClickListener {
            viewPager.setCurrentItem(2)
        }
        nextFirst!!.setOnClickListener {
            viewPager.setCurrentItem(0)
        }

        return view
    }

    companion object {
        fun newInstance(): FirstScreen {
            val fragmentTransfer = FirstScreen()
            val args = Bundle()
            fragmentTransfer.arguments = args
            return fragmentTransfer
        }

    }
}
