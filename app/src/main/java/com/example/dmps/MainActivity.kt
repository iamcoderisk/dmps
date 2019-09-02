package com.example.dmps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.postDelayed
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import androidx.annotation.NonNull
import androidx.viewpager.widget.ViewPager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private  val TIMER:Long =  2000

    var mAuthListener: FirebaseAuth.AuthStateListener? = null
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseDatabase
    lateinit var users: DatabaseReference
    private lateinit var viewPager:ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager =  findViewById(R.id.viewPager)
//        val adapter =
        viewPager.adapter = IntroAdapter(supportFragmentManager)

//        auth = FirebaseAuth.getInstance()
//        db = FirebaseDatabase.getInstance()
//        users = db.getReference("Users")
//
//        mAuthListener = FirebaseAuth.AuthStateListener {
//            val user = auth.currentUser
//            if (user != null) {
//                val intent = Intent(this@MainActivity, Dashboard::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//        var handler = Handler()
//        var runnable = Runnable {
//            var openLoginActivity = Intent(this@MainActivity,LoginActivity::class.java)
//            startActivity(openLoginActivity)
//            finish()
//            overridePendingTransition(R.anim.from_middle, R.anim.to_middle)
//        }
//        handler.postDelayed(runnable,TIMER)
    }
//    override fun onStart() {
//        super.onStart()
//        mAuthListener?.let { auth.addAuthStateListener(it) }
//    }
//
//
//    override fun onResume() {
//        super.onResume()
//        mAuthListener?.let { auth.addAuthStateListener(it) }
//    }
//
//
//    override fun onStop() {
//        super.onStop()
//        mAuthListener?.let { auth.removeAuthStateListener(it) }
//    }
}
