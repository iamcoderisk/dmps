package com.example.dmps

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.example.dmps.Partials.MakeText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FundWallet : AppCompatActivity() {
    private lateinit var fundWallet: Button
    private lateinit var amount:EditText
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var preferences: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fund_wallet)
        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        amount = findViewById(R.id.amount)
        fundWallet = findViewById(R.id.fundBtn)
        fundWallet.setOnClickListener {
           if(amount.text.toString().isNotEmpty()){
               auth = FirebaseAuth.getInstance()
               val firebaseuser = auth.currentUser!!.uid
               database = FirebaseDatabase.getInstance().getReference("cardDetails")

               database.addListenerForSingleValueEvent(object : ValueEventListener{
                   override fun onDataChange(snapshot: DataSnapshot) {
                       if(snapshot.hasChild(firebaseuser)){
                           var openCards = Intent(this@FundWallet,ListUserCards::class.java)
                           startActivity(openCards)
                           finish()
                           overridePendingTransition(R.anim.from_middle,R.anim.to_middle)
                           preferences = getSharedPreferences("enteredAmount", Context.MODE_PRIVATE).edit()
                           preferences.putInt("amount",amount.text.toString().toInt())
                           preferences.apply()



                       }else{
                           var openCardDetails = Intent(this@FundWallet,CardDetails::class.java)
                           startActivity(openCardDetails)
                           finish()
                           overridePendingTransition(R.anim.from_middle,R.anim.to_middle)
                           preferences = getSharedPreferences("enteredAmount", Context.MODE_PRIVATE).edit()
                           preferences.putInt("amount",amount.text.toString().toInt())
                           preferences.apply()
                       }
                   }

                   override fun onCancelled(error: DatabaseError) {
                       MakeText(applicationContext,error.message.toString())
                   }

               })
           }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
    private fun ifCardDetailsExists(){

    }

}
