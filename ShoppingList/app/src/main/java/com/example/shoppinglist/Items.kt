package com.example.shoppinglist

class Items (
    var docId : String?,
    var name : String?,
    var qtd : Double?,
    var checked : Boolean = false) {

    constructor() : this(null,null,null, false)
}