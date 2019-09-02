package com.example.dmps

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.dmps.Partials.MakeText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_recieve.*;
class Recieve : Fragment() {
    private lateinit var recieveBtn: Button
    private lateinit var amount:EditText
    private lateinit var preferences: SharedPreferences.Editor
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view:View  = inflater.inflate(R.layout.fragment_recieve, container, false)
        recieveBtn = view.findViewById(R.id.recieveBtn)
        amount = view.findViewById(R.id.amount)
        recieveBtn.setOnClickListener {
            val amount = amount.text.toString()
//            MakeText(activity!!.applicationContext,amount)
//            preferences = activity!!.getSh
            preferences = activity!!.getSharedPreferences("recieveAmount", Context.MODE_PRIVATE).edit()
            preferences.putInt("amount",amount.toInt())
            preferences.apply()

            //

            database = FirebaseDatabase.getInstance().getReference("Account").child(auth.currentUser!!.uid)
            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val realbalance  = snapshot.child("account_balance").value as Int
                    if(realbalance < amount.toInt()){
                        MakeText(activity!!.applicationContext,"Insufficient wallet balance")
                    }else {
                        val openProfile = Intent(activity,ListUsers::class.java)
                        startActivity(openProfile)
                        activity!!.finish()
                        activity!!.overridePendingTransition(R.anim.to_middle,R.anim.from_middle)
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }

            })

            ///

        }
        return view
    }

    companion object {
        fun newInstance(): Recieve {
            var fragmentTransfer = Recieve()
            var args = Bundle()
            fragmentTransfer.arguments = args
            return fragmentTransfer
        }

    }

}
