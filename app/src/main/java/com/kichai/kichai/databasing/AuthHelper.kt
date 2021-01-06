package com.kichai.kichai.databasing

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.kichai.kichai.R
import com.kichai.kichai.data.User
import java.util.concurrent.TimeUnit

class AuthHelper(var context: Context?) {

    private lateinit var mOnCustomerRegistrationSuccessListener: onCustomerRegistrationSuccessListener
    private lateinit var mOnCustomerRegistrationFailureListener: onCustomerRegistrationFailureListener
    private lateinit var mOnCustomerLoginSuccessListener: onCustomerLoginSuccessListener
    private lateinit var mOnCustomerLoginFailureListener: onCustomerLoginFailureListener
    private lateinit var mOnDeliverymanLoginSuccessListener: onDeliverymanLoginSuccessListener
    private lateinit var mOnDeliverymanLoginFailureListener: onDeliverymanLoginFailureListener

    lateinit var layoutInflater: LayoutInflater

    fun authWithPhoneNumber(phoneNum: String, loginRegisterType: Int, usertype: Int) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNum,
            60L,
            TimeUnit.SECONDS,
            context!! as Activity,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    //When device finishes verification automatically
                    FirebaseAuth.getInstance().signInWithCredential(p0)
                        .addOnSuccessListener { auth ->
                            if ( loginRegisterType == 0 ) //registration
                            {
                                //Call the interface that directs to the details enlisting screen
                                val alertDialog = AlertDialog.Builder(context).create()
                                layoutInflater = LayoutInflater.from(context)
                                val alertDialogView = layoutInflater.inflate(R.layout.details_dialog, null)
                                alertDialogView.findViewById<Button>(R.id.done_button).setOnClickListener {
                                    if ( alertDialogView.findViewById<EditText>(R.id.name_text).text.toString().isNotEmpty() )
                                    {
                                        val user = User(alertDialogView.findViewById<EditText>(R.id.name_text).text.toString(), phoneNum)
                                        FirebaseFirestore.getInstance().collection("customers").document(auth.user!!.uid).set(user)
                                            .addOnSuccessListener {
                                                mOnCustomerRegistrationSuccessListener.customerRegistrationSuccess()
                                            }
                                            .addOnFailureListener {
                                                mOnCustomerRegistrationFailureListener.customerRegistrationFailure()
                                            }
                                    }
                                    else
                                    {
                                        Toast.makeText(context, "Please enter a valid username", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                alertDialog.setView(alertDialogView)
                                alertDialog.show()
                            }
                            else if ( loginRegisterType == 1 ) //login
                            {
                                //Call the interface that directs to the relevant homepage
                                if ( usertype == 0 ) //customer
                                {
                                    //Direct to customer homescreen
                                    mOnCustomerLoginSuccessListener.customerLoginSuccess()
                                }
                                else if ( usertype == 1 ) //deliveryman
                                {
                                    //Direct to deliveryman homescreen
                                    mOnDeliverymanLoginSuccessListener.deliverymanLoginSuccess()
                                }
                            }
                        }
                        .addOnFailureListener {
                            //Show some toast message
                            Toast.makeText(context, "Failed to sign in with credentials. Error: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    //When verification fails, show some toast message
                    Toast.makeText(context, "Verification failed. Error: ${p0.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(
                    verificationId: String,
                    p1: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verificationId, p1)
                    val alertDialog = AlertDialog.Builder(context).create()
                    layoutInflater = LayoutInflater.from(context)
                    val alertDialogView = layoutInflater.inflate(R.layout.otp_dialog, null)
                    alertDialogView.findViewById<Button>(R.id.verify_button).setOnClickListener {
                        if ( alertDialogView.findViewById<EditText>(R.id.otp_text).text.toString().isNotEmpty() )
                        {
                            val credential = PhoneAuthProvider.getCredential(verificationId, alertDialogView.findViewById<EditText>(R.id.otp_text).text.toString())
                            //Take the OTP code and then create a PhoneAuthCredential object with it
                            FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnSuccessListener { auth ->
                                    if ( loginRegisterType == 0 ) //registration
                                    {
                                        //Call the interface that directs to the details enlisting screen
                                        val alertDialog2 = AlertDialog.Builder(context).create()
                                        layoutInflater = LayoutInflater.from(context)
                                        val alertDialogView2 = layoutInflater.inflate(R.layout.details_dialog, null)
                                        alertDialogView2.findViewById<Button>(R.id.done_button).setOnClickListener {
                                            if ( alertDialogView2.findViewById<EditText>(R.id.name_text).text.toString().isNotEmpty() )
                                            {
                                                val user = User(alertDialogView2.findViewById<EditText>(R.id.name_text).text.toString(), phoneNum)
                                                user.uid = auth.user!!.uid
                                                FirebaseFirestore.getInstance().collection("customer").document(auth.user!!.uid).set(user)
                                                    .addOnSuccessListener {
                                                        mOnCustomerRegistrationSuccessListener.customerRegistrationSuccess()
                                                    }
                                                    .addOnFailureListener {
                                                        mOnCustomerRegistrationFailureListener.customerRegistrationFailure()
                                                    }
                                            }
                                            else
                                            {
                                                Toast.makeText(context, "Please enter a valid username", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                        alertDialog2.setView(alertDialogView2)
                                        alertDialog2.show()
                                    }
                                    else if ( loginRegisterType == 1 ) //login
                                    {
                                        //Call the interface that directs to the relevant homepage
                                        if ( usertype == 0 ) //customer
                                        {
                                            //Direct to customer homescreen
                                            mOnCustomerLoginSuccessListener.customerLoginSuccess()
                                        }
                                        else if ( usertype == 1 ) //deliveryman
                                        {
                                            //Direct to deliveryman homescreen
                                            mOnDeliverymanLoginSuccessListener.deliverymanLoginSuccess()
                                        }
                                    }
                                }
                                .addOnFailureListener {
                                    //Show some toast message
                                    Toast.makeText(context, "Failed to sign in with credentials. Error: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                        else
                        {
                            Toast.makeText(context, "Please enter an OTP code", Toast.LENGTH_SHORT).show()
                        }
                    }
                    alertDialog.setView(alertDialogView)
                    alertDialog.show()
                }
            })
    }

    interface onCustomerRegistrationSuccessListener{
        fun customerRegistrationSuccess()
    }

    interface onCustomerRegistrationFailureListener{
        fun customerRegistrationFailure()
    }

    interface onCustomerLoginSuccessListener{
        fun customerLoginSuccess()
    }

    interface onCustomerLoginFailureListener{
        fun customerLoginFailure()
    }

    interface onDeliverymanLoginSuccessListener{
        fun deliverymanLoginSuccess()
    }

    interface onDeliverymanLoginFailureListener{
        fun deliverymanLoginFailure()
    }

    fun setOnCustomerRegistrationSuccessListener(lol: onCustomerRegistrationSuccessListener)
    {
        this.mOnCustomerRegistrationSuccessListener = lol
    }

    fun setOnCustomerRegistrationFailureListener(lol: onCustomerRegistrationFailureListener)
    {
        this.mOnCustomerRegistrationFailureListener = lol
    }

    fun setOnCustomerLoginSuccessListener(lol: onCustomerLoginSuccessListener)
    {
        this.mOnCustomerLoginSuccessListener = lol
    }

    fun setOnCustomerLoginFailureListener(lol: onCustomerLoginFailureListener)
    {
        this.mOnCustomerLoginFailureListener = lol
    }

    fun setOnDeliverymanLoginSuccessListener(lol: onDeliverymanLoginSuccessListener)
    {
        this.mOnDeliverymanLoginSuccessListener = lol
    }

    fun setOnDeliverymanLoginFailureListener(lol: onDeliverymanLoginFailureListener)
    {
        this.mOnDeliverymanLoginFailureListener = lol
    }
}