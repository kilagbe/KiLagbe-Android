package com.kichai.kichai.tools

import android.content.Context
import com.kichai.kichai.R
import com.kichai.kichai.data.CompleteOrder
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.recyclerview_deliveryman_current_order_list.view.*

class DeliverymanMyOrderAdapter(val orderItem: CompleteOrder, val context: Context) : Item<GroupieViewHolder>()
{
    override fun getLayout(): Int {
        return R.layout.recyclerview_deliveryman_current_order_list
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.order_no_textview.text = orderItem.orderId
        viewHolder.itemView.phone_no_textview.text = orderItem.customerphone
        viewHolder.itemView.address_textview.text = orderItem.address
        viewHolder.itemView.total_price_textview.text = orderItem.cart!!.total!!.toString()
    }
}