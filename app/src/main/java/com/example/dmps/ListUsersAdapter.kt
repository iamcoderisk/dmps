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
import com.example.dmps.Model.User
import com.example.dmps.Partials.MakeText

class ListUsersAdapter(context: Context, userLists:List<User>)
    :BaseAdapter(){

    private val mInflate:LayoutInflater = LayoutInflater.from(context)
    private val userList = userLists
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View =  mInflate.inflate(R.layout.list_user_details_rows,parent, false)
        val userName = view.findViewById<TextView>(R.id.list_user_detail)
       userName!!.text = userList[position].email
        return view
    }

    override fun getItem(p0: Int): Any  = userList.get(p0)

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int  = userList.count()

}