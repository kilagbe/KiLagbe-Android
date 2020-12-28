package com.kichai.kichai.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kichai.kichai.R
import com.kichai.kichai.databasing.AuthHelper
import com.kichai.kichai.ui.CustomerHome
import com.kichai.kichai.ui.DeliverymanHome
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.phone_text
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity(), AuthHelper.onCustomerRegistrationSuccessListener, AuthHelper.onCustomerRegistrationFailureListener, AuthHelper.onCustomerLoginSuccessListener, AuthHelper.onDeliverymanLoginSuccessListener {

    lateinit var ah: AuthHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ah = AuthHelper(this!!)
        ah.setOnCustomerRegistrationSuccessListener(this)
        ah.setOnCustomerRegistrationFailureListener(this)
        ah.setOnCustomerLoginSuccessListener(this)
        ah.setOnDeliverymanLoginSuccessListener(this)

        register_text.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.and(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        login_button.setOnClickListener {
            if (phone_text.text.toString().isNotEmpty()) {
                if ( usertype_selector.checkedRadioButtonId == R.id.customer_radio )
                    ah.authWithPhoneNumber(phone_text.text.toString(), 1, 0)
                else if ( usertype_selector.checkedRadioButtonId == R.id.deliveryman_radio )
                    ah.authWithPhoneNumber(phone_text.text.toString(), 1, 1)
            }
        }
    }

    override fun customerRegistrationSuccess() {
        val intent = Intent(this, CustomerHome::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.and(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun customerRegistrationFailure() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }

    override fun customerLoginSuccess() {
        val intent = Intent(this, CustomerHome::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.and(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun deliverymanLoginSuccess() {
        val intent = Intent(this, DeliverymanHome::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.and(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
