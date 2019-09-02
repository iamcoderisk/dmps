package com.example.dmps.Partials

 class CreditCard : CardInterface {
     var isCardValid  = false
    override fun getCard(cardNumber: String, expiryMonth: String, expiryYear: String, cvv: String) {
        if(cardNumber == this.cardNumber && expiryMonth == this.expiryMonth &&
            expiryYear==this.expiryYear && cvv==this.cvv){
            this.isCardValid = true
        }

    }

    override fun isValid(): Boolean {
        return this.isCardValid
    }



}