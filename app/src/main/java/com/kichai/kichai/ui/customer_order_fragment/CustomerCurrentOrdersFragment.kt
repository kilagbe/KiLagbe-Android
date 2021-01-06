package com.kichai.kichai.ui.customer_order_fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kichai.kichai.R
import com.kichai.kichai.data.CompleteOrder
import com.kichai.kichai.databasing.OrderHelper
import com.kichai.kichai.databasing.ProfileHelper
import com.kichai.kichai.tools.CustomerCurrentCompleteOrder
import com.kichai.kichai.tools.CustomerOrderItemOnClickListener
import com.kichai.kichai.tools.LoadingDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class CustomerCurrentOrdersFragment : Fragment(), OrderHelper.getOrdersSuccessListener,
    OrderHelper.getOrdersFailureListener {

    lateinit var currentOrdersRecycler: RecyclerView
    lateinit var mContext: Context

    private lateinit var navController: NavController

    lateinit var oh: OrderHelper
    lateinit var ph: ProfileHelper

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_customer_current_orders, container, false)

        currentOrdersRecycler = root.findViewById(R.id.current_orders_recyclerview)

        mContext = this.context!!

        setupLoading()

        oh = OrderHelper()
        ph = ProfileHelper()

        oh.setGetOrdersSuccessListener(this)
        oh.setGetOrdersFailureListener(this)

        // FAB
        val fab: View = root.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            navController.navigate(R.id.navigation_cart)
        }

        return root
    }

    override fun onStart() {
        initRecyclerView()
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun initRecyclerView() {
        oh.getCustomerPersonalCurrentOrders(ph.getUid().toString())
    }

    override fun getOrdersSuccess(orderArray: ArrayList<CompleteOrder>) {
        val adapter = GroupAdapter<GroupieViewHolder>()
        orderArray.forEach {
            adapter.add(CustomerCurrentCompleteOrder(it))
        }
        val listener = CustomerOrderItemOnClickListener(mContext)
        adapter.setOnItemClickListener(listener)
        currentOrdersRecycler.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        currentOrdersRecycler.adapter = adapter
    }

    override fun getOrdersFailure() {
        Toast.makeText(mContext, "Failed to get orders", Toast.LENGTH_SHORT).show()
    }

    private fun setupLoading() {
        val loadingDialog = LoadingDialog(mContext)
        loadingDialog.startLoadingDialog()
    }
}