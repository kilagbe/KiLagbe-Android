package com.kilagbe.kilagbe.ui.deliveryman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kilagbe.kilagbe.R

/**
 * A simple [Fragment] subclass.
 */
class DeliveryMyOrdersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery_my_orders, container, false)
    }

}
