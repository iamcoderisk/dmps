package com.example.dmps

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import co.paystack.android.model.Card
import com.example.dmps.Model.CardDetails
import com.example.dmps.Partials.MakeText

class ListUserCardAdapter(context: Context, cardDetails:List<CardDetails>)
    :BaseAdapter(){

    private val mInflate:LayoutInflater = LayoutInflater.from(context)
    private val cardList = cardDetails
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View =  mInflate.inflate(R.layout.list_user_card_rows,parent, false)
        val cardnumber = view.findViewById<TextView>(R.id.list_user_card_text)
        cardnumber!!.text = cardList[position].cardNumber
        return view
    }

    override fun getItem(p0: Int): Any  = cardList.get(p0)

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int  = cardList.count()

}