package com.kichai.kichai.data

// TODO: switch the sequence of customeruid and timestamp. keeping timestamp first may allow sort by age of the order
data class CompleteOrder(val customeruid: String, val timestamp: String) {
    var cart: Cart? = null
    var orderId: String? = null
    var deliverymanstatus: String? = null
    var customerstatus: String? = null
    var address: String? = null
    var deliverymanphone: String? = null
    var deliverymanuid: String? = null
    var customerphone: String? = null
    constructor():this("", "")
}