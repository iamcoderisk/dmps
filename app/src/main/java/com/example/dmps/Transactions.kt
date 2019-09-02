package com.example.dmps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Transactions : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.transactions, container, false)
    }

    companion object {
        fun newInstance(): Transactions {
            var fragmentTransactions = Transactions()
            var args = Bundle()
            fragmentTransactions.arguments = args
            return fragmentTransactions
        }

    }
}