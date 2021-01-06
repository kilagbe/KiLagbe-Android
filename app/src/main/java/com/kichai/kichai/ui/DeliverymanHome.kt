package com.kichai.kichai.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.kichai.kichai.R
import kotlinx.android.synthetic.main.activity_deliveryman_home.*

class DeliverymanHome : AppCompatActivity() {

    lateinit var nav: NavigationView
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliveryman_home)
        nav = findViewById(R.id.nav_menu)
        drawerLayout = findViewById(R.id.drawer)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navController = findNavController(R.id.nav_host_fragment)

        nav.setupWithNavController(navController)

        val drawerToggle: ActionBarDrawerToggle= object :ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            (R.string.navigation_drawer_open)   ,
            (R.string.navigation_drawer_close)
        )
        {

        }
        drawerToggle.isDrawerIndicatorEnabled=true
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

    }

}
