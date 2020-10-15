package com.kichai.kichai.ui.deliveryman

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kichai.kichai.R
import com.kichai.kichai.data.CompleteOrder
import com.kichai.kichai.databasing.OrderHelper
import com.kichai.kichai.tools.DeliverymanOrderAdapter
import com.kichai.kichai.tools.DeliverymanOrderItemOnClickListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

/**
 * A simple [Fragment] subclass.
 */
class DeliveryAllOrdersFragment : Fragment(), OrderHelper.getOrdersSuccessListener, OrderHelper.getOrdersFailureListener {

    lateinit var oh: OrderHelper
    lateinit var mContext: Context

    private lateinit var allOrdersRecycler: RecyclerView
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        oh = OrderHelper()
        oh.setGetOrdersFailureListener(this)
        oh.setGetOrdersSuccessListener(this)
        val root = inflater.inflate(R.layout.fragment_delivery_all_orders, container, false)
        allOrdersRecycler = root.findViewById(R.id.all_orders_recycler_view)

        mContext = this.context!!

        return root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onStart() {
        initRecyclerView()
        super.onStart()
    }

    private fun initRecyclerView() {
        oh.getDeliverymanAllOrders()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun getOrdersSuccess(orderArray: ArrayList<CompleteOrder>) {
        val context = mContext
        val adapter = GroupAdapter<GroupieViewHolder>()

        orderArray.forEach {
            adapter.add(DeliverymanOrderAdapter(it, mContext))
        }
        val listener = DeliverymanOrderItemOnClickListener(context)
        adapter.setOnItemClickListener(listener)
        allOrdersRecycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL ,false)
        allOrdersRecycler.adapter = adapter
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun getOrdersFailure() {
        Toast.makeText(mContext, "Failed to get orders", Toast.LENGTH_SHORT).show()
    }
}
