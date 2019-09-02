package com.example.dmps.Partials

import android.content.Context
import android.widget.Toast

class MakeText{
     constructor(context: Context,message: String){
        return Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
}
