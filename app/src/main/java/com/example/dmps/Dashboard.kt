package com.example.dmps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Dashboard :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        val fragment = HomeFragment.newInstance()
        addFragment(fragment)
//        val profile:ImageView = findViewById(R.id.user_profile)
//        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.user_picture)
//        val mDrawable = RoundedBitmapDrawableFactory.create(resources,bitmap)
//        mDrawable.isCircular = true
//        profile.setImageDrawable(mDrawable)
    }
//    public override fun onStart() {
//        super.onStart()
//    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.dashboard ->{
                val homeFragment = HomeFragment.newInstance()
                addFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.transactions_menu ->{
                val fragment = Transactions.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.transfer_menu ->{
                val fragment = TransferFragment.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.recieve_menu ->{
                val fragment = Recieve.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false

    }
    private fun addFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container,fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
    }
//    fun openProfile(){
////        user_profile = findViewById(R.id.user_profile)
//
////    }
}