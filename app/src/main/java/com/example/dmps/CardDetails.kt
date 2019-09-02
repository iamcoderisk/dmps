package com.example.dmps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
//import jdk.nashorn.internal.objects.NativeDate.getTime
import java.text.SimpleDateFormat
import java.util.*
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import co.paystack.android.PaystackSdk
import co.paystack.android.model.Card
import co.paystack.android.model.Charge
import com.example.dmps.Model.CardDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import co.paystack.android.Paystack
import co.paystack.android.Transaction
import com.example.dmps.Model.Account
import com.example.dmps.Partials.CreditCard
import com.example.dmps.Partials.MakeText


class CardDetails : AppCompatActivity() {
    private lateinit var cardNumber:EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var submitCard:Button
    private lateinit var monthAndYear:EditText
    private lateinit var database: DatabaseReference
    private lateinit var cvvNumber:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)
        submitCard = findViewById(R.id.submitCardBtn)
        cardNumber = findViewById(R.id.cardNumber)
        cvvNumber = findViewById(R.id.cvv)
        monthAndYear = findViewById(R.id.month_and_year)

        monthAndYear.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var working = s.toString()
                var isValid = true
                if (working.length == 2 && before == 0) {
                    if (Integer.parseInt(working) < 1 || Integer.parseInt(working) > 12) {
                        isValid = false
                    } else {
                        working += "/"
                        monthAndYear.setText(working)
                        monthAndYear.setSelection(working.length)
                    }
                } else if (working.length == 5 && before == 0) {
                    val enteredYear = working.substring(3)
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100//getting last 2 digits of current year i.e. 2018 % 100 = 18
                    if (Integer.parseInt(enteredYear) < currentYear) {
                        isValid = false
                    }
                } else if (working.length != 5) {
                    isValid = false
                }

                if (!isValid) {
//                    et_expire_date.error = getString(R.string.enter_valid_date_mm_yy)
                } else {
                    monthAndYear.error = null
                }

            }

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })



        submitCard.setOnClickListener {
            database = FirebaseDatabase.getInstance().reference
            val spl = monthAndYear.text.toString().split("/")
            val month = Integer.parseInt(spl[0])
            val year = Integer.parseInt(spl[1])
            auth = FirebaseAuth.getInstance()
            val firebaseuser = auth.currentUser!!.uid
            var cardDetails = CardDetails()
            cardDetails.cardNumber = cardNumber.text.toString()
            cardDetails.cvvNumber = cvvNumber.text.toString()
            cardDetails.expiryMonth = month.toString().toInt()
            cardDetails.expiryYear = year.toString().toInt()
            database.child("cardDetails").child(firebaseuser).setValue(cardDetails)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext,"Card Details Saved!",Toast.LENGTH_LONG).show()
                    val transactionDialog = ProgressDialog(this)
                    transactionDialog.setMessage("Transaction processing...")
                    transactionDialog.setCancelable(false)
                    transactionDialog.show()
                    val card_number = cardNumber.text.toString()
                    val card_cvv = cvvNumber.text.toString()
                    val card_expiry_month = month.toString().toInt()
                    val card_expiry_year = year.toString().toInt()

                    val creditCard = CreditCard()
                        creditCard.getCard(card_number,card_expiry_month.toString(),card_expiry_year.toString(),card_cvv)
                    if(creditCard.isValid()==true){
                        val preferences = getSharedPreferences("enteredAmount", Context.MODE_PRIVATE)
                            if (preferences != null) {
                                val amount = preferences.getInt("amount",0)
                                val date = Date()
                                val account = Account()
                                    account.account_balance = amount.toInt()
                                    account.created_at=date.time
                                    database.child("account").child(firebaseuser).setValue(account)
                                        .addOnSuccessListener {
                                            transactionDialog.dismiss()
                                            MakeText(applicationContext,"Account funded")

                                            val openDashboard = Intent(this@CardDetails,Dashboard::class.java)
                                            startActivity(openDashboard)
                                            overridePendingTransition(R.anim.from_middle,R.anim.to_middle)
                                            finish()
                                        }.addOnFailureListener {

                                        }
                                MakeText(applicationContext,amount.toString())
                            }

                    }

//                    val card = Card(card_number,card_expiry_month,card_expiry_year,card_cvv)
//                    if(card.isValid){
//                    val charge = Charge()
//                        charge.card = card
//                        PaystackSdk.chargeCard(this@CardDetails, charge, object : Paystack.TransactionCallback {
//                            override fun onSuccess(transaction: Transaction) {
////                                if(transaction.toString() == )
//                                val preferences = getSharedPreferences("enteredAmount", Context.MODE_PRIVATE)
//                                if (preferences != null) {
//
//                                }
//
//
//                                // This is called only after transaction is deemed successful.
//                                // Retrieve the transaction, and send its reference to your server
//                                // for verification.
//                            }
//
//                            override fun beforeValidate(transaction: Transaction) {
//                                // This is called only before requesting OTP.
//                                // Save reference so you may send to server. If
//                                // error occurs with OTP, you should still verify on server.
//                            }
//
//                            override fun onError(error: Throwable, transaction: Transaction) {
//                                //handle error here
//                            }
//
//                        })
//
//                    }else{
//                        val preferences = getSharedPreferences("enteredAmount", Context.MODE_PRIVATE)
//                        val amount = preferences.getInt("amount",0)
//                        val account = Account()
//                        MakeText(applicationContext,amount.toString())
////                    MakeText(applicationContext,"Card is not valid!")
//                    }

                    //initiate loading transaction processing ...
                    //charge user card
                    //get the amount from the shared preferences

                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext,it.message.toString(),Toast.LENGTH_LONG).show()

                }
        }

    }

    private fun isValidated():Boolean {
        var isEmpty = false
        var card_Number = cardNumber.text.toString()
        var month_and_year = monthAndYear
        return isEmpty
    }
}
