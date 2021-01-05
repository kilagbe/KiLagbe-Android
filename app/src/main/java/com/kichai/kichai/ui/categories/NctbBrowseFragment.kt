package com.kichai.kichai.ui.categories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kichai.kichai.R
import com.kichai.kichai.data.Book
import com.kichai.kichai.databasing.ItemHelper
import com.kichai.kichai.tools.BookAdapter
import com.kichai.kichai.tools.ItemOnClickListener
import com.kichai.kichai.tools.RecycleViewAdapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_nctb_browse.*


class NctbBrowseFragment : Fragment(), RecycleViewAdapter.OnCatListener,
    ItemOnClickListener.onExitListener, ItemHelper.getSomeDoubleCategoryBookSuccessListener,
    ItemHelper.getSomeDoubleCategoryBookFailureListener {

    private lateinit var nctbTopChatRecyclerView: RecyclerView
    private lateinit var navController: NavController

    private lateinit var ih: ItemHelper

    lateinit var mContext: Context

    private var demoBookNames = arrayListOf<String>()


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_nctb_browse, container, false)


        nctbTopChatRecyclerView = root.findViewById(R.id.nctb_hsc_recyclerView) as RecyclerView

        /*getting demo book names data from string resources*/
        demoBookNames = resources.getStringArray(R.array.demo_book_names).toCollection(ArrayList())

        mContext = this.context!!

        ih = ItemHelper()
        ih.setGetSomeDoubleCategoryBookSuccessListener(this)
        ih.setGetSomeDoubleCategoryBookFailureListener(this)

        // FAB
        val fab: View = root.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            navController.navigate(R.id.navigation_cart)
        }

        return root
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onStart() {
        initRecyclerView()
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupButtons()
    }

    private fun setupButtons() {
        button_see_nctb_hsc.setOnClickListener {
            val action =
                NctbBrowseFragmentDirections.actionNctbBrowseFragmentToSeeAllBoooksFragment(
                    "NCTB",
                    "HSC"
                )
            navController.navigate(action)
        }
        button_see_nctb_ssc.setOnClickListener {
            val action =
                NctbBrowseFragmentDirections.actionNctbBrowseFragmentToSeeAllBoooksFragment(
                    "NCTB",
                    "SSC"
                )
            navController.navigate(action)
        }
    }

    private fun initRecyclerView() {
        ih.getSomeDoubleCategoryBook("NCTB", "HSC", 6)
        ih.getSomeDoubleCategoryBook("NCTB", "SSC", 6)
    }


    override fun onCatClick(name: String?) {
        Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onExit() {
        initRecyclerView()
    }

    override fun getSomeDoubleCategoryBookSuccess(bookArray: ArrayList<Book>, cat2: String) {
        val adapter = GroupAdapter<GroupieViewHolder>()
        lateinit var recycler: RecyclerView

        //Finding device width to decide whether to choose GridLayout spanCount
        var gridSpanCount: Int
        val dm = DisplayMetrics()
        activity?.windowManager!!.defaultDisplay.getMetrics(dm)
        var screenWidth = (dm.widthPixels.toDouble() / dm.xdpi).toInt()
        gridSpanCount = if (screenWidth < 5) {
            2
        } else {
            4
        }

        when (cat2) {
            "HSC" -> {
                recycler = nctb_hsc_recyclerView
                if (bookArray.size <= gridSpanCount) nctb_hsc_recyclerView.layoutParams.height =
                    resources.getDimension(R.dimen.recyclerview_parent_custom_height_books).toInt()
            }
            "SSC" -> {
                recycler = nctb_ssc_recyclerView
                if (bookArray.size <= gridSpanCount) nctb_ssc_recyclerView.layoutParams.height =
                    resources.getDimension(R.dimen.recyclerview_parent_custom_height_books).toInt()
            }
        }
        bookArray.forEach {
            adapter.add(BookAdapter(it))
        }

        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(
            context,
            gridSpanCount,
            GridLayoutManager.VERTICAL,
            false
        )

        val listener = ItemOnClickListener(mContext)
        listener.setOnExitListener(this)
        adapter.setOnItemClickListener(listener)
    }

    override fun getSomeDoubleCategoryBookFailure() {
        Toast.makeText(mContext, "Failed to get books", Toast.LENGTH_SHORT).show()
    }

}
