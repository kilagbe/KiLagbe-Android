package com.kichai.kichai.ui.categories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.postDelayed
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
import kotlinx.android.synthetic.main.fragment_undergraduate_browse.*

class UndergraduateBrowseFragment : Fragment(), RecycleViewAdapter.OnCatListener,
    ItemOnClickListener.onExitListener, ItemHelper.getSomeDoubleCategoryBookSuccessListener,
    ItemHelper.getSomeDoubleCategoryBookFailureListener {


    private lateinit var undergradMedicalRecyclerView: RecyclerView
    private lateinit var undergradEngineeringRecyclerView: RecyclerView
    private lateinit var undergradBbaRecyclerView: RecyclerView

    private lateinit var navController: NavController

    lateinit var mContext: Context
    lateinit var ih: ItemHelper

    private var demoBookNames = arrayListOf<String>()


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_undergraduate_browse, container, false)


        undergradMedicalRecyclerView =
            root.findViewById(R.id.undergraduate_medical_recycler_view) as RecyclerView
        undergradEngineeringRecyclerView =
            root.findViewById(R.id.undergraduate_engineering_recycler_view) as RecyclerView
        undergradBbaRecyclerView =
            root.findViewById(R.id.undergraduate_bba_recycler_view) as RecyclerView

        /*getting demo book names data from string resources*/
        demoBookNames = resources.getStringArray(R.array.demo_book_names).toCollection(ArrayList())

        mContext = this.context!!

        setupLoading()

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

//            navController.navigate(R.id.action_undergraduateBrowseFragment_to_seeAllBoooksFragment)

//            for these classes, check java generated folder...search in youtube navigation controller safe args
        button_see_ug_med.setOnClickListener {
            val action =
                UndergraduateBrowseFragmentDirections.actionUndergraduateBrowseFragmentToSeeAllBoooksFragment(
                    "Undergraduate",
                    "Medical"
                )
            navController.navigate(action)
        }
        button_see_ug_eng.setOnClickListener {
            val action =
                UndergraduateBrowseFragmentDirections.actionUndergraduateBrowseFragmentToSeeAllBoooksFragment(
                    "Undergraduate",
                    "Engineering"
                )
            navController.navigate(action)
        }
        button_see_ug_bba.setOnClickListener {
            val action =
                UndergraduateBrowseFragmentDirections.actionUndergraduateBrowseFragmentToSeeAllBoooksFragment(
                    "Undergraduate",
                    "BBA"
                )
            navController.navigate(action)
        }

    }

    private fun initRecyclerView() {
        ih.getSomeDoubleCategoryBook("Undergraduate", "Medical", 6)
        ih.getSomeDoubleCategoryBook("Undergraduate", "Engineering", 6)
        ih.getSomeDoubleCategoryBook("Undergraduate", "BBA", 6)

    }


    override fun onCatClick(name: String?) {
        Toast.makeText(this.context, name, Toast.LENGTH_SHORT).show()
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
        if (screenWidth < 5) {
            gridSpanCount = 2
        } else {
            gridSpanCount = 4
        }

        when (cat2) {
            "Medical" -> {
                recycler = undergradMedicalRecyclerView
                if (bookArray.size <= gridSpanCount) undergradMedicalRecyclerView.layoutParams.height =
                    resources.getDimension(R.dimen.recyclerview_parent_custom_height_books).toInt()
            }
            "Engineering" -> {
                recycler = undergradEngineeringRecyclerView
                if (bookArray.size <= gridSpanCount) undergradEngineeringRecyclerView.layoutParams.height =
                    resources.getDimension(R.dimen.recyclerview_parent_custom_height_books).toInt()
            }
            "BBA" -> {
                recycler = undergradBbaRecyclerView
                if (bookArray.size <= gridSpanCount) undergradBbaRecyclerView.layoutParams.height =
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

    override fun getSomeDoubleCategoryBookFailure() {
        Toast.makeText(mContext, "Failed to get books", Toast.LENGTH_SHORT).show()
    }

    private fun setupLoading(){
        val loadingDialog = LoadingDialog(mContext)
        loadingDialog.startLoadingDialog()
    }
}
