package com.example.dmps

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.dmps.Model.Transactions

class ListTransactionAdapter(context: Context, transactionDetails:ArrayList<Transactions>)
    :BaseAdapter(){

    private val mInflate:LayoutInflater = LayoutInflater.from(context)
    private val transactionList = transactionDetails
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View =  mInflate.inflate(R.layout.list_transaction_row,parent, false)
        val transactionMode = view.findViewById<TextView>(R.id.transMode)
        val transactionAmount = view.findViewById<TextView>(R.id.amount)
//        transactionMode!!.text = transactionList[position].mode
        transactionAmount!!.text = transactionList[position].amount.toString()
        return view
    }

    override fun getItem(p0: Int): Any  = transactionList.get(p0)

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int  = transactionList.count()

}