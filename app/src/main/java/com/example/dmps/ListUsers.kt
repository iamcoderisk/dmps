package com.example.dmps

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.dmps.Model.CardDetails
import com.example.dmps.Model.Requests
import com.example.dmps.Model.Transactions
import com.example.dmps.Model.User
import com.example.dmps.Partials.MakeText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ListUsers : AppCompatActivity(){
    private lateinit var listView: ListView
     var userLists: ArrayList<User> = arrayListOf()
    lateinit var adapter: ListUsersAdapter
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_users)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Transaction processing...")
        val preferences = getSharedPreferences("recieveAmount", Context.MODE_PRIVATE)
        val amount = preferences.getInt("amount",0)
        listView = findViewById(R.id.list_view_users)
        auth = FirebaseAuth.getInstance()


        database = FirebaseDatabase.getInstance().getReference("Users")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
              snapshot.children.forEach {
                 var user = it.getValue(User::class.java)!!
                  userLists.add(user)
                }
                adapter = ListUsersAdapter(applicationContext, userLists)
                listView.adapter = adapter
               listView.setOnItemClickListener { _, _, position, _ ->
                   progressDialog.setCancelable(false)
                   progressDialog.show()
                   userLists[position].userId.let {
                       //begin transaction

                       database = FirebaseDatabase.getInstance().getReference("Account").child(auth.currentUser!!.uid)
                       database.addListenerForSingleValueEvent(object : ValueEventListener {
                           override fun onDataChange(snapshot: DataSnapshot) {
                               val realbalance  = snapshot.child("account_balance").value as Int

                               val serviceCharge  = 50 /2
                               val requestedAmount = amount + serviceCharge
                               val updatedBalance = realbalance - (amount+requestedAmount)

                               database = FirebaseDatabase.getInstance().reference
                               val key = database.push().key
                               if (key != null) {

                                   val requests =  Requests()
                                   requests.amount = requestedAmount
                                   requests.hasrecieved =0
                                   requests.hassent = 0
                                   requests.requestBy = auth.currentUser!!.uid
                                   requests.requestFrom = it
                                   requests.status = "pending"
                                   database.child("Requests").child(key).setValue(requests)
                                       .addOnSuccessListener {
                                           progressDialog.setMessage("Transaction successful")
                                           progressDialog.hide()

                                       }
                                       .addOnFailureListener {

                                       }

                               }



                           }
                           override fun onCancelled(error: DatabaseError) {

                           }

                       })
                       //




                   }
                }
//                listView.setOnItemClickListener { adapterView, view, i, l ->
//                    MakeText(applicationContext,listView.getItemAtPosition(i).toString())
//
//                }




            }

            override fun onCancelled(error: DatabaseError) {

            }

        })





    }

}