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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kichai.kichai.R
import com.kichai.kichai.data.Book
import com.kichai.kichai.databasing.ItemHelper
import com.kichai.kichai.tools.BookAdapter
import com.kichai.kichai.tools.ItemOnClickListener
import com.kichai.kichai.tools.LoadingDialog
import com.kichai.kichai.tools.RecycleViewAdapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_see_all_boooks.*

class SeeAllBoooksFragment : Fragment(), RecycleViewAdapter.OnCatListener, ItemOnClickListener.onExitListener, ItemHelper.getDoubleCategoryBookSuccessListener, ItemHelper.getDoubleCategoryBookFailureListener, ItemHelper.getCategoryBookSuccessListener, ItemHelper.getCategoryBookFailureListener {

    private lateinit var cat1:String
    private lateinit var cat2:String

    private lateinit var navController : NavController

    lateinit var mContext : Context
    private lateinit var ih: ItemHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_see_all_boooks, container, false)

        mContext = this.requireContext()

        setupLoading()

        ih = ItemHelper()
        ih.setGetDoubleCategoryBookSuccessListener(this)
        ih.setGetDoubleCategoryBookFailureListener(this)

        ih.setGetCategoryBookSuccessListener(this)
        ih.setGetCategoryBookFailureListener(this)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val args = SeeAllBoooksFragmentArgs.fromBundle(requireArguments())
        cat1 = args.cat1
        cat2 = args.cat2.toString()     // if null, converted to string "null"

        var s : String = cat1
        if(cat2 != "null"){
            s = "$s $cat2"
        }

        textView.text = s
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onStart() {
        initRecyclerView()
        super.onStart()
    }

    private fun initRecyclerView() {
        if (cat2 != "null"){
            ih.getDoubleCategoryBook(cat1, cat2)
        }else{
            ih.getCategoryBook(cat1)
        }
    }

    override fun onCatClick(name: String?) {
        Toast.makeText(this.context, name, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onExit() {
        initRecyclerView()
    }

    override fun getDoubleCategoryBookSuccess(bookArray: ArrayList<Book>, cat2: String) {

        //Finding device width to decide whether to choose GridLayout spanCount
        val gridSpanCount:Int
        val dm = DisplayMetrics()
        activity?.windowManager!!.defaultDisplay.getMetrics(dm)
        val screenWidth = (dm.widthPixels.toDouble() / dm.xdpi).toInt()
        gridSpanCount = if(screenWidth < 5) {
            2
        }else{
            4
        }

        val adapter = GroupAdapter<GroupieViewHolder>()
        var recycler: RecyclerView = recycler_view

        if (bookArray.size <= gridSpanCount ){
            recycler_view.layoutParams.height =
                resources.getDimension(R.dimen.recyclerview_parent_custom_height_books).toInt()
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
//        recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL ,false)

        val listener = ItemOnClickListener(mContext)
        listener.setOnExitListener(this)
        adapter.setOnItemClickListener(listener)
    }


    override fun getDoubleCategoryBookFailure() {
        Toast.makeText(mContext, "Failed to get books", Toast.LENGTH_SHORT).show()
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun getCategoryBookSuccess(bookArray: ArrayList<Book>) {

        val gridSpanCount:Int
        val dm = DisplayMetrics()
        activity?.windowManager!!.defaultDisplay.getMetrics(dm)
        val screenWidth = (dm.widthPixels.toDouble() / dm.xdpi).toInt()
        gridSpanCount = if(screenWidth < 5) {
            2
        }else{
            4
        }

        val adapter = GroupAdapter<GroupieViewHolder>()
        var recycler: RecyclerView = recycler_view

        if (bookArray.size <= gridSpanCount ){
            recycler_view.layoutParams.height =
                resources.getDimension(R.dimen.recyclerview_parent_custom_height_books).toInt()
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
//        recycler_view.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL ,false)

        val listener = ItemOnClickListener(mContext)
        listener.setOnExitListener(this)
        adapter.setOnItemClickListener(listener)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun getCategoryBookFailure() {
        Toast.makeText(mContext, "Failed to get books", Toast.LENGTH_SHORT).show()
    }

    private fun setupLoading(){
        val loadingDialog = LoadingDialog(mContext)
        loadingDialog.startLoadingDialog()
    }
}