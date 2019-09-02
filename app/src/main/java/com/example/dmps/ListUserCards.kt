package com.example.dmps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.dmps.Model.CardDetails
import com.example.dmps.Partials.MakeText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_card_details.*

class ListUserCards : AppCompatActivity() {
    private lateinit var listView: ListView
    lateinit var cardDetailsList: ArrayList<CardDetails>
    lateinit var adapter: ListUserCardAdapter
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user_cards)

        listView = findViewById(R.id.listUserCard_View)
        auth = FirebaseAuth.getInstance()
//        val firebaseuser = auth.currentUser!!.uid
        database = FirebaseDatabase.getInstance().getReference("cardDetails").child(auth.currentUser!!.uid)
        database.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val  cardDetails = CardDetails()
                cardDetails.cardNumber = snapshot.child("cardNumber").value as String
                cardDetailsList = arrayListOf(cardDetails)

                cardDetailsList.add(cardDetails)
//
                adapter = ListUserCardAdapter(applicationContext, cardDetailsList)
                listView.adapter = adapter
            }


        })





    }


}
