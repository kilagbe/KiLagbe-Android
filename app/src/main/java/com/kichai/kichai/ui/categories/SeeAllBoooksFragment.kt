package com.kichai.kichai.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kichai.kichai.R
import kotlinx.android.synthetic.main.fragment_see_all_boooks.*

class SeeAllBoooksFragment : Fragment() {

    private lateinit var cat1:String
    private lateinit var cat2:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_see_all_boooks, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments!=null) {
            val args = SeeAllBoooksFragmentArgs.fromBundle(requireArguments())
            cat1 = args.cat1
            cat2 = args.cat2!!
        }

        var s : String = cat1 + " " +cat2
        textView.text = s
    }
}