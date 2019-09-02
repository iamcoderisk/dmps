package com.example.dmps

import com.example.dmps.Model.Requests

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListRequestAdapter(context: Context, requestDetails:ArrayList<Requests>)
    :BaseAdapter(){

    private val mInflate:LayoutInflater = LayoutInflater.from(context)
    private val requestList = requestDetails
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View =  mInflate.inflate(R.layout.listrequests,parent, false)
        val recieverAddress = view.findViewById<TextView>(R.id.recieverAddress)
        val amount =  view.findViewById<TextView>(R.id.amount)
        val status  = view.findViewById<TextView>(R.id.status)
        recieverAddress!!.text =  requestList[position].requestFrom
        amount!!.text = requestList[position].amount.toString()
        status!!.text = requestList[position].status
        return view
    }

    override fun getItem(p0: Int): Any  = requestList.get(p0)

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int  = requestList.count()

}