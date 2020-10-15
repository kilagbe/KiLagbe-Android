package com.kichai.kichai.data

data class OrderItems(val itemid: String) {
    var qty: Int? = null
    var cost: Double? = null
    constructor():this("")
}