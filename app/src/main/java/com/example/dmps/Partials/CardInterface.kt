package com.example.dmps.Partials

interface CardInterface  {
//    var isCardValid: Boolean
//        get() = false
//        set(value) =
    val cardNumber: String
         get() = "2012012012012019"
     val cvv: String
         get() = "201"
    val expiryMonth: String
        get() = "1"

    val expiryYear: String
        get() = "19"

    fun getCard(cardNumber:String,expiryMonth:String,expiryYear:String,cvv:String )
    fun isValid():Boolean
}