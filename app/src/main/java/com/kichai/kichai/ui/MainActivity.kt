package com.kichai.kichai.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.kichai.kichai.R
import com.kichai.kichai.data.User
import com.kichai.kichai.databasing.ProfileHelper
import com.kichai.kichai.ui.auth.LoginActivity

class MainActivity : AppCompatActivity(), ProfileHelper.getCustomerSuccessListener, ProfileHelper.getCustomerFailureListener, ProfileHelper.getDeliverymanSuccessListener, ProfileHelper.getDeliverymanFailureListener{

    lateinit var ph: ProfileHelper
    private val SPLASH_TIME_OUT:Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        val logo : ImageView = findViewById(R.id.splash_logo)
        logo.startAnimation(anim)
        Handler().postDelayed({
            ph = ProfileHelper()
            ph.setGetDeliverymanFailureListener(this)
            ph.setGetDeliverymanSuccessListener(this)
            ph.setGetCustomerFailureListener(this)
            ph.setGetCustomerSuccessListener(this)
            checkUser()
        },SPLASH_TIME_OUT)



    }

    fun checkUser()
    {
        val uid = ph.getUid()
        if ( uid == null )
        {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.and(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        else
        {
            ph.getCustomer(uid)
            ph.getDeliveryman(uid)
        }
    }

    override fun getCustomerSuccess(customer: User) {
        val intent = Intent(this, CustomerHome::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.and(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun getCustomerFailure() {
        Log.d("MainActivity", "Not a customer")
    }

    override fun getDeliverymanFailure() {
        Log.d("MainActivity", "Not a deliveryman")
    }

    override fun getDeliverymanSuccess(deliveryman: User) {
        val intent = Intent(this, DeliverymanHome::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.and(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}