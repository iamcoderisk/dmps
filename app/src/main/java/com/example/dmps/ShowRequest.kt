package com.example.dmps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import com.example.dmps.Model.CardDetails
import com.example.dmps.Model.Requests
import com.example.dmps.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ShowRequest : AppCompatActivity() {
    private lateinit var listView: ListView
     var requestList: ArrayList<Requests> = arrayListOf()
    lateinit var adapter: ListRequestAdapter
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var hasRecieveBtn: Button
    private lateinit var hasSentBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_request)
        listView = findViewById(R.id.listRequest)
        hasRecieveBtn = findViewById(R.id.hasRecieved)
        hasSentBtn = findViewById(R.id.hasSent)

        auth = FirebaseAuth.getInstance()


        database = FirebaseDatabase.getInstance().getReference("Requests")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val request = it.getValue(Requests::class.java)!!
                    if(request.hassent.toString() == auth.currentUser!!.uid){
                        hasRecieveBtn.visibility = View.GONE
                    }
                    requestList.add(request)
                }
                adapter = ListRequestAdapter(applicationContext, requestList)
                listView.adapter = adapter
            }
        })
    }
}
