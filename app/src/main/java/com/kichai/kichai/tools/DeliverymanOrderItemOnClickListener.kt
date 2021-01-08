package com.kichai.kichai.tools

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kichai.kichai.R
import com.kichai.kichai.data.Cart
import com.kichai.kichai.databasing.OrderHelper
import com.kichai.kichai.databasing.ProfileHelper
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener

class DeliverymanOrderItemOnClickListener(val context: Context) : OnItemClickListener,
    OrderHelper.confirmOrderSuccessListener, OrderHelper.confirmOrderFailureListener,
    OrderHelper.deliverOrderSuccessListener, OrderHelper.deliverOrderFailureListener {
    lateinit var dialog: AlertDialog
    lateinit var layoutInflater: LayoutInflater
    lateinit var recyclerView: RecyclerView

    private lateinit var loadingDialog: LoadingDialog
    lateinit var ph: ProfileHelper
    lateinit var oh: OrderHelper

    override fun onItemClick(item: Item<*>, view: View) {
        var orderId: String? = null
        var customerphone: String? = null
        var address: String? = null
        var cart: Cart? = null

        if (item is DeliverymanOrderAdapter) {
            item
            orderId = item.orderItem.orderId!!
            customerphone = item.orderItem.customerphone!!
            address = item.orderItem.address!!
            cart = item.orderItem.cart!!
        } else {
            item as DeliverymanMyOrderAdapter
            orderId = item.orderItem.orderId!!
            customerphone = item.orderItem.customerphone!!
            address = item.orderItem.address!!
            cart = item.orderItem.cart!!
        }

        dialog = AlertDialog.Builder(context).create()
        layoutInflater = LayoutInflater.from(context)
        ph = ProfileHelper()
        oh = OrderHelper()

        val uid = ph.getUid()
        oh.setConfirmOrderSuccessListener(this)
        oh.setConfirmOrderFailureListener(this)
        oh.setDeliverOrderSuccessListener(this)
        oh.setDeliverOrderFailureListener(this)

        val dialogview = layoutInflater.inflate(R.layout.alert_dialog_order_details, null)
        dialogview.findViewById<TextView>(R.id.order_no_textview).text = orderId
        dialogview.findViewById<TextView>(R.id.phone_no_textview).text = customerphone
        dialogview.findViewById<TextView>(R.id.address_textview).text = address

        recyclerView = dialogview.findViewById<RecyclerView>(R.id.item_list_recycler_view)

        val adapter = GroupAdapter<GroupieViewHolder>()
        cart.orderBookItems.forEach {
            adapter.add(DeliverymanOrderDetailsAdapter(it))
        }

        cart.orderEssentialItems.forEach {
            adapter.add(DeliverymanOrderDetailsAdapter(it))
        }

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        dialogview.findViewById<TextView>(R.id.grand_total_text_view).text = cart.total.toString()

        if (item is DeliverymanOrderAdapter) {
            setupLoading()
            dialogview.findViewById<Button>(R.id.confirm_button).setOnClickListener {
                oh.acceptDeliverymanOrder(orderId.toString(), uid!!)
            }
        } else {
            setupLoading()
            dialogview.findViewById<Button>(R.id.confirm_button).text = "DELIVERED"
            dialogview.findViewById<Button>(R.id.confirm_button).setOnClickListener {
                oh.deliverDeliverymanOrder(orderId.toString())
            }
        }

        dialog.setView(dialogview)
        dialog.setCancelable(true)
        dialog.show()
    }

    override fun confirmOrderSuccess() {
        Toast.makeText(context, "Confirmed order", Toast.LENGTH_SHORT).show()
        dialog.dismiss()
        loadingDialog.dismissDialog()
    }

    override fun confirmOrderFailure() {
        Toast.makeText(context, "Failed to confirm order", Toast.LENGTH_SHORT).show()
        loadingDialog.dismissDialog()
    }

    override fun deliverOrderSuccess() {
        Toast.makeText(context, "Delivered order", Toast.LENGTH_SHORT).show()
        loadingDialog.dismissDialog()
        dialog.dismiss()
    }

    override fun deliverOrderFailure() {
        Toast.makeText(context, "Delivered order", Toast.LENGTH_SHORT).show()
        loadingDialog.dismissDialog()
    }

    private fun setupLoading() {
        loadingDialog = LoadingDialog(context)
        loadingDialog.startLoadingDialog(Runnable { }, null)
    }
}