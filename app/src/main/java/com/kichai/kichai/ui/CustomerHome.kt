package com.kichai.kichai.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.kichai.kichai.R
import com.kichai.kichai.data.Book
import com.kichai.kichai.databasing.ItemHelper
import com.kichai.kichai.tools.AutoCompleteTextViewOnItemClickListener

class CustomerHome : AppCompatActivity(), ItemHelper.getAllBooksSuccessListener, ItemHelper.getAllBooksFailureListener, AutoCompleteTextViewOnItemClickListener.onExitListener {

    lateinit var nav: NavigationView
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var actv: AutoCompleteTextView
    lateinit var ih: ItemHelper

    private var suggestions = mutableListOf<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_home)

        ih = ItemHelper()
        ih.setGetAllBooksSuccessListener(this)
        ih.setGetAllBooksFailureListener(this)

        ih.getAllBooks()

        nav = findViewById(R.id.nav_menu)
        drawerLayout = findViewById(R.id.drawer)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)


        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navController = findNavController(R.id.nav_host_fragment)

        nav.setupWithNavController(navController)
    }

    override fun getAllBooksSuccess(bookArray: ArrayList<Book>) {
        suggestions = bookArray
        actv = findViewById(R.id.actv_customer)
        val adapter = ArrayAdapter<Book>(this, android.R.layout.select_dialog_item, suggestions)
        actv.threshold = 1
        val listener = AutoCompleteTextViewOnItemClickListener(this)
        listener.setOnExitListener(this)
        actv.setOnItemClickListener(listener)
        actv.setAdapter(adapter)
    }

    override fun getAllBooksFailure() {
        Toast.makeText(this, "Could not fetch all books", Toast.LENGTH_SHORT).show()
    }

    override fun onExit() {
        ih.getAllBooks()
    }
}
