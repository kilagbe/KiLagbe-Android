package com.kichai.kichai.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hbb20.CountryCodePicker
import com.kichai.kichai.R
import com.kichai.kichai.databasing.AuthHelper
import com.kichai.kichai.ui.CustomerHome
import com.kichai.kichai.ui.DeliverymanHome
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthHelper.onCustomerRegistrationSuccessListener, AuthHelper.onCustomerRegistrationFailureListener, AuthHelper.onCustomerLoginSuccessListener, AuthHelper.onDeliverymanLoginSuccessListener, CountryCodePicker.OnCountryChangeListener {

    lateinit var ah: AuthHelper
    private var ccp: CountryCodePicker? = null
    private var countryCode:String? = null
    private var countryName:String? = null

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
                countryCode = ccp!!.selectedCountryCode
                if ( usertype_selector.checkedRadioButtonId == R.id.customer_radio )
                    ah.authWithPhoneNumber("+" + countryCode + phone_text.text.toString(), 1, 0)
                else if ( usertype_selector.checkedRadioButtonId == R.id.deliveryman_radio )
                    ah.authWithPhoneNumber("+" + countryCode + phone_text.text.toString(), 1, 1)
            }
        }
        ccp = findViewById(R.id.countryCodePicker)
        ccp!!.setOnCountryChangeListener(this)
    }

    override fun onCountrySelected() {
        countryCode = ccp!!.selectedCountryCode
        countryName = ccp!!.selectedCountryName
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
