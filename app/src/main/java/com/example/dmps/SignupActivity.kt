package com.example.dmps

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.dmps.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue


class SignupActivity : AppCompatActivity() {
    private lateinit var openLoginActivity: TextView
    private var signupBtn: Button? = null
    private var fullName:EditText? = null
    private var userEmail:EditText? = null
    private var userPassword:EditText? = null

    //firebase config
    lateinit var auth: FirebaseAuth
    lateinit var db:FirebaseDatabase
    lateinit var users:DatabaseReference
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        signupBtn = findViewById(R.id.signupBtn)
        fullName = findViewById(R.id.full_name)
        userEmail = findViewById(R.id.user_email)
        userPassword = findViewById(R.id.user_password)
        //firebase references
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        users = db.getReference("Users")

        signupBtn?.setOnClickListener {
            if(!isValidated()){
                val progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Loading...")
                progressDialog.setCancelable(false)
                progressDialog.show()

                auth.createUserWithEmailAndPassword(userEmail?.text.toString(),userPassword?.text.toString())
                    .addOnSuccessListener {
                        var user = User()
                        user.fullname = fullName?.text.toString()
                        user.email = userEmail?.text.toString()
                        user.password = userPassword?.text.toString()
                        user.userId = auth.currentUser!!.uid
//                        user.date = ServerValue.TIMESTAMP
                        users.child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(user)
                            .addOnSuccessListener {
                                Toast.makeText(applicationContext,"Registration successful",Toast.LENGTH_LONG)
                                    .show()
                                progressDialog.dismiss()
                                //redirect to login
                                val openLoginIntent = Intent(this@SignupActivity,LoginActivity::class.java)
                                startActivity(openLoginIntent)
                                finish()
                                overridePendingTransition(R.anim.to_middle,R.anim.from_middle)
                            }
                            .addOnFailureListener {
                                Toast.makeText(applicationContext,it.message.toString(),Toast.LENGTH_LONG)
                                    .show()
                            }

                    }
            }
        }

        openLoginActivity = findViewById(R.id.openSignIn)
        openLoginActivity.setOnClickListener {
            val openLoginIntent = Intent(this@SignupActivity,LoginActivity::class.java)
            startActivity(openLoginIntent)
            finish()
            overridePendingTransition(R.anim.from_middle,R.anim.to_middle)
        }

    }
    private fun isValidated():Boolean {
        var isEmpty = false
        var full_name:String = fullName?.text.toString()
        var user_email:String = userEmail?.text.toString()
        var user_password:String = userPassword?.text.toString()

        if(full_name.isEmpty()){
            fullName?.error = "Fullname cannot be empty"
            isEmpty = true
        }else{
            fullName?.error = null

        }
        if(!user_email.isValidEmail()){
            userEmail?.error = "Email field cannot be blank"
            isEmpty = true
        }else{
            userEmail?.error = null
        }
        if(user_password.isEmpty()){
            userPassword?.error = "Password field cannot be empty"
            isEmpty = true
        }else{
            userPassword?.error = null
        }
        return isEmpty
    }
    fun String.isValidEmail() = this.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

}
