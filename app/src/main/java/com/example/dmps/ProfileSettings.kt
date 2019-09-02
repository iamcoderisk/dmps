package com.example.dmps

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import android.provider.MediaStore
import android.graphics.Bitmap
import java.io.IOException
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import java.util.UUID.randomUUID






class ProfileSettings : AppCompatActivity() {
    private val imageView: ImageView? = null

    private var filePath: Uri? = null

    private val PICK_IMAGE_REQUEST = 71

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

      //for experts
        findViewById<ImageView>(R.id.logoutBtn).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@ProfileSettings,LoginActivity::class.java))
            finish()
            overridePendingTransition(R.anim.from_middle,R.anim.to_middle)
        }
        //

        findViewById<Button>(R.id.chooseImage).setOnClickListener {
           chooseImage()

        }
        findViewById<Button>(R.id.chooseImage).setOnClickListener {

        }



    }
    fun chooseImage(){
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.data != null
        ) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                findViewById<ImageView>(R.id.upload).setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
    private fun uploadImage() {
        val storage: FirebaseStorage
        val storageReference: StorageReference
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()
        if(filePath !=null){
           val progressDialog =  ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val ref = storageReference.child("images/" + UUID.randomUUID().toString())
            ref.putFile(filePath!!)
                .addOnSuccessListener {

                }
                .addOnFailureListener {

                }
                .addOnProgressListener {

                }

        }
    }

}
