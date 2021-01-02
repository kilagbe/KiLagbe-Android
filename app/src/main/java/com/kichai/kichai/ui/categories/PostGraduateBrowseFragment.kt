package com.kichai.kichai.ui.categories

import android.annotation.SuppressLint
import android.app.ActionBar
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
import kotlinx.android.synthetic.main.fragment_post_graduate_browse.*


class PostGraduateBrowseFragment : Fragment(), RecycleViewAdapter.OnCatListener,
    ItemOnClickListener.onExitListener, ItemHelper.getDoubleCategoryBookSuccessListener,
    ItemHelper.getDoubleCategoryBookFailureListener {


    lateinit var mContext: Context
    lateinit var ih: ItemHelper

    private lateinit var postgradMedicalRecyclerView: RecyclerView
    private lateinit var postgradEngineeringRecyclerView: RecyclerView
    private lateinit var postgradMbaRecyclerView: RecyclerView

    private lateinit var navController: NavController

    private var demoBookNames = arrayListOf<String>()


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_post_graduate_browse, container, false)


        postgradMedicalRecyclerView =
            root.findViewById(R.id.postgraduate_medical_recycler_view) as RecyclerView
        postgradEngineeringRecyclerView =
            root.findViewById(R.id.postgraduate_engineering_recycler_view) as RecyclerView
        postgradMbaRecyclerView =
            root.findViewById(R.id.postgraduate_mba_recycler_view) as RecyclerView

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
        button_see_pg_med.setOnClickListener {
            val action = PostGraduateBrowseFragmentDirections.actionPostGraduateBrowseFragmentToSeeAllBoooksFragment("Postgraduate","Medical")
            navController.navigate(action)
        }
        button_see_pg_eng.setOnClickListener {
            val action = PostGraduateBrowseFragmentDirections.actionPostGraduateBrowseFragmentToSeeAllBoooksFragment("Postgraduate","Engineering")
            navController.navigate(action)
        }
        button_see_pg_mba.setOnClickListener {
            val action = PostGraduateBrowseFragmentDirections.actionPostGraduateBrowseFragmentToSeeAllBoooksFragment("Postgraduate","MBA")
            navController.navigate(action)
        }
    }


    private fun initRecyclerView() {

        ih.getDoubleCategoryBook("Postgraduate", "Medical")
        ih.getDoubleCategoryBook("Postgraduate", "Engineering")
        ih.getDoubleCategoryBook("Postgraduate", "MBA")
    }


    override fun onCatClick(name: String?) {
        Toast.makeText(this.context, name, Toast.LENGTH_SHORT).show()
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
            "Medical" -> {
                recycler = postgradMedicalRecyclerView

                if (bookArray.size <= gridSpanCount ) postgradMedicalRecyclerView.layoutParams.height =
                    resources.getDimension(R.dimen.recyclerview_parent_custom_height_books).toInt()
            }
            "Engineering" -> {
                recycler = postgradEngineeringRecyclerView
                if (bookArray.size <= gridSpanCount) postgradEngineeringRecyclerView.layoutParams.height =
                    resources.getDimension(R.dimen.recyclerview_parent_custom_height_books).toInt()
            }
            "MBA" -> {
                recycler = postgradMbaRecyclerView
                if (bookArray.size <= gridSpanCount ) postgradMbaRecyclerView.layoutParams.height =
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

//        recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL ,false)


        val listener = ItemOnClickListener(mContext)
        listener.setOnExitListener(this)
        adapter.setOnItemClickListener(listener)
    }

    override fun getDoubleCategoryBookFailure() {
        Toast.makeText(mContext, "Failed to get books", Toast.LENGTH_SHORT).show()
    }
}
