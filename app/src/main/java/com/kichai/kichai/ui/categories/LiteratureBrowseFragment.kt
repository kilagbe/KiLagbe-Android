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
import com.kichai.kichai.tools.RecycleViewAdapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_literature_browse.*


class LiteratureBrowseFragment : Fragment(), RecycleViewAdapter.OnCatListener, ItemOnClickListener.onExitListener, ItemHelper.getDoubleCategoryBookSuccessListener, ItemHelper.getDoubleCategoryBookFailureListener {


    private lateinit var literatureTopChatRecyclerView: RecyclerView
    private lateinit var navController : NavController

    lateinit var mContext: Context

    private lateinit var ih: ItemHelper

    private var demoBookNames = arrayListOf<String>()

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //TODO: change the layout
        val root = inflater.inflate(R.layout.fragment_literature_browse, container, false)

        //todo: change topchart
        literatureTopChatRecyclerView = root.findViewById(R.id.literature_bangla_recycler_view) as RecyclerView

        /*getting demo book names data from string resources*/
        demoBookNames = resources.getStringArray(R.array.demo_book_names).toCollection(ArrayList())

        mContext = this.context!!

        ih = ItemHelper()
        ih.setGetDoubleCategoryBookSuccessListener(this)
        ih.setGetDoubleCategoryBookFailureListener(this)


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
        button_see_lit_bangla.setOnClickListener {
            val action = LiteratureBrowseFragmentDirections.actionLiteratureBrowseFragmentToSeeAllBoooksFragment("Literature", "Bangla")
            navController.navigate(action)
        }
        button_see_lit_eng.setOnClickListener {
            val action = LiteratureBrowseFragmentDirections.actionLiteratureBrowseFragmentToSeeAllBoooksFragment("Literature", "English")
            navController.navigate(action)
        }
    }


    private fun initRecyclerView() {
        ih.getDoubleCategoryBook("Literature", "Bangla")
        ih.getDoubleCategoryBook("Literature", "English")
    }

    override fun onCatClick(name: String?) {
        Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onExit() {
        initRecyclerView()
    }

    override fun getDoubleCategoryBookSuccess(bookArray: ArrayList<Book>, cat2: String) {
        val adapter = GroupAdapter<GroupieViewHolder>()
        lateinit var recycler: RecyclerView

        //Finding device width to decide whether to choose GridLayout spanCount
        var gridSpanCount:Int
        val dm = DisplayMetrics()
        activity?.windowManager!!.defaultDisplay.getMetrics(dm)
        var screenWidth = (dm.widthPixels.toDouble() / dm.xdpi).toInt()
        if(screenWidth < 5) {
            gridSpanCount = 2
        }else{
            gridSpanCount = 4
        }

        when (cat2) {
            "Bangla" -> {
                recycler = literature_bangla_recycler_view
                if (bookArray.size <= gridSpanCount ) literature_bangla_recycler_view.layoutParams.height =
                    resources.getDimension(R.dimen.recyclerview_parent_custom_height_books).toInt()
            }
            "English" -> {
                recycler = literature_english_recycler_view
                if (bookArray.size <= gridSpanCount ) literature_english_recycler_view.layoutParams.height =
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

    override fun getDoubleCategoryBookFailure() {
        Toast.makeText(mContext, "Failed to get books", Toast.LENGTH_SHORT).show()
    }
}
