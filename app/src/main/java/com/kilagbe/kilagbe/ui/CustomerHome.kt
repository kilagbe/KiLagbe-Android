package com.kilagbe.kilagbe.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.kilagbe.kilagbe.R
import com.kilagbe.kilagbe.ui.cart.CartFragment
import com.kilagbe.kilagbe.ui.customer_order_fragment.CustomerCurrentOrdersFragment
import com.kilagbe.kilagbe.ui.home.HomeFragment
import com.kilagbe.kilagbe.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_customer_home.*

class CustomerHome : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration
//
//    lateinit var homeFragment: HomeFragment
//    lateinit var cartFragment: CartFragment
//    lateinit var profileFragment: ProfileFragment
//    lateinit var ordersFragment: CustomerCurrentOrdersFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_home)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_profile,
                R.id.navigation_home,
                R.id.navigation_orders,
                R.id.navigation_cart
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        drawer_view.setNavigationItemSelectedListener(this)
//        homeFragment= HomeFragment()
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.nav_host_fragment,homeFragment)
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            .commit()

    }

//This is what I tried to do to connect the fragments

//  override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.navigation_home ->{
//                homeFragment= HomeFragment()
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.nav_host_fragment,homeFragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//
//            }
//            R.id.navigation_cart ->{
//                cartFragment= CartFragment()
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.nav_host_fragment,cartFragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//
//            }
//            R.id.navigation_profile ->{
//                profileFragment= ProfileFragment()
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.navigation_profile,profileFragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//
//            }
//            R.id.navigation_orders->{
//                ordersFragment= CustomerCurrentOrdersFragment()
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.nav_host_fragment,ordersFragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//            }
//
//        }
//      drawer_layout.closeDrawer(GravityCompat.START)
//        return true
//    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

//    override fun onBackPressed() {
//        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
//            drawer_layout.closeDrawer(GravityCompat.START)
//        }
//        else
//        {
//            super.onBackPressed()
//
//        }
//    }


}
