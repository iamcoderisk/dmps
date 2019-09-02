package com.example.dmps
import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.dmps.Model.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.example.dmps.Model.Transactions
import com.example.dmps.Partials.MakeText
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    lateinit var disPlayName: TextView
    private lateinit var real_balance: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var fund_wallet: ImageView
    private lateinit var database: DatabaseReference
    private lateinit var recieve: CardView
    private lateinit var user_profile: ImageView
    private lateinit var transactionCard: CardView
    private lateinit var request:CardView

    private var MYPERMISSIONREQUESTCODE: Int = 7000
    private  var latitude:Long = 0
    private  var longitude:Long =0

    private lateinit var lm:LocationManager
    private  var transactionList: ArrayList<Transactions> = arrayListOf()
    private lateinit var listViewTransactions: ListView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun checkLocationPermission() {

        if (checkSelfPermission(
                activity!!,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            ActivityCompat.requestPermissions(activity!!, permissions, MYPERMISSIONREQUESTCODE)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)


        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_home,container,false)
        request = view.findViewById(R.id.payBills)
        request.setOnClickListener {
            val openProfile = Intent(activity,ShowRequest::class.java)
            startActivity(openProfile)
            activity!!.finish()
            activity!!.overridePendingTransition(R.anim.to_middle,R.anim.from_middle)
        }
//
//        var transaction = Transactions()
//        transaction.amount = 12000
////        transaction.created = ServerValue.TIMESTAMP
//        transaction.receiverId = "0IIP6RKBUb0hLPI2Nf7UqKeToYZXQT2"
//        transaction.senderId = "P6RKBUb0hLPI2Nf7UqKeToYZXQT2"
//        database = FirebaseDatabase.getInstance().reference
//        val key = database.push().key
//        if (key != null) {
//            database.child("Transactions").child(key).setValue(transaction)
//                .addOnSuccessListener {
//
//                }
//                .addOnFailureListener {
//
//                }
//        }


//
            database = FirebaseDatabase.getInstance().getReference("Transactions")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
            for(childData in snapshot.children){
                var   transaction = Transactions()
                transaction = childData.getValue(Transactions::class.java)!!
                if(transaction.senderId == auth.currentUser!!.uid  || transaction.receiverId== auth.currentUser!!.uid
                    || transaction.receiverId ==auth.currentUser!!.uid || transaction.senderId == auth.currentUser!!.uid){

                    transactionList.add(transaction)
                }

            }
                listViewTransactions = view.findViewById(R.id.listTransactions)
                listViewTransactions.adapter = ListTransactionAdapter(activity!!.applicationContext,transactionList)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

        return view
    }

    override fun onStart() {
        super.onStart()

        database = FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser!!.uid)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                displayName.text = snapshot.child("fullName").value as String
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })

        database = FirebaseDatabase.getInstance().getReference("account").child(auth.currentUser!!.uid)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                real_balance.text = snapshot.child("account_balance").value as String
                if(snapshot.child("account_balance").value as String == ""){
                    real_balance.text = "0"
                }else{
                    real_balance.text = snapshot.child("account_balance").value as String
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(checkSelfPermission(activity!!.applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            activity!!.finish()

        }
//        checkLocationPermission()

                var user = User()
        real_balance = view.findViewById(R.id.real_balance)
        disPlayName = view.findViewById(R.id.displayName)
//        transactionCard = view.findViewById(R.id.transactionCard)
//        recieve = view.findViewById(R.id.recieve)
        user_profile = view.findViewById(R.id.user_profile)
        user_profile.setOnClickListener {
            val openProfile = Intent(activity,ProfileSettings::class.java)
            startActivity(openProfile)
            activity!!.finish()
            activity!!.overridePendingTransition(R.anim.to_middle,R.anim.from_middle)
        }
//        transactionCard.setOnClickListener {
//
//            AlertDialog.Builder(activity!!.applicationContext)
//                .setTitle("Notification")
//                .setMessage("This feature is not currently available!")
//                .setPositiveButton("Ok"){ _, _ ->
//                }
//                .create()
//




//            MakeText(activity!!.applicationContext,"clicked")
//            val builder = AlertDialog.Builder(activity!!.applicationContext)
//                .setTitle("Notification")
//                .setMessage("This feature is not currently available!")
//                .setPositiveButton("Ok"){ _, _ ->
//                }
//            val dialog: AlertDialog = builder.create()
//            // Display the alert dialog on app interface
//            dialog.show()

//        }
        //open recieve money activity
//        recieve.setOnClickListener {
//            val openListUser = Intent(activity,ListUsers::class.java)
//            startActivity(openListUser)
//            activity!!.finish()
//            activity!!.overridePendingTransition(R.anim.to_middle,R.anim.from_middle)
//
//        }

        //initialize firebase
        auth = FirebaseAuth.getInstance()
        val firebaseuser = auth.currentUser!!.uid

        //transfer fund
        fund_wallet = view.findViewById(R.id.fund_wallet)
        fund_wallet.setOnClickListener {
            val openTransferFundWallet = Intent(activity,FundWallet::class.java)
            startActivity(openTransferFundWallet)
              activity!!.finish()
            activity!!.overridePendingTransition(R.anim.to_middle,R.anim.from_middle)

        }

    }
    companion object {
        fun newInstance(): HomeFragment {
            val fragmentHome = HomeFragment()
            val args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }

    }


}