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
import kotlinx.android.synthetic.main.fragment_third_screen.*

class ThirdScreen : Fragment() {
    private  var finishBtn: Button? = null
    private lateinit var viewPager: ViewPager
    private lateinit var skipThird: TextView
    private lateinit var backThird: TextView
    private lateinit var thirdfirstUnticked: ImageView
    private lateinit var thirdTicked: ImageView
    private lateinit var thirdsecondUnticked: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_third_screen, container, false)
        finishBtn = view.findViewById(R.id.finishBtn)
        skipThird = view.findViewById(R.id.thirdSkip)
        backThird = view.findViewById(R.id.thirdBack)
        thirdfirstUnticked = view.findViewById(R.id.thirdfirstUnticked)
        thirdTicked = view.findViewById(R.id.thirdTicked)
        thirdsecondUnticked = view.findViewById(R.id.thirdsecondUnticked)
        viewPager = activity!!.findViewById(R.id.viewPager)
        thirdSkip.setOnClickListener {
            val openLogin = Intent(activity,LoginActivity::class.java)
            startActivity(openLogin)
            activity!!.finish()
            activity!!.overridePendingTransition(R.anim.to_middle,R.anim.from_middle)
        }
        thirdTicked.setOnClickListener {
            viewPager.setCurrentItem(2)
        }
        backThird.setOnClickListener {
            viewPager.setCurrentItem(1)
        }
        thirdsecondUnticked.setOnClickListener {
            viewPager.setCurrentItem(1)
        }
        thirdfirstUnticked.setOnClickListener {
            viewPager.setCurrentItem(1)
        }
        finishBtn!!.setOnClickListener {
            viewPager.setCurrentItem(0)
        }

        return view
    }

    companion object {
        fun newInstance(): ThirdScreen {
            var fragmentTransfer = ThirdScreen()
            var args = Bundle()
            fragmentTransfer.arguments = args
            return fragmentTransfer
        }

    }
}
