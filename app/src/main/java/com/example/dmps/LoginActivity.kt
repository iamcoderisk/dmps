package com.example.dmps

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {
    private lateinit var openSignupActivity:  TextView
    private lateinit var openForgotPassword: TextView
    private lateinit var signIn:Button
    private lateinit var userEmail:EditText
    private lateinit var userPassword: EditText
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseDatabase
    lateinit var users: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        openForgotPassword = findViewById(R.id.forgot_password)
        openSignupActivity = findViewById(R.id.openSignUp)
        signIn = findViewById(R.id.signInBtn)
        userEmail = findViewById(R.id.user_email)
        userPassword = findViewById(R.id.user_password)
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        users = db.getReference("Users")
        signIn.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Logging In...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            if(!isValidated()){
                val credential = EmailAuthProvider.getCredential(userEmail.text.toString(),userPassword.text.toString())
                auth.signInWithCredential(credential)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        val dashboardIntent = Intent(this@LoginActivity,Dashboard::class.java)
                        startActivity(dashboardIntent)
                        finish()
                    }
                    .addOnFailureListener {
                        progressDialog.cancel()
                        val builder = AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage("Invalid credentials provided")
                            .setPositiveButton("Ok"){ _, _ ->


                            }
                        val dialog: AlertDialog = builder.create()

                        // Display the alert dialog on app interface
                        dialog.show()
                    }
//                auth.signInWithEmailAndPassword( userEmail.text.toString(),userPassword.text.toString())
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            progressDialog.dismiss()
//                            val dashboardIntent = Intent(this@LoginActivity,Dashboard::class.java)
//                            startActivity(dashboardIntent)
//                            finish()
//                        } else {
//                            progressDialog.cancel()
//                            val builder = AlertDialog.Builder(this)
//                                .setTitle("Error")
//                                .setMessage("Invalid credentials provided")
//                                .setPositiveButton("Ok"){ _, _ ->
//
//
//                                }
//                            val dialog: AlertDialog = builder.create()
//
//                            // Display the alert dialog on app interface
//                            dialog.show()
////                            Toast.makeText(applicationContext,task.exception.toString(),Toast.LENGTH_LONG).show()
//                        }
//                    }
            }
        }

        openSignupActivity.setOnClickListener {
            val signupActivityIntent = Intent(this@LoginActivity,SignupActivity::class.java)
            startActivity(signupActivityIntent)
            finish()
            overridePendingTransition(R.anim.from_middle,R.anim.to_middle)
        }

        openForgotPassword.setOnClickListener {
            val forgotPasswordIntent = Intent(this@LoginActivity,Dashboard::class.java)
            startActivity(forgotPasswordIntent)
            finish()
            overridePendingTransition(R.anim.from_middle,R.anim.to_middle)
        }
    }
    private fun isValidated():Boolean {
        var isEmpty = false
        var user_email:String = userEmail?.text.toString()
        var user_password:String = userPassword?.text.toString()
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
